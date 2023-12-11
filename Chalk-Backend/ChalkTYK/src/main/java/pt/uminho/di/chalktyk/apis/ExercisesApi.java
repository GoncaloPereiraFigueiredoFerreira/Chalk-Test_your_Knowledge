
package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Comment;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Exercise;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.ExerciseIdRubricBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.ExerciseResolution;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.ExercisesBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.ExercisesExerciseIdBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InlineResponse200;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Rubric;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Tag;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

@Validated
public interface ExercisesApi {

    @Operation(summary = "Delete exercise by its id.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Exercise deleted successfully."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/exercises/{exerciseId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteExerciseById(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Duplicates the exercise using its identifier.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful duplication.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Exercise with the given id does not exist.") })
    @RequestMapping(value = "/exercises/{exerciseId}/duplicate",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<String> duplicateExerciseById(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Update an exercise", description = "This method is used to update an existing exercise. Check the schema", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Exercise updated successfully."),
        
        @ApiResponse(responseCode = "400", description = "Bad input."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/exercises/{exerciseId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateExercise(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExercisesExerciseIdBody body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Issue the automatic correction of the exercise resolutions.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success."),
        
        @ApiResponse(responseCode = "400", description = "The requested type of correction is not supported for the given exercise."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/exercises/{exerciseId}/resolutions/correction",
        method = RequestMethod.PUT)
    ResponseEntity<Void> issueExerciseResolutionsCorrection(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.QUERY, description = "Type of correction. The correction can either be automatic or done by AI. For a given exercise, it may support either, both, or none of the correction types. " ,schema=@Schema(allowableValues={ "auto", "ai" }
)) @Valid @RequestParam(value = "correctionType", required = false) String correctionType
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Retrieves the number of students that submitted a resolution for a specific exercise.", description = "Retrieves the number of students that submitted a resolution for a specific exercise.  The total number of  submissions can be obtained by setting the 'total' query parameter to 'true'. ", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
        
        @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/exercises/{exerciseId}/resolutions/count",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Integer> countExerciseResolutions(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.QUERY, description = "'false' to count the number of students that made a submission. 'true' to count the total number of submissions." ,schema=@Schema( defaultValue="false")) @Valid @RequestParam(value = "total", required = false, defaultValue="false") Boolean total
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get all exercise resolutions.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InlineResponse200.class)))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/exercises/{exerciseId}/resolutions",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<InlineResponse200>> getExerciseResolutions(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Create an exercise resolution", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Exercise resolution created successfully."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Could not find any exercise with the given id.") })
    @RequestMapping(value = "/exercises/{exerciseId}/resolutions",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> createExerciseResolution(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("exerciseId") Integer exerciseId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExerciseResolution body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Retrieves the number of (resolution) submissions a student has made for a specific exercise.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))),
        
