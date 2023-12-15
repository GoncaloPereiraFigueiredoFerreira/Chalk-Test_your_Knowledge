package pt.uminho.di.chalktyk.services;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.models.relational.ExerciseSQL;
import pt.uminho.di.chalktyk.models.relational.SpecialistSQL;
import pt.uminho.di.chalktyk.repositories.relational.ExerciseSqlDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service("securatyService") 
public class SecurityService {
    private final IUsersService usersService;
    private final IStudentsService studentsService;
    private final ISpecialistsService specialistsService;
    private final ExerciseSqlDAO exerciseSqlDAO;

    public SecurityService(IUsersService usersService, IStudentsService studentsService, ISpecialistsService specialistsService, ExerciseSqlDAO exerciseSqlDAO){
        this.usersService = usersService;
        this.studentsService = studentsService;
        this.specialistsService = specialistsService;
        this.exerciseSqlDAO = exerciseSqlDAO;
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

    public SpecialistSQL specialistOwnesExercise(string specialistId, string exerciseId){
        Boolean ret = true;
        
        if(!exerciseSqlDAO.existsById(exerciseId)){
            throw new NotFoundException("Exercise not found");
        } 
        if(!specialistsService.existsSpecialistById(specialistId)){
            throw new NotFoundException("Specialist not found");
        }

        ExerciseSQL exercise = exerciseSqlDAO.getReferenceById(exerciseId);
        SpecialistSQL specialist = exercise.getSpecialist();

        if(specialist != null || specialist.getId().equals(specialistId)){
            throw new UnauthorizedException("The specilist is not the owner of the exercise");
        }

        return specialist;
    } 
    //specilist owner exercicio
    //consegue ver (dividir para tipo)
    //Dono curso

}
