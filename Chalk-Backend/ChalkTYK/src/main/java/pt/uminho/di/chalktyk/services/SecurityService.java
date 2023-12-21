package pt.uminho.di.chalktyk.services;

import org.apache.tomcat.util.json.ParseException;
import org.hibernate.mapping.Set;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Service("securatyService") 
public class SecurityService {
    private final IUsersService usersService;
    private final IStudentsService studentsService;
    private final ISpecialistsService specialistsService;
    private final IExercisesService exercisesService;
    private final ICoursesService coursesService;
    private final IInstitutionsService institutionsService;

    
    public SecurityService(IUsersService usersService, IStudentsService studentsService,
            ISpecialistsService specialistsService, IExercisesService exercisesService, ICoursesService coursesService,
            IInstitutionsService institutionsService) {
        this.usersService = usersService;
        this.studentsService = studentsService;
        this.specialistsService = specialistsService;
        this.exercisesService = exercisesService;
        this.coursesService = coursesService;
        this.institutionsService = institutionsService;
    }

    public Boolean userIsSpecialist(String jwtToken) throws NotFoundException, BadInputException{
        JWT jwt;
        try {
            jwt = new JWT(jwtToken);
        } catch (JwtException | ParseException e) {
            throw new BadInputException("Bad formatted JWT token");
        }
        String role = (String) jwt.getPayloadParam("role");
        String userId = (String) jwt.getPayloadParam("username");
        Boolean ret = false;

        if(role.equals("specialist")){
            ret = true;
        } 

        if(specialistsService.existsSpecialistById(userId)){
            throw new NotFoundException("Specialist not found"); 
        }

        return ret;
    }
    
    public Boolean userIsStudent(String jwtToken) throws NotFoundException, BadInputException{
        JWT jwt;
        try {
            jwt = new JWT(jwtToken);
        } catch (JwtException | ParseException e) {
            throw new BadInputException("Bad formatted JWT token");
        }
        String role = (String) jwt.getPayloadParam("role");
        String userId = (String) jwt.getPayloadParam("username");
        Boolean ret = false;

        if(role.equals("student")){
            ret = true;
        } 

        if(studentsService.existsStudentById(userId)){
            throw new NotFoundException("Student not found"); 
        }

        return ret;
    }
    
    public Boolean userExists(String jwtToken) throws NotFoundException, BadInputException{
        JWT jwt;
        try {
            jwt = new JWT(jwtToken);
        } catch (JwtException | ParseException e) {
            throw new BadInputException("Bad formatted JWT token");
        }
        String role = (String) jwt.getPayloadParam("role");
        String userId = (String) jwt.getPayloadParam("username");
        Boolean ret = false;

        if(role.equals("specialist") || role.equals("user")){
            ret = true;
        } 

        if(studentsService.existsStudentById(userId) || specialistsService.existsSpecialistById(userId)){
            throw new NotFoundException("User not found"); 
        }

        return ret;
    }

    public Boolean specialistOwnesExercise(String specialistId, String exerciseId) throws NotFoundException, UnauthorizedException{
        
        if(!exercisesService.exerciseExists(exerciseId)){
            throw new NotFoundException("Exercise not found");
        } 
        if(!specialistsService.existsSpecialistById(specialistId)){
            throw new NotFoundException("Specialist not found");
        }

        Exercise exercise = exercisesService.getExerciseById(exerciseId);

        if(exercise.getSpecialistId().equals(specialistId)){
            throw new UnauthorizedException("The specilist is not the owner of the exercise");
        }

        return true;
    } 

    public Boolean studentCanSeeExercise(String studentId, String exerciseId) throws NotFoundException, UnauthorizedException{
        Boolean ret = false;
        String vis = exercisesService.getExerciseVisibility(exerciseId);

        switch (vis) {
            case "public":
                ret = true;
                break;
            case "course":
                String courseId = exercisesService.getExerciseCourse(exerciseId);
                if(courseId != null && coursesService.checkStudentInCourse(courseId, studentId)){
                    ret = true;
                }else{
                    throw new UnauthorizedException("The student can not see the course's exercise");
                }
                break;
            case "institution":
                Institution studentInstitution = institutionsService.getStudentInstitution(studentId);
                Institution exerciseInstitution = exercisesService.getExerciseInstitution(exerciseId);
                if(studentInstitution.equals(exerciseInstitution)){
                    ret = true;
                }else{
                  throw new UnauthorizedException("The student can not see the institution's exercise");  
                }
                break;
            default:
                throw new UnauthorizedException("The student can not see the exercise");
                //break;
        }

        return ret;
    }  

    public Boolean specialistCanSeeExercise(String specialistId, String exerciseId) throws NotFoundException, UnauthorizedException{
        Boolean ret = false;
        String vis = exercisesService.getExerciseVisibility(exerciseId);

        switch (vis) {
            case "public":
                ret = true;
                break;
            case "course":
                String courseId = exercisesService.getExerciseCourse(exerciseId);
                if(courseId != null && coursesService.checkSpecialistInCourse(courseId, specialistId)){
                    ret = true;
                }else{
                    throw new UnauthorizedException("The specialist can not see the course's exercise");
                }
                break;
            case "institution":
                Institution specialistInstitution = institutionsService.getSpecialistInstitution(specialistId);
                Institution exerciseInstitution = exercisesService.getExerciseInstitution(exerciseId);
                if(specialistInstitution.equals(exerciseInstitution)){
                    ret = true;
                }else{
                  throw new UnauthorizedException("The specialist can not see the institution's exercise");  
                }
                break;
            default:
                throw new UnauthorizedException("The specialist can not see the exercise");
                //break;
        }

        return ret;
    }  

    //specilist owner exercicio
    //consegue ver exercicio(dividir para tipo)
    //Dono curso(A instituição ja tem uma função igual)

}
