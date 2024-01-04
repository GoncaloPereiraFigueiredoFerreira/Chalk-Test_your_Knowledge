
package pt.uminho.di.chalktyk.apis;

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
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import pt.uminho.di.chalktyk.dtos.CreateExerciseDTO;
import pt.uminho.di.chalktyk.dtos.ListPairStudentExerciseResolution;
import pt.uminho.di.chalktyk.dtos.UpdateExerciseDTO;
import pt.uminho.di.chalktyk.models.exercises.*;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksData;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksExercise;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksOptionsExercise;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksRubric;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceData;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceExercise;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceRubric;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerData;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerExercise;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;

import java.util.List;
import java.util.Set;

@Validated
public interface ExercisesApi {
    @Operation(summary = "Get exercise using its identifier.", description = "", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise retrieved successfully.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(oneOf = {
                                    MultipleChoiceExercise.class,
                                    OpenAnswerExercise.class,
                                    FillTheBlanksExercise.class,
                                    FillTheBlanksOptionsExercise.class
                            }))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            produces = { "application/json" },
            value = "/{exerciseId}",
            method = RequestMethod.GET)
    ResponseEntity<Exercise> getExerciseById(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId);

    @Operation(summary = "Get exercise using its identifier.",
            description = "Get exercise using its identifier. Returns the identifier of the exercise.",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise created successfully.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateExerciseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            produces = { "application/json" },
            value = "",
            method = RequestMethod.POST)
    ResponseEntity<String> createExercise(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody CreateExerciseDTO createExerciseDTO);

    @Operation(summary = "Delete exercise by its id.", description = "", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/{exerciseId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteExerciseById(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId);

    @Operation(summary = "Duplicates the exercise using its identifier.", description = "", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful duplication.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise does not exist.") })
    @RequestMapping(value = "/{exerciseId}/duplicate",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<String> duplicateExerciseById(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.QUERY, required = false) @RequestParam(value = "courseId", required = false) String courseId,
            @Parameter(in = ParameterIn.QUERY, required = false) @RequestParam(value = "visibility", required = false) String visibility
    );

    @Operation(summary = "Updates an exercise.",
            description = "Updates an exercise. If an object is 'null' than it is considered that it should remain the same." +
                          "To delete it, a specific delete method should be invoked.",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad properties."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{exerciseId}",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateAllOnExercise(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody UpdateExerciseDTO updateExerciseDTO);

    @Operation(summary = "Updates the body of an exercise.",
            description = "",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad properties."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{exerciseId}/body",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateExerciseBody(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.DEFAULT, required = true,
                    schema = @Schema(oneOf = {
                            MultipleChoiceExercise.class,
                            OpenAnswerExercise.class,
                            FillTheBlanksExercise.class,
                            FillTheBlanksOptionsExercise.class
                    }))
            @RequestBody Exercise newBody);

    @Operation(summary = "Updates the visibility of an exercise.",
            description = "",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise updated successfully."),
            @ApiResponse(responseCode = "400", description = "Not a valid visibility."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{exerciseId}/visibility",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateExerciseVisibility(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody Visibility visibility);

    @Operation(summary = "Updates the rubric of an exercise.",
            description = "",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise updated successfully."),
            @ApiResponse(responseCode = "400", description = "Not a valid rubric."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{exerciseId}/rubric",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateExerciseRubric(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.DEFAULT, required = true,
                    schema = @Schema(oneOf = {
                            MultipleChoiceRubric.class,
                            OpenAnswerRubric.class,
                            FillTheBlanksRubric.class
                    }))
            @RequestBody ExerciseRubric rubric);

    @Operation(summary = "Updates the solution of an exercise.",
            description = "",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise updated successfully."),
            @ApiResponse(responseCode = "400", description = "Not a valid solution."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{exerciseId}/solution",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateExerciseSolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.DEFAULT, required = true,
                    schema = @Schema(oneOf = {
                            MultipleChoiceData.class,
                            OpenAnswerData.class,
                            FillTheBlanksData.class
                    }))
            @RequestBody ExerciseResolutionData solution);

    @Operation(summary = "Updates the course of an exercise.",
            description = "",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise updated successfully."),
            @ApiResponse(responseCode = "400", description = "Not a valid course. Specialist might not be associated with the course."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{exerciseId}/course",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateExerciseCourse(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.DEFAULT, description = "Can be null", required = true) @RequestBody(required = false) String courseId);

    @Operation(summary = "Updates the tags of an exercise.",
            description = "",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid tag(s)."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{exerciseId}/tags",
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateExerciseTags(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody List<String> tagsIds);


    @Operation(summary = "Retrieves the rubric of an exercise.", description = "Retrieve exercise rubric. 'null' is returned if a rubric does not exist.", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieval successful.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(oneOf = {
                                    MultipleChoiceRubric.class,
                                    OpenAnswerRubric.class,
                                    FillTheBlanksRubric.class
                            }))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise does not exist.") })
    @RequestMapping(value = "/{exerciseId}/rubric",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<ExerciseRubric> getExerciseRubric(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId);

    @Operation(summary = "Creates the rubric of an exercise.",
            description = "",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise rubric created successfully."),
            @ApiResponse(responseCode = "400", description = "Not a valid rubric."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{exerciseId}/rubric",
            method = RequestMethod.POST)
    ResponseEntity<Void> createExerciseRubric(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.DEFAULT, required = true,
                    schema = @Schema(oneOf = {
                            MultipleChoiceRubric.class,
                            OpenAnswerRubric.class,
                            FillTheBlanksRubric.class
                    }))
            @RequestBody ExerciseRubric rubric);

    @Operation(summary = "Delete exercise rubric by exercise id.", description = "", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise rubric deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/{exerciseId}/rubric",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteExerciseRubric(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId);

    @Operation(summary = "Requests that the correction of the exercise resolutions be done autonomously.", 
            description = "Requests that the correction of the exercise resolutions be done autonomously. " +
                    "The correction can either be automatic or done by AI. " +
                    "For a given exercise, it may support either, both, or none of the correction types.", 
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "400", description = "The requested type of correction is not supported for the given exercise."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/{exerciseId}/resolutions/correction",
            method = RequestMethod.PUT)
    ResponseEntity<Void> issueExerciseResolutionsCorrection(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.QUERY, description = "Type of correction. The correction can either " +
                    "be automatic or done by AI. For a given exercise, it may support either," +
                    " both, or none of the correction types. ",
                    schema=@Schema(allowableValues={ "auto", "ai" })) @Valid
            @RequestParam(value = "correctionType") String correctionType);

    @Operation(summary = "Requests that the correction of the exercise resolution be done autonomously.",
            description = "Requests that the correction of the exercise resolutions be done autonomously. " +
                    "The correction can either be automatic or done by AI. " +
                    "For a given exercise, it may support either, both, or none of the correction types.",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "400", description = "The requested type of correction is not supported for the given exercise."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/resolutions/{resolutionId}/correction",
            method = RequestMethod.PUT)
    ResponseEntity<Void> issueExerciseResolutionCorrection(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("resolutionId") String resolutionId,
            @Parameter(in = ParameterIn.QUERY, description = "Type of correction. The correction can either " +
                    "be automatic or done by AI. For a given exercise, it may support either," +
                    " both, or none of the correction types. ",
                    schema=@Schema(allowableValues={ "auto", "ai" })) @Valid
            @RequestParam(value = "correctionType") String correctionType);

    @Operation(summary = "Retrieves the number of students that submitted a resolution for a specific exercise.", 
            description = "Retrieves the number of students that submitted a resolution for a specific exercise.  " +
                    "The total number of submissions can be obtained by setting the 'total' query parameter to 'true'," +
                    " otherwise the number of students that have a resolution is returned. ", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/{exerciseId}/resolutions/count",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Integer> countExerciseResolutions(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.QUERY, description = "'false' to count the number of students that made a submission. 'true' to count the total number of submissions.", schema=@Schema( defaultValue="false")) @Valid 
            @RequestParam(value = "total", required = false, defaultValue="false") Boolean total);

    @Operation(summary = "Get exercise resolutions of a specific exercise.", 
            description = "Get exercise resolutions of a specific exercise. If the 'latest' param is set to 'true', " +
                    "only the latest resolution of each student can be returned." +
                    " Returns a list of pairs of a student and its correspondent exercise resolution for the requested exercise." , tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval.", 
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/{exerciseId}/resolutions",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<ListPairStudentExerciseResolution> getExerciseResolutions(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.QUERY, required=true) @RequestParam("page") int page,
            @Parameter(in = ParameterIn.QUERY, required=true) @RequestParam("itemsPerPage") int itemsPerPage,
            @Parameter(in = ParameterIn.QUERY, schema = @Schema(defaultValue = "true")) @RequestParam(value = "latest", defaultValue = "true") Boolean latest);

    @Operation(summary = "Creates the resolution of an exercise.",
            description = "",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise resolution created successfully."),
            @ApiResponse(responseCode = "400", description = "Not a valid resolution."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{exerciseId}/resolutions",
            method = RequestMethod.POST)
    ResponseEntity<Void> createExerciseResolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.DEFAULT, required = true,
                    schema = @Schema(description = "exercise resolution data",
                            oneOf = {
                                    MultipleChoiceData.class,
                                    OpenAnswerData.class,
                                    FillTheBlanksData.class
                            }))
            @RequestBody ExerciseResolutionData resolutionData);

    @Operation(summary = "Retrieves the number of (resolution) submissions a student has made for a specific exercise.", description = "", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/{exerciseId}/resolutions/countByStudent",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Integer> countExerciseResolutionsByStudent(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.QUERY, required = true) @RequestParam("studentId") String studentId);

    @Operation(summary = "Get the list of student's resolutions for the given exercise.", description = "", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ExerciseResolution.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation.") })
    @RequestMapping(value = "/{exerciseId}/resolutions/listByStudent",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<ExerciseResolution>> getStudentListOfExerciseResolutions(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.QUERY, required = true) @RequestParam("studentId") String studentId);

    @Operation(summary = "Get latest exercise resolution made by the student.", description = "Gets last resolution made by the student for a given exercise, or 'null' if it does not exist.", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExerciseResolution.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/{exerciseId}/resolutions/lastByStudent",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<ExerciseResolution> getLastExerciseResolutionByStudent(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.QUERY, required = true) @RequestParam("studentId") String studentId);

    @Operation(summary = "Retrieve exercises.", description = "Retrieves exercises that match the given filters.", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(oneOf = {
                                    MultipleChoiceExercise.class,
                                    OpenAnswerExercise.class,
                                    FillTheBlanksExercise.class,
                                    FillTheBlanksOptionsExercise.class
                            })))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation. Example: When trying to access exercises from a specific course that the user is not associated to.") })
    @RequestMapping(value = "",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Exercise>> getExercises(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.QUERY, required=true) @RequestParam("page") Integer page,
            @Parameter(in = ParameterIn.QUERY, required=true) @RequestParam("itemsPerPage") Integer itemsPerPage,
            @Parameter(in = ParameterIn.QUERY, description = "Array of identifiers of the tags that will be used to filter the exercises." , schema=@Schema(defaultValue="[]")) @Valid
            @RequestParam(value = "tags", required = false) List<String> tags,
            @Parameter(in = ParameterIn.QUERY, description = "Value that defines if the exercise must have all the given tags to be retrieved." ,schema=@Schema( defaultValue="false")) @Valid
            @RequestParam(value = "matchAllTags", required = false, defaultValue="false") Boolean matchAllTags,
            @Parameter(in = ParameterIn.QUERY, description = "Describes the type of visibility that the exercises must have." ,schema=@Schema(allowableValues={ "public", "institution", "course", "not_listed", "private"}))
            @RequestParam(value = "visibility", required = false) String visibility,
            @Parameter(in = ParameterIn.QUERY, description = "Course identifier.") @RequestParam(value = "courseId", required = false) String courseId,
            @Parameter(in = ParameterIn.QUERY, description = "Institution identifier.") @RequestParam(value = "institutionId", required = false) String institutionId,
            @Parameter(in = ParameterIn.QUERY, description = "Specialist identifier.") @RequestParam(value = "specialistId", required = false) String specialistId,
            @Parameter(in = ParameterIn.QUERY, description = "Exercise title alike.") @RequestParam(value = "title", required = false) String title,
            @Parameter(in = ParameterIn.QUERY, description = "Exercise type.", schema = @Schema(allowableValues = {"MC", "OA", "FTB", "FTBO", "CHAT"})) @RequestParam(value = "exerciseType", required = false) String exerciseType);

    @Operation(summary = "Add comment to a resolution",
            description = "Adds a comment to an exercise resolution. " +
                "If the resolution already has a " +
                "comment associated, it will be overwritten.",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise resolution not found.") })
    @RequestMapping(value = "/resolutions/{resolutionId}/comment",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Void> addCommentToExerciseResolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("resolutionId") String resolutionId,
            @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, example = "You should have done something like ...") @RequestBody String comment);

    @Operation(summary = "Deletes a comment made to an exercise resolution.", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise resolution not found.") })
    @RequestMapping(value = "/resolutions/{resolutionId}/comment",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> removeCommentFromExerciseResolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("resolutionId") String resolutionId);

    @Operation(summary = "Get exercise resolution using its identifier.", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExerciseResolution.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise resolution not found.") })
    @RequestMapping(value = "/resolutions/{resolutionId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<ExerciseResolution> getExerciseResolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("resolutionId") String resolutionId);

    @Operation(summary = "Delete exercise resolution by its identifier.", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise resolution deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise or resolution not found.") })
    @RequestMapping(value = "/resolutions/{resolutionId}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteExerciseResolutionById(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("resolutionId") String resolutionId);

    @Operation(summary = "To set the points of an exercise resolution manually.", description = "", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Resolution not found.") })
    @RequestMapping(value = "/resolutions/{resolutionId}/manual-correction",
            method = RequestMethod.POST)
    ResponseEntity<Void> manuallyCorrectExerciseResolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("resolutionId") String resolutionId,
            @Parameter(in = ParameterIn.QUERY, description = "points to be attributed to the exercise resolution", required=true) @RequestParam(value = "points") float points);

    @Operation(summary = "Retrieves the solution of an exercise.", description = "Retrieve exercise solution. 'null' is returned if a solution does not exist.", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieval successful.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExerciseSolution.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise does not exist.") })
    @RequestMapping(value = "/{exerciseId}/solution",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<ExerciseSolution> getExerciseSolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId);

    @Operation(summary = "Creates the solution of an exercise.",
            description = "",
            tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise solution created successfully."),
            @ApiResponse(responseCode = "400", description = "Not a valid solution."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(
            consumes = { "application/json" },
            value = "/{exerciseId}/solution",
            method = RequestMethod.POST)
    ResponseEntity<Void> createExerciseSolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId,
            @Parameter(in = ParameterIn.DEFAULT, required = true,
                    schema = @Schema(description = "exercise solution",
                            oneOf = {
                                    MultipleChoiceData.class,
                                    OpenAnswerData.class,
                                    FillTheBlanksData.class
                            }))
            @RequestBody ExerciseResolutionData solution);

    @Operation(summary = "Delete exercise solution by exercise id.", description = "", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise solution deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/{exerciseId}/solution",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteExerciseSolution(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId);

    @Operation(summary = "Retrieves the identifier of the course that the exercise belongs to.", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieval successful.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise does not exist.") })
    @RequestMapping(value = "/{exerciseId}/course/id",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<String> getExerciseCourseId(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId);

    @Operation(summary = "Retrieves the identifier of the institution that the exercise belongs to.", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieval successful.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise does not exist.") })
    @RequestMapping(value = "/{exerciseId}/institution/id",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<String> getExerciseInstitutionId(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId);

    @Operation(summary = "Retrieves the visibility of an exercise.", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieval successful.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise does not exist.") })
    @RequestMapping(value = "/{exerciseId}/visibility",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<String> getExerciseVisibility(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId);

    @Operation(summary = "Retrieves the tags of an exercise.", tags={ "exercise" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieval successful.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
            @ApiResponse(responseCode = "404", description = "Exercise does not exist.") })
    @RequestMapping(value = "/{exerciseId}/tags",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Set<Tag>> getExerciseTags(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, required = true) @PathVariable("exerciseId") String exerciseId);

}

