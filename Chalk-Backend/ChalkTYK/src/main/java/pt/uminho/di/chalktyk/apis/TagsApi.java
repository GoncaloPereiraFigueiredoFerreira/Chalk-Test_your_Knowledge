package pt.uminho.di.chalktyk.apis;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
import jakarta.validation.Valid;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;

/**
 * TagsApi
 */
public interface TagsApi {

    @Operation(summary = "Get tag by id", description = "", tags = { "tags" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tag.class))),

        @ApiResponse(responseCode = "400", description = "Invalid tag id supplied"),

        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),

        @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @RequestMapping(value = "/{tagId}", produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<Tag> getTag(@Parameter(in = ParameterIn.PATH, description = "Tag identifier", required = true, schema = @Schema()) @PathVariable("tagId") String tagId);

    @Operation(summary = "Get tag by name and path",description = "",tags = { "tags" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),

        @ApiResponse(responseCode = "400", description = "Invalid tag name or path"),

        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),

        @ApiResponse(responseCode = "404", description = "Tag or Path not found")
    })
    @RequestMapping(value = "/name", produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<Boolean> existsTagByNameAndPath(@Parameter(in = ParameterIn.QUERY, description = "Tag name", schema = @Schema(defaultValue = "")) @RequestParam(value = "name", required = true) String tagName, @Parameter(in = ParameterIn.QUERY, description = "Tag Path", schema = @Schema(defaultValue = "")) @Valid @RequestParam(value = "path", required = true) String tagPath);

    @Operation(summary = "Get list of tags starting at the given path", description = "", tags = { "" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),

        @ApiResponse(responseCode = "400", description = "Invalid path or level"),

        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),

        @ApiResponse(responseCode = "404", description = "Path not found")
    })
    @RequestMapping(value = "/path", produces = { "application/json" },method = RequestMethod.GET)
    ResponseEntity<List<Tag>> listTagsPath(@Parameter(in = ParameterIn.PATH, description = "Level of search depth(-1 for all levels)", schema = @Schema(defaultValue = "0")) @RequestParam(value = "level", required = true) Integer levels,  @Parameter(in = ParameterIn.QUERY, description = "Tag Path", schema = @Schema(defaultValue = "")) @Valid @RequestParam(value = "path", required = true) String tagPath);

    @Operation(summary = "Get list of tags starting at the given path", description = "", tags = { "" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "successful operation"),

        @ApiResponse(responseCode = "400", description = "Invalid path or level"),

        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),

        @ApiResponse(responseCode = "404", description = "Path not found")
    })
    @RequestMapping(value = "/paths", produces = { "application/json" },method = RequestMethod.GET)
    ResponseEntity<List<Tag>> listTagsPaths(@Parameter(in = ParameterIn.PATH, description = "Level of search depth(-1 for all levels)", schema = @Schema(defaultValue = "0")) @RequestParam(value = "level", required = true) Integer levels,  @Parameter(in = ParameterIn.QUERY, description = "Tag Path", schema = @Schema(defaultValue = "[]")) @Valid @RequestParam(value = "path", required = true) List<String> tagPaths);

}
