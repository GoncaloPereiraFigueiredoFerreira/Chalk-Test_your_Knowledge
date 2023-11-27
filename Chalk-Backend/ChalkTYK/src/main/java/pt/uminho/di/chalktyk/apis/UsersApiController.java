package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InlineResponse2002;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InstitutionManager;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Specialist;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Student;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.UsersBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.UsersBody1;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Object>> usersGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Find users from this institution." ,schema=@Schema()) @Valid @RequestParam(value = "institution", required = false) String institution
,@Parameter(in = ParameterIn.QUERY, description = "Find users from this course (institution is required)." ,schema=@Schema()) @Valid @RequestParam(value = "course", required = false) String course
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Object>>(objectMapper.readValue("[ \"\", \"\" ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Object>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<InstitutionManager>> usersManagersGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Find managers from this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<InstitutionManager>>(objectMapper.readValue("[ {\n  \"id\" : \"id_Ray\",\n  \"institutionId\" : \"institution1\",\n  \"name\" : \"Rui Braga\",\n  \"photoPath\" : \"https://media.vogue.fr/photos/5c3618b2093f3f72befae9d6/2:3/w_2560%2Cc_limit/mood_gosling_8365.jpeg?lang=eng\"\n}, {\n  \"id\" : \"id_Ray\",\n  \"institutionId\" : \"institution1\",\n  \"name\" : \"Rui Braga\",\n  \"photoPath\" : \"https://media.vogue.fr/photos/5c3618b2093f3f72befae9d6/2:3/w_2560%2Cc_limit/mood_gosling_8365.jpeg?lang=eng\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<InstitutionManager>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<InstitutionManager>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> usersPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UsersBody1 body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> usersPut(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UsersBody body
,
@Parameter(in = ParameterIn.COOKIE, description = "" ,schema=@Schema()) @CookieValue(value="userId", required=false) String userId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Specialist>> usersSpecialistsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Find the specialists that are the owners of the test. " ,schema=@Schema()) @Valid @RequestParam(value = "testId", required = false) String testId
,@Parameter(in = ParameterIn.QUERY, description = "Find specialists from this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
,@Parameter(in = ParameterIn.QUERY, description = "Find specialists from this course (institution is required). " ,schema=@Schema()) @Valid @RequestParam(value = "courseId", required = false) String courseId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Specialist>>(objectMapper.readValue("[ {\n  \"id\" : \"id_Alex\",\n  \"name\" : \"Alexandre Martins\",\n  \"photoPath\" : \"https://i.imgur.com/FRwhrhG.jpg\"\n}, {\n  \"id\" : \"id_Alex\",\n  \"name\" : \"Alexandre Martins\",\n  \"photoPath\" : \"https://i.imgur.com/FRwhrhG.jpg\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Specialist>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Specialist>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Student>> usersStudentsGet(@Parameter(in = ParameterIn.QUERY, description = "Find the student that is the owner of the resoluton. " ,schema=@Schema()) @Valid @RequestParam(value = "testResolutionId", required = false) String testResolutionId
,@Parameter(in = ParameterIn.QUERY, description = "Find the student that is the owner of the resoluton. " ,schema=@Schema()) @Valid @RequestParam(value = "exerciseResolutionId", required = false) String exerciseResolutionId
,@Parameter(in = ParameterIn.QUERY, description = "Find students from this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
,@Parameter(in = ParameterIn.QUERY, description = "Find students from this course (institution is required). " ,schema=@Schema()) @Valid @RequestParam(value = "courseId", required = false) String courseId
,@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "page", required = false) Integer page
,@Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = false) Integer itemsPerPage
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Student>>(objectMapper.readValue("[ \"\", \"\" ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Student>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Student>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> usersUserIdDelete(@Parameter(in = ParameterIn.PATH, description = "User identifier", required=true, schema=@Schema()) @PathVariable("userId") String userId
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InlineResponse2002> usersUserIdGet(@Parameter(in = ParameterIn.PATH, description = "User identifier", required=true, schema=@Schema()) @PathVariable("userId") String userId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<InlineResponse2002>(objectMapper.readValue("\"\"", InlineResponse2002.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InlineResponse2002>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<InlineResponse2002>(HttpStatus.NOT_IMPLEMENTED);
    }

}
