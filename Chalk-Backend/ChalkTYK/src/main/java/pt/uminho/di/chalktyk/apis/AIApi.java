package pt.uminho.di.chalktyk.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pt.uminho.di.chalktyk.dtos.ChatExerciseDTO;
import pt.uminho.di.chalktyk.dtos.GenerateQuestionAIDTO;
import pt.uminho.di.chalktyk.dtos.MultipleChoiceAIDTO;
import pt.uminho.di.chalktyk.dtos.TrueOrFalseAIDTO;
import pt.uminho.di.chalktyk.dtos.OpenAnswerAIDTO;

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
   ResponseEntity<String> getNewQuestion(@Parameter(in = ParameterIn.DEFAULT, required = true) @RequestBody ChatExerciseDTO chatExercise, @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwt);

    @Operation(summary = "Return the evaluation the the chat exercise given by IA", description = "", tags = { "ai" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Float.class))),
        @ApiResponse(responseCode = "400", description = "Invalid camp for Questions or Topics"),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation")
    })
    @RequestMapping(value = "/chat/evaluation", produces = { "application/json"}, method = RequestMethod.GET)
    ResponseEntity<Float> getEvaluationChat(@Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @RequestParam(value = "resolutionId", required = true) String resolutionId,@Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @RequestParam(value = "exerciseId") String exerciseId, @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwt);

    @Operation(summary = "Return the evaluation the the open answer exercise given by IA", description = "", tags = { "ai" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Float.class))),
        @ApiResponse(responseCode = "400", description = "Invalid camp for Questions or Topics"),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation")
    })
    @RequestMapping(value = "/open/evaluation", produces = { "application/json"}, method = RequestMethod.GET)
    ResponseEntity<Float> getEvaluationOpenAnswer(@Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @RequestParam(value = "resolutionId", required = true) String resolutionId,@Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema()) @RequestParam(value = "exerciseId") String exerciseId, @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwt);

    @Operation(summary = "Return a new multiple choice question based on the text provided and input from the user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json",schema =  @Schema(implementation = MultipleChoiceAIDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid camp text or input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation")
    })    
    @RequestMapping(value = "/new/multipleChoice", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<MultipleChoiceAIDTO> getNewMultiple(@Parameter(in = ParameterIn.DEFAULT, required = true) GenerateQuestionAIDTO inputQuestion, @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwt);

    @Operation(summary = "Return a new open answer question based on the text provided and input from the user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json",schema =  @Schema(implementation = OpenAnswerAIDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid camp text or input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation")
    })    
    @RequestMapping(value = "/new/openAnswer", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<OpenAnswerAIDTO> getNewOpenAnswer(@Parameter(in = ParameterIn.DEFAULT, required = true) GenerateQuestionAIDTO inputQuestion, @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwt);

    @Operation(summary = "Return a new true or false question based on the text provided and input from the user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json",schema =  @Schema(implementation = TrueOrFalseAIDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid camp text or input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized operation")
    })    
    @RequestMapping(value = "/new/trueOrFalse", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<TrueOrFalseAIDTO> getNewTrueOrFalse(@Parameter(in = ParameterIn.DEFAULT, required = true) GenerateQuestionAIDTO inputQuestion, @Parameter(in = ParameterIn.HEADER, required = true, description = "authentication token") @CookieValue("chalkauthtoken") String jwt);
}
