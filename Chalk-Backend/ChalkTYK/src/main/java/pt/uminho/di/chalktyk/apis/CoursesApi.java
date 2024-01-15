package pt.uminho.di.chalktyk.apis;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;
import pt.uminho.di.chalktyk.models.courses.Course;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;

import java.util.List;

@Validated
public interface CoursesApi {
    @Operation(summary = "Get course by course id", description = "", tags={ "course" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
        @ApiResponse(responseCode = "400", description = "Invalid course id supplied"),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        @ApiResponse(responseCode = "404", description = "Course not found") })
    @RequestMapping(value = "/{courseId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Course> getCourse(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
, @CookieValue("chalkauthtoken") String jwt);

    @Operation(summary = "", description = "", tags={ "course" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Course.class)))),
            @ApiResponse(responseCode = "400", description = "Either 'studentId' or 'specialistId' or 'institution' are required."),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Course>> getCourses(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token")
            @CookieValue("chalkauthtoken") String jwt,
            @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(minimum="1", maximum="50"))
            @RequestParam(value = "page")  int page,
            @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema())
            @Min(1) @Max(50) @RequestParam(value = "itemsPerPage", required = true) int itemsPerPage,
            @Parameter(in = ParameterIn.QUERY, description = "Find the courses this student belongs to. ",schema=@Schema())
            @Valid @RequestParam(value = "studentId", required = false) String studentId,
            @Parameter(in = ParameterIn.QUERY, description = "Find the courses this specialist belongs to. " ,schema=@Schema())
            @Valid @RequestParam(value = "specialistId", required = false) String specialistId);

    @Operation(summary = "Create course", description = "This method is used to create an course", tags={ "course" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<String> createCourse(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwt,
            @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @RequestBody Course body);

    @Operation(summary = "Update course", description = "", tags={ "course" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid course id supplied"),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        @ApiResponse(responseCode = "404", description = "Course not found") })
    @RequestMapping(value = "/{courseId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateCourse(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token")
            @CookieValue("chalkauthtoken") String jwt,
            @Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema())
            @PathVariable("courseId") String courseId,
            @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @RequestBody Course body);


    @Operation(summary = "Gets students of course", description = "", tags={ "course" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(example = ""))) })
    @RequestMapping(value = "/{courseId}/students",
            method = RequestMethod.GET)
    ResponseEntity<List<Student>> getCourseStudents(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token")
            @CookieValue("chalkauthtoken") String jwt,
            @Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema())
            @PathVariable("courseId") String courseId,
            @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(minimum="1", maximum="50"))
            @RequestParam(value = "page")  int page,
            @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema())
            @Min(1) @Max(50) @RequestParam(value = "itemsPerPage") int itemsPerPage);


    @Operation(summary = "Gets specialists of course", description = "", tags={ "course" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(example = ""))) })
    @RequestMapping(value = "/{courseId}/specialists",
            method = RequestMethod.GET)
    ResponseEntity<List<Specialist>> getCourseSpecialists(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token")
            @CookieValue("chalkauthtoken") String jwt,
            @Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema())
            @PathVariable("courseId") String courseId,
            @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(minimum="1", maximum="50"))
            @RequestParam(value = "page")  int page,
            @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema())
            @Min(1) @Max(50) @RequestParam(value = "itemsPerPage") int itemsPerPage);

    @Operation(summary = "Add students to course", description = "", tags={ "course" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(example = ""))) })
    @RequestMapping(value = "/{courseId}/students/add",
            consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST)
    ResponseEntity<Void> addStudentsToCourse(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token")
            @CookieValue("chalkauthtoken") String jwt,
            @Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true)
            @PathVariable("courseId") String courseId,
            @Parameter(in = ParameterIn.DEFAULT, description = "Emails of the students to be added.",required=true)
            @RequestBody List<String> studentsEmails);

    @Operation(summary = "Add specialists to course", description = "", tags={ "course" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(example = ""))) })
    @RequestMapping(value = "/{courseId}/specialists/add",
            consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST)
    ResponseEntity<Void> addSpecialistsToCourse(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token")
            @CookieValue("chalkauthtoken") String jwt,
            @Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true)
            @PathVariable("courseId") String courseId,
            @Parameter(in = ParameterIn.DEFAULT, description = "Emails of the specialists to be added.",required=true)
            @RequestBody List<String> specialistsEmails);

    @Operation(summary = "Remove students from course", description = "", tags={ "course" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(example = ""))) })
    @RequestMapping(value = "/{courseId}/students/remove",
            consumes = "application/json",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> removeStudentsFromCourse(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token")
            @CookieValue("chalkauthtoken") String jwt,
            @Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true)
            @PathVariable("courseId") String courseId,
            @Parameter(in = ParameterIn.DEFAULT, description = "Emails of the students to be removed.",required=true)
            @RequestBody List<String> studentsEmails);

    @Operation(summary = "Remove specialists from course", description = "", tags={ "course" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation", content = @Content(schema = @Schema(example = ""))),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(schema = @Schema(example = ""))) })
    @RequestMapping(value = "/{courseId}/specialists/remove",
            consumes = "application/json",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> removeSpecialistsFromCourse(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token")
            @CookieValue("chalkauthtoken") String jwt,
            @Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true)
            @PathVariable("courseId") String courseId,
            @Parameter(in = ParameterIn.DEFAULT, description = "Emails of the specialists to be removed.",required=true)
            @RequestBody List<String> specialistsEmails);
}

