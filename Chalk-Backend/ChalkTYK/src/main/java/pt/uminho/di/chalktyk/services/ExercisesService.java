package pt.uminho.di.chalktyk.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.exercises.*;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.repositories.ExerciseDAO;
import pt.uminho.di.chalktyk.repositories.ExerciseResolutionDAO;
import pt.uminho.di.chalktyk.repositories.ExerciseRubricDAO;
import pt.uminho.di.chalktyk.repositories.ExerciseSolutionDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.ServiceException;
import pt.uminho.di.chalktyk.services.exceptions.ForbiddenException;

import java.util.*;
import java.util.stream.Collectors;

@Service("exercisesService")
public class ExercisesService implements IExercisesService{
    private final ISpecialistsService specialistsService;
    private final IStudentsService studentsService;
    private final ICoursesService coursesService;
    private final IInstitutionsService institutionsService;
    private final ITagsService iTagsService;
    private final ExerciseDAO exerciseDAO;
    private final ExerciseSolutionDAO exerciseSolutionDAO;
    private final ExerciseRubricDAO exerciseRubricDAO;
    private final ExerciseResolutionDAO exerciseResolutionDAO;
    @PersistenceContext
    private final EntityManager entityManager;

    public ExercisesService(ISpecialistsService specialistsService, IStudentsService studentsService, ExerciseDAO exerciseDAO, ICoursesService coursesService,
                            IInstitutionsService institutionsService, ITagsService iTagsService, ExerciseSolutionDAO exerciseSolutionDAO,
                            ExerciseRubricDAO exerciseRubricDAO, EntityManager entityManager,
                            ExerciseResolutionDAO exerciseResolutionDAO) {
        this.specialistsService = specialistsService;
        this.studentsService = studentsService;
        this.exerciseDAO = exerciseDAO;
        this.coursesService = coursesService;
        this.institutionsService = institutionsService;
        this.iTagsService = iTagsService;
        this.exerciseSolutionDAO = exerciseSolutionDAO;
        this.exerciseRubricDAO = exerciseRubricDAO;
        this.entityManager = entityManager;
        this.exerciseResolutionDAO = exerciseResolutionDAO;
    }

    /**
     * Get Exercise by ID
     *
     * @param exerciseId of the exercise
     * @return exercise from the given ID.
     **/
    private Exercise _getExerciseById(String exerciseId) throws NotFoundException {
        Optional<Exercise> obj = exerciseDAO.findById(exerciseId);
        if (obj.isPresent()){
            return obj.get();
        }
        else
            throw new NotFoundException("Couldn't get exercise with id: " + exerciseId);
    }

    /**
     * Get Exercise by ID. Loads everything except solution and rubric.
     *
     * @param exerciseId of the exercise
     * @return concrete exercise with the given ID
     **/
    @Override
    public Exercise getExerciseById(String exerciseId) throws NotFoundException {
        // gets tags
        Exercise exercise = exerciseDAO.loadByIdWithoutSolutionAndRubric(exerciseId).orElse(null);
        if(exercise == null)
            throw new NotFoundException("Exercise does not exist.");
        return exercise;
    }

    /**
     * Get Exercise by ID
     *
     * @param exerciseId of the exercise
     * @return true if exercise exists, false otherwise
     **/
    @Override
    public boolean exerciseExists(String exerciseId) {
        return exerciseDAO.existsById(exerciseId);
    }

    /**
     * Creates an exercise.
     *
     * @param exercise body of the exercise to be created. Regarding the metadata should
     *                 contain, at least, the specialist identifier but may contain the institutionId and courseId
     * @param solution
     * @param rubric
     * @param tagsIds
     * @return new exercise identifier
     * @throws BadInputException if the exercise is not formed correctly
     */

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String createExercise(Exercise exercise, ExerciseSolution solution, ExerciseRubric rubric, List<String> tagsIds) throws BadInputException {
        if (exercise == null)
            throw new BadInputException("Cannot create exercise: Exercise is null");

        // Check if specialist is valid
        Specialist specialist;
        try {
            specialist = specialistsService.getSpecialistById(exercise.getSpecialistId());
            exercise.setSpecialist(specialist);
        } catch (NotFoundException nfe) {
            throw new BadInputException("Cannot create exercise: Specialist does not exist.");
        }

        // Check if tags are valid
        Set<Tag> tags = new HashSet<>();
        if(tagsIds != null) {
            for (String id : tagsIds) {
                Tag tag = iTagsService.getTagById(id);
                if (tag == null)
                    throw new BadInputException("Cannot create exercise: There is not tag with id \"" + id + "\".");
                tags.add(tag);
            }
        }
        exercise.setTags(tags);

        // Check if visibility is valid
        Visibility visibility = exercise.getVisibility();
        if (visibility == null)
            throw new BadInputException("Cannot create exercise: Visibility cant be null");

        // Checks if specialist exists and gets the institution where it belongs
        Institution institution = specialist.getInstitution();
        // the exercise's institution is the same has its owner
        exercise.setInstitution(institution);

        // If the specialist that owns the exercise does not have an institution,
        // then the exercise's visibility cannot be set to institution.
        if (visibility == Visibility.INSTITUTION && institution == null)
            throw new BadInputException("Cannot create exercise: cannot set visibility to INSTITUTION");

        // Check if course is valid
        String courseId = exercise.getCourseId();
        try {
            if (courseId != null) {
                // the owner of the exercise must be associated with the course
                if (!coursesService.checkSpecialistInCourse(courseId, exercise.getSpecialistId()))
                    throw new BadInputException("Cannot create exercise: specialist does not belong to the given course.");
                else {
                    Course course = coursesService.getCourseById(courseId);
                    exercise.setCourse(course);
                }
            }
            else exercise.setCourse(null);
        } catch (NotFoundException nfe) {
            throw new BadInputException("Cannot create exercise: course not found.");
        }

        // Cannot set visibility to COURSE without a course associated
        if (courseId == null && visibility == Visibility.COURSE)
            throw new BadInputException("Cannot create exercise: cannot set visibility to COURSE without a course associated.");

        // Prevent overrides
        exercise.setId(null);
        exercise.setRubric(null);
        exercise.setSolution(null);

        // Check exercise properties
        exercise.verifyInsertProperties();

        // Check solution
        if(solution != null)
            _createExerciseSolution(exercise, solution);

        // Check rubric
        if(rubric != null)
            _createExerciseRubric(exercise, rubric);

        // persists the exercise in database
        exercise = exerciseDAO.save(exercise);

        return exercise.getId();
    }

