package pt.uminho.di.chalktyk.services;

import java.util.List;

import org.springframework.data.domain.Page;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.tests.Test;
import pt.uminho.di.chalktyk.models.tests.TestResolution;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

public interface ITestsService {
    /**
     * Retrieves tests that match the given filters. Necessary to check authorization.
     *
     * @param page
     * @param itemsPerPage   maximum items in a page
     * @param tags           Array of identifiers from the tags that will be used to filter the tests
     * @param matchAllTags   Value that defines if the exercise must have all the given tags to be retrieved
     * @param visibilityType Describes the type of visibility that the tests must have.  This parameter must be paired with the parameter 'visibilityTarget'  when the value is either 'institution' or 'course'.
     * @param courseId       to search for a test from a specific course
     * @param institutionId  to search for a test from a specific institution
     * @param title          to search for a test title
     * @param specialistId
     * @param verifyParams   if 'true' then verify if parameters exist in the database (example: verify if specialist exists),
     *                       *                         'false' does not verify database logic
     * @return page of tests
     **/
     Page<Test> getTests(Integer page, Integer itemsPerPage, List<String> tags, Boolean matchAllTags, String visibilityType, String specialistId, String courseId, String institutionId, String title, boolean verifyParams) throws BadInputException, NotFoundException;

    /**
     * Get test using its id
     *
     * @param testId
     * @return test
     * @throws NotFoundException if no test was found with the given id
     **/
    Test getTestById(String testId) throws NotFoundException;

    /**
     * Create a test
     *
     * @param body
     * @return test identifier
     * @throws BadInputException if any property of the test is not valid.
     * @throws NotFoundException if any exercise referenced in the test is not found
     **/
    String createTest(Test body) throws BadInputException, NotFoundException;

    /**
     * Delete test by its id
     *
     * @param testId
     * @throws NotFoundException if no test was found with the given id
     **/
    void deleteTestById(String testId) throws NotFoundException;

    /**
     * Duplicates the test using its identifier
     * id and specialist, institution are changed (to correspond the new specialist)
     * visibility is set to private
     * course is set to null
     *
     * @param specialistId
     * @param testId
     * @return duplicate identifier
     * @throws BadInputException if an error occurs when replicating any entity
     * @throws NotFoundException if no test or specialist were found with the given ids
     **/
    String duplicateTestById(String specialistId, String testId) throws BadInputException, NotFoundException;

    /**
     * Update a test
     *
     * @param testId
     * @param body
     * @throws BadInputException if any property of the test is not valid.
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTest(String testId, Test body) throws BadInputException, NotFoundException;

    /**
     * Issue the automatic correction of the test resolutions
     *
     * @param testId
     * @param correctionType Type of correction ("auto" or "ai")
     **/
    void automaticCorrection(String testId, String correctionType);

    /**
     * Retrieves the number of students that submitted a resolution for a specific test
     *
     * @param testId
     * @param total 'false' to count the number of students that made a submission. 'true' to count the total number of submissions
     * @return number of students
     * @throws NotFoundException if no test was found with the given id
     **/
    Integer countStudentsTestResolutions(String testId, Boolean total) throws NotFoundException;

    /**
     * Get all test resolutions
     *
     * @param testId
     * @param page
     * @param itemsPerPage
     * @return page with resolutions
     * @throws NotFoundException if no test was found with the given id
     **/
    List<TestResolution> getTestResolutions(String testId, Integer page, Integer itemsPerPage) throws NotFoundException;

    /**
     * Get test resolution using its id
     *
     * @param resolutionId
     * @return test resolution
     * @throws NotFoundException if no test resolution was found with the given id
     **/
    TestResolution getTestResolutionById(String resolutionId) throws NotFoundException;

    /**
     * Create an initial test resolution, indicating that a test is starting
     *
     * @param testId
     * @param studentId
     * @return test resolution identifier
     * @throws BadInputException if any property of the test resolution is not valid.
     * @throws NotFoundException if no test was found with the given id
     **/
    String startTest(String testId, String studentId) throws BadInputException, NotFoundException;

