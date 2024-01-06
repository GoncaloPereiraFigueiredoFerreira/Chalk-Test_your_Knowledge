
package pt.uminho.di.chalktyk.apis;

import org.springframework.web.bind.annotation.*;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InlineResponse2001;
import pt.uminho.di.chalktyk.dtos.CreateTestExerciseDTO;
import pt.uminho.di.chalktyk.dtos.DuplicateTestDTO;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.Test;
import pt.uminho.di.chalktyk.models.tests.TestGroup;
import pt.uminho.di.chalktyk.models.tests.TestResolution;
import pt.uminho.di.chalktyk.models.tests.TestTag;
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

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

@Validated
public interface TestsApi {

    @Operation(summary = "Retrieve tests.", description = "Retrieves tests that match the given filters.", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Test.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation. Example: When trying to access tests from a specific course that the user does not belong to.")
    })
    @RequestMapping(value = "",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Test>> getTests(@NotNull @Parameter(in = ParameterIn.QUERY, description = "", required = true, schema = @Schema()) @Valid @RequestParam(value = "page", required = true) Integer page,
                                        @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "", required = true, schema = @Schema(allowableValues = {"1", "50"}, minimum = "1", maximum = "50"
                                        )) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage,
                                        @Parameter(in = ParameterIn.QUERY, description = "Array of identifiers from the tags that will be used to filter the tests.", schema = @Schema(defaultValue = "[]")) @Valid @RequestParam(value = "tags", required = false, defaultValue = "[]") List<String> tags,
                                        @Parameter(in = ParameterIn.QUERY, description = "Value that defines if the exercise must have all the given tags to be retrieved.", schema = @Schema(defaultValue = "false")) @Valid @RequestParam(value = "matchAllTags", required = false, defaultValue = "false") Boolean matchAllTags,
                                        @Parameter(in = ParameterIn.QUERY, description = "Describes the type of visibility that the tests must have.  This parameter must be paired with the parameter 'visibilityTarget'  when the value is either 'institution' or 'course'. ", schema = @Schema(allowableValues = {"PUBLIC", "INSTITUTION", "COURSE","NOT_LISTED","PRIVATE"},
                                                defaultValue = "PUBLIC")) @Valid @RequestParam(value = "visibilityType", required = false, defaultValue = "PUBLIC") String visibilityType,
                                        @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "title", required = false) String title,
                                        @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId,
                                        @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "courseId", required = false) String courseId,
                                        @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId,
                                        @CookieValue("chalkauthtoken") String jwt);

    @Operation(summary = "Retrieves tags present in a test", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/tags",
            method = RequestMethod.GET)
    ResponseEntity<List<TestTag>> getTestTags(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId);

    @Operation(summary = "Create a test", description = "", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad input."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation.")
    })
    @RequestMapping(value = "",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<String> createTest(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema(example = "{\n" +
            "    \"title\": \"Teste exemplo\",\n" +
            "    \"globalInstructions\": \"Não fazer batota\",\n" +
            "    \"globalPoints\": 10,\n" +
            "    \"conclusion\": \"Parabéns \",\n" +
            "    \"creationDate\": \"2024-01-04T16:30:05.970Z\",\n" +
            "    \"publishDate\": \"2024-01-10T16:30:05.970Z\",\n" +
            "    \"visibility\": \"public\",\n" +
            "    \"groups\": [\n" +
            "      {\n" +
            "        \"groupInstructions\": \"string\",\n" +
            "        \"groupPoints\": 10,\n" +
            "        \"exercises\": [\n" +
            "          {\n" +
            "            \"points\": 10,\n" +
            "            \"id\": \"id\",\n" +
            "            \"Type\": \"reference\"\n" +
            "          },\n" +
            "          \n" +
            "          {\n" +
            "            \"points\": 10,\n" +
            "          \"exercise\":{\n" +
            "           \"title\":\"Exemplo Resposta Aberta\",\n" +
            "           \"type\": \"OA\",\n" +
            "           \"visibility\":\"public\",\n" +
            "           \"statement\":{\n" +
            "               \"text\":\"Qual é o sentido da vida?\",\n" +
            "               \"imagePath\":\"https://cdn.flash.pt/images/2019-10/img_828x523$2019_10_15_13_49_46_227057_im_637409554131662405.png\",\n" +
            "               \"imagePosition\":\"down\"\n" +
            "           },\n" +
            "            \"course\": {\n" +
            "               \"id\" : null\n" +
            "           }\n" +
            "            },\n" +
            "            \"Type\": \"concrete\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ],\n" +
            "    \"courseId\": \"id\",\n" +
            "    \"institutionId\": null\n" +
            "  }")) @Valid @RequestBody Test body,
                                      @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get test resolution using its id.", description = "", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TestResolution.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Test resolution not found.")
    })
    @RequestMapping(value = "/resolutions/{resolutionId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<TestResolution> getTestResolutionById(@Parameter(in = ParameterIn.PATH, description = "Test resolution identifier", required = true, schema = @Schema()) @PathVariable("resolutionId") String resolutionId,
                                                         @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Delete test by its id.", description = "", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Test not found.")
    })
    @RequestMapping(value = "/{testId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTestById(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required = true, schema = @Schema()) @PathVariable("testId") String testId,
                                        @CookieValue("chalkauthtoken") String jwt);

    @Operation(summary = "Get test by its id.", description = "", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Test not found.")
    })
    @RequestMapping(value = "/{testId}",
            method = RequestMethod.GET)
    ResponseEntity<Test> getTest(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required = true, schema = @Schema()) @PathVariable("testId") String testId,
                                        @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Duplicates the test using its identifier.", description = "", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful duplication.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Test with the given id does not exist.")
    })
    @RequestMapping(value = "/{testId}/duplicate",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<String> duplicateTestById(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required = true, schema = @Schema()) @PathVariable("testId") String testId,
                                             @CookieValue("chalkauthtoken") String jwt,
                                             @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody DuplicateTestDTO duplicateTestDTO);


    /*
    @Operation(summary = "Update a test", description = "This method is used to update an existing test. Check the schema", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Test updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad input."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Test not found.")
    })
    @RequestMapping(value = "/{testId}",
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTest(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required = true, schema = @Schema()) @PathVariable("testId") String testId,
                                    @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Test body,
                                    @CookieValue("chalkauthtoken") String jwt);*/


    @Operation(summary = "Issue the automatic correction of the test resolutions.", description = "", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Test not found.")
    })
    @RequestMapping(value = "/{testId}/resolutions/correction",
            method = RequestMethod.PUT)
    ResponseEntity<Void> automaticCorrection(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required = true, schema = @Schema()) @PathVariable("testId") String testId,
                                             @Parameter(in = ParameterIn.QUERY, description = "Type of correction. The correction can either be automatic or done by AI. When using AI correction, the AI will only be used to correct questions that cannot be corrected automatically, i.e., by using the solution. ", schema = @Schema(allowableValues = {"auto", "ai"}
                                             )) @Valid @RequestParam(value = "correctionType", required = false) String correctionType,
                                             @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Retrieves the number of students that submitted a resolution for a specific test.", description = "- Retrieves the number of students that submitted a resolution for a specific test.  The total number of submissions can be obtained by setting the 'total' query parameter to 'true'. ", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "Test not found.")
    })
    @RequestMapping(value = "/{testId}/resolutions/count",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Integer> countStudentsTestResolutions(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required = true, schema = @Schema()) @PathVariable("testId") String testId,
                                                         @Parameter(in = ParameterIn.QUERY, description = "'false' to count the number of students that made a submission. 'true' to count the total number of submissions.", schema = @Schema(defaultValue = "false")) @Valid @RequestParam(value = "total", required = false, defaultValue = "false") Boolean total,
                                                         @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get all test resolutions.", description = "", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval.",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InlineResponse2001.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Test not found.")
    })
    @RequestMapping(value = "/{testId}/resolutions",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<TestResolution>> getTestResolutions(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required = true, schema = @Schema()) @PathVariable("testId") String testId,
                                                            @NotNull @Parameter(in = ParameterIn.QUERY, description = "", required = true, schema = @Schema()) @Valid @RequestParam(value = "page", required = true) Integer page,
                                                            @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "", required = true, schema = @Schema(allowableValues = {"1", "50"}, minimum = "1", maximum = "50"
                                                            )) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage,
                                                            @CookieValue("chalkauthtoken") String jwt);

