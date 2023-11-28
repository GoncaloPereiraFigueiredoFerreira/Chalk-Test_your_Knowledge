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

    public ResponseEntity<List<Institution>> institutionsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> institutionsInstitutionIdDelete(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<InstitutionWithoutId> institutionsInstitutionIdGet(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> institutionsInstitutionIdPut(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody InstitutionsInstitutionIdBody body
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> institutionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody InstitutionsBody body
) {
        throw new RuntimeException("Not implemented");
    }

}
