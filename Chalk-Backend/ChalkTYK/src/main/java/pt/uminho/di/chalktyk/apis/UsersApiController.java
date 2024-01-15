package pt.uminho.di.chalktyk.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.dtos.UserUpdateDTO;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.IUsersService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@RestController
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class UsersApiController implements UsersApi {

    private final ISecurityService securityService;
    private final IUsersService usersService;

    @Autowired
    public UsersApiController(ISecurityService securityService, IUsersService usersService) {
        this.securityService = securityService;
        this.usersService = usersService;
    }

    @Override
    public ResponseEntity<Void> updateBasicProperties(String authToken, UserUpdateDTO userDTO) {
        try {
            JWT jwt = securityService.validateJWT(authToken);
            String userId = jwt.getUserId();
            usersService.updateBasicProperties(userId, userDTO.getName(), userDTO.getEmail(), userDTO.getPhotoPath(), userDTO.getDescription());
            return ResponseEntity.ok(null);
        } catch (UnauthorizedException | BadInputException | NotFoundException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<User> getUserById(String authToken, String userId) {
        try {
            JWT jwt = securityService.validateJWT(authToken);
            if(userId == null)
                throw new BadInputException("There is no user with a 'null' identifier.");
            if(!userId.equals(jwt.getUserId()))
                throw new UnauthorizedException("The user is not allowed to check another user's information.");
            userId = jwt.getUserId();
            return ResponseEntity.ok(usersService.getUserById(userId));
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<User>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<User> login(String authToken) {
        try {
            return ResponseEntity.ok(securityService.login(authToken));
        } catch (UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<User>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> logout(String authToken) {
        try {
            securityService.logout(authToken);
            return ResponseEntity.ok(null);
        } catch (UnauthorizedException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }
}
