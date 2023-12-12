package pt.uminho.di.chalktyk.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.*;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Exercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.relational.*;
import pt.uminho.di.chalktyk.repositories.nonrelational.ExerciseDAO;
import pt.uminho.di.chalktyk.repositories.nonrelational.ExerciseRubricDAO;
import pt.uminho.di.chalktyk.repositories.nonrelational.ExerciseSolutionDAO;
import pt.uminho.di.chalktyk.repositories.relational.ExerciseSqlDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.*;

@Service("exercisesService")
public class ExercisesService implements IExercisesService{
    private final ISpecialistsService specialistsService;
    private final ExerciseDAO exerciseDAO;
    private final ExerciseSqlDAO exerciseSqlDAO;
    private final ICoursesService coursesService;
    private final IInstitutionsService institutionsService;
    private final ITagsService iTagsService;
    private final ExerciseSolutionDAO exerciseSolutionDAO;
    private final ExerciseRubricDAO exerciseRubricDAO;
    @PersistenceContext
    private final EntityManager entityManager;
    public ExercisesService(ISpecialistsService specialistsService, ExerciseDAO exerciseDAO, ExerciseSqlDAO exerciseSqlDAO, ICoursesService coursesService, IInstitutionsService institutionsService, ITagsService iTagsService, ExerciseSolutionDAO exerciseSolutionDAO, ExerciseRubricDAO exerciseRubricDAO, EntityManager entityManager) {
        this.specialistsService = specialistsService;
        this.exerciseDAO = exerciseDAO;
        this.exerciseSqlDAO = exerciseSqlDAO;
        this.coursesService = coursesService;
        this.institutionsService = institutionsService;
        this.iTagsService = iTagsService;
        this.exerciseSolutionDAO = exerciseSolutionDAO;
        this.exerciseRubricDAO = exerciseRubricDAO;
        this.entityManager = entityManager;
    }

    /**
     * Get Exercise by ID
     *
     * @param exerciseId of the exercise
     * @return exercise from the given ID
     **/
    @Override
    public Exercise getExerciseById(String exerciseId) throws NotFoundException {
        Optional<Exercise> obj = exerciseDAO.findById(exerciseId);
        if (obj.isPresent()){
            return obj.get();
        }
        else
            throw new NotFoundException("Couldn't get exercise with id: " + exerciseId);
    }

    /**
     * Get Exercise by ID
     *
     * @param exerciseId of the exercise
     * @return true if exercise exists, false otherwise
     **/
    @Override
    public boolean exerciseExists(String exerciseId) {
        return exerciseSqlDAO.existsById(exerciseId);
    }

    /**
     * Verify is exercise is shallow
     *
     * @param exerciseId of the exercise
     * @return true if exercise is shallow, false otherwise
     **/
    @Override
    public boolean exerciseIsShallow(String exerciseId) throws NotFoundException {
        return getExerciseById(exerciseId) instanceof ShallowExercise;
    }

    /**
     * Creates an exercise.
     *
     * @param exercise     body of the exercise to be created. Regarding the metadata should
     *                     contain, at least, the specialist identifier but may contain the institutionId and courseId
     * @param rubric
     * @param solution
     * @param tagsIds
     * @param visibility
     * @return new exercise identifier
     * @throws BadInputException if the exercise is not formed correctly
     */

