package pt.uminho.di.chalktyk.services;

import org.springframework.data.util.Pair;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.*;
import pt.uminho.di.chalktyk.models.relational.ExerciseResolutionSQL;
import pt.uminho.di.chalktyk.models.relational.StudentSQL;
import pt.uminho.di.chalktyk.models.relational.VisibilitySQL;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.List;

public interface IExercisesService{

    /**
     * Get Exercise by ID
     *
     * @param exerciseId of the exercise
     * @return concrete exercise with the given ID
     **/
    Exercise getExerciseById(String exerciseId) throws NotFoundException;


    /**
     * Verify if exercise exists
     *
     * @param exerciseId of the exercise
     * @return true if exercise exists, false otherwise
     **/
    boolean exerciseExists(String exerciseId);

    /**
     * Verify is exercise is shallow
     *
     * @param exerciseId of the exercise
     * @return true if exercise is shallow, false otherwise
     **/
    boolean exerciseIsShallow(String exerciseId) throws NotFoundException;

    /**
     * Creates an exercise.
     * @param exercise body of the exercise to be created. Regarding the metadata should
     *                 contain, at least, the specialist identifier
     * @return new exercise identifier
     * @throws BadInputException if the exercise is not formed correctly
     */
    String createExercise(ConcreteExercise exercise, ExerciseRubric rubric, ExerciseSolution solution, List<String> tagsIds, VisibilitySQL visibility) throws BadInputException;

    /**
     * Delete exercise by id.
     *
     * @param exerciseId identifier of the exercise
     * @throws NotFoundException     if the exercise was not found
     */
    void deleteExerciseById(String exerciseId) throws NotFoundException;

    /**
     * Duplicates the exercise that contains the given identifier.
     * The id of the specialist, and if existent, the institution identifier
     * is added to the new exercise metadata. The visibility of the new exercise is
     * set to private, and is not associated with any course.
     * @param specialistId identifier of the specialist that wants to own the exercise
     * @param exerciseId exercise identifier
     * @throws UnauthorizedException if the exercise is not owned by the specialist
     * @throws NotFoundException if the exercise was not found
     * @return new exercise identifier
     */
    String duplicateExerciseById(String specialistId, String exerciseId) throws NotFoundException;

    /**
     * Updates an exercise. If an object is 'null' than it is considered that it should remain the same.
     * To delete it, a specific delete method should be invoked.
     * @param exerciseId identifier of the exercise to be updated
     * @param exercise new exercise body
     * @param rubric new exercise rubric
     * @param solution new exercise solution
     * @param tagsIds new list of tags
     * @param visibility new visibility
     * @throws UnauthorizedException if the exercise is not owned by the specialist
     * @throws NotFoundException if the exercise was not found
     */
    // TODO - criar metodos privados para update individual de cada componente
    public void updateAllOnExercise(String exerciseId, Exercise exercise, ExerciseRubric rubric, ExerciseSolution solution, List<String> tagsIds, VisibilitySQL visibility)  throws UnauthorizedException, NotFoundException, BadInputException;

    /**
     * Retrieves the rubric of an exercise.
     * @param exerciseId exercise identifier
     * @return rubric of the exercise or null if it doesn't exist.
     * @throws NotFoundException     if the exercise was not found
     */
    ExerciseRubric getExerciseRubric(String exerciseId) throws NotFoundException;

    /**
     * Create an exercise rubric
     * @param exerciseId exercise identifier
     * @param rubric new rubric
     * @throws UnauthorizedException if the user does not have authorization to check the rubric of the exercise.
     * @throws NotFoundException if the exercise was not found
     */
    void createExerciseRubric(String exerciseId, ExerciseRubric rubric) throws NotFoundException, BadInputException;

    /**
     * Issue the automatic correction of the exercise resolutions.
     * The correction can either be automatic or done by AI.
     * For a given exercise, it may support either, both, or none of the correction types.
     * @param userId identifier of the user that has permission to issue the correction,
     *               such as the owner of the exercise, or another specialist that belongs
     *               to the course that is associated with the exercise.
     * @param exerciseId identifier of the exercise
     * @param correctionType type of correction
     */
    void issueExerciseResolutionsCorrection(String userId, String exerciseId, String correctionType);

