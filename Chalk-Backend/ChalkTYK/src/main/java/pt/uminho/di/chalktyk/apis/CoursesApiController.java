package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.services.ICoursesService;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/courses")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
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

    @Override
    public ResponseEntity<Course> getCourse(String courseId, String jwt) {
        try {
            securityService.validateJWT(jwt);
            Course c = coursesService.getCourseById(courseId);
            return ResponseEntity.ok().body(c);
        }
        catch (NotFoundException e){
            return new ExceptionResponseEntity<Course>().createRequest(e);
        }
        catch (UnauthorizedException e){
            return new ExceptionResponseEntity<Course>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateCourse(String courseId, Course body, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId();
            String role = token.getUserRole();

            if (!role.equals("SPECIALIST"))
                throw new UnauthorizedException("Cannot update course: owner is not a specialist!");

            Course c = coursesService.getCourseById(courseId);
            if (!userId.equals(c.getOwnerId()))
                throw new UnauthorizedException("Cannot update course: user is not the owner!");

            coursesService.updateCourseBasicProperties(courseId, body.getName(), body.getDescription());
            return ResponseEntity.ok().build();
        }
        catch (BadInputException e){
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
        catch (NotFoundException e){
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
        catch (UnauthorizedException e){
            return new ExceptionResponseEntity<Void>().createRequest(e);
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

    @Override
    public ResponseEntity<Void> createCourse(Course body, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId();
            String role = token.getUserRole();

            if (!role.equals("SPECIALIST"))
                throw new UnauthorizedException("Cannot create course: owner is not a specialist!");

            body.setOwnerId(userId);
            coursesService.createCourse(body);
            return ResponseEntity.ok().build();
        }
        catch (UnauthorizedException e){
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
        catch (BadInputException e){
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }
}
