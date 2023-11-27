
package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InlineResponse2001;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Test;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.TestResolution;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Visibility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

@Validated
public interface TestsApi {

    @Operation(summary = "Retrieve tests.", description = "Retrieves tests that match the given filters.", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Test.class)))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation. Example: When trying to access tests from a specific course that the user does not belong to.") })
    @RequestMapping(value = "/tests",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Test>> testsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
, @Parameter(in = ParameterIn.QUERY, description = "Array of identifiers from the tags that will be used to filter the tests." ,schema=@Schema( defaultValue="[]")) @Valid @RequestParam(value = "tags", required = false, defaultValue="[]") List<Integer> tags
, @Parameter(in = ParameterIn.QUERY, description = "Value that defines if the exercise must have all the given tags to be retrieved." ,schema=@Schema( defaultValue="false")) @Valid @RequestParam(value = "matchAllTags", required = false, defaultValue="false") Boolean matchAllTags
, @Parameter(in = ParameterIn.QUERY, description = "Describes the type of visibility that the tests must have.  This parameter must be paired with the parameter 'visibilityTarget'  when the value is either 'institution' or 'course'. " ,schema=@Schema(allowableValues={ "public", "institution", "course" }
, defaultValue="public")) @Valid @RequestParam(value = "visibilityType", required = false, defaultValue="public") String visibilityType
, @Parameter(in = ParameterIn.QUERY, description = "Identifier of the visibility target. For example, if visibilityType='institution',  then this parameter is the identifier of the institution. " ,schema=@Schema()) @Valid @RequestParam(value = "visibilityTarget", required = false) String visibilityTarget
, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId
);


    @Operation(summary = "Create a test", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad input."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation.") })
    @RequestMapping(value = "/tests",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<String> testsPost(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "visibility", required = true) Visibility visibility
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Test body
);


    @Operation(summary = "Get test resolution using its id.", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TestResolution.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Test resolution not found.") })
    @RequestMapping(value = "/tests/resolutions/{resolutionId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<TestResolution> testsResolutionsResolutionIdGet(@Parameter(in = ParameterIn.PATH, description = "Test resolution identifier", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
);


    @Operation(summary = "Delete test by its id.", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Test deleted successfully."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Test not found.") })
    @RequestMapping(value = "/tests/{testId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> testsTestIdDelete(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
);


    @Operation(summary = "Duplicates the test using its identifier.", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful duplication.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Test with the given id does not exist.") })
    @RequestMapping(value = "/tests/{testId}/duplicate",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<String> testsTestIdDuplicatePost(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
);


    @Operation(summary = "Update a test", description = "This method is used to update an existing test. Check the schema", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Test updated successfully."),
        
        @ApiResponse(responseCode = "400", description = "Bad input."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Test not found.") })
    @RequestMapping(value = "/tests/{testId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> testsTestIdPut(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Test body
);


    @Operation(summary = "Issue the automatic correction of the test resolutions.", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Test not found.") })
    @RequestMapping(value = "/tests/{testId}/resolutions/correction",
        method = RequestMethod.PUT)
    ResponseEntity<Void> testsTestIdResolutionsCorrectionPut(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
, @Parameter(in = ParameterIn.QUERY, description = "Type of correction. The correction can either be automatic or done by AI. When using AI correction, the AI will only be used to correct questions that cannot be corrected automatically, i.e., by using the solution. " ,schema=@Schema(allowableValues={ "auto", "ai" }
)) @Valid @RequestParam(value = "correctionType", required = false) String correctionType
);


    @Operation(summary = "Retrieves the number of students that submitted a resolution for a specific test.", description = "- Retrieves the number of students that submitted a resolution for a specific test.  The total number of submissions can be obtained by setting the 'total' query parameter to 'true'. ", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
        
        @ApiResponse(responseCode = "404", description = "Test not found.") })
    @RequestMapping(value = "/tests/{testId}/resolutions/count",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Integer> testsTestIdResolutionsCountGet(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
, @Parameter(in = ParameterIn.QUERY, description = "'false' to count the number of students that made a submission. 'true' to count the total number of submissions." ,schema=@Schema( defaultValue="false")) @Valid @RequestParam(value = "total", required = false, defaultValue="false") Boolean total
);


    @Operation(summary = "Get all test resolutions.", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InlineResponse2001.class)))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Test not found.") })
    @RequestMapping(value = "/tests/{testId}/resolutions",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<InlineResponse2001>> testsTestIdResolutionsGet(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
, @NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
);


    @Operation(summary = "Create a test resolution", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Test resolution created successfully."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Could not find any test with the given id.") })
    @RequestMapping(value = "/tests/{testId}/resolutions",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> testsTestIdResolutionsPost(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("testId") Integer testId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody TestResolution body
);


    @Operation(summary = "Allows to check if the student can submit a resolution for the test.", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
        
        @ApiResponse(responseCode = "404", description = "Test not found.") })
    @RequestMapping(value = "/tests/{testId}/resolutions/{studentId}/can-submit",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Boolean> testsTestIdResolutionsStudentIdCanSubmitGet(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
, @Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
);


    @Operation(summary = "Retrieves the number of (resolution) submissions a student has made for a specific test.", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
        
        @ApiResponse(responseCode = "404", description = "Test not found.") })
    @RequestMapping(value = "/tests/{testId}/resolutions/{studentId}/count",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Integer> testsTestIdResolutionsStudentIdCountGet(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
, @Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
);


    @Operation(summary = "Get the list of identifiers of the student's resolutions for the given test.", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation.") })
    @RequestMapping(value = "/tests/{testId}/resolutions/{studentId}/ids",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<String>> testsTestIdResolutionsStudentIdIdsGet(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId
, @Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
);


    @Operation(summary = "Get latest test resolution made by the student.", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TestResolution.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(value = "/tests/{testId}/resolutions/{studentId}/last",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<TestResolution> testsTestIdResolutionsStudentIdLastGet(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("testId") String testId
, @Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
);

}

