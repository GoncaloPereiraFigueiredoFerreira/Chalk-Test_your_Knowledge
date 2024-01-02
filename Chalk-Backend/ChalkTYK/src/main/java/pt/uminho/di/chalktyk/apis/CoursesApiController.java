package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.services.ICoursesService;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import jakarta.validation.Valid;

import java.util.List;

@CrossOrigin(originPatterns = "*", allowCredentials = "true")
@RestController
public class CoursesApiController implements CoursesApi {
    private final ICoursesService coursesService;
    private final ISecurityService securityService;

    @Autowired
    public CoursesApiController(ICoursesService coursesService, ISecurityService securityService){
        this.coursesService = coursesService;
        this.securityService = securityService;
    }

    /* 
    public ResponseEntity<Void> coursesCourseIdDelete(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
, @CookieValue("chalkauthtoken") String jwt) {

        throw new RuntimeException("Not implemented");
        //jwt.verify if is specialist
        //throw new UnauthorizedException("Method is only available for specialists");
    }
    */



    public ResponseEntity<Course> getCourse(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
, @RequestHeader("chalkauthtoken") String jwt) {
        try {
            securityService.userExists(jwt);
            Course c = coursesService.getCourseById(courseId);
            return ResponseEntity.ok().body(c);
        }
        catch (NotFoundException e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-error", e.getMessage());
            headers.setAccessControlExposeHeaders(List.of("*"));
            return new ResponseEntity<Course>(headers, HttpStatus.NOT_FOUND);
        }
        catch (BadInputException e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-error", e.getMessage());
            headers.setAccessControlExposeHeaders(List.of("*"));
            return new ResponseEntity<Course>(headers, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> updateCourse(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Course body
, @RequestHeader("chalkauthtoken") String jwt) {
        try {
            securityService.userExists(jwt);
            coursesService.updateCourseBasicProperties(courseId, body.getName(), body.getDescription());
            return ResponseEntity.ok().build();
        }
        catch (BadInputException e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-error", e.getMessage());
            headers.setAccessControlExposeHeaders(List.of("*"));
            return new ResponseEntity<Void>(headers, HttpStatus.BAD_REQUEST);
        }
        catch (NotFoundException e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-error", e.getMessage());
            headers.setAccessControlExposeHeaders(List.of("*"));
            return new ResponseEntity<Void>(headers, HttpStatus.NOT_FOUND);
        }
    }

    /* 
    public ResponseEntity<List<Course>> getCourses(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Find the courses this student belongs to. " ,schema=@Schema()) @Valid @RequestParam(value = "studentId", required = false) String studentId
,@Parameter(in = ParameterIn.QUERY, description = "Find the courses this specialist belongs to. " ,schema=@Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId
,@Parameter(in = ParameterIn.QUERY, description = "Find the courses of this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
, @CookieValue("chalkauthtoken") String jwt) {

    }
    */

    public ResponseEntity<Void> createCourse(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Course body
, @RequestHeader("chalkauthtoken") String jwt) {
        try {
            securityService.userExists(jwt);
            coursesService.createCourse(body);
            return ResponseEntity.ok().build();
        }
        catch (NotFoundException e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-error", e.getMessage());
            headers.setAccessControlExposeHeaders(List.of("*"));
            return new ResponseEntity<Void>(headers, HttpStatus.NOT_FOUND);
        }
        catch (BadInputException e){
            HttpHeaders headers = new HttpHeaders();
            headers.add("x-error", e.getMessage());
            headers.setAccessControlExposeHeaders(List.of("*"));
            return new ResponseEntity<Void>(headers, HttpStatus.BAD_REQUEST);
        }
    }
}