    /**
     * Create a test resolution
     *
     * @param testId
     * @return test resolution identifier
     * @throws BadInputException if any property of the test resolution is not valid.
     * @throws NotFoundException if no test was found with the given id
     **/
    TestResolution createTestResolution(String testId, TestResolution body) throws BadInputException, NotFoundException;

    /**
     * Delete test resolution by its id
     *
     * @param  resolutionId
     * @throws NotFoundException if no test resolution was found with the given id
     **/
    void deleteTestResolutionById(String resolutionId) throws NotFoundException;

    /**
     * Update a test resolution
     *
     * @param  testResId
     * @param  resolution
     * @throws BadInputException if any property of the test resolution is not valid.
     * @throws NotFoundException if no test resolution was found with the given id
     **/
    void updateTestResolution(String testResId, TestResolution resolution) throws BadInputException, NotFoundException;

    /**
     * Allows to check if the student can submit a resolution for the test
     *
     * @param testId
     * @param studentId
     * @return True or False
     * @throws NotFoundException if no test or student was found with the given id
     **/
    Boolean canStudentSubmitResolution(String testId, String studentId) throws NotFoundException;

    /**
     * Retrieves the number of (resolution) submissions a student has made for a specific test
     *
     * @param testId
     * @param studentId
     * @return number of submissions the student made
     * @throws NotFoundException if no test or student was found with the given ids
     **/
    Integer countStudentSubmissionsForTest(String testId, String studentId) throws NotFoundException;

    /**
     * Get the list of identifiers of the student's resolutions for the given test
     *
     * @param testId
     * @param studentId
     * @return list of identifiers of the student's resolution
     * @throws NotFoundException if no test or student was found with the given ids
     **/
    List<String> getStudentTestResolutionsIds(String testId, String studentId) throws NotFoundException;

    /**
     * Get latest test resolution made by the student
     *
     * @param testId
     * @param studentId
     * @return latest submission made by the student
     * @throws NotFoundException if no test or student was found with the given ids
     **/
    TestResolution getStudentLastResolution(String testId, String studentId) throws NotFoundException;

    /**
     * Get latest test resolution made by the student
     *
     * @param exeResId  exercise resolution identifier
     * @param testResId test resolution identifier
     * @param points    points attributed
     * @param comment   additional comments made by the specialist
     * @throws NotFoundException if no exercise resolution was found
     **/
    void manualCorrectionForExercise(String exeResId, String testResId, Float points, String comment) throws NotFoundException;

    /**
     * Uploads a resolution for a specific exercise on a given test
     * 
     * @param  testResId
     * @param  exeId
     * @param  resolution
     * @throws NotFoundException if no test or exercise were found
     */
    void uploadResolution(String testResId, String exeId, ExerciseResolution resolution) throws NotFoundException;

    /**
     * If an exercise belongs to a test removes it from the test,
     * and deletes it.
     *
     * @param exerciseId exercise identifier
     * @return identifier of the exercise from where it was removed, or
     * 'null' if it does not belong to any test.
     * @throws NotFoundException if the exercise does not exist
     */
    String deleteExerciseFromTest(String exerciseId) throws NotFoundException;

    /**
     * If an exercise belongs to a test removes it from the test.
     * The exercise is not deleted. Its visibility is changed to "private".
     * @param exerciseId exercise identifier
     * @return identifier of the exercise from where it was removed, or
     * 'null' if it does not belong to any test.
     * @throws NotFoundException if the exercise does not exist
     */
    String removeExerciseFromTest(String exerciseId) throws NotFoundException;
    
    /**
     * Gets the exercise resolution identified from the test resolution and the exercise.
     * @param exerciseId    identifier of the exercise
     * @param testResId     identifier of the test resolution
     * @return the exercise resolution identified by the given identifier.
     * @throws NotFoundException if the resolution does not exist
     */
    ExerciseResolution getExerciseResolution(String exerciseId, String testResId) throws NotFoundException;

    /**
     * Delete all exercise resolutions related to a certain test resolution.
     *
     * @param  testResId            identifier of the test resolution
     * @throws NotFoundException    if the test resolution was not found
     */
    void deleteAllExerciseResolutionByTestResolutionId(String testResId) throws NotFoundException;
}
