package pt.uminho.di.chalktyk.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pt.uminho.di.chalktyk.dtos.ChatExerciseDTO;
import pt.uminho.di.chalktyk.dtos.OpenAnswerDTO;

/**
 * AIApi
 */
@Validated
public interface AIApi {
    @Operation(summary = "Return a new question given a answer",description = "", tags = { "ai" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Invalid camp Questions or Topics"),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation")
    })
    @RequestMapping(value = "/chat/new", produces = { "application/json" }, method = RequestMethod.POST)
    ResponseEntity<String> getNewQuestion(@Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody ChatExerciseDTO chatExercise);

    @Operation(summary = "Return the evaluation the the chat exercise given by IA", description = "", tags = { "ai" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Float.class))),
        @ApiResponse(responseCode = "400", description = "Invalid camp for Questions or Topics"),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation")
    })
    @RequestMapping(value = "/chat/evaluation", produces = { "application/json"}, method = RequestMethod.POST)
    ResponseEntity<Float> getEvaluationChat(@Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody ChatExerciseDTO chatExercise);

    @Operation(summary = "Return the evaluation the the open answer exercise given by IA", description = "", tags = { "ai" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Float.class))),
        @ApiResponse(responseCode = "400", description = "Invalid camp for Questions or Topics"),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation")
    })
    @RequestMapping(value = "/open/evaluation", produces = { "application/json"}, method = RequestMethod.POST)
    ResponseEntity<Float> getEvaluationOpenAnswer(@Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody OpenAnswerDTO OAExercise);
}