    /**
     * Delete exercise by id. If the exercise has copies, it won't be deleted,
     * but instead, its visibility will be set to "deleted",
     * the specialist, course and institution will be set to null.
     * Calling method should be transactional, to rollback changes in case of
     * an exception.
     *
     * @param exerciseId identifier of the exercise
     * @throws NotFoundException     if the exercise was not found
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteExerciseById(String exerciseId) throws NotFoundException {
        // Checks if exercise exists
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if (exercise == null)
            throw new NotFoundException("Could not delete exercise: Exercise does not exist.");

        // Checks existence of resolutions
        deleteExerciseResolutions(exerciseId);

        // Delete exercise from the database
        exerciseDAO.deleteById(exerciseId);

        // Delete rubric if exists
        if (exercise.getSolutionId() != null)
            exerciseSolutionDAO.deleteById(exercise.getSolutionId());

        // Delete solution if exists
        if (exercise.getRubricId() != null)
            exerciseRubricDAO.deleteById(exercise.getRubricId());
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String duplicateExerciseByIdNoSpecialist(String exerciseId, Visibility visibility) throws NotFoundException {
        return _duplicateExerciseById(null, exerciseId, null, visibility);
    }

    /**
     * Duplicates the exercise that contains the given identifier.
     * The id of the specialist, and if existent, the institution identifier
     * is added to the new exercise metadata. The visibility of the new exercise is
     * set to private, and is not associated with any course.
     *
     * @param specialistId identifier of the specialist that wants to own the exercise
     * @param exerciseId   exercise identifier
     * @return new exercise identifier
     * @throws NotFoundException     if the exercise was not found
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String duplicateExerciseById(String specialistId, String exerciseId) throws NotFoundException {
        return duplicateExerciseById(specialistId, exerciseId, null, null);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String duplicateExerciseById(String specialistId, String exerciseId, String courseId, Visibility visibility) throws NotFoundException {
        // checks if the specialist exists
        Specialist specialist = specialistsService.getSpecialistById(specialistId);
        return _duplicateExerciseById(specialist, exerciseId, courseId, visibility);
    }

    private String _duplicateExerciseById(Specialist specialist, String exerciseId, String courseId, Visibility visibility) throws NotFoundException {
        // gets specialists institution
        String institutionId = null;
        Institution institution = specialist != null ? institutionsService.getSpecialistInstitution(specialist.getId()) : null;

        Exercise source = _getExerciseById(exerciseId);

        // duplicates rubric
        ExerciseRubric rubric = exerciseRubricDAO.findByExerciseId(exerciseId).orElse(null),
                rubricCopy = null;

        if(rubric != null) {
            rubricCopy = rubric.clone();
            rubricCopy.setId(null);
            rubricCopy = exerciseRubricDAO.save(rubricCopy);
        }

        // duplicates solution
        ExerciseSolution solution = exerciseSolutionDAO.findByExerciseId(exerciseId).orElse(null),
                solutionCopy = null;

        if(solution != null) {
            solutionCopy = new ExerciseSolution();
            solutionCopy.setData(solution.getData().clone());
            solutionCopy.setId(null);
            solutionCopy = exerciseSolutionDAO.save(solutionCopy);
        }

        // duplicates data related information
        // and sets other information
        Exercise copy = source.cloneExerciseDataOnly();
        copy.setVisibility(visibility == null ? Visibility.PRIVATE : visibility);
        copy.setTags(new HashSet<>(source.getTags()));
        copy.setInstitution(institution);
        copy.setSpecialist(specialist);
        copy.setCourse(courseId == null ? null : coursesService.getCourseById(courseId));
        copy.setRubric(rubricCopy);
        copy.setSolution(solutionCopy);

        // persists exercise
        copy.setId(null); // id needs to be set to null
        copy = exerciseDAO.save(copy);

        return copy.getId();
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String updateAllOnExercise(String exerciseId, Exercise newBody, ExerciseRubric rubric, ExerciseSolution solution, List<String> tagsIds, Visibility visibility) throws NotFoundException, BadInputException {
        // gets exercise using the identifier, or throws not found exception
        Exercise exercise = _getExerciseById(exerciseId);

        // flag that indicates if the exercise was duplicated,
        // since an exercise with resolutions cannot be duplicated.
        // The exercise is duplicated, and the copy is updated instead.
        boolean duplicated = false;

        // checks if body is valid and updates it in the case it is valid
        if(newBody != null) {
            //if rubric or solution are not null, then they will verify exercise
            exercise = _updateExerciseBody(exercise, newBody, rubric != null, solution != null);
            if (!exercise.getId().equals(exerciseId)) { // 'true' if the exercise was duplicated
                duplicated = true;
                exerciseId = exercise.getId();
            }
        }

        if(rubric!=null)
            _createExerciseRubric(exercise,rubric); // this method is used to create/update the rubric of an exercise.
        if(solution!=null)
            _createExerciseSolution(exercise,solution); // this method is used to create/update the solution of an exercise.
        if(tagsIds!=null)
            _updateExerciseTags(exercise,tagsIds);
        if(visibility!=null)
            _updateExerciseVisibility(exercise,visibility);

        return duplicated ? exerciseId : null; // returns the exercise identifier of the duplicate exercise, or 'null' if it was not duplicated
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Exercise updateExerciseBody(String exerciseId, Exercise newBody) throws NotFoundException, BadInputException {
        Exercise exercise = _getExerciseById(exerciseId);
        exercise = _updateExerciseBody(exercise, newBody, false, false);
        return exercise.getId().equals(exerciseId) ? null : exercise; // returns the exercise if the exercise was duplicated
    }

    /**
     * Updates an exercise body.
     * If the exercise already has resolutions,
     * then the exercise is duplicated,
     * and the updates are made to the copy.
     *
     * @param exercise exercise that will get the body updated
     * @param newBody    new exercise body
     * @return the updated exercise. Check the id to verify if the exercise returned is not a duplicate.
     * @throws BadInputException solution, rubric, institution or specialist ids where changed,
     *                           course was not found,
     *                           the rubric or solution don't belong to the new exercise body
     */
    private Exercise _updateExerciseBody(Exercise exercise, Exercise newBody, Boolean hasNewRubric, Boolean hasNewSolution) throws BadInputException {
        String exerciseId = exercise.getId();

        // Check new body properties
        if(newBody == null)
            throw new BadInputException("Could not update exercise body: body is null.");

        newBody.verifyInsertProperties();

        // If there are resolutions, the exercise body cannot be updated.
        // The exercise is duplicated. And the duplicate is updated.
        if(exerciseResolutionDAO.existsExerciseResolutions(exercise.getId())){
            try {
                // duplicates the exercise, and switches the exercise instance by the duplicated exercise instance
                exerciseId = duplicateExerciseById(exercise.getSpecialistId(), exerciseId, exercise.getCourseId(), exercise.getVisibility());
                exercise = _getExerciseById(exerciseId);
            } catch (NotFoundException ignored) {
                assert false; // should not get here.
            }
        }

        // copies exercise related data to the original exercise
        newBody.setId(exerciseId); // needed to avoid override attacks
        newBody.copyExerciseDataOnlyTo(exercise);

        boolean deleteRubric = false;
        ExerciseRubric rubric = exerciseRubricDAO.findByExerciseId(exerciseId).orElse(null);
        if(hasNewRubric){
            // if there is a new rubric, delete the current rubric 
            if(rubric != null){
                exercise.setRubric(null);
                deleteRubric = true;
            }
        }else{
            // else, check if the exercise and the rubric are compatible.
            if(rubric != null)
                exercise.verifyRubricProperties(rubric);
        }

        boolean deleteSolution = false;
        ExerciseSolution solution = exerciseSolutionDAO.findByExerciseId(exerciseId).orElse(null);
        if(hasNewSolution){
            // if there is a new solution, delete the current solution 
            if(solution != null) {
                exercise.setSolution(null);
                deleteSolution = true;
            }
        }else{
            // else, check if the exercise and the solution are compatible.
            if(solution != null)
                exercise.verifyResolutionProperties(solution.getData());
        }

        exerciseDAO.save(exercise);
        
        if(deleteRubric)
            exerciseRubricDAO.deleteById(rubric.getId());
        if(deleteSolution)
            exerciseSolutionDAO.deleteById(solution.getId());

        return exercise;
    }

