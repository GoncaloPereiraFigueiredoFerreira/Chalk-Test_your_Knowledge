package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InlineResponse2001;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.TestResolution;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Visibility;
import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.models.tests.Test;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.ITestsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
            securityService.validateJWT(jwt);
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

    public ResponseEntity<String> createTest(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "visibility", required = true) Visibility visibility
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Test body
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<TestResolution> getTestResolutionById(@Parameter(in = ParameterIn.PATH, description = "Test resolution identifier", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> deleteTestById(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<String> duplicateTestById(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> updateTest(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Test body
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> automaticCorrection(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.QUERY, description = "Type of correction. The correction can either be automatic or done by AI. When using AI correction, the AI will only be used to correct questions that cannot be corrected automatically, i.e., by using the solution. " ,schema=@Schema(allowableValues={ "auto", "ai" }
)) @Valid @RequestParam(value = "correctionType", required = false) String correctionType
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Integer> countStudentsTestResolutions(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.QUERY, description = "'false' to count the number of students that made a submission. 'true' to count the total number of submissions." ,schema=@Schema( defaultValue="false")) @Valid @RequestParam(value = "total", required = false, defaultValue="false") Boolean total
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<List<InlineResponse2001>> getTestResolutions(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> createTestResolution(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("testId") Integer testId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody TestResolution body
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Boolean> canStudentSubmitResolution(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Integer> countStudentSubmissionsForTest(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<List<String>> getStudentTestResolutionsIds(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<TestResolution> getStudentLastResolution(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

}