        @ApiResponse(responseCode = "404", description = "Exercise not found.") })
    @RequestMapping(value = "/exercises/{exerciseId}/resolutions/{studentId}/count",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Integer> countExerciseResolutionsByStudent(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get the list of identifiers of the student's resolutions for the given exercise.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation.") })
    @RequestMapping(value = "/exercises/{exerciseId}/resolutions/{studentId}/ids",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<String>> getStudentListOfExerciseResolutionsIdsByExercise(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get latest exercise resolution made by the student.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExerciseResolution.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Not found.") })
    @RequestMapping(value = "/exercises/{exerciseId}/resolutions/{studentId}/last",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ExerciseResolution> getLastExerciseResolutionByStudent(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Retrieve exercise rubric.", description = "Retrieve exercise rubric. 'null' is returned if a rubric does not exist.", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Retrieval successful.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rubric.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Exercise does not exist.") })
    @RequestMapping(value = "/exercises/{exerciseId}/rubric",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Rubric> getExerciseRubric(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Create a rubric for an exercise.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Rubric created successfully."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Exercise does not exist.") })
    @RequestMapping(value = "/exercises/{exerciseId}/rubric",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> createExerciseRubric(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExerciseIdRubricBody body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Retrieve exercises.", description = "Retrieves exercises that match the given filters.", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Exercise.class)))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation. Example: When trying to access exercises from a specific course that the user does not belong to.") })
    @RequestMapping(value = "/exercises",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Exercise>> getExercises(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
, @Parameter(in = ParameterIn.QUERY, description = "Array of identifiers from the tags that will be used to filter the exercises." ,schema=@Schema( defaultValue="[]")) @Valid @RequestParam(value = "tags", required = false, defaultValue="[]") List<String> tags
, @Parameter(in = ParameterIn.QUERY, description = "Value that defines if the exercise must have all the given tags to be retrieved." ,schema=@Schema( defaultValue="false")) @Valid @RequestParam(value = "matchAllTags", required = false, defaultValue="false") Boolean matchAllTags
, @Parameter(in = ParameterIn.QUERY, description = "Describes the type of visibility that the exercises must have.  This parameter must be paired with the parameter 'visibilityTarget'  when the value is either 'institution' or 'course'. " ,schema=@Schema(allowableValues={ "public", "institution", "course" }
, defaultValue="public")) @Valid @RequestParam(value = "visibilityType", required = false, defaultValue="public") String visibilityType
, @Parameter(in = ParameterIn.QUERY, description = "Identifier of the visibility target. For example, if visibilityType='institution',  then this parameter is the identifier of the institution. " ,schema=@Schema()) @Valid @RequestParam(value = "visibilityTarget", required = false) String visibilityTarget
, @Parameter(in = ParameterIn.QUERY, description = "Identifier of the specialist. Used when we want to retrieve the exercises created by a specific specialist." ,schema=@Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Create an exercise", description = "This method is used to create an exercise regardless of its type. Check the request body schema to understand how to create the different exercises.", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Exercise created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        
        @ApiResponse(responseCode = "400", description = "Bad input."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation.") })
    @RequestMapping(value = "/exercises",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<String> createExercise(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExercisesBody body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Add comment to a resolution", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Exercise resolution not found.") })
    @RequestMapping(value = "/exercises/resolutions/{resolutionId}/comment",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> addCommentToExerciseResolution(@Parameter(in = ParameterIn.PATH, description = "Exercise resolution identifier", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Comment body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get exercise resolution using its id.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful retrieval.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExerciseResolution.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Exercise resolution not found.") })
    @RequestMapping(value = "/exercises/resolutions/{resolutionId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ExerciseResolution> getExerciseResolution(@Parameter(in = ParameterIn.PATH, description = "Exercise resolution identifier", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "To set the cotation of an exercise resolution manually.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Success."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Resolution not found.") })
    @RequestMapping(value = "/exercises/resolutions/{resolutionId}/manual-correction",
        method = RequestMethod.POST)
    ResponseEntity<Void> exerciseResolutionManualCorrection(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
, @NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "cotation", required = true) Float cotation
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Delete the exercise rubric.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Deletion successful."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Exercise does not exist.") })
    @RequestMapping(value = "/exercises/rubrics/{rubricId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteExerciseRubric(@Parameter(in = ParameterIn.PATH, description = "rubric identifier", required=true, schema=@Schema()) @PathVariable("rubricId") String rubricId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Update the rubric.", description = "", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Rubric created successfully."),
        
        @ApiResponse(responseCode = "400", description = "Malformed request. Rubric may not be the correct type."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation."),
        
        @ApiResponse(responseCode = "404", description = "Exercise does not exist.") })
    @RequestMapping(value = "/exercises/rubrics/{rubricId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateRubric(@Parameter(in = ParameterIn.PATH, description = "rubric identifier", required=true, schema=@Schema()) @PathVariable("rubricId") String rubricId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Rubric body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Retrieve tags.", description = "Retrieves the tags used to characterize the exercises.  A path or paths can be given to define where the retrieval of tags should start.  A number of levels can also be provided to inform how much depth the retrieval should cover. ", tags={ "exercises" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Tags retrieved successfully.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Tag.class)))) })
    @RequestMapping(value = "/exercises/tag",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Tag>> listTags(@Parameter(in = ParameterIn.QUERY, description = "Array of paths from which the tags should be retrieved." ,schema=@Schema( defaultValue="[]")) @Valid @RequestParam(value = "paths", required = false, defaultValue="[]") List<String> paths
, @Parameter(in = ParameterIn.QUERY, description = "Number of levels, starting from the given paths that should be retrieved. -1 to retrieve every tag starting at the given paths." ,schema=@Schema( defaultValue="-1")) @Valid @RequestParam(value = "levels", required = false, defaultValue="-1") Integer levels
, @CookieValue("chalkauthtoken") String jwt);

}

