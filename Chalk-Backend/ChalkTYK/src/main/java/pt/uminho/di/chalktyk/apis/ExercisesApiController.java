package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Comment;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Exercise;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.ExerciseIdRubricBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.ExerciseResolution;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.ExercisesBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.ExercisesExerciseIdBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InlineResponse200;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Rubric;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Tag;
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
public class ExercisesApiController implements ExercisesApi {

    private static final Logger log = LoggerFactory.getLogger(ExercisesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ExercisesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> exercisesExerciseIdDelete(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> exercisesExerciseIdDuplicatePost(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<String>(objectMapper.readValue("\"\"", String.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> exercisesExerciseIdPut(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExercisesExerciseIdBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> exercisesExerciseIdResolutionsCorrectionPut(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
,@Parameter(in = ParameterIn.QUERY, description = "Type of correction. The correction can either be automatic or done by AI. For a given exercise, it may support either, both, or none of the correction types. " ,schema=@Schema(allowableValues={ "auto", "ai" }
)) @Valid @RequestParam(value = "correctionType", required = false) String correctionType
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Integer> exercisesExerciseIdResolutionsCountGet(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
,@Parameter(in = ParameterIn.QUERY, description = "'false' to count the number of students that made a submission. 'true' to count the total number of submissions." ,schema=@Schema( defaultValue="false")) @Valid @RequestParam(value = "total", required = false, defaultValue="false") Boolean total
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Integer>(objectMapper.readValue("0", Integer.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Integer>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<InlineResponse200>> exercisesExerciseIdResolutionsGet(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
,@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<InlineResponse200>>(objectMapper.readValue("[ {\n  \"student\" : {\n    \"name\" : \"name\",\n    \"id\" : \"id\",\n    \"email\" : \"\"\n  },\n  \"resolution\" : \"\"\n}, {\n  \"student\" : {\n    \"name\" : \"name\",\n    \"id\" : \"id\",\n    \"email\" : \"\"\n  },\n  \"resolution\" : \"\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<InlineResponse200>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<InlineResponse200>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> exercisesExerciseIdResolutionsPost(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("exerciseId") Integer exerciseId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExerciseResolution body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Integer> exercisesExerciseIdResolutionsStudentIdCountGet(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
,@Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Integer>(objectMapper.readValue("0", Integer.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Integer>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<String>> exercisesExerciseIdResolutionsStudentIdIdsGet(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
,@Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<String>>(objectMapper.readValue("[ \"\", \"\" ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<String>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ExerciseResolution> exercisesExerciseIdResolutionsStudentIdLastGet(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
,@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ExerciseResolution>(objectMapper.readValue("\"\"", ExerciseResolution.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ExerciseResolution>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ExerciseResolution>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Rubric> exercisesExerciseIdRubricGet(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Rubric>(objectMapper.readValue("\"\"", Rubric.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Rubric>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Rubric>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> exercisesExerciseIdRubricPost(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExerciseIdRubricBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Exercise>> exercisesGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Array of identifiers from the tags that will be used to filter the exercises." ,schema=@Schema( defaultValue="[]")) @Valid @RequestParam(value = "tags", required = false, defaultValue="[]") List<String> tags
,@Parameter(in = ParameterIn.QUERY, description = "Value that defines if the exercise must have all the given tags to be retrieved." ,schema=@Schema( defaultValue="false")) @Valid @RequestParam(value = "matchAllTags", required = false, defaultValue="false") Boolean matchAllTags
,@Parameter(in = ParameterIn.QUERY, description = "Describes the type of visibility that the exercises must have.  This parameter must be paired with the parameter 'visibilityTarget'  when the value is either 'institution' or 'course'. " ,schema=@Schema(allowableValues={ "public", "institution", "course" }
, defaultValue="public")) @Valid @RequestParam(value = "visibilityType", required = false, defaultValue="public") String visibilityType
,@Parameter(in = ParameterIn.QUERY, description = "Identifier of the visibility target. For example, if visibilityType='institution',  then this parameter is the identifier of the institution. " ,schema=@Schema()) @Valid @RequestParam(value = "visibilityTarget", required = false) String visibilityTarget
,@Parameter(in = ParameterIn.QUERY, description = "Identifier of the specialist. Used when we want to retrieve the exercises created by a specific specialist." ,schema=@Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Exercise>>(objectMapper.readValue("[ \"\", \"\" ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Exercise>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Exercise>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> exercisesPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExercisesBody body
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<String>(objectMapper.readValue("\"\"", String.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> exercisesResolutionsResolutionIdCommentPost(@Parameter(in = ParameterIn.PATH, description = "Exercise resolution identifier", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Comment body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ExerciseResolution> exercisesResolutionsResolutionIdGet(@Parameter(in = ParameterIn.PATH, description = "Exercise resolution identifier", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ExerciseResolution>(objectMapper.readValue("\"\"", ExerciseResolution.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ExerciseResolution>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ExerciseResolution>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> exercisesResolutionsResolutionIdManualCorrectionPost(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
,@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "cotation", required = true) Float cotation
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> exercisesRubricsRubricIdDelete(@Parameter(in = ParameterIn.PATH, description = "rubric identifier", required=true, schema=@Schema()) @PathVariable("rubricId") String rubricId
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> exercisesRubricsRubricIdPut(@Parameter(in = ParameterIn.PATH, description = "rubric identifier", required=true, schema=@Schema()) @PathVariable("rubricId") String rubricId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Rubric body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Tag>> exercisesTagGet(@Parameter(in = ParameterIn.QUERY, description = "Array of paths from which the tags should be retrieved." ,schema=@Schema( defaultValue="[]")) @Valid @RequestParam(value = "paths", required = false, defaultValue="[]") List<String> paths
,@Parameter(in = ParameterIn.QUERY, description = "Number of levels, starting from the given paths that should be retrieved. -1 to retrieve every tag starting at the given paths." ,schema=@Schema( defaultValue="-1")) @Valid @RequestParam(value = "levels", required = false, defaultValue="-1") Integer levels
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Tag>>(objectMapper.readValue("[ {\n  \"name\" : \"Algebra\",\n  \"path\" : \"/Matematica/\"\n}, {\n  \"name\" : \"Algebra\",\n  \"path\" : \"/Matematica/\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Tag>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Tag>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
