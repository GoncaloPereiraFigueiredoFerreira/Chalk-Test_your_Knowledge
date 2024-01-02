package pt.uminho.di.chalktyk.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.uminho.di.chalktyk.models.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.users.Specialist;

public interface SpecialistsApi {
    /**
     * Creates a specialist.
     * @param specialist specialist properties
     * @return identifier of the new specialist
     */
    @Operation(summary = "Create specialist", description = "This method is used to create a specialist.", tags={ "specialist" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid properties.") })
    @RequestMapping(value = "",
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<String> createSpecialist(
            @Parameter(in = ParameterIn.DEFAULT, description = "", required=true,
                    schema=@Schema(implementation = Specialist.class))
            @Valid @RequestBody Specialist specialist);

    /**
     * Gets specialist
     *
     * @param jwtToken
     * @param specialistId identifier of the specialist
     * @return specialist
     */
    @Operation(summary = "Get specialist by specialist id", description = "", tags={ "specialist" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Specialist.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
            @ApiResponse(responseCode = "404", description = "Specialist not found") })
    @RequestMapping(value = "/{specialistId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Specialist> getSpecialistById(
            @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, description = "Specialist identifier", required=true, schema=@Schema()) @PathVariable("specialistId") String specialistId);

    /**
     * Checks if a specialist exists with the given id.
     *
     * @param jwtToken
     * @param specialistId identifier of the specialist
     * @return 'true' if a specialist exists with the given id
     */
    @Operation(summary = "Exists specialist by specialist id.", description = "", tags={ "specialist" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(oneOf = {Specialist.class, Specialist.class, InstitutionManager.class}))),
            @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
            @ApiResponse(responseCode = "404", description = "Specialist not found") })
    @RequestMapping(value = "/{specialistId}/exists",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Boolean> existsSpecialistById(
            @RequestHeader("chalkauthtoken") String jwtToken,
            @Parameter(in = ParameterIn.PATH, description = "Specialist identifier", required=true, schema=@Schema()) @PathVariable("specialistId") String specialistId);
}
