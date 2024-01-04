package pt.uminho.di.chalktyk.services;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.login.BlackListedJWT;
import pt.uminho.di.chalktyk.models.login.Login;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.repositories.BlackListedJWTDao;
import pt.uminho.di.chalktyk.repositories.LoginDao;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Service("securityService")
public class SecurityService implements ISecurityService{
    private final IUsersService usersService;
    private final IStudentsService studentsService;
    private final ISpecialistsService specialistsService;
    private final IExercisesService exercisesService;
    private final ICoursesService coursesService;
    private final IInstitutionsService institutionsService;
    private final LoginDao loginDao;
    private final BlackListedJWTDao blackListedJWTDao;
    
    public SecurityService(IUsersService usersService, IStudentsService studentsService,
                           ISpecialistsService specialistsService, IExercisesService exercisesService, ICoursesService coursesService,
                           IInstitutionsService institutionsService, LoginDao loginDao, BlackListedJWTDao blackListedJWTDao) {
        this.usersService = usersService;
        this.studentsService = studentsService;
        this.specialistsService = specialistsService;
        this.exercisesService = exercisesService;
        this.coursesService = coursesService;
        this.institutionsService = institutionsService;
        this.loginDao = loginDao;
        this.blackListedJWTDao = blackListedJWTDao;
    }

    /* ***** Auxiliary methods ***** */

    private boolean isJWTValid(String jwtToken){
        try {
            new JWT(jwtToken);
            return true;
        } catch (JwtException | ParseException e) {
            return false;
        }
    }

    /**
     * Converts email present in the jwt token to a user id,
     * and sets it in the JWT.
     * @param jwt
     * @throws UnauthorizedException if user with the given email was not found
     */
    private void setsJWTUserId(JWT jwt) throws UnauthorizedException {
        try {
            // converts the email to a user id, and saves it in the JWT instance
            String email = (String) jwt.getPayloadParam("username");
            jwt.setUserId(usersService.getUserIdByEmail(email));
        }catch (NotFoundException nfe){
            throw new UnauthorizedException("User does not exist.");
        }
    }

    /**
     * Checks if user is logged in. If the user is logged in but the token expired, updates the token.
     * @param jwt JWT Token
     * @return user's id
     * @throws UnauthorizedException if the user does not exist, of if the user already is logged in with a valid
     */
    private String checkAndUpdateLogin(JWT jwt) throws UnauthorizedException{
        String userId = jwt.getUserId();
        Login login = loginDao.findById(userId).orElse(null);

        // If the user is not logged in, then a log in operation should be performed first, before using the token,
        if(login == null)
            throw new UnauthorizedException("Not logged in.");

        //If the currentTokenString is no longer valid, it needs to be updated
        String currentTokenString = login.getJwtTokenString();
        if(isJWTValid(currentTokenString)){
            // The current token is valid and should not be updated without a new login.
            // If the current token is valid and the given token is different from the current token,
            // an Unauthorized exception should be thrown.
            if(!jwt.getJwsString().equals(currentTokenString))
                throw new UnauthorizedException("Current token is still valid. Only a new login can update the token.");
        }else{
            // current token is invalid, therefore it can be updated
            login.setJwtTokenString(jwt.getJwsString());
            loginDao.save(login);
        }

        return userId;
    }

    /* ***** Main methods ***** */

    public JWT parseJWT(String jwtToken) throws UnauthorizedException {
        try {
            return new JWT(jwtToken);
        } catch (JwtException | ParseException e) {
            throw new UnauthorizedException("Invalid token.");
        }
    }

    /**
     * Cannot be used to log in. As this checks if the token is valid relatively to the existing token.
     * @param jwtTokenString JWT token as string
     * @return
     * @throws UnauthorizedException
     */
    @Override
    @Transactional
    public JWT validateJWT(String jwtTokenString) throws UnauthorizedException {
        JWT jwt = parseJWT(jwtTokenString);
        setsJWTUserId(jwt);
        checkAndUpdateLogin(jwt);
        return jwt;
    }

    @Override
    @Transactional
    public User login(String jwtToken) throws UnauthorizedException, NotFoundException {
        if(blackListedJWTDao.existsById(jwtToken))
            throw new UnauthorizedException("Authentication token is blacklisted.");
        JWT jwt = parseJWT(jwtToken);
        setsJWTUserId(jwt);
        String userId = jwt.getUserId();
        User user = usersService.getUserById(userId);
        Login login = loginDao.findById(userId).orElse(null);

        // If the user is not logged in, logs the user in.
        if(login == null) {
            loginDao.save(new Login(userId, jwtToken));
        } else{
            String currentTokenString = login.getJwtTokenString();

            if(!jwt.getJwsString().equals(currentTokenString)) {
                // If the current token is valid and is not equal to the given token,
                // blacklists the current token and sets the given token
                // as the current one.
                if(isJWTValid(currentTokenString))
                    blackListedJWTDao.save(new BlackListedJWT(currentTokenString));

                // updates the login with the new jwt token
                login.setJwtTokenString(jwt.getJwsString());
                loginDao.save(login);
            }
        }

        return user;
    }

    @Override
    @Transactional
    public void logout(String jwtToken) throws UnauthorizedException {
        JWT jwt = parseJWT(jwtToken);
        String userId = checkAndUpdateLogin(jwt);
        Login login = loginDao.findById(userId).orElse(null);
        assert login != null; // login is used in checkAndUpdateLogin therefore it should not be null.
        blackListedJWTDao.save(new BlackListedJWT(login.getJwtTokenString())); // blacklists token
        loginDao.delete(login); // deletes login information
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
        String vis = exercisesService.getExerciseVisibility(exerciseId).toString();

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
                String studentInstitutionId = institutionsService.getStudentInstitution(studentId).getName();
                String exerciseInstitutionId = exercisesService.getExerciseInstitution(exerciseId);
                if(studentInstitutionId.equals(exerciseInstitutionId)){
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
        String vis = exercisesService.getExerciseVisibility(exerciseId).toString();

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
                String specialistInstitutionId = institutionsService.getSpecialistInstitution(specialistId).getName();
                String exerciseInstitutionId = exercisesService.getExerciseInstitution(exerciseId);
                if(specialistInstitutionId.equals(exerciseInstitutionId)){
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