    /**
     * @param exerciseId identifier of an exercise
     * @param total The total number of submissions can be obtained by setting the value to 'true'.
     *              The number of students that submitted can be obtained by setting the value to 'false'
     * @return the total resolution submissions or the number of students
     *         that submitted a resolution for a specific exercise depending
     *         on the value of the 'total' parameter.
     */
    Integer countExerciseResolutions(String exerciseId, boolean total);

    /**
     * @param exerciseId   identifier of the exercise
     * @param page         index of the page
     * @param itemsPerPage number of pairs in each page
     * @param latest
     * @return list of pairs of a student and its correspondent exercise resolution for the requested exercise.
     */
    List<Pair<StudentSQL, ExerciseResolution>> getExerciseResolutions(String exerciseId, Integer page, Integer itemsPerPage, boolean latest);

    /**
     * Create a resolution for a specific exercise.
     *
     * @param exerciseId identifier of the exercise
     * @param resolution new resolution
     * @param studentId  identifier of the creator of the resolution.
     * @return
     * @throws NotFoundException if the exercise was not found
     * @throws BadInputException if there is some problem regarding the resolution of the exercise,
     *                           like the type of resolution does not match the type of the exercise
     */
    ExerciseResolution createExerciseResolution(String studentId, String exerciseId, ExerciseResolution resolution) throws NotFoundException, BadInputException;

    /**
     *
     * @param exerciseId identifier of the exercise
     * @param studentId identifier of the student
     * @return the number of resolutions a student has made for a specific exercise.
     * @throws NotFoundException if the exercise does not exist
     */
    Integer countExerciseResolutionsByStudent(String exerciseId, String studentId);

    /**
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return list of metadata of all the resolutions a student has made for an exercise.
     * @throws NotFoundException if the exercise does not exist
     */
    List<ExerciseResolutionSQL> getStudentListOfExerciseResolutionsMetadataByExercise(String exerciseId, String studentId) throws NotFoundException;

    /**
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return last resolution made by the student for a given exercise, or 'null' if it does not exist.
     */
    ExerciseResolution getLastExerciseResolutionByStudent(String exerciseId, String studentId);

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
    List<Exercise> getExercises(String userId, Integer page, Integer itemsPerPage, List<String> tags, boolean matchAllTags, String visibilityType, String courseId, String institutionId, String specialistId, String title, String exerciseType, boolean verifyParams) throws BadInputException, NotFoundException;

    /**
     * Adds a comment to an exercise resolution.
     * If the resolution already has a
     * comment associated, it will be overwritten.
     * @param resolutionId identifier of the resolution
     * @param comment body of the comment
     * @throws NotFoundException if the resolution does not exist
     * @throws BadInputException if the comment is malformed or is null.
     */
    void addCommentToExerciseResolution(String resolutionId, Comment comment) throws NotFoundException, BadInputException;

    /**
     * Deletes a comment made to an exercise resolution.
     * @param resolutionId identifier of the resolution
     * @throws NotFoundException if the resolution does not exist
     */
    void removeCommentFromExerciseResolution(String resolutionId) throws NotFoundException;

    /**
     * Gets the exercise resolution identified by the given identifier.
     * @param resolutionId identifier of the resolution
     * @return the exercise resolution identified by the given identifier.
     * @throws NotFoundException if the resolution does not exist
     */
    ExerciseResolution getExerciseResolution(String resolutionId) throws NotFoundException;

    void exerciseResolutionManualCorrection(String resolutionId, Float cotation);

    void deleteExerciseRubric(String exerciseId) throws BadInputException, NotFoundException;

    void updateExerciseRubric(String exerciseId, ExerciseRubric rubric) throws BadInputException, NotFoundException;

    void createExerciseSolution(String exerciseId, ExerciseSolution exerciseSolution) throws NotFoundException, BadInputException;

    ExerciseSolution getExerciseSolution(String exerciseId) throws NotFoundException;

    void updateExerciseSolution(String exerciseId, ExerciseSolution exerciseSolution) throws NotFoundException, BadInputException;

    void deleteExerciseSolution(String exerciseId) throws NotFoundException, BadInputException;

    /**
     * @param exerciseId identifier of the exercise
     * @return visibility of an exercise
     * @throws NotFoundException if the exercise does not exist
     */
    String getExerciseVisibility(String exerciseId) throws NotFoundException;
}
