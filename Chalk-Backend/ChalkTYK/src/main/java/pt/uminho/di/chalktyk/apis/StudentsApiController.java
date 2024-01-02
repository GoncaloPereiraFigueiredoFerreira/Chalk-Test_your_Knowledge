package pt.uminho.di.chalktyk.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.IStudentsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@RestController
@RequestMapping("/students")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class StudentsApiController implements StudentsApi{

    private final ISecurityService securityService;
    private final IStudentsService studentsService;

    @Autowired
    public StudentsApiController(ISecurityService securityService, IStudentsService studentsService) {
        this.securityService = securityService;
        this.studentsService = studentsService;
    }

    @Override
    public ResponseEntity<String> createStudent(Student student) {
        try {
            return ResponseEntity.ok(studentsService.createStudent(student));
        } catch (BadInputException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Student> getStudentById(String jwtToken, String studentId) {
        try {
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = securityService.getUserId(jwt);
            if(studentId == null)
                throw new BadInputException("Student id is null.");
            if(!studentId.equals(userId))
                throw new UnauthorizedException("The user is not allowed to check another user's information.");
            return ResponseEntity.ok(studentsService.getStudentById(studentId));
        } catch (UnauthorizedException | BadInputException | NotFoundException e) {
            return new ExceptionResponseEntity<Student>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> existsStudentById(String jwtToken, String studentId) {
        try {
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = securityService.getUserId(jwt);
            if(studentId == null)
                throw new BadInputException("Student id is null.");
            if(!studentId.equals(userId))
                throw new UnauthorizedException("The user is not allowed to check another user's information.");
            return ResponseEntity.ok(studentsService.existsStudentById(studentId));
        } catch (UnauthorizedException | BadInputException e) {
            return new ExceptionResponseEntity<Boolean>().createRequest(e);
        }
    }
}