    @Override
    @Transactional
    public String createExercise(ConcreteExercise exercise, ExerciseRubric rubric, ExerciseSolution solution, List<String> tagsIds, VisibilitySQL visibility) throws BadInputException {
        if(exercise == null)
            throw new BadInputException("Cannot create exercise: Exercise is null");

        // Check if tags are valid
        for (String id:tagsIds)
            if(iTagsService.getTagById(id)==null)
                throw new BadInputException("Cannot create exercise: There is not tag with id \"" + id + "\".");
        // Check if visibility is valid
        if(visibility == null)
            throw new BadInputException("Cannot create exercise: Visibility cant be null");

        // Checks if specialist exists and gets the institution where it belongs
        pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution institution;
        try { institution = institutionsService.getSpecialistInstitution(exercise.getSpecialistId()); }
        catch (NotFoundException nfe){ throw new BadInputException("Cannot create exercise: Specialist does not exist."); }

        // sets identifier of the institution
        if(institution != null) exercise.setInstitutionId(institution.getName());

        // If the specialist that owns the exercise does not have an institution,
        // then the exercise's visibility cannot be set to institution.
        if (visibility == VisibilitySQL.INSTITUTION && institution == null)
            throw new BadInputException("Cannot create exercise: cannot set visibility to INSTITUTION");

        // Check if course is valid
        String courseId = exercise.getCourseId();
        try {
            if (courseId != null && coursesService.checkSpecialistInCourse(courseId, exercise.getSpecialistId()))
                throw new BadInputException("Cannot create exercise: course not found.");
        } catch (NotFoundException nfe){
            throw new BadInputException("Cannot create exercise: Specialist does not belong to the given course.");
        }

        // Cannot set visibility to COURSE without a course associated
        if (courseId == null && visibility == VisibilitySQL.COURSE)
            throw new BadInputException("Cannot create exercise: cannot set visibility to COURSE without a course associated.");

        // Prevent overrides
        exercise.setId(null);
        exercise.setRubricId(null);
        exercise.setSolutionId(null);

        // Check exercise properties
        exercise.verifyProperties();

        // Check solution
        if (solution != null) {
            solution.verifyInsertProperties();
            exercise.verifyResolutionProperties(solution.getData());
            solution = exerciseSolutionDAO.save(solution);
            exercise.setSolutionId(solution.getId());
        }

        // Check rubric
        if (rubric != null) {
            exercise.verifyRubricProperties(rubric);
            rubric = exerciseRubricDAO.save(rubric);
            exercise.setRubricId(rubric.getId());
        }

        // persists the exercise in nosql database
        exercise = exerciseDAO.save(exercise);

        InstitutionSQL institutionSql = exercise.getInstitutionId() != null ? entityManager.getReference(InstitutionSQL.class, exercise.getInstitutionId()) : null;
        SpecialistSQL specialistSql = exercise.getSpecialistId() != null ? entityManager.getReference(SpecialistSQL.class, exercise.getSpecialistId()) : null;
        CourseSQL courseSql = exercise.getCourseId() != null ? entityManager.getReference(CourseSQL.class, exercise.getCourseId()) : null;
        Set<TagSQL> tagsSet = new HashSet<>();
        for(String tag : tagsIds){
            tagsSet.add(entityManager.getReference(TagSQL.class, tag));
        }

        ExerciseSQL relExercise =
                new ExerciseSQL(
                        exercise.getId(),
                        institutionSql,
                        specialistSql,
                        courseSql,
                        exercise.getTitle(),
                        exercise.getExerciseType(),
                        visibility,
                        0,
                        tagsSet);
        exerciseSqlDAO.save(relExercise);
        return exercise.getId();
    }

