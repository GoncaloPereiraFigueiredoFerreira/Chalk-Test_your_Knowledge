package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Institution;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InstitutionWithoutId;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InstitutionsBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InstitutionsInstitutionIdBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class InstitutionsApiController implements InstitutionsApi {

    private static final Logger log = LoggerFactory.getLogger(InstitutionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public InstitutionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Institution>> institutionsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Institution>>(objectMapper.readValue("[ {\n  \"id\" : \"uminho\"\n}, {\n  \"id\" : \"uminho\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Institution>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Institution>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> institutionsInstitutionIdDelete(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InstitutionWithoutId> institutionsInstitutionIdGet(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<InstitutionWithoutId>(objectMapper.readValue("{\n  \"description\" : \"LEI>LCC\",\n  \"institutionId\" : \"\",\n  \"name\" : \"Universidade do minho\"\n}", InstitutionWithoutId.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InstitutionWithoutId>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<InstitutionWithoutId>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> institutionsInstitutionIdPut(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody InstitutionsInstitutionIdBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> institutionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody InstitutionsBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
