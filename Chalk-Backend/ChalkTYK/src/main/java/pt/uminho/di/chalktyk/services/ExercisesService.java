package pt.uminho.di.chalktyk.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.*;

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
     * @param exercise   body of the exercise to be created. Regarding the metadata should
     *                   contain, at least, the specialist identifier but may contain the institutionId and courseId
     * @param solution
     * @param rubric
     * @param visibility
     * @param tagsIds
     * @return new exercise identifier
     * @throws BadInputException if the exercise is not formed correctly
     */

    @Override
    @Transactional
    public String createExercise(Exercise exercise, ExerciseSolution solution, ExerciseRubric rubric, Visibility visibility, List<String> tagsIds) throws BadInputException {
        if(exercise == null)
            throw new BadInputException("Cannot create exercise: Exercise is null");

        // Check if specialist is valid
        Specialist specialist;
        try {
            specialist = specialistsService.getSpecialistById(exercise.getSpecialistId());
            exercise.setSpecialist(specialist);
        }catch (NotFoundException nfe){
            throw new BadInputException("Cannot create exercise: Specialist does not exist.");
        }

        // Check if tags are valid
        Set<Tag> tags = new HashSet<>();
        for (String id:tagsIds) {
            Tag tag = iTagsService.getTagById(id);
            if (tag == null)
                throw new BadInputException("Cannot create exercise: There is not tag with id \"" + id + "\".");
            tags.add(tag);
        }
        exercise.setTags(tags);

        // Check if visibility is valid
        if(visibility == null)
            throw new BadInputException("Cannot create exercise: Visibility cant be null");

        // Checks if specialist exists and gets the institution where it belongs
        Institution institution = specialist.getInstitution();
        exercise.setInstitution(institution);

        // If the specialist that owns the exercise does not have an institution,
        // then the exercise's visibility cannot be set to institution.
        if (visibility == Visibility.INSTITUTION && institution == null)
            throw new BadInputException("Cannot create exercise: cannot set visibility to INSTITUTION");

        // Check if course is valid
        String courseId = exercise.getCourseId();
        try {
            if (courseId != null && !coursesService.checkSpecialistInCourse(courseId, exercise.getSpecialistId()))
                throw new BadInputException("Cannot create exercise: specialist does not belong to the given course.");
        } catch (NotFoundException nfe){
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
        if (solution != null) {
            solution.verifyInsertProperties();
            exercise.verifyResolutionProperties(solution.getData());
            solution = exerciseSolutionDAO.save(solution);
            exercise.setSolution(solution);
        }

        // Check rubric
        if (rubric != null) {
            exercise.verifyRubricProperties(rubric);
            rubric = exerciseRubricDAO.save(rubric);
            exercise.setRubric(rubric);
        }

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
    @Transactional
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

    // TODO - verificar se existe metodo para dar update a visibilidade, ao curso e à cotacao de um exercicio.
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
    @Transactional
    public String duplicateExerciseById(String specialistId, String exerciseId) throws NotFoundException {
        return duplicateExerciseById(specialistId, exerciseId, null, null, null);
    }

    @Override
    @Transactional
    public String duplicateExerciseById(String specialistId, String exerciseId, String courseId, Float points, Visibility visibility) throws NotFoundException {
        // checks if the specialist exists
        Specialist specialist = specialistsService.getSpecialistById(specialistId);

        // gets specialists institution
        String institutionId = null;
        Institution institution =
                institutionsService.getSpecialistInstitution(specialistId);

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
        copy.setPoints(points == null || points <= 0.0f ? 1.0f : points);
        copy.setTags(new HashSet<>(source.getTags()));
        copy.setInstitution(institution);
        copy.setSpecialist(specialist);
        copy.setCourse(courseId == null ? null : coursesService.getCourseById(courseId));
        copy.setRubric(rubricCopy);
        copy.setSolution(solutionCopy);

        // persists exercise
        exerciseDAO.save(copy);

        return copy.getId();
    }

    /**
     * Updates an exercise. If an object is 'null' than it is considered that it should remain the same.
     * To delete it, a specific delete method should be invoked.
     *
     * @param exerciseId   identifier of the exercise to be updated
     * @param exercise     new exercise body
     * @param rubric       new exercise rubric
     * @param solution     new exercise solution
     * @param tagsIds      new list of tags
     * @param visibility   new visibility
     * @throws NotFoundException     if the exercise was not found
     */
    @Override
    @Transactional
    public void updateAllOnExercise(String exerciseId, Exercise exercise, ExerciseRubric rubric, ExerciseSolution solution, List<String> tagsIds, Visibility visibility) throws NotFoundException, BadInputException {
        if(!exerciseExists(exerciseId))
            throw new NotFoundException("No exercise with the given id exists.");
        if(exercise!=null) {
            if(exercise.getId() == null)
                exercise.setId(exerciseId);
            if (!Objects.equals(exerciseId, exercise.getId()))
                throw new BadInputException("ExerciseId must be the same as the one given in the exercise");
            updateExerciseBody(exercise, rubric == null, solution == null); //if rubric or solution are not null, then they will verify exercise
        }
        if(rubric!=null)
            updateExerciseRubric(exerciseId,rubric);
        if(solution!=null)
            updateExerciseSolution(exerciseId,solution);
        if(tagsIds!=null)
            updateExerciseTags(exerciseId,tagsIds);
        if(visibility!=null)
            updateExerciseVisibility(exerciseId,visibility);
    }


    // TODO - ver se existe update exercise's course and owner
    /**
     * Updates an exercise body.
     *
     * @param newBody new exercise body
     * @param hasNewSolution if true then method verifies if current solution corresponds to the exercise
     * @param hasNewRubric if true then method verifies if current rubric corresponds to the exercise
     * @throws NotFoundException if the test wasn't found
     * @throws BadInputException solution, rubric, institution or specialist ids where changed,
     * course was not found,
     * the rubric or solution don't belong to the new exercise body
     */
    @Transactional
    public void updateExerciseBody(Exercise newBody, Boolean hasNewRubric, Boolean hasNewSolution) throws NotFoundException, BadInputException {
        String exerciseId = newBody.getId();
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if (exercise == null)
            throw new NotFoundException("Couldn't update exercise: exercise with id \'" + exerciseId + "\' wasn't found");

        // Check new body properties
        newBody.verifyInsertProperties();
        
        // copies exercise related data to the original exercise
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

        exerciseDAO.save(newBody);
        
        if(deleteRubric)
            exerciseRubricDAO.deleteById(rubric.getId());
        if(deleteSolution)
            exerciseSolutionDAO.deleteById(solution.getId());
    }

    /**
     * Updates an exercise tags. Assumes exercise exists
     *
     * @param exerciseId   identifier of the exercise to be updated
     * @param tagsIds      new list of tags
     */
    @Transactional
    public void updateExerciseTags(String exerciseId, List<String> tagsIds) throws BadInputException, NotFoundException {
        for (String id:tagsIds)
            if(iTagsService.getTagById(id)==null)
                throw new BadInputException("Cannot create exercise: There is not tag with id \"" + id + "\".");
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        assert exercise!=null;

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


    /**
     * Updates and exercise visibility. Assumes that the exercise exists
     *
     * @param exerciseId   identifier of the exercise to be updated
     * @param visibility   new visibility
     * @throws BadInputException if there's something wrong with the text
     */
    private void updateExerciseVisibility(String exerciseId, Visibility visibility) throws BadInputException {
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        assert exercise != null;

        // Checks if specialist exists and gets the institution where it belongs
        Institution institution;
        try { institution = institutionsService.getSpecialistInstitution(exercise.getSpecialist().getId()); }
        catch (NotFoundException nfe){ throw new BadInputException("Cannot create exercise: Specialist does not exist."); }

        // If the specialist that owns the exercise does not have an institution,
        // then the exercise's visibility cannot be set to institution.
        if (visibility == Visibility.INSTITUTION && institution == null)
            throw new BadInputException("Cannot set visibility to INSTITUTION");

        // Check if course is valid
        String courseId = exercise.getCourse().getId();
        try {
            if (courseId != null && !coursesService.checkSpecialistInCourse(courseId, exercise.getSpecialist().getId()))
                throw new BadInputException("Cannot create exercise: course not found.");
        } catch (NotFoundException nfe){
            throw new BadInputException("Cannot create exercise: Specialist does not belong to the given course.");
        }

        // Cannot set visibility to COURSE without a course associated
        if (courseId == null && visibility == Visibility.COURSE)
            throw new BadInputException("Cannot create exercise: cannot set visibility to COURSE without a course associated.");
        exercise.setVisibility(visibility);
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
    @Transactional
    public void createExerciseRubric(String exerciseId, ExerciseRubric rubric) throws NotFoundException, BadInputException {
        // finds exercise
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if (exercise == null)
            throw new NotFoundException("Cannot create exercise rubric: exercise does not exist.");

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

        // sets new rubric and persists the exercise to save the association
        exercise.setRubric(rubric);
        exerciseDAO.save(exercise);

        // if the exercise did not have a rubric,
        // then the association needs to be created
        if (!update) {
            exercise.setRubric(rubric);
            exerciseDAO.save(exercise); // updates the exercise to save the association
        }
    }

    @Override
    @Transactional
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
    @Transactional
    public void updateExerciseRubric(String exerciseId, ExerciseRubric rubric) throws BadInputException, NotFoundException {
        createExerciseRubric(exerciseId, rubric);
    }

    @Override
    @Transactional
    public void createExerciseSolution(String exerciseId, ExerciseSolution solution) throws NotFoundException, BadInputException {
        // finds exercise
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if (exercise == null)
            throw new NotFoundException("Cannot create exercise solution: exercise does not exist.");

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

        // sets new solution and persists the exercise to save the association
        exercise.setSolution(solution);
        exerciseDAO.save(exercise);

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
    @Transactional
    public void updateExerciseSolution(String exerciseId, ExerciseSolution exerciseSolution) throws NotFoundException, BadInputException {
        createExerciseSolution(exerciseId, exerciseSolution);
    }

    @Override
    @Transactional
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
    @Transactional
    public String getExerciseCourse(String exerciseId) throws NotFoundException {
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if(exercise==null)
            throw new NotFoundException("There is no exercise for the given id");
        if(exercise.getCourse()==null)
            return null;
        return exercise.getCourse().getId();
    }

    @Override
    @Transactional
    public Institution getExerciseInstitution(String exerciseId) throws NotFoundException {
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if(exercise == null)
            throw new NotFoundException("There is no exercise for the given id");
        return exercise.getInstitution();
    }

    /**
     * Issue the automatic correction of the exercise resolutions.
     * The correction can either be automatic or done by AI.
     * For a given exercise, it may support either, both, or none of the correction types.
     *
     * @param exerciseId     identifier of the exercise
     * @param correctionType type of correction. Can be 'auto' or 'ai'.
     * @throws BadInputException     if the correction type is not valid. It should be 'auto' or 'ai'.
     * @throws NotFoundException     if the exercise does not exist
     * @throws UnauthorizedException if the exercise does not support the requested correction type.
     */
    @Transactional
    @Override
    public void issueExerciseResolutionsCorrection(String exerciseId, String correctionType) throws BadInputException, NotFoundException, UnauthorizedException {
        if(!correctionType.equalsIgnoreCase("auto") && !correctionType.equalsIgnoreCase("ai"))
            throw new BadInputException("Could not correct exercise resolutions: Correction type must be 'auto' or 'ai'.");

        // gets instance of the exercise and it's rubric
        Exercise exercise = _getExerciseById(exerciseId);

        // checks existence of a rubric, which is required
        // for the automatic correction of the exercise
        ExerciseRubric rubric = getExerciseRubric(exerciseId);

        // checks existence of a solution, which is required
        // for the automatic correction of the exercise
        ExerciseSolution solution = getExerciseSolution(exerciseId);

        if(correctionType.equalsIgnoreCase("auto"))
            automaticExerciseResolutionsCorrection(exercise, rubric, solution);
        else if (correctionType.equalsIgnoreCase("ai")) {
            // TODO - add the AI part
        }
    }

    /**
     * Issue the automatic correction of the exercise resolutions.
     * The correction can either be automatic or done by AI.
     * For a given exercise, it may support either, both, or none of the correction types.
     *
     * @param resolutionId   identifier of the exercise resolution
     * @param correctionType type of correction. Can be 'auto' or 'ai'.
     * @return points attributed to the resolution
     * @throws BadInputException     if the correction type is not valid. It should be 'auto' or 'ai'.
     * @throws NotFoundException     if the resolution, or the exercise, or the rubric of the exercise, or the solution of the exercise does not exist
     * @throws UnauthorizedException if the exercise does not support the requested correction type.
     */
    @Transactional
    @Override
    public float issueExerciseResolutionCorrection(String resolutionId, String correctionType) throws BadInputException, NotFoundException, UnauthorizedException{
        // gets the identifier of the exercise
        ExerciseResolution resolution = exerciseResolutionDAO.findById(resolutionId).orElse(null);
        if(resolution == null)
            throw new NotFoundException("Could not correct exercise: resolution does not exist.");

        // if the exercise is already revised, then returns the points attributed to the resolution
        if(resolution.getStatus() == ExerciseResolutionStatus.REVISED)
            return resolution.getPoints();
        String exerciseId = resolution.getExerciseId();

        // gets instance of the exercise and it's rubric
        Exercise exercise = _getExerciseById(exerciseId);

        // checks existence of a rubric, which is required
        // for the automatic correction of the exercise
        ExerciseRubric rubric = getExerciseRubric(exerciseId);

        // checks existence of a solution, which is required
        // for the automatic correction of the exercise
        ExerciseSolution solution = getExerciseSolution(exerciseId);

        return automaticExerciseResolutionCorrection(resolution, exercise, rubric, solution);
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
     * @param exerciseId   identifier of the exercise
     * @param page         index of the page
     * @param itemsPerPage number of pairs in each page
     * @param latest if 'true' only the latest resolution of a student is returned.
     *               'false' every resolution can be returned, i.e., can have
     *               multiple resolutions of a student
     * @return list of pairs of a student and its latest exercise resolution for the requested exercise.
     */
    @Transactional
    @Override
    public List<Pair<Student, ExerciseResolution>> getExerciseResolutions(String exerciseId, Integer page, Integer itemsPerPage, boolean latest) {
        Page<ExerciseResolution> resolutions;

        // gets the page of resolutions to return
        if(latest)
            resolutions = exerciseResolutionDAO.findLatestResolutionsByExercise_Id(exerciseId, PageRequest.of(page, itemsPerPage));
        else
            resolutions = exerciseResolutionDAO.findAllByExercise_Id(exerciseId,PageRequest.of(page, itemsPerPage));

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
    @Transactional
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
    public List<ExerciseResolution> getStudentListOfExerciseResolutionsMetadataByExercise(String exerciseId, String studentId) throws NotFoundException {
        return exerciseResolutionDAO.findAllByExercise_IdAndStudent_Id(exerciseId, studentId);
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
     * @param userId           identifier of the user that made the request. Necessary to check authorization.
     * @param page             index of the page
     * @param itemsPerPage     number of items per page
     * @param tags             list of tags to filter exercises
     * @param matchAllTags     if 'false' an exercise will match if at least one tag of the exercise matches one of the given list.
     *                         if 'true' the exercise must have all the tags present in the list
     * @param visibilityType   type of visibility
     * @param courseId         to search for an exercise from a specific course
     * @param institutionId    to search for and exercise from a specific institution
     * @param specialistId     to search for the exercises created by a specific specialist
     * @param title            to search for an exercise title
     * @param exerciseType     to search for an exercise of a certain type
     * @param verifyParams     if 'true' then verify if parameters exist in the database (example: verify if specialist exists),
     *                         'false' does not verify database logic
     * @return list of exercises that match the given filters
     */
    @Override
    public List<Exercise> getExercises(String userId, Integer page, Integer itemsPerPage, List<String> tags, boolean matchAllTags, String visibilityType, String courseId, String institutionId, String specialistId, String title, String exerciseType, boolean verifyParams) throws BadInputException, NotFoundException {
        Visibility visibility= null;
        if (visibilityType != null) {
            visibility = Visibility.fromValue(visibilityType);
            if (visibility == null)
                throw new BadInputException("Visibility type not found");
        }

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
        } //TODO verificação das tags
        Page<Exercise> exerciseS = exerciseDAO.getExercises(PageRequest.of(page, itemsPerPage),tags,matchAllTags,visibility,institutionId,courseId,specialistId,title,exerciseType);
        return exercisesToNo(exerciseS);
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
    @Transactional
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
    @Transactional
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
    @Transactional
    public void setExerciseResolutionPoints(String resolutionId, float points) throws NotFoundException, BadInputException {
        // checks if the resolution exists
        ExerciseResolution resolution = exerciseResolutionDAO.findById(resolutionId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Could not set resolution points: Resolution does not exist.");

        // finds the max points for the exercise
        Exercise exercise = exerciseDAO.findById(resolution.getExerciseId()).orElse(null);
        assert exercise != null;
        float maxPoints = exercise.getPoints();

        // points cannot be higher than the defined points for the question
        if (points > maxPoints)
            throw new BadInputException("Could not set resolution points: Points exceed the max points of the exercise");

        // sets the points and persists the document
        resolution.setPoints(points);
        exerciseResolutionDAO.save(resolution);
    }

    /**
     * @param exerciseId identifier of the exercise
     * @return visibility of an exercise
     * @throws NotFoundException if the exercise does not exist
     */
    @Override
    public String getExerciseVisibility(String exerciseId) throws NotFoundException {
        Optional<Exercise> exercise = exerciseDAO.findById(exerciseId);
        if(exercise.isPresent())
            return exercise.get().getVisibility().toString();
        else
            throw new NotFoundException("Exercise does not exist.");
    }


    /* **** Auxiliary methods **** */

    /**
     * Checks if a specialist is the owner of an exercise.
     * @param exerciseId identifier of the exercise
     * @param specialistId identifier of the specialist
     * @return 'true' if a specialist is the owner of the exercise
     */
    private boolean isExerciseOwner(String exerciseId, String specialistId){
        if(specialistId == null) return false;
        return specialistId.equals(exerciseDAO.getExerciseSpecialistId(exerciseId));
    }

    private Specialist verifyOwnershipAuthorization(String specialistId, String exerciseId) throws NotFoundException, UnauthorizedException {
        if(!exerciseDAO.existsById(exerciseId))
            throw new NotFoundException("Exercise not found");
        if(!specialistsService.existsSpecialistById(specialistId))
            throw new NotFoundException("Specialist not found");

        Exercise relExerciseToBeCopied = exerciseDAO.getReferenceById(exerciseId);
        Specialist specialist = relExerciseToBeCopied.getSpecialist();
        if(specialist==null || !specialist.getId().equals(specialistId)) //TODO maybe mudar isto
            throw new UnauthorizedException("The specialist is not the owner of the exercise");
        return specialist;
    }

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
     * Converts a page of Exercise to a list of Exercise(No)s
     * @param exercisePage page of exercises
     * @return a list of Exercise(No)s
     * @throws NotFoundException if one of the exercises, on the page, was not found
     */
    private List<Exercise> exercisesToNo(Page<Exercise> exercisePage) throws NotFoundException {
        List<Exercise> exerciseList = new ArrayList<>();
        for(var exercise : exercisePage)
            exerciseList.add(this.getExerciseById(exercise.getId()));
        return exerciseList;
    }

    // TODO - meter a rubrica e a solucao como transient no concrete exercise?
    /**
     * Automatically corrects the not revised resolutions of an exercise.
     * @param exercise concrete exercise
     * @param rubric rubric of the exercise
     * @param solution solution of the exercise
     * @throws NotFoundException if the exercise, or its rubric, or its solution were not found
     * @throws UnauthorizedException if the resolutions cannot be corrected automatically
     */
    private void automaticExerciseResolutionsCorrection(Exercise exercise, ExerciseRubric rubric, ExerciseSolution solution) throws NotFoundException, UnauthorizedException {
        String exerciseId = exercise.getId();

        // Get number of resolutions not revised
        /* 
        long resolutionsCount = exerciseResolutionDAO.countByExerciseIdAndStatus(exerciseId, ExerciseResolutionStatus.NOT_REVISED);

        // Iterates over all resolutions that are not revised, and corrects them
        // Corrects a portion at a time, to avoid a great memory consumption
        for(int pageIndex = 0, i = 0; i < resolutionsCount; pageIndex++, i += 5){
            // gets page
            Page<ExerciseResolution> page =
                    exerciseResolutionDAO.findAllByExerciseIdAndStatus(exerciseId,
                                                                    ExerciseResolutionStatus.NOT_REVISED,
                                                                    PageRequest.of(pageIndex, 5));

            // corrects each exercise of the page
            for (ExerciseResolution res : page)
                automaticExerciseResolutionCorrection(res, exercise, rubric, solution);
        }
        */
    }

    /**
     * Automatically corrects a specific resolution of an exercise.
     * @param resolution resolution to be corrected
     * @param exercise concrete exercise
     * @param rubric rubric of the exercise
     * @param solution solution of the exercise
     * @return points attributed to the resolution
     * @throws UnauthorizedException if the resolution cannot be corrected automatically
     */
    private float automaticExerciseResolutionCorrection(ExerciseResolution resolution, Exercise exercise, ExerciseRubric rubric, ExerciseSolution solution) throws UnauthorizedException {
        assert resolution != null && exercise != null && rubric != null && solution != null;
        resolution = exercise.automaticEvaluation(resolution, solution, rubric);
        exerciseResolutionDAO.save(resolution);
        return resolution.getPoints();
    }
}
