package pt.uminho.di.chalktyk.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.ISpecialistsService;
import pt.uminho.di.chalktyk.services.exceptions.*;

@RestController
@RequestMapping("/specialists")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class SpecialistsApiController implements SpecialistsApi{
    
    private final ISecurityService securityService;
    private final ISpecialistsService specialistsService;

    @Autowired
    public SpecialistsApiController(ISecurityService securityService, ISpecialistsService specialistsService) {
        this.securityService = securityService;
        this.specialistsService = specialistsService;
    }
    
    @Override
    public ResponseEntity<User> createSpecialist(String jwtToken, Specialist specialist) {
        try {
            JWT jwt = securityService.parseJWT(jwtToken);
            if(specialist == null || !jwt.getUserEmail().equals(specialist.getEmail()))
                throw new BadInputException("Invalid specialist.");
            specialistsService.createSpecialist(specialist);
            return ResponseEntity.ok(securityService.login(jwtToken));
        } catch (UnauthorizedException | BadInputException | NotFoundException e) {
            return new ExceptionResponseEntity<User>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Specialist> getSpecialistById(String jwtToken, String specialistId) {
        try {
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId();
            if(specialistId == null)
                throw new BadInputException("Specialist id is null.");
            if(!specialistId.equals(userId))
                throw new ForbiddenException("The user is not allowed to check another user's information.");
            return ResponseEntity.ok(specialistsService.getSpecialistById(specialistId));
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Specialist>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> existsSpecialistById(String jwtToken, String specialistId) {
        try {
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId();
            if(specialistId == null)
                throw new BadInputException("Specialist id is null.");
            if(!specialistId.equals(userId))
                throw new ForbiddenException("The user is not allowed to check another user's information.");
            return ResponseEntity.ok(specialistsService.existsSpecialistById(specialistId));
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Boolean>().createRequest(e);
        }
    }

}
