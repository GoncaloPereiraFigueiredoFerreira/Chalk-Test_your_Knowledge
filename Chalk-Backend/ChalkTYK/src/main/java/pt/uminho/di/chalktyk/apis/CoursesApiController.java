package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.services.ICoursesService;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.ForbiddenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.uminho.di.chalktyk.services.exceptions.ServiceException;

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
        catch (ServiceException e){
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
                throw new ForbiddenException("Cannot update course: user does not have permission to update the course!");

            Course c = coursesService.getCourseById(courseId);
            if (!userId.equals(c.getOwnerId()))
                throw new ForbiddenException("Cannot update course: user is not the owner!");

            coursesService.updateCourseBasicProperties(courseId, body.getName(), body.getDescription());
            return ResponseEntity.ok().build();
        }
        catch (ServiceException e){
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getCourseStudents(String jwt, String courseId, int page, int itemsPerPage) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId();
            String role = token.getUserRole();

            if ((role.equals("SPECIALIST") && coursesService.checkSpecialistInCourse(courseId, userId)) ||
                    (role.equals("STUDENT") && coursesService.checkStudentInCourse(courseId, userId))) {
                return ResponseEntity.ok(coursesService.getCourseStudents(courseId, page, itemsPerPage));
            } else
                throw new ForbiddenException("User does not have permission to list the students of the course.");
        }
        catch (ServiceException e){
            return new ExceptionResponseEntity<List<Student>>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<List<Specialist>> getCourseSpecialists(String jwt, String courseId, int page, int itemsPerPage) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId();
            String role = token.getUserRole();

            if ((role.equals("SPECIALIST") && coursesService.checkSpecialistInCourse(courseId, userId)) ||
                    (role.equals("STUDENT") && coursesService.checkStudentInCourse(courseId, userId))) {
                return ResponseEntity.ok(coursesService.getCourseSpecialists(courseId, page, itemsPerPage));
            } else
                throw new ForbiddenException("User does not have permission to list the specialists of the course.");
        }
        catch (ServiceException e){
            return new ExceptionResponseEntity<List<Specialist>>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> addStudentsToCourse(String jwt, String courseId, List<String> studentsEmails) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId();
            String role = token.getUserRole();

            Course c = coursesService.getCourseById(courseId);
            if (role.equals("SPECIALIST") && userId.equals(c.getOwnerId()))
                coursesService.addStudentsToCourseByEmails(courseId, studentsEmails);
            else
                throw new ForbiddenException("User does not have permission to add students to the course.");

            return ResponseEntity.ok().build();
        }
        catch (ServiceException e){
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> addSpecialistsToCourse(String jwt, String courseId, List<String> specialistsEmails) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId();
            String role = token.getUserRole();

            Course c = coursesService.getCourseById(courseId);
            if (role.equals("SPECIALIST") && userId.equals(c.getOwnerId()))
                coursesService.addSpecialistsToCourseByEmails(courseId, specialistsEmails);
            else
                throw new ForbiddenException("User does not have permission to add specialists to the course.");

            return ResponseEntity.ok().build();
        }
        catch (ServiceException e){
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> removeStudentsFromCourse(String jwt, String courseId, List<String> studentsEmails) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId();
            String role = token.getUserRole();

            Course c = coursesService.getCourseById(courseId);
            if (role.equals("SPECIALIST") && userId.equals(c.getOwnerId()))
                coursesService.removeStudentsFromCourseByEmails(courseId, studentsEmails);
            else
                throw new ForbiddenException("User does not have permission to remove students to the course.");

            return ResponseEntity.ok().build();
        }
        catch (ServiceException e){
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> removeSpecialistsFromCourse(String jwt, String courseId, List<String> specialistsEmails) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId();
            String role = token.getUserRole();

            Course c = coursesService.getCourseById(courseId);
            if (role.equals("SPECIALIST") && userId.equals(c.getOwnerId()))
                coursesService.removeSpecialistsFromCourseByEmails(courseId, specialistsEmails);
            else
                throw new ForbiddenException("User does not have permission to remove specialists to the course.");

            return ResponseEntity.ok().build();
        }
        catch (ServiceException e){
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
                if(specialistId != null && !userId.equals(specialistId))
                    throw new ForbiddenException("Cannot lists courses: User is not allowed to get another user's courses.!");
                else {
                    return ResponseEntity.ok(coursesService.getSpecialistCourses(userId, page, itemsPerPage));
                }
            } else if (role.equals("STUDENT")) {
                if(studentId != null && !userId.equals(studentId))
                    throw new ForbiddenException("Cannot lists courses: User is not allowed to get another user's courses.!");
                else {
                    return ResponseEntity.ok(coursesService.getStudentCourses(userId, page, itemsPerPage));
                }
            }
            throw new ForbiddenException("User does not have permission to get courses.");
        }
        catch (ServiceException e){
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
                throw new ForbiddenException("Cannot create course: owner is not a specialist!");

            body.setOwnerId(userId);

            return ResponseEntity.ok(coursesService.createCourse(body));
        }
        catch (ServiceException e){
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }
}
