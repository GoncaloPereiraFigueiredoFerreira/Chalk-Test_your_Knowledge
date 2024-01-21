package pt.uminho.di.chalktyk.apis;





import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.servlet.http.HttpServletRequest;
import pt.uminho.di.chalktyk.models.institutions.Institution;

import java.io.IOException;
import java.util.List;

@RestController
public class InstitutionsApiController implements InstitutionsApi {

    public ResponseEntity<List<Institution>> getInstitutions(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> deleteInstitutionByID(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Institution> getInstitutionByID(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> updateInstitutionByID(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Institution body
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> createInstitution(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Institution body
, @CookieValue("chalkauthtoken") String jwt) {
        throw new RuntimeException("Not implemented");
    }

}
