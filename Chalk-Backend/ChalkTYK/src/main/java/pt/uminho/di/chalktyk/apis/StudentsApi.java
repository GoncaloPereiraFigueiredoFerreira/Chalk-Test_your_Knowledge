package pt.uminho.di.chalktyk.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.uminho.di.chalktyk.models.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;

public interface StudentsApi {
    /**
     * Creates a student.
     *
     * @param student  student properties
     * @return identifier of the new student
     */
    @Operation(summary = "Create student", description = "This method is used to create a student.", tags={ "student" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid properties.") })
    @RequestMapping(value = "",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<String> createStudent(
            @Parameter(in = ParameterIn.DEFAULT, description = "", required=true,
                    schema=@Schema(implementation = Student.class))
            @Valid @RequestBody Student student);

    /**
     * Gets student
     *
     * @param jwtToken
     * @param studentId identifier of the student
     * @return student
     */
    @Operation(summary = "Get student by student id", description = "", tags={ "student" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
            @ApiResponse(responseCode = "404", description = "Student not found") })
    @RequestMapping(value = "/{studentId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Student> getStudentById(
            @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, description = "Student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId);

    /**
     * Checks if a student exists with the given id.
     *
     * @param jwtToken
     * @param studentId identifier of the student
     * @return 'true' if a student exists with the given id
     */
    @Operation(summary = "Exists student by student id.", description = "", tags={ "student" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(oneOf = {Student.class, Specialist.class, InstitutionManager.class}))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
            @ApiResponse(responseCode = "404", description = "Student not found") })
    @RequestMapping(value = "/{studentId}/exists",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Boolean> existsStudentById(
            @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, description = "Student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId);
}
