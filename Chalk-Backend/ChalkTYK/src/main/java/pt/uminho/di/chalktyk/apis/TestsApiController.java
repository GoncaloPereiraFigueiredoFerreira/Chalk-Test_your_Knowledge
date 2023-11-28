package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InlineResponse2001;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Test;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.TestResolution;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class TestsApiController implements TestsApi {

    public ResponseEntity<List<Test>> testsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Array of identifiers from the tags that will be used to filter the tests." ,schema=@Schema( defaultValue="[]")) @Valid @RequestParam(value = "tags", required = false, defaultValue="[]") List<Integer> tags
,@Parameter(in = ParameterIn.QUERY, description = "Value that defines if the exercise must have all the given tags to be retrieved." ,schema=@Schema( defaultValue="false")) @Valid @RequestParam(value = "matchAllTags", required = false, defaultValue="false") Boolean matchAllTags
,@Parameter(in = ParameterIn.QUERY, description = "Describes the type of visibility that the tests must have.  This parameter must be paired with the parameter 'visibilityTarget'  when the value is either 'institution' or 'course'. " ,schema=@Schema(allowableValues={ "public", "institution", "course" }
, defaultValue="public")) @Valid @RequestParam(value = "visibilityType", required = false, defaultValue="public") String visibilityType
,@Parameter(in = ParameterIn.QUERY, description = "Identifier of the visibility target. For example, if visibilityType='institution',  then this parameter is the identifier of the institution. " ,schema=@Schema()) @Valid @RequestParam(value = "visibilityTarget", required = false) String visibilityTarget
,@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<String> testsPost(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "visibility", required = true) Visibility visibility
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Test body
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<TestResolution> testsResolutionsResolutionIdGet(@Parameter(in = ParameterIn.PATH, description = "Test resolution identifier", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> testsTestIdDelete(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<String> testsTestIdDuplicatePost(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> testsTestIdPut(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Test body
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> testsTestIdResolutionsCorrectionPut(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.QUERY, description = "Type of correction. The correction can either be automatic or done by AI. When using AI correction, the AI will only be used to correct questions that cannot be corrected automatically, i.e., by using the solution. " ,schema=@Schema(allowableValues={ "auto", "ai" }
)) @Valid @RequestParam(value = "correctionType", required = false) String correctionType
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Integer> testsTestIdResolutionsCountGet(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.QUERY, description = "'false' to count the number of students that made a submission. 'true' to count the total number of submissions." ,schema=@Schema( defaultValue="false")) @Valid @RequestParam(value = "total", required = false, defaultValue="false") Boolean total
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<List<InlineResponse2001>> testsTestIdResolutionsGet(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> testsTestIdResolutionsPost(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("testId") Integer testId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody TestResolution body
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Boolean> testsTestIdResolutionsStudentIdCanSubmitGet(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Integer> testsTestIdResolutionsStudentIdCountGet(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<List<String>> testsTestIdResolutionsStudentIdIdsGet(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<TestResolution> testsTestIdResolutionsStudentIdLastGet(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("testId") String testId
,@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        throw new RuntimeException("Not implemented");
    }

}