/*
    @Operation(summary = "Create a test resolution", description = "", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test resolution created successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Could not find any test with the given id.")
    })
    @RequestMapping(value = "/{testId}/resolutions",
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Void> createTestResolution(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("testId") String testId,
                                              @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody TestResolution body,
                                              @CookieValue("chalkauthtoken") String jwt);*/


    /*
    @Operation(summary = "Allows to check if the student can submit a resolution for the test.", description = "", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Test not found.")
    })
    @RequestMapping(value = "/{testId}/resolutions/{studentId}/can-submit",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Boolean> canStudentSubmitResolution(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required = true, schema = @Schema()) @PathVariable("testId") String testId,
                                                       @Parameter(in = ParameterIn.PATH, description = "student identifier", required = true, schema = @Schema()) @PathVariable("studentId") String studentId,
                                                       @CookieValue("chalkauthtoken") String jwt);*/


    @Operation(summary = "Retrieves the number of (resolution) submissions a student has made for a specific test.", description = "", tags = {"tests"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "Test not found.")
    })
    @RequestMapping(value = "/{testId}/resolutions/{studentId}/count",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Integer> countStudentSubmissionsForTest(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required = true, schema = @Schema()) @PathVariable("testId") String testId,
                                                           @Parameter(in = ParameterIn.PATH, description = "student identifier", required = true, schema = @Schema()) @PathVariable("studentId") String studentId,
                                                           @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get the list of identifiers of the student's resolutions for the given test.", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success.",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = String.class
                        )))),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation.") })
    @RequestMapping(value = "/{testId}/resolutions/{studentId}/ids",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<String>> getStudentTestResolutionsIds(@Parameter(in = ParameterIn.PATH, description = "Test identifier", required=true, schema=@Schema()) @PathVariable("testId") String testId,
                                                              @Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId,
                                                              @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get latest test resolution made by the student.", description = "", tags={ "tests" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TestResolution.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(value = "/{testId}/resolutions/{studentId}/last",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<TestResolution> getStudentLastResolution(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("testId") String testId,
                                                            @Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("studentId") String studentId,
                                                            @CookieValue("chalkauthtoken") String jwt);

    @Operation(summary = "Updates a test's title.", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/title",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestTitle(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody String title);

    @Operation(summary = "Updates a test's global instructions.", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/globalInstructions",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestGlobalInstructions(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody String globalInstructions);

    @Operation(summary = "Updates a test's conclusion.", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/conclusion",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestConclusion(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody String conclusion);

    @Operation(summary = "Updates a test's publish date", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/publishDate",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestPublishDate(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody LocalDateTime publishDate);

    @Operation(summary = "Updates a test's visibility", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/visibility",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestVisibility(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody Visibility visibility);

    @Operation(summary = "Updates a test's course", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/course",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestCourse(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody String courseId);

    @Operation(summary = "Updates a test's groups", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/groups",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestGroups(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody List<TestGroup> groups);

    @Operation(summary = "Updates a specific group in a test", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/groups/{groupIndex}",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestGroup(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("groupIndex") Integer groupIndex,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody TestGroup group);


    @Operation(summary = "Updates a test's deliver date", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/deliverDate",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestDeliverDate(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody LocalDateTime deliverDate);

    @Operation(summary = "Updates a test's start date", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/startDate",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestStartDate(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody LocalDateTime startDate);

    @Operation(summary = "Updates a test's duration", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/duration",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestDuration(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody long duration);

    @Operation(summary = "Updates a test's start tolerance", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test updated Successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/startTolerance",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestStartTolerance(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody long startTolerance);

    @Operation(summary = "Uploads a resolution for a specific exercise on a given test", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/resolutions/{resolutionId}/exercise",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateTestExerciseResolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("resolutionId") String testResolutionId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody ExerciseResolution exerciseResolution);


    @Operation(summary = "Add an exercise to a given test", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/createExercise",
            method = RequestMethod.PUT)
    ResponseEntity<String> createTestExercise(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody CreateTestExerciseDTO createTestExerciseDTO);


    @Operation(summary = "If an exercise belongs to a test removes it from the test, and deletes it.", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/deleteExercise",
            method = RequestMethod.PUT)
    ResponseEntity<Void> deleteExerciseFromTest(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody String exerciseId);

    @Operation(summary = "If an exercise belongs to a test removes it from the test.", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{testId}/removeExercise",
            method = RequestMethod.PUT)
    ResponseEntity<Void> removeExerciseFromTest(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody String exerciseId);

    @Operation(summary = "Create an initial test resolution, indicating that a test is starting", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Test started successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            value = "/{testId}/resolutions/start",
            method = RequestMethod.POST)
    ResponseEntity<String> startTest(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("testId") String testId);

    @Operation(summary = "Delete test resolution by its id", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/resolutions/{resolutionId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteTestResolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("resolutionId") String testId);


    @Operation(summary = "Issue the automatic correction of a single test resolution", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/resolutions/{resolutionId}/automaticCorrection",
            method = RequestMethod.PUT)
    ResponseEntity<Void> automaticCorrectionSingleResolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("resolutionId") String resolutionId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody String correctionType);

    @Operation(summary = "Submits a test resolution and revises it (with 'auto' correction type)", description = "", tags={ "tests" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/resolutions/{resolutionId}/submit",
            method = RequestMethod.PUT)
    ResponseEntity<Void> submitTestResolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("resolutionId") String resolutionId);
}

