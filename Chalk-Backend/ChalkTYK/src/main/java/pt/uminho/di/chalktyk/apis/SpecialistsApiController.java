package pt.uminho.di.chalktyk.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.ISpecialistsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

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
    public ResponseEntity<String> createSpecialist(Specialist specialist) {
        try {
            return ResponseEntity.ok(specialistsService.createSpecialist(specialist));
        } catch (BadInputException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Specialist> getSpecialistById(String jwtToken, String specialistId) {
        try {
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = securityService.getUserId(jwt);
            if(specialistId == null)
                throw new BadInputException("Specialist id is null.");
            if(!specialistId.equals(userId))
                throw new UnauthorizedException("The user is not allowed to check another user's information.");
            return ResponseEntity.ok(specialistsService.getSpecialistById(specialistId));
        } catch (UnauthorizedException | BadInputException | NotFoundException e) {
            return new ExceptionResponseEntity<Specialist>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> existsSpecialistById(String jwtToken, String specialistId) {
        try {
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = securityService.getUserId(jwt);
            if(specialistId == null)
                throw new BadInputException("Specialist id is null.");
            if(!specialistId.equals(userId))
                throw new UnauthorizedException("The user is not allowed to check another user's information.");
            return ResponseEntity.ok(specialistsService.existsSpecialistById(specialistId));
        } catch (UnauthorizedException | BadInputException e) {
            return new ExceptionResponseEntity<Boolean>().createRequest(e);
        }
    }

}
