package pt.uminho.di.chalktyk.services;

import java.util.List;

import org.springframework.data.domain.Page;

import pt.uminho.di.chalktyk.models.nonrelational.tests.Test;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolution;
import pt.uminho.di.chalktyk.models.relational.Visibility;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

public interface ITestsService {
    /**
     * Retrieves tests that match the given filters
     *
     * @param page
     * @param itemsPerPage maximum items in a page
     * @param tags Array of identifiers from the tags that will be used to filter the tests
     * @param matchAllTags Value that defines if the exercise must have all the given tags to be retrieved
     * @param visibilityType Describes the type of visibility that the tests must have.  This parameter must be paired with the parameter 'visibilityTarget'  when the value is either 'institution' or 'course'.
     * @param visibilityTarget institute or course targeted
     * @param specialistId
     * @return page of tests
     **/
     Page<Test> getTests(Integer page, Integer itemsPerPage, List<Integer> tags, Boolean matchAllTags, String visibilityType, String visibilityTarget, String specialistId);

    /**
     * Create a test
     *
     * @param visibility
     * @param body
     * @return test identifier
     * @throws BadInputException if any property of the test is not valid.
     **/
    String createTest(Visibility visibility, Test body) throws BadInputException;

    /**
     * Get test resolution using its id
     *
     * @param resolutionId
     * @return test resolution
     * @throws NotFoundException if no test resolution was found with the given id
     **/
    TestResolution getTestResolutionById(String resolutionId) throws NotFoundException;

    /**
     * Delete test by its id
     *
     * @param testId
     **/
    void deleteTestById(String testId);

    /**
     * Duplicates the test using its identifier
     *
     * @param testId
     * @return duplicate identifier
     **/
    String duplicateTestById(String testId);

    /**
     * Update a test
     *
     * @param testId
     * @param body
     **/
    void updateTest(String testId, Test body);

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
     **/
    Integer countStudentsTestResolutions(String testId, Boolean total);

    /**
     * Get all test resolutions
     *
     * @param testId
     * @param page
     * @param itemsPerPage
     * @return page with resolutions
     **/
    Page<TestResolution> getTestResolutions(String testId, Integer page, Integer itemsPerPage);

    /**
     * Create a test resolution
     *
     * @param testId
     * @param studentId
     * @throws BadInputException if any property of the test resolution is not valid.
     **/
    void createTestResolution(String testId, TestResolution body) throws BadInputException;

    /**
     * Allows to check if the student can submit a resolution for the test
     *
     * @param testId
     * @param studentId
     * @return True or False
     **/
    Boolean canStudentSubmitResolution(String testId, String studentId);

    /**
     * Retrieves the number of (resolution) submissions a student has made for a specific test
     *
     * @param testId
     * @param studentId
     * @return number of submissions the student made
     **/
    Integer countStudentSubmissionsForTest(String testId, String studentId);

    /**
     * Get the list of identifiers of the student's resolutions for the given test
     *
     * @param testId
     * @param studentId
     * @return list of identifiers of the student's resolution
     **/
    List<String> getStudentTestResolutionsIds(String testId, String studentId);

    /**
     * Get latest test resolution made by the student
     *
     * @param testId
     * @param studentId
     * @return latest submission made by the student
     **/
    TestResolution getStudentLastResolution(String testId, String studentId);
}
