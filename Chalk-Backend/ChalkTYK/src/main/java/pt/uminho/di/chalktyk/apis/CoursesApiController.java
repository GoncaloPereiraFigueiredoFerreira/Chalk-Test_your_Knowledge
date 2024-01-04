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

import java.util.List;


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

    @Override
    public ResponseEntity<Course> getCourse(String courseId, String jwt) {
        try {
            securityService.validateJWT(jwt);
            Course c = coursesService.getCourseById(courseId);
            return ResponseEntity.ok(c);
        }
        catch (NotFoundException | UnauthorizedException e){
            return new ExceptionResponseEntity<Course>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateCourse(String jwt, String courseId, Course body) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId();
            String role = token.getUserRole();

            if (!role.equals("SPECIALIST"))
                throw new UnauthorizedException("Cannot update course: user does not have permission to update the course!");

            Course c = coursesService.getCourseById(courseId);
            if (!userId.equals(c.getOwnerId()))
                throw new UnauthorizedException("Cannot update course: user is not the owner!");

            coursesService.updateCourseBasicProperties(courseId, body.getName(), body.getDescription());
            return ResponseEntity.ok().build();
        }
        catch (BadInputException | NotFoundException | UnauthorizedException e){
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<List<Course>> getCourses(String jwt, int page, int itemsPerPage, String studentId, String specialistId) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId();
            String role = token.getUserRole();

            if (role.equals("SPECIALIST")){
                if(!userId.equals(specialistId))
                    throw new UnauthorizedException("Cannot lists courses: User is not allowed to get another user's courses.!");
                else {
                    return ResponseEntity.ok(coursesService.getSpecialistCourses(specialistId, page, itemsPerPage));
                }
            } else if (role.equals("STUDENT")) {
                if(!userId.equals(studentId))
                    throw new UnauthorizedException("Cannot lists courses: User is not allowed to get another user's courses.!");
                else {
                    return ResponseEntity.ok(coursesService.getStudentCourses(studentId, page, itemsPerPage));
                }
            }
            throw new UnauthorizedException("User does not have permission to get courses.");
        }
        catch (NotFoundException | UnauthorizedException e){
            return new ExceptionResponseEntity<List<Course>>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<String> createCourse(String jwt, Course body) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId();
            String role = token.getUserRole();

            if (!role.equals("SPECIALIST"))
                throw new UnauthorizedException("Cannot create course: owner is not a specialist!");

            body.setOwnerId(userId);

            return ResponseEntity.ok(coursesService.createCourse(body));
        }
        catch (UnauthorizedException | BadInputException e){
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }
}
