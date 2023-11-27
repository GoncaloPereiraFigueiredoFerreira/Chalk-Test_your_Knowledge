package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Course;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.CourseWithoutId;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.CoursesBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.CoursesCourseIdBody;
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
public interface CoursesApi {

    @Operation(summary = "Delete course", description = "", tags={ "course" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid coursename supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Course not found") })
    @RequestMapping(value = "/courses/{courseId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> coursesCourseIdDelete(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
);


    @Operation(summary = "Get course by course id", description = "", tags={ "course" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseWithoutId.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid course id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Course not found") })
    @RequestMapping(value = "/courses/{courseId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<CourseWithoutId> coursesCourseIdGet(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
);


    @Operation(summary = "Update course", description = "Update an existent course in the store", tags={ "course" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid course id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Course not found") })
    @RequestMapping(value = "/courses/{courseId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> coursesCourseIdPut(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CoursesCourseIdBody body
);


    @Operation(summary = "", description = "", tags={ "course" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Course.class)))),
        
        @ApiResponse(responseCode = "400", description = "Either 'studentId' or 'specialistId' or 'institution' are required."),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/courses",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Course>> coursesGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
, @Parameter(in = ParameterIn.QUERY, description = "Find the courses this student belongs to. " ,schema=@Schema()) @Valid @RequestParam(value = "studentId", required = false) String studentId
, @Parameter(in = ParameterIn.QUERY, description = "Find the courses this specialist belongs to. " ,schema=@Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId
, @Parameter(in = ParameterIn.QUERY, description = "Find the courses of this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
);


    @Operation(summary = "Create course", description = "This method is used to create an course", tags={ "course" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/courses",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> coursesPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CoursesBody body
);

}

