package pt.uminho.di.chalktyk.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.Test;
import pt.uminho.di.chalktyk.models.tests.TestGroup;
import pt.uminho.di.chalktyk.models.tests.TestResolution;
import pt.uminho.di.chalktyk.models.tests.TestResolutionStatus;
import pt.uminho.di.chalktyk.models.tests.TestExercise.TestExercise;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

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
     * @param visibility
     * @param courseId
     * @return duplicate identifier
     * @throws BadInputException if an error occurs when replicating any entity
     * @throws NotFoundException if no test or specialist were found with the given ids
     **/
    String duplicateTestById(String specialistId, String testId, Visibility visibility, String courseId) throws BadInputException, NotFoundException;

    /**
     * Updates a test's title
     *
     * @param testId
     * @param title
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTestTitle(String testId, String title) throws NotFoundException;

    /**
     * Updates a test's global instructions
     *
     * @param testId
     * @param globalInstructions
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTestGlobalInstructions(String testId, String globalInstructions) throws NotFoundException;

    /**
     * Updates a test's conclusion
     *
     * @param testId
     * @param conclusion
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTestConclusion(String testId, String conclusion) throws NotFoundException;

    /**
     * Updates a test's publish date
     *
     * @param testId
     * @param publishDate
     * @throws BadInputException if any property of the test is not valid.
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTestPublishDate(String testId, LocalDateTime publishDate) throws NotFoundException, BadInputException;

    /**
     * Updates a test's visibility
     *
     * @param testId
     * @param visibility
     * @throws BadInputException if any property of the test is not valid.
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTestVisibility(String testId, Visibility visibility) throws NotFoundException, BadInputException;
    
    /**
     * Updates a test's course
     *
     * @param testId
     * @param courseId
     * @throws BadInputException if any property of the test is not valid.
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTestCourse(String testId, String courseId) throws NotFoundException, BadInputException;
    
    /**
     * Updates a test's groups
     *
     * @param testId
     * @param groups
     * @throws BadInputException if any property of the test is not valid.
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTestGroups(String testId, List<TestGroup> groups) throws NotFoundException, BadInputException;

    /**
     * Updates a test's deliver date
     * Exclusive for tests of the type DeliverDateTest
     *
     * @param testId
     * @param deliverDate
     * @throws BadInputException if any property of the test is not valid.
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTestDeliverDate(String testId, LocalDateTime deliverDate) throws NotFoundException, BadInputException;

    /**
     * Updates a test's start date
     * Exclusive for tests of the type LiveTest
     *
     * @param testId
     * @param startDate
     * @throws BadInputException if any property of the test is not valid.
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTestStartDate(String testId, LocalDateTime startDate) throws NotFoundException, BadInputException;
    
    /**
     * Updates a test's duration
     * Exclusive for tests of the type LiveTest
     *
     * @param testId
     * @param duration
     * @throws BadInputException if any property of the test is not valid.
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTestDuration(String testId, long duration) throws NotFoundException, BadInputException;
    
    /**
     * Updates a test's start tolerance
     * Exclusive for tests of the type LiveTest
     *
     * @param testId
     * @param startTolerance
     * @throws BadInputException if any property of the test is not valid.
     * @throws NotFoundException if no test was found with the given id
     **/
    void updateTestStartTolerance(String testId, long startTolerance) throws NotFoundException, BadInputException;


    /**
     * Issue the automatic correction of the test resolutions
     *
     * @param testId
     * @param correctionType         Type of correction ("auto" or "ai")
     * @throws BadInputException     if the correction type is not valid. It should be 'auto' or 'ai'.
     * @throws NotFoundException     if the test or any exercise were not found
     * @throws UnauthorizedException if the test does not support the requested correction type.
     **/
    void issueTestResolutionsCorrection(String testId, String correctionType) throws NotFoundException, BadInputException, UnauthorizedException;

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
     * Update a test resolution's start date
     *
     * @param  testResId
     * @param  startDate
     * @throws NotFoundException if no test resolution was found with the given id
     * @throws BadInputException if any property of the test resolution is not valid.
     **/
    void updateTestResolutionStartDate(String testResId, LocalDateTime startDate) throws NotFoundException, BadInputException;

    /**
     * Update a test resolution's submission date
     *
     * @param  testResId
     * @param  submissionDate
     * @throws NotFoundException if no test resolution was found with the given id
     * @throws BadInputException if any property of the test resolution is not valid.
     **/
    void updateTestResolutionSubmissionDate(String testResId, LocalDateTime submissionDate) throws NotFoundException, BadInputException;

    /**
     * Update a test resolution's submission number
     *
     * @param  testResId
     * @param  submissionNr
     * @throws NotFoundException if no test resolution was found with the given id
     * @throws BadInputException if any property of the test resolution is not valid.
     **/
    void updateTestResolutionSubmissionNr(String testResId, int submissionNr) throws NotFoundException, BadInputException;

    /**
     * Update a test resolution's status
     *
     * @param  testResId
     * @param  status
     * @throws NotFoundException if no test resolution was found with the given id
     **/
    void updateTestResolutionStatus(String testResId, TestResolutionStatus status) throws NotFoundException;

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
     * @throws BadInputException if any property of the resolution is not valid
     */
    void uploadResolution(String testResId, String exeId, ExerciseResolution resolution) throws NotFoundException, BadInputException;

    /**
     * Add an exercise to a given test
     * 
     * @param  testId
     * @param  exercise
     * @param  groupIndex        index of the TestGroup to add the exercise to
     * @param  exeIndex          position in the TestGroup
     * @param  groupInstructions instructions to add if the test groups is new
     * @throws NotFoundException if no test or exercise were found
     * @throws BadInputException if any property of the exercise is not valid.
     */
    void createTestExercise(String testId, TestExercise exercise, Integer groupIndex, Integer exeIndex, String groupInstructions) throws NotFoundException, BadInputException;

    /**
     * If an exercise belongs to a test removes it from the test,
     * and deletes it.
     *
     * @param testId     test identifier
     * @param exerciseId exercise identifier
     * @throws NotFoundException if the test or exercise were not found
     * @throws BadInputException if the test has already been published (can't be changed)
     */
    void deleteExerciseFromTest(String testId, String exerciseId) throws NotFoundException, BadInputException;

    /**
     * If an exercise belongs to a test removes it from the test.
     * The exercise is not deleted. Its visibility is changed to "private".
     * 
     * @param testId     test identifier
     * @param exerciseId exercise identifier
     * @throws NotFoundException if the test or exercise were not found
     * @throws BadInputException if the test has already been published (can't be changed)
     */
    void removeExerciseFromTest(String testId, String exerciseId) throws NotFoundException, BadInputException;
}
