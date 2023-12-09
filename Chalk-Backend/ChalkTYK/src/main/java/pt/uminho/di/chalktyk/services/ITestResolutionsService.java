package pt.uminho.di.chalktyk.services;

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
}
