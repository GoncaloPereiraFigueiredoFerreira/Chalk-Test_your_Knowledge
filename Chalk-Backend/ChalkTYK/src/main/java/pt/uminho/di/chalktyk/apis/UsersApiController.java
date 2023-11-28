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

    public ResponseEntity<List<Object>> usersGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Find users from this institution." ,schema=@Schema()) @Valid @RequestParam(value = "institution", required = false) String institution
,@Parameter(in = ParameterIn.QUERY, description = "Find users from this course (institution is required)." ,schema=@Schema()) @Valid @RequestParam(value = "course", required = false) String course
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<List<InstitutionManager>> usersManagersGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Find managers from this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> usersPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UsersBody1 body
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> usersPut(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody UsersBody body
,
@Parameter(in = ParameterIn.COOKIE, description = "" ,schema=@Schema()) @CookieValue(value="userId", required=false) String userId) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<List<Specialist>> usersSpecialistsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Find the specialists that are the owners of the test. " ,schema=@Schema()) @Valid @RequestParam(value = "testId", required = false) String testId
,@Parameter(in = ParameterIn.QUERY, description = "Find specialists from this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
,@Parameter(in = ParameterIn.QUERY, description = "Find specialists from this course (institution is required). " ,schema=@Schema()) @Valid @RequestParam(value = "courseId", required = false) String courseId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<List<Student>> usersStudentsGet(@Parameter(in = ParameterIn.QUERY, description = "Find the student that is the owner of the resoluton. " ,schema=@Schema()) @Valid @RequestParam(value = "testResolutionId", required = false) String testResolutionId
,@Parameter(in = ParameterIn.QUERY, description = "Find the student that is the owner of the resoluton. " ,schema=@Schema()) @Valid @RequestParam(value = "exerciseResolutionId", required = false) String exerciseResolutionId
,@Parameter(in = ParameterIn.QUERY, description = "Find students from this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
,@Parameter(in = ParameterIn.QUERY, description = "Find students from this course (institution is required). " ,schema=@Schema()) @Valid @RequestParam(value = "courseId", required = false) String courseId
,@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "page", required = false) Integer page
,@Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = false) Integer itemsPerPage
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> usersUserIdDelete(@Parameter(in = ParameterIn.PATH, description = "User identifier", required=true, schema=@Schema()) @PathVariable("userId") String userId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<InlineResponse2002> usersUserIdGet(@Parameter(in = ParameterIn.PATH, description = "User identifier", required=true, schema=@Schema()) @PathVariable("userId") String userId
) {
        throw new RuntimeException("Not implemented");
    }

}
