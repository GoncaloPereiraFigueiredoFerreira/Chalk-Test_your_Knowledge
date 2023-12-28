package pt.uminho.di.chalktyk.services;

import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;

import pt.uminho.di.chalktyk.models.exercises.*;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.users.Student;
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
     * Creates an exercise.
     *
     * @param exercise body of the exercise to be created. Regarding the metadata should
     *                 contain, at least, the specialist identifier
     * @return new exercise identifier
     * @throws BadInputException if the exercise is not formed correctly
     */
    String createExercise(Exercise exercise, ExerciseSolution solution, ExerciseRubric rubric, Visibility visibility, List<String> tagsIds) throws BadInputException;

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

    String duplicateExerciseById(String specialistId, String exerciseId, String courseId, Float points, Visibility visibility) throws NotFoundException;

    /**
     * Updates an exercise. If an object is 'null' than it is considered that it should remain the same.
     * To delete it, a specific delete method should be invoked.
     *
     * @param exerciseId identifier of the exercise to be updated
     * @param exercise   new exercise body
     * @param rubric     new exercise rubric
     * @param solution   new exercise solution
     * @param tagsIds    new list of tags
     * @param visibility new visibility
     * @param points
     * @throws UnauthorizedException if the exercise is not owned by the specialist
     * @throws NotFoundException     if the exercise was not found
     */
    void updateAllOnExercise(String exerciseId, Exercise exercise, ExerciseRubric rubric, ExerciseSolution solution, List<String> tagsIds, Visibility visibility, Float points)  throws NotFoundException, BadInputException;

    /**
     * Updates an exercise body.
     *
     * @param exerciseId
     * @param newBody    new exercise body
     * @throws NotFoundException if the test wasn't found
     * @throws BadInputException solution, rubric, institution or specialist ids where changed,
     *                           course was not found,
     *                           the rubric or solution don't belong to the new exercise body
     */
    void updateExerciseBody(String exerciseId, Exercise newBody) throws NotFoundException, BadInputException;

    void updateExerciseVisibility(String exerciseId, Visibility visibility) throws NotFoundException, BadInputException;

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
     *
     * @param exerciseId     identifier of the exercise
     * @param correctionType type of correction. Can be 'auto' or 'ai'.
     * @throws BadInputException     if the correction type is not valid. It should be 'auto' or 'ai'.
     * @throws NotFoundException     if the exercise does not exist
     * @throws UnauthorizedException if the exercise does not support the requested correction type.
     */
    void issueExerciseResolutionsCorrection(String exerciseId, String correctionType) throws BadInputException, NotFoundException, UnauthorizedException;

    /**
     * Issue the automatic correction of the exercise resolutions.
     * The correction can either be automatic or done by AI.
     * For a given exercise, it may support either, both, or none of the correction types.
     *
     * @param resolutionId   identifier of the exercise resolution
     * @param correctionType type of correction. Can be 'auto' or 'ai'.
     * @return
     * @throws BadInputException     if the correction type is not valid. It should be 'auto' or 'ai'.
     * @throws NotFoundException     if the resolution, or the exercise, or the rubric of the exercise, or the solution of the exercise does not exist
     * @throws UnauthorizedException if the exercise does not support the requested correction type.
     */
    float issueExerciseResolutionCorrection(String resolutionId, String correctionType) throws BadInputException, NotFoundException, UnauthorizedException;

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
    List<Pair<Student, ExerciseResolution>> getExerciseResolutions(String exerciseId, Integer page, Integer itemsPerPage, boolean latest);

    /**
     * Create a resolution for a specific exercise.
     *
     * @param studentId      identifier of the creator of the resolution.
     * @param exerciseId     identifier of the exercise
     * @param resolutionData new resolution
     * @return
     * @throws NotFoundException if the exercise was not found
     * @throws BadInputException if there is some problem regarding the resolution of the exercise,
     *                           like the type of resolution does not match the type of the exercise
     */
    ExerciseResolution createExerciseResolution(String studentId, String exerciseId, ExerciseResolutionData resolutionData) throws NotFoundException, BadInputException;

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
    List<ExerciseResolution> getStudentListOfExerciseResolutions(String exerciseId, String studentId) throws NotFoundException;

    /**
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return last resolution made by the student for a given exercise, or 'null' if it does not exist.
     */
    ExerciseResolution getLastExerciseResolutionByStudent(String exerciseId, String studentId);

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
    Page<Exercise> getExercises(Integer page, Integer itemsPerPage, List<String> tags, boolean matchAllTags, Visibility visibilityType, String courseId, String institutionId, String specialistId, String title, String exerciseType, boolean verifyParams) throws BadInputException, NotFoundException;

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

    /**
     * Delete exercise resolution by its id
     *
     * @param  exeResId
     * @throws NotFoundException if no exercise resolution was found with the given id
     **/
    void deleteExerciseResolutionById(String exeResId) throws NotFoundException;

    /**
     * Used to set the points of an exercise resolution.
     * @param resolutionId identifier of the resolution
     * @param points points to set
     * @throws NotFoundException if the resolution does not exist
     * @throws BadInputException if the points exceed the max points for the exercise.
     */
    void manuallyCorrectExerciseResolution(String resolutionId, float points) throws NotFoundException, BadInputException;

    void deleteExerciseRubric(String exerciseId);

    void updateExerciseRubric(String exerciseId, ExerciseRubric rubric) throws BadInputException, NotFoundException;

    void createExerciseSolution(String exerciseId, ExerciseSolution exerciseSolution) throws NotFoundException, BadInputException;

    ExerciseSolution getExerciseSolution(String exerciseId) throws NotFoundException;

    void updateExerciseSolution(String exerciseId, ExerciseSolution exerciseSolution) throws NotFoundException, BadInputException;

    void deleteExerciseSolution(String exerciseId);

    String getExerciseCourse(String exerciseId) throws NotFoundException;
    
    String getExerciseInstitution(String exerciseId) throws NotFoundException;

    /**
     * @param exerciseId identifier of the exercise
     * @return visibility of an exercise
     * @throws NotFoundException if the exercise does not exist
     */
    Visibility getExerciseVisibility(String exerciseId) throws NotFoundException;

    void updateExerciseCourse(String exerciseId, String courseId) throws NotFoundException;

    void updateExercisePoints(String exerciseId, float points) throws NotFoundException, BadInputException;
    void updateExerciseTags(String exerciseId, List<String> tagsIds) throws BadInputException, NotFoundException;

    boolean isExerciseOwner(String exerciseId, String specialistId);
}