    /**
     * Updates an exercise tags. Assumes exercise exists
     *
     * @param exerciseId   identifier of the exercise to be updated
     * @param tagsIds      new list of tags
     */
    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void updateExerciseTags(String exerciseId, List<String> tagsIds) throws BadInputException, NotFoundException {
        Exercise exercise = _getExerciseById(exerciseId);
        _updateExerciseTags(exercise, tagsIds);
    }

    private void _updateExerciseTags(Exercise exercise, List<String> tagsIds) throws BadInputException, NotFoundException{
        for (String id:tagsIds)
            if(iTagsService.getTagById(id)==null)
                throw new BadInputException("Cannot create exercise: There is not tag with id \"" + id + "\".");

        Set<String> setTagsIds = new HashSet<>(tagsIds);
        Set<Tag> tags = new HashSet<>();
        for (String tagId:setTagsIds){
            Tag tag = iTagsService.getTagById(tagId);
            if(tag==null)
                throw new NotFoundException("Tag with id "+tagId+" was not found");
            tags.add(tag);
        }
        exercise.setTags(tags);
        exerciseDAO.save(exercise);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateExerciseVisibility(String exerciseId, Visibility visibility) throws NotFoundException, BadInputException {
        Exercise exercise = _getExerciseById(exerciseId);
        _updateExerciseVisibility(exercise, visibility);
    }

    /**
     * Updates the exercise visibility.
     *
     * @param exercise   exercise. Assumed not null
     * @param visibility   new visibility
     * @throws BadInputException if there's something wrong with the text
     */
    private void _updateExerciseVisibility(Exercise exercise, Visibility visibility) throws BadInputException {
        // If the specialist that owns the exercise does not have an institution,
        // then the exercise's visibility cannot be set to institution.
        if (visibility == Visibility.INSTITUTION && exercise.getInstitution() == null)
            throw new BadInputException("Cannot set visibility to INSTITUTION because the exercise does not have any institution associated.");

        // Cannot set visibility to COURSE without a course associated
        if (visibility == Visibility.COURSE && exercise.getCourse() == null)
            throw new BadInputException("Cannot set visibility to COURSE because the exercise does not have a course associated.");
        exercise.setVisibility(visibility);
        exerciseDAO.save(exercise);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateExerciseCourse(String exerciseId, String courseId) throws NotFoundException, BadInputException {
        Exercise exercise = _getExerciseById(exerciseId);
        _updateExerciseCourse(exercise, courseId);
    }

    /**
     * Updates the exercise course.
     *
     * @param exercise   exercise. Assumed not null
     * @param courseId   identifier of the new course
     * @throws NotFoundException if the exercise or the course do no exist.
     */
    private void _updateExerciseCourse(Exercise exercise, String courseId) throws NotFoundException, BadInputException {
        Course course = null;
        if(courseId != null) {
            course = coursesService.getCourseById(courseId);
            if (!coursesService.checkSpecialistInCourse(courseId, exercise.getSpecialistId()))
                throw new BadInputException("Specialist does not belong to the course.");
        }
        exercise.setCourse(course);
        exerciseDAO.save(exercise);
    }

    /**
     * Retrieves the rubric of an exercise.
     * @param exerciseId exercise identifier
     * @return rubric of the exercise or null if it doesn't exist.
     * @throws NotFoundException if the exercise was not found
     */
    @Override
    public ExerciseRubric getExerciseRubric(String exerciseId) throws NotFoundException {
        if(!exerciseDAO.existsById(exerciseId))
            throw new NotFoundException("Cannot get exercise rubric: exercise does not exist.");
        
        return exerciseRubricDAO.findByExerciseId(exerciseId).orElse(null);
    }

    /**
     * Create an exercise rubric
     *
     * @param exerciseId exercise identifier
     * @param rubric     new rubric
     * @throws NotFoundException if the exercise was not found
     * @throws BadInputException if the rubric is not correctly formulated
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void createExerciseRubric(String exerciseId, ExerciseRubric rubric) throws NotFoundException, BadInputException {
        Exercise exercise = _getExerciseById(exerciseId);
        _createExerciseRubric(exercise, rubric);
    }

    /**
     * Used to create or update the rubric of an exercise.
     * @param exercise the exercise that should get its rubric updated. Cannot be null.
     * @param rubric the new rubric
     * @throws BadInputException if the rubric is not valid.
     */
    public void _createExerciseRubric(Exercise exercise, ExerciseRubric rubric) throws BadInputException {
        // checks if rubric is not null
        if (rubric == null)
            throw new BadInputException("Cannot create exercise rubric: rubric is null.");
        rubric.setId(null); // prevent overwrite attack

        // checks if rubric is valid
        exercise.verifyRubricProperties(rubric);

        // if exercise already has rubric, then the rubric should
        // be overwritten, conserving the identifier.
        String oldRubricID = exercise.getRubricId();
        boolean update = oldRubricID != null;

        // persists rubric
        rubric.setId(oldRubricID);
        rubric = exerciseRubricDAO.save(rubric);

        // if the exercise did not have a rubric,
        // then the association needs to be created
        if (!update) {
            exercise.setRubric(rubric);
            exerciseDAO.save(exercise); // updates the exercise to save the association
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteExerciseRubric(String exerciseId) {
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if(exercise != null) {
            ExerciseRubric rubric = exercise.getRubric();
            if(rubric != null) {
                exercise.setRubric(null);
                exerciseDAO.save(exercise);
                exerciseRubricDAO.delete(rubric);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateExerciseRubric(String exerciseId, ExerciseRubric rubric) throws BadInputException, NotFoundException {
        createExerciseRubric(exerciseId, rubric);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void createExerciseSolution(String exerciseId, ExerciseSolution solution) throws NotFoundException, BadInputException {
        Exercise exercise = _getExerciseById(exerciseId);
        _createExerciseSolution(exercise, solution);
    }

    /**
     * Used to create or update the solution of an exercise.
     * @param exercise the exercise that should get its solution updated. Cannot be null.
     * @param solution the new solution
     * @throws BadInputException if the solution is not valid.
     */
    public void _createExerciseSolution(Exercise exercise, ExerciseSolution solution) throws BadInputException {
        // checks if solution is not null
        if (solution == null)
            throw new BadInputException("Cannot create exercise solution: solution is null.");
        solution.setId(null); // prevent overwrite attack

        // checks if solution is valid
        exercise.verifyResolutionProperties(solution.getData());

        // if exercise already has solution, then the solution should
        // be overwritten, conserving the identifier.
        String oldSolutionID = exercise.getSolutionId();
        boolean update = oldSolutionID != null;

        // persists solution
        solution.setId(oldSolutionID);
        solution = exerciseSolutionDAO.save(solution);

        // if the exercise did not have a solution,
        // then the association needs to be created
        if (!update) {
            exercise.setSolution(solution);
            exerciseDAO.save(exercise); // updates the exercise to save the association
        }
    }

    @Override
    public ExerciseSolution getExerciseSolution(String exerciseId) throws NotFoundException {
        if(!exerciseDAO.existsById(exerciseId))
            throw new NotFoundException("Cannot get exercise solution: exercise does not exist.");
        return exerciseSolutionDAO.findByExerciseId(exerciseId).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateExerciseSolution(String exerciseId, ExerciseSolution exerciseSolution) throws NotFoundException, BadInputException {
        createExerciseSolution(exerciseId, exerciseSolution);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteExerciseSolution(String exerciseId) {
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if(exercise != null) {
            ExerciseSolution solution = exercise.getSolution();
            if(solution != null) {
                exercise.setSolution(null);
                exerciseDAO.save(exercise);
                exerciseSolutionDAO.delete(solution);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String getExerciseCourse(String exerciseId) throws NotFoundException {
        Exercise exercise = _getExerciseById(exerciseId);
        return exercise.getCourseId();
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String getExerciseInstitution(String exerciseId) throws NotFoundException {
        Exercise exercise = _getExerciseById(exerciseId);
        return exercise.getInstitutionId();
    }

    /**
     * Requests that the correction of the exercise resolutions be done autonomously.
     * The correction can either be automatic or done by AI.
     * For a given exercise, it may support either, both, or none of the correction types.
     *
     * @param exerciseId     identifier of the exercise
     * @param correctionType type of correction. Can be 'auto' or 'ai'.
     * @throws BadInputException     if the correction type is not valid. It should be 'auto' or 'ai'.
     * @throws NotFoundException     if the exercise does not exist
     * @throws ForbiddenException if the exercise does not support the requested correction type.
     */
    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void issueExerciseResolutionsCorrection(String exerciseId, String correctionType) throws BadInputException, NotFoundException, ForbiddenException {
        // gets instance of the exercise and it's rubric
        Exercise exercise = _getExerciseById(exerciseId);
        if(!exercise.supportsCorrectionType(correctionType))
            throw new BadInputException("Could not correct exercise resolutions: correction type not supported.");

        // checks existence of a rubric, which is required
        // for the automatic correction of the exercise
        ExerciseRubric rubric = getExerciseRubric(exerciseId);

        // checks existence of a solution, which is required
        // for the automatic correction of the exercise
        ExerciseSolution solution = getExerciseSolution(exerciseId);

        if(correctionType.equalsIgnoreCase("auto"))
            automaticExerciseResolutionsCorrection(exercise, rubric, solution);
        else // more correction types ...
            throw new ForbiddenException("Correction type is supported, but cannot be issued by this method.");
    }

    /**
     * Requests that the correction of the exercise resolution be done autonomously.
     * The correction can either be automatic or done by AI.
     * For a given exercise, it may support either, both, or none of the correction types.
     *
     * @param resolutionId   identifier of the exercise resolution
     * @param correctionType type of correction. Can be 'auto' or 'ai'.
     * @return points attributed to the resolution
     * @throws BadInputException     if the correction type is not valid. It should be 'auto' or 'ai'.
     * @throws NotFoundException     if the resolution, or the exercise, or the rubric of the exercise, or the solution of the exercise does not exist
     * @throws ForbiddenException if the exercise does not support the requested correction type.
     */
    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public float issueExerciseResolutionCorrection(String resolutionId, String correctionType) throws BadInputException, NotFoundException, ForbiddenException {
        // gets the identifier of the exercise
        ExerciseResolution resolution = exerciseResolutionDAO.findById(resolutionId).orElse(null);
        if(resolution == null)
            throw new NotFoundException("Could not correct exercise resolution: resolution does not exist.");

        // if the exercise is already revised, then returns the points attributed to the resolution
        if(resolution.getStatus() == ExerciseResolutionStatus.REVISED)
            return resolution.getPoints();
        String exerciseId = resolution.getExerciseId();

        // gets instance of the exercise and it's rubric
        Exercise exercise = _getExerciseById(exerciseId);
        if(!exercise.supportsCorrectionType(correctionType))
            throw new BadInputException("Could not correct exercise resolution: correction type not supported.");

        // checks existence of a rubric, which is required
        // for the automatic correction of the exercise
        ExerciseRubric rubric = getExerciseRubric(exerciseId);

        // checks existence of a solution, which is required
        // for the automatic correction of the exercise
        ExerciseSolution solution = getExerciseSolution(exerciseId);

        if(correctionType.equalsIgnoreCase("auto"))
            return automaticExerciseResolutionCorrection(resolution, exercise, rubric, solution);
        else // ... more correction types
            throw new ForbiddenException("Correction type is supported, but cannot be issued by this method.");
    }

    /**
     * @param exerciseId identifier of an exercise
     * @param total      The total number of submissions can be obtained by setting the value to 'true'.
     *                   The number of students that submitted can be obtained by setting the value to 'false'
     * @return the total resolution submissions or the number of students
     * that submitted a resolution for a specific exercise depending
     * on the value of the 'total' parameter.
     */
    @Override
    public Integer countExerciseResolutions(String exerciseId, boolean total) {
        if(total)
            return exerciseResolutionDAO.countAllByExercise_Id(exerciseId);
        else
            return exerciseResolutionDAO.countStudentsWithResolutionForExercise(exerciseId);
    }

    /**
     * @param exerciseId     identifier of the exercise
     * @param page           index of the page
     * @param itemsPerPage   number of pairs in each page
     * @param latest         if 'true' only the latest resolution of a student is returned.
     *                       'false' every resolution can be returned, i.e., can have
     *                       multiple resolutions of a student
     * @param onlyNotRevised if 'true' only exercises resolutions that haven't been corrected will be returned.
     * @return list of pairs of a student and its latest exercise resolution for the requested exercise.
     */
    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public List<Pair<Student, ExerciseResolution>> getExerciseResolutions(String exerciseId, Integer page, Integer itemsPerPage, boolean latest, boolean onlyNotRevised) {
        Page<ExerciseResolution> resolutions;

        // gets the page of resolutions to return
        if(!onlyNotRevised){
            if (latest)
                resolutions = exerciseResolutionDAO.findLatestResolutionsByExercise_Id(
                        exerciseId,
                        PageRequest.of(page, itemsPerPage));
            else
                resolutions = exerciseResolutionDAO.findAllByExercise_Id(
                        exerciseId,
                        PageRequest.of(page, itemsPerPage));
        }else{
            if (latest)
                resolutions = exerciseResolutionDAO.findLatestResolutionsByExercise_IdAndStatus(
                        exerciseId,
                        ExerciseResolutionStatus.NOT_REVISED,
                        PageRequest.of(page, itemsPerPage));
            else
                resolutions = exerciseResolutionDAO.findAllByExercise_IdAndStatus(
                        exerciseId,
                        ExerciseResolutionStatus.NOT_REVISED,
                        PageRequest.of(page, itemsPerPage));
        }

        // gets the basic student info (Student) from the ExerciseResolution instances
        // and pairs it with the respective ExerciseResolution (No) instances
        List<Pair<Student, ExerciseResolution>> list = new ArrayList<>();
        for (ExerciseResolution res : resolutions)
            list.add(Pair.of(res.getStudent(), res));
        return list;
    }

    /**
     * Create a resolution for a specific exercise.
     *
     * @param studentId      identifier of the creator of the resolution.
     * @param exerciseId     identifier of the exercise
     * @param resolutionData new resolution
     * @return updated version of the resolution
     * @throws NotFoundException if the exercise was not found
     * @throws BadInputException if there is some problem regarding the resolution of the exercise,
     *                           like the type of resolution does not match the type of the exercise
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public ExerciseResolution createExerciseResolution(String studentId, String exerciseId, ExerciseResolutionData resolutionData) throws NotFoundException, BadInputException {
        // checks if exercise exists
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if (exercise == null)
            throw new NotFoundException("Could not create exercise resolution: exercise does not exist.");

        // checks if student exists
        Student student = studentsService.getStudentById(studentId);

        // checks if resolution is valid
        if(resolutionData == null)
            throw new BadInputException("Could not create exercise resolution: resolution data is null.");

        // prepares resolution
        ExerciseResolution resolution = new ExerciseResolution(null, // prevents overwrite attacks
                null, // new resolution so it should not have a points
                null, // submission number will be set later in this method
                resolutionData,
                ExerciseResolutionStatus.NOT_REVISED, // new resolution so cannot be already revised
                null, // new resolution so it cannot have a comment
                exercise,
                student
                );

        // checks the resolution data against the exercise data
        checkResolutionData(resolution);

        // sets resolution number
        ExerciseResolution lastResolution = exerciseResolutionDAO.getStudentLastResolution(studentId,exerciseId);
        int submissionNr = lastResolution != null ? lastResolution.getSubmissionNr() + 1 : 1;
        resolution.setSubmissionNr(submissionNr);

        // persists resolution
        return exerciseResolutionDAO.save(resolution);
    }

    @Override
    public ExerciseResolution updateExerciseResolution(String exeResId, ExerciseResolutionData resolutionData) throws NotFoundException, BadInputException {
        ExerciseResolution res = exerciseResolutionDAO.findById(exeResId).orElse(null);
        if(res == null)
            throw new NotFoundException("Could not update exercise resolution: Exercise resolution does not exist.");

        // checks if resolution data is valid
        if(resolutionData == null)
            throw new BadInputException("Could not update exercise resolution: resolution data is null.");
        // checks the resolution data against the exercise data
        checkResolutionData(res.getExerciseId(), resolutionData);

        // sets the new data
        res.setData(resolutionData);

        // updates resolution
        return exerciseResolutionDAO.save(res);
    }

    /**
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return the number of resolutions a student has made for a specific exercise.
     * @throws NotFoundException if the exercise does not exist
     */
    @Override
    public Integer countExerciseResolutionsByStudent(String exerciseId, String studentId) {
        return exerciseResolutionDAO.countAllByExercise_IdAndStudent_Id(exerciseId, studentId);
    }

    /**
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return list of metadata of all the resolutions a student has made for an exercise.
     * @throws NotFoundException if the exercise does not exist
     */
    @Override
    public List<ExerciseResolution> getStudentListOfExerciseResolutions(String exerciseId, String studentId) throws NotFoundException {
        return exerciseResolutionDAO.findAllByExercise_IdAndStudent_IdOrderBySubmissionNrAsc(exerciseId, studentId);
    }

    /**
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return last resolution made by the student for a given exercise, or 'null' if it does not exist.
     */
    @Override
    public ExerciseResolution getLastExerciseResolutionByStudent(String exerciseId, String studentId) {
        ExerciseResolution resolution = exerciseResolutionDAO.getStudentLastResolution(studentId, exerciseId);
        try {
            return resolution != null ? getExerciseResolution(resolution.getId()) : null;
        }catch (NotFoundException nfe){
            throw new AssertionError(nfe.getMessage());
        }
    }

    /**
     * @param page           index of the page
     * @param itemsPerPage   number of items per page
     * @param tags           list of tags to filter exercises
     * @param matchAllTags   if 'false' an exercise will match if at least one tag of the exercise matches one of the given list.
     *                       if 'true' the exercise must have all the tags present in the list
     * @param visibilityType type of visibility
     * @param courseId       to search for an exercise from a specific course
     * @param institutionId  to search for and exercise from a specific institution
     * @param specialistId   to search for the exercises created by a specific specialist
     * @param title          to search for an exercise title
     * @param exerciseType   to search for an exercise of a certain type
     * @param verifyParams   if 'true' then verify if parameters exist in the database (example: verify if specialist exists),
     *                       'false' does not verify database logic
     * @return list of exercises that match the given filters
     */
    @Override
    public List<Exercise> getExercises(Integer page, Integer itemsPerPage, List<String> tags, boolean matchAllTags, Visibility visibilityType, String courseId, String institutionId, String specialistId, String title, String exerciseType, boolean verifyParams) throws BadInputException, NotFoundException {

        if(verifyParams && courseId!=null) {
            if(!coursesService.existsCourseById(courseId))
                throw new NotFoundException("Theres no course with the given id");
        }

        if(verifyParams && institutionId!=null) {
            if(!institutionsService.existsInstitutionById(institutionId))
                throw new NotFoundException("Theres no institution with the given id");
        }

        if (verifyParams && specialistId != null) {
            if(!specialistsService.existsSpecialistById(specialistId))
                throw new NotFoundException("Theres no specialist with the given id");
        }

        // adds the sql wildcards before and after the title
        title = title != null ? '%' + title + '%' : null;

        Page<Exercise> exercises;
        if(matchAllTags && tags != null && !tags.isEmpty())
            exercises = exerciseDAO.getExercisesMatchAllTags(tags, tags.size(), visibilityType, institutionId,
                                                             courseId, specialistId, title, exerciseType,
                                                             PageRequest.of(page, itemsPerPage));
        else
            exercises = exerciseDAO.getExercisesMatchAnyGivenTag(tags,visibilityType,institutionId,courseId,
                                                                 specialistId,title,exerciseType,
                                                                 PageRequest.of(page, itemsPerPage));
        return exercises.toList();
    }

    /**
     * Gets the exercise resolution identified by the given identifier.
     * @param resolutionId identifier of the resolution
     * @return the exercise resolution identified by the given identifier.
     * @throws NotFoundException if the resolution does not exist
     */
    @Override
    public ExerciseResolution getExerciseResolution(String resolutionId) throws NotFoundException {
        ExerciseResolution resolution = exerciseResolutionDAO.findById(resolutionId).orElse(null);
        if(resolution == null)
            throw new NotFoundException("Could not get exercise resolution: No resolution with the given id exists.");
        return resolution;
    }


    /**
     * Adds a comment to an exercise resolution.
     * If the resolution already has a
     * comment associated, it will be overwritten.
     * @param resolutionId identifier of the resolution
     * @param comment body of the comment
     * @throws NotFoundException if the resolution does not exist
     * @throws BadInputException if the comment is malformed or is null.
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void addCommentToExerciseResolution(String resolutionId, Comment comment) throws NotFoundException, BadInputException {
        // checks that the comment is valid
        if(comment == null)
            throw new BadInputException("Could not add comment to exercise resolution: Comment is null.");
        String commentError = comment.verifyComment();
        if (commentError != null)
            throw new BadInputException("Could not add comment to exercise resolution: " + commentError);

        // adds the comment to the resolution
        ExerciseResolution resolution = getExerciseResolution(resolutionId);
        resolution.setComment(comment);

        // updates the document
        exerciseResolutionDAO.save(resolution);
    }

    /**
     * Deletes a comment made to an exercise resolution.
     *
     * @param resolutionId identifier of the resolution
     * @throws NotFoundException if the resolution does not exist
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void removeCommentFromExerciseResolution(String resolutionId) throws NotFoundException {
        ExerciseResolution resolution = getExerciseResolution(resolutionId);
        resolution.setComment(null);
        exerciseResolutionDAO.save(resolution);
    }

    /**
     * Used to set the points of an exercise resolution.
     * @param resolutionId identifier of the resolution
     * @param points points to set
     * @throws NotFoundException if the resolution does not exist
     * @throws BadInputException if the points exceed the max points for the exercise.
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void manuallyCorrectExerciseResolution(String resolutionId, float points) throws NotFoundException, BadInputException {
        manuallyCorrectExerciseResolution(resolutionId, points, null);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void manuallyCorrectExerciseResolution(String resolutionId, float points, Comment comment) throws NotFoundException, BadInputException {
        // checks if the resolution exists
        ExerciseResolution resolution = exerciseResolutionDAO.findById(resolutionId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Could not set resolution points: Resolution does not exist.");

        // points cannot be higher than 100 and cannot be lower than 0
        if (points < 0.0f || points > 100.0f)
            throw new BadInputException("Could not set resolution points: Points is a percentage so it needs to be a value between 0 and 100.");

        // sets the points and persists the document
        resolution.setPoints(points);
        if(comment != null)
            resolution.setComment(comment);
        resolution.setStatus(ExerciseResolutionStatus.REVISED);
        exerciseResolutionDAO.save(resolution);
    }

    /**
     * @param exerciseId identifier of the exercise
     * @return visibility of an exercise
     * @throws NotFoundException if the exercise does not exist
     */
    @Override
    public Visibility getExerciseVisibility(String exerciseId) throws NotFoundException {
        Exercise exercise = _getExerciseById(exerciseId);
        return exercise.getVisibility();
    }

    @Override
    public void deleteExerciseResolutionById(String exeResId) throws NotFoundException {
        ExerciseResolution resolution = exerciseResolutionDAO.findById(exeResId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't delete exercise resolution: Resolution \'" + exeResId + "\'was not found");
        exerciseResolutionDAO.delete(resolution);
    }

    /**
     * Checks if a specialist is the owner of an exercise.
     * @param exerciseId identifier of the exercise
     * @param specialistId identifier of the specialist
     * @return 'true' if a specialist is the owner of the exercise
     */
    public boolean isExerciseOwner(String exerciseId, String specialistId){
        if(specialistId == null) return false;
        return specialistId.equals(exerciseDAO.getExerciseSpecialistId(exerciseId));
    }

    @Override
    public Set<Tag> getExerciseTags(String exerciseId) {
        return exerciseDAO.getExerciseTags(exerciseId);
    }

    @Override
    public Set<Pair<Tag, Long>> countTagsOccurrencesForExercisesList(List<String> exercisesIds) {
        if(exercisesIds == null)
            return null;
        Set<Object[]> objects = exerciseDAO.countTagsOccurrencesForExercisesList(exercisesIds);
        return objects.stream().map(o -> Pair.of((Tag) o[0], (Long) o[1])).collect(Collectors.toSet());
    }

    /* **** Auxiliary methods **** */

    /**
     * Delete all resolutions associated with an exercise
     * @param exerciseId identifier of the exercise
     */
    private void deleteExerciseResolutions(String exerciseId){
        // Delete resolutions entries from database
        exerciseResolutionDAO.deleteAllByExercise_Id(exerciseId);
    }

    /**
     * Checks the resolution data against the exercise data.
     * @param exerciseResolution resolution
     * @throws NotFoundException if the exercise does not exist
     * @throws BadInputException if the resolution data is malformed
     */
    private void checkResolutionData(ExerciseResolution exerciseResolution) throws NotFoundException, BadInputException {
        assert exerciseResolution != null;

        // gets concrete exercise
        Exercise exercise = _getExerciseById(exerciseResolution.getExerciseId());

        // checks the resolution data against the exercise data.
        exercise.verifyResolutionProperties(exerciseResolution.getData());
    }

    /**
     * Checks the resolution data against the exercise data.
     * @param exerciseResolutionData resolution data
     * @throws NotFoundException if the exercise does not exist
     * @throws BadInputException if the resolution data is malformed
     */
    private void checkResolutionData(String exerciseId, ExerciseResolutionData exerciseResolutionData) throws NotFoundException, BadInputException {
        assert exerciseResolutionData != null;

        // gets concrete exercise
        Exercise exercise = _getExerciseById(exerciseId);

        // checks the resolution data against the exercise data.
        exercise.verifyResolutionProperties(exerciseResolutionData);
    }

    /**
     * Automatically corrects the not revised resolutions of an exercise.
     * @param exercise concrete exercise
     * @param rubric rubric of the exercise
     * @param solution solution of the exercise
     * @throws NotFoundException if the exercise, or its rubric, or its solution were not found
     * @throws ForbiddenException if the resolutions cannot be corrected automatically
     */
    private void automaticExerciseResolutionsCorrection(Exercise exercise, ExerciseRubric rubric, ExerciseSolution solution) throws NotFoundException, ForbiddenException {
        String exerciseId = exercise.getId();

        // Get number of resolutions not revised
        long resolutionsCount = exerciseResolutionDAO.countByExerciseIdAndStatus(exerciseId, ExerciseResolutionStatus.NOT_REVISED);

        // Iterates over all resolutions that are not revised, and corrects them
        // Corrects a portion at a time, to avoid a great memory consumption
        for(int pageIndex = 0, i = 0; i < resolutionsCount; pageIndex++, i += 5){
            // gets page
            Page<ExerciseResolution> page =
                    exerciseResolutionDAO.findAllByExercise_IdAndStatus(exerciseId,
                                                                    ExerciseResolutionStatus.NOT_REVISED,
                                                                    PageRequest.of(pageIndex, 5));

            // corrects each exercise of the page
            for (ExerciseResolution res : page)
                automaticExerciseResolutionCorrection(res, exercise, rubric, solution);
        }
    }

    /**
     * Automatically corrects a specific resolution of an exercise.
     * @param resolution resolution to be corrected
     * @param exercise concrete exercise
     * @param rubric rubric of the exercise
     * @param solution solution of the exercise
     * @return points attributed to the resolution
     * @throws ForbiddenException if the resolution cannot be corrected automatically
     */
    private float automaticExerciseResolutionCorrection(ExerciseResolution resolution, Exercise exercise, ExerciseRubric rubric, ExerciseSolution solution) throws ForbiddenException {
        assert resolution != null && exercise != null;
        ExerciseRubric unproxiedRubric = null;
        if(rubric != null)
            unproxiedRubric = (ExerciseRubric) Hibernate.unproxy(rubric);
        resolution = exercise.automaticEvaluation(resolution, solution, unproxiedRubric);
        exerciseResolutionDAO.save(resolution);
        return resolution.getPoints();
    }
}