    /**
     * Delete exercise by id.
     *
     * @param specialistId identifier of the specialist that wants to delete the exercise
     * @param exerciseId   identifier of the exercise
     * @throws UnauthorizedException if the exercise is not owned by the specialist
     * @throws NotFoundException     if the exercise was not found
     */
    @Override
    @Transactional
    public void deleteExerciseById(String specialistId, String exerciseId) throws UnauthorizedException, NotFoundException {
        verifyOwnershipAuthorization(specialistId, exerciseId);

        //Success
        //TODO acho que preciso duas cabeças para fazer este método
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
     * @throws UnauthorizedException if the exercise is not owned by the specialist
     * @throws NotFoundException     if the exercise was not found
     */
    @Override
    @Transactional
    public String duplicateExerciseById(String specialistId, String exerciseId) throws UnauthorizedException, NotFoundException {
        SpecialistSQL specialist = verifyOwnershipAuthorization(specialistId, exerciseId);
        ExerciseSQL relExerciseToBeCopied;


        //Success
        Exercise nonRelExerciseToBeCopied = getExerciseById(exerciseId);
        if(nonRelExerciseToBeCopied instanceof ShallowExercise shallowExercise){
            nonRelExerciseToBeCopied = getExerciseById(shallowExercise.getId());
        }

        String institutionId=null;
        pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution institution =
                institutionsService.getSpecialistInstitution(specialistId);
        if(institution!=null)
            institutionId = institution.getName();


        ShallowExercise shallowExercise = new ShallowExercise(nonRelExerciseToBeCopied.getId(),specialistId,institutionId,null,nonRelExerciseToBeCopied.getCotation());

        exerciseSqlDAO.increaseExerciseCopies(nonRelExerciseToBeCopied.getId());
        relExerciseToBeCopied = exerciseSqlDAO.getReferenceById(nonRelExerciseToBeCopied.getId());

        shallowExercise = exerciseDAO.save(shallowExercise);
        InstitutionSQL relInstitution = institutionId != null ? entityManager.getReference(InstitutionSQL.class, institutionId) :null;
        ExerciseSQL shallowRelExercise =
                relExerciseToBeCopied.createShallow(shallowExercise.getId(),relInstitution,specialist,null);
        exerciseSqlDAO.save(shallowRelExercise);
        return shallowExercise.getId();
    }

    private SpecialistSQL verifyOwnershipAuthorization(String specialistId, String exerciseId) throws NotFoundException, UnauthorizedException {
        if(!exerciseSqlDAO.existsById(exerciseId))
            throw new NotFoundException("Exercise not found");
        if(!specialistsService.existsSpecialistById(specialistId))
            throw new NotFoundException("Specialist not found");

        ExerciseSQL relExerciseToBeCopied = exerciseSqlDAO.getReferenceById(exerciseId);
        SpecialistSQL specialist = relExerciseToBeCopied.getSpecialist();
        if(specialist==null || !specialist.getId().equals(specialistId)) //TODO maybe mudar isto
            throw new UnauthorizedException("The specialist is not the owner of the exercise");
        return specialist;
    }

    /**
     * Updates an exercise. If an object is 'null' than it is considered that it should remain the same.
     * To delete it, a specific delete method should be invoked.
     *
     * @param specialistId identifier of the specialist that owns the exercise
     * @param exerciseId   identifier of the exercise to be updated
     * @param exercise     new exercise body
     * @param rubric       new exercise rubric
     * @param solution     new exercise solution
     * @param tagsIds      new list of tags
     * @param visibility   new visibility
     * @throws UnauthorizedException if the exercise is not owned by the specialist
     * @throws NotFoundException     if the exercise was not found
     */
    @Override
    public void updateExercise(String specialistId, String exerciseId, Exercise exercise, ExerciseRubric rubric, ExerciseSolution solution, List<String> tagsIds, VisibilitySQL visibility) throws UnauthorizedException, NotFoundException {
        SpecialistSQL specialist = verifyOwnershipAuthorization(specialistId, exerciseId);
        //TODO verify if ids in multiple choice are changed
    }

    /**
     * Retrieves the rubric of an exercise.
     *
     * @param userId
     * @param exerciseId exercise identifier
     * @return rubric of the exercise or null if it doesn't exist.
     * @throws UnauthorizedException if the user does not have authorization to check the rubric of the exercise.
     * @throws NotFoundException     if the exercise was not found
     */
    @Override
    public ExerciseRubric getExerciseRubric(String userId, String exerciseId) throws UnauthorizedException, NotFoundException {
        return null;
    }

    /**
     * Create an exercise rubric
     *
     * @param exerciseId exercise identifier
     * @param rubric     new rubric
     * @throws UnauthorizedException if the user does not have authorization to check the rubric of the exercise.
     * @throws NotFoundException     if the exercise was not found
     */
    @Override
    public void createExerciseRubric(String exerciseId, ExerciseRubric rubric) throws UnauthorizedException, NotFoundException {

    }

    /**
     * Issue the automatic correction of the exercise resolutions.
     * The correction can either be automatic or done by AI.
     * For a given exercise, it may support either, both, or none of the correction types.
     *
     * @param userId         identifier of the user that has permission to issue the correction,
     *                       such as the owner of the exercise, or another specialist that belongs
     *                       to the course that is associated with the exercise.
     * @param exerciseId     identifier of the exercise
     * @param correctionType type of correction
     */
    @Override
    public void issueExerciseResolutionsCorrection(String userId, String exerciseId, String correctionType) {

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
        return null;
    }

    /**
     * @param exerciseId   identifier of the exercise
     * @param page         index of the page
     * @param itemsPerPage number of pairs in each page
     * @return list of pairs of a student and its correspondent exercise resolution for the requested exercise.
     */
    @Override
    public List<Pair<StudentSQL, ExerciseResolution>> getExerciseResolutions(String exerciseId, Integer page, Integer itemsPerPage) {
        return null;
    }

    /**
     * Create a resolution for a specific exercise.
     *
     * @param studentId  identifier of the creator of the resolution.
     * @param exerciseId identifier of the exercise
     * @param resolution new resolution
     * @throws UnauthorizedException if the student does not have permission to create
     *                               a resolution for the given exercise.
     * @throws NotFoundException     if the exercise was not found
     * @throws BadInputException     if there is some problem regarding the resolution of the exercise,
     *                               like the type of resolution does not match the type of the exercise
     */
    @Override
    public void createExerciseResolution(String studentId, Integer exerciseId, ExerciseResolution resolution) {

    }

    /**
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return the number of resolutions a student has made for a specific exercise.
     * @throws NotFoundException if the exercise does not exist
     */
    @Override
    public Integer countExerciseResolutionsByStudent(String exerciseId, String studentId) {
        return null;
    }

    /**
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return list of the identifiers of all the resolutions a student has made for an exercise.
     * @throws NotFoundException if the exercise does not exist
     */
    @Override
    public List<String> getStudentListOfExerciseResolutionsIdsByExercise(String exerciseId, String studentId) {
        return null;
    }

    /**
     * @param userId     identifier of the user that made the request. Necessary to check authorization.
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return last resolution made by the student for a given exercise
     */
    @Override
    public ExerciseResolution getLastExerciseResolutionByStudent(String userId, String exerciseId, String studentId) {
        return null;
    }

    /**
     * @param userId           identifier of the user that made the request. Necessary to check authorization.
     * @param page             index of the page
     * @param itemsPerPage     number of items per page
     * @param tags             list of tags to filter exercises
     * @param matchAllTags     if 'false' an exercise will match if at least one tag of the exercise matches one of the given list.
     *                         if 'true' the exercise must have all the tags present in the list
     * @param visibilityType   type of visibility
     * @param visibilityTarget target of the visibility, for example, if the visibility is set to course,
     *                         then this argument is used to specify the course
     * @param specialistId     to search for the exercises created by a specific specialist
     * @return list of exercises that match the given filters
     */
    @Override
    public List<Exercise> getExercises(String userId, Integer page, Integer itemsPerPage, List<String> tags, boolean matchAllTags, String visibilityType, String visibilityTarget, String specialistId) {
        return null;
    }

    @Override
    public Void addCommentToExerciseResolution(String resolutionId, Comment body) {
        return null;
    }

    @Override
    public ExerciseResolution getExerciseResolution(String resolutionId) {
        return null;
    }

    @Override
    public Void exerciseResolutionManualCorrection(String resolutionId, Float cotation) {
        return null;
    }

    @Override
    public Void deleteExerciseRubric(String rubricId) {
        return null;
    }

    @Override
    public Void updateRubric(String rubricId, ExerciseRubric rubric) {
        return null;
    }
}
