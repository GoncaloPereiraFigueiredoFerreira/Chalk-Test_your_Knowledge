
package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InlineResponse2002;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InstitutionManager;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Specialist;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Student;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.UsersBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.UsersBody1;
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
import org.springframework.web.bind.annotation.CookieValue;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

@Validated
public interface UsersApi {

    @Operation(summary = "", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Object.class)))),
        
        @ApiResponse(responseCode = "400", description = "Either 'institution' or 'course'+'institution' are required."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/users",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Object>> usersGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
, @Parameter(in = ParameterIn.QUERY, description = "Find users from this institution." ,schema=@Schema()) @Valid @RequestParam(value = "institution", required = false) String institution
, @Parameter(in = ParameterIn.QUERY, description = "Find users from this course (institution is required)." ,schema=@Schema()) @Valid @RequestParam(value = "course", required = false) String course
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InstitutionManager.class)))),
        
        @ApiResponse(responseCode = "400", description = "The 'institutionId+page+itemsPerPage' is required."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/users/managers",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<InstitutionManager>> usersManagersGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
, @Parameter(in = ParameterIn.QUERY, description = "Find managers from this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Create user", description = "This method is used to create an user regardless of its type.", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/users",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> usersPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UsersBody1 body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Update user", description = "Update an existent user in the store", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid user id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "User not found") })
    @RequestMapping(value = "/users",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> usersPut(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UsersBody body
, @Parameter(in = ParameterIn.COOKIE, description = "" ,schema=@Schema()) @CookieValue(value="userId", required=false) String userId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Specialist.class)))),
        
        @ApiResponse(responseCode = "400", description = "Either 'institutionId+page+itemsPerPage' or 'courseId+institutionId+page+itemsPerPage' or 'testId+page+itemsPerPage' are required."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/users/specialists",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Specialist>> usersSpecialistsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
, @Parameter(in = ParameterIn.QUERY, description = "Find the specialists that are the owners of the test. " ,schema=@Schema()) @Valid @RequestParam(value = "testId", required = false) String testId
, @Parameter(in = ParameterIn.QUERY, description = "Find specialists from this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
, @Parameter(in = ParameterIn.QUERY, description = "Find specialists from this course (institution is required). " ,schema=@Schema()) @Valid @RequestParam(value = "courseId", required = false) String courseId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Student.class)))),
        
        @ApiResponse(responseCode = "400", description = "Either 'institutionId+page+itemsPerPage' or 'courseId'+'institutionId+page+itemsPerPage' or 'testResolutionId' or 'exerciseResolutionId' are required."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/users/students",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Student>> usersStudentsGet(@Parameter(in = ParameterIn.QUERY, description = "Find the student that is the owner of the resoluton. " ,schema=@Schema()) @Valid @RequestParam(value = "testResolutionId", required = false) String testResolutionId
, @Parameter(in = ParameterIn.QUERY, description = "Find the student that is the owner of the resoluton. " ,schema=@Schema()) @Valid @RequestParam(value = "exerciseResolutionId", required = false) String exerciseResolutionId
, @Parameter(in = ParameterIn.QUERY, description = "Find students from this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
, @Parameter(in = ParameterIn.QUERY, description = "Find students from this course (institution is required). " ,schema=@Schema()) @Valid @RequestParam(value = "courseId", required = false) String courseId
, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "page", required = false) Integer page
, @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = false) Integer itemsPerPage
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Delete user", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid username supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "User not found") })
    @RequestMapping(value = "/users/{userId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> usersUserIdDelete(@Parameter(in = ParameterIn.PATH, description = "User identifier", required=true, schema=@Schema()) @PathVariable("userId") String userId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get user by user id", description = "", tags={ "user" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InlineResponse2002.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid user id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "User not found") })
    @RequestMapping(value = "/users/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<InlineResponse2002> usersUserIdGet(@Parameter(in = ParameterIn.PATH, description = "User identifier", required=true, schema=@Schema()) @PathVariable("userId") String userId
, @CookieValue("chalkauthtoken") String jwt);

}

