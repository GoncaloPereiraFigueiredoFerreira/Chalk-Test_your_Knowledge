package pt.uminho.di.chalktyk.services;

import java.util.List;

import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolution;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

public interface ITestResolutionsService {
    /**
     * Creates a test resolution.
     * @param resolution test resolution properties
     * @return identifier of the new test resolution
     * @throws BadInputException if any property of the test resolution is not valid.
     */
    String createTestResolution(TestResolution resolution) throws BadInputException;

    /**
     * Gets test resolution
     * @param resolutionId identifier of the test resolution
     * @return test resolution
     * @throws NotFoundException if no test resolution was found with the given id
     */
    TestResolution getTestResolutionById(String resolutionId) throws NotFoundException;

    /**
     * Checks if a test resolution exists with the given id.
     * @param resolutionId identifier of the test resolution
     * @return 'true' if a test resolution exists with the given id
     */
    boolean existsTestResolutionById(String resolutionId);

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
}