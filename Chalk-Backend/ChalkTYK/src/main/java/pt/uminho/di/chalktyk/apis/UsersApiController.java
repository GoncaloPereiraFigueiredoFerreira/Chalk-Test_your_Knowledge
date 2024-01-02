package pt.uminho.di.chalktyk.apis;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InlineResponse2002;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.UsersBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.UsersBody1;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.servlet.http.HttpServletRequest;
import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.dtos.UserUpdateDTO;
import pt.uminho.di.chalktyk.models.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.IUsersService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.io.IOException;
import java.util.List;

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
            JWT jwt = securityService.checkAndUpdateJWT(authToken);
            String userId = securityService.getUserId(jwt);
            usersService.updateBasicProperties(userId, userDTO.getName(), userDTO.getEmail(), userDTO.getPhotoPath(), userDTO.getDescription());
        } catch (UnauthorizedException | BadInputException | NotFoundException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
        return null;
    }

    @Override
    public ResponseEntity<User> getUser(String authToken) {
        return null;
    }

    @Override
    public ResponseEntity<User> login(String authToken) {
        try {
            return new ResponseEntity<>(securityService.login(authToken), HttpStatus.OK);
        } catch (UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<User>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> logout(String authToken) {
        return null;
    }
}
