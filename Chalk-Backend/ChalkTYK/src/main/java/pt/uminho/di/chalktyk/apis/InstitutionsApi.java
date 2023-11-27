
package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Institution;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InstitutionWithoutId;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InstitutionsBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InstitutionsInstitutionIdBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

@Validated
public interface InstitutionsApi {

    @Operation(summary = "Get page of institutions.", description = "", tags={ "institution" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Institution.class)))),
        
        @ApiResponse(responseCode = "400", description = "page and itemsPerPage are required"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/institutions",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Institution>> institutionsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
);


    @Operation(summary = "Delete institution", description = "", tags={ "institution" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid institution supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Institution not found") })
    @RequestMapping(value = "/institutions/{institutionId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> institutionsInstitutionIdDelete(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
);


    @Operation(summary = "Get institution by institution id", description = "", tags={ "institution" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InstitutionWithoutId.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid institution id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Institution not found") })
    @RequestMapping(value = "/institutions/{institutionId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<InstitutionWithoutId> institutionsInstitutionIdGet(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
);


    @Operation(summary = "Update Institution", description = "Update an existent institution in the store", tags={ "institution" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid institution supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Institution not found") })
    @RequestMapping(value = "/institutions/{institutionId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> institutionsInstitutionIdPut(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody InstitutionsInstitutionIdBody body
);


    @Operation(summary = "Create institution", description = "This method is used to create an institution", tags={ "institution" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/institutions",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> institutionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody InstitutionsBody body
);

}

