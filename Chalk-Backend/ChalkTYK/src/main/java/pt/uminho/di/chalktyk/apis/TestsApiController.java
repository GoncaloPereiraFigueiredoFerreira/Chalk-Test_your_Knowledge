package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InlineResponse2001;
import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.Test;
import pt.uminho.di.chalktyk.models.tests.TestResolution;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.ITestsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/tests")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class TestsApiController implements TestsApi {
    private final ITestsService testsService;
    private final ISecurityService securityService;

    @Autowired
    public TestsApiController(ITestsService testsService, ISecurityService securityService){
        this.testsService = testsService;
        this.securityService = securityService;
    }

    public ResponseEntity<List<Test>> getTests(Integer page, Integer itemsPerPage, List<String> tags, Boolean matchAllTags, String visibilityType, 
                                                String visibilityTarget, String specialistId, String courseId, String institutionId, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
            Page<Test> tests = testsService.getTests(page, itemsPerPage, tags, matchAllTags, visibilityType, specialistId, courseId, institutionId, jwt, false);
            return ResponseEntity.ok().body(tests.toList());
        } catch (UnauthorizedException e){
            return new ExceptionResponseEntity<List<Test>>().createRequest(e);
        } catch (BadInputException e) {
            return new ExceptionResponseEntity<List<Test>>().createRequest(e);
        } catch (NotFoundException e) {
            return new ExceptionResponseEntity<List<Test>>().createRequest(e);
        }
    }

    // TODO: adicionar à interface
    public ResponseEntity<Test> getTestById(String testId, String jwt){
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<String> createTest(Visibility visibility, Test body, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<TestResolution> getTestResolutionById(String resolutionId, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> deleteTestById(String testId, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<String> duplicateTestById(String testId, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> updateTest(String testId, Test body, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> automaticCorrection(String testId, String correctionType, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Integer> countStudentsTestResolutions(String testId, Boolean total, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<List<InlineResponse2001>> getTestResolutions(String testId, Integer page, Integer itemsPerPage, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> createTestResolution(String testId, TestResolution body, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Boolean> canStudentSubmitResolution(String testId, String studentId, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Integer> countStudentSubmissionsForTest(String testId, String studentId, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<List<String>> getStudentTestResolutionsIds(String testId, String studentId, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<TestResolution> getStudentLastResolution(String testId, String studentId, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
        } 
        catch (UnauthorizedException e) {

        }
        throw new RuntimeException("Not implemented");
    }

}
