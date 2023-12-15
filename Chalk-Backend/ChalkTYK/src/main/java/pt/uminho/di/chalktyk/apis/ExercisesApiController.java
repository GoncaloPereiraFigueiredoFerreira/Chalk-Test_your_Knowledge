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

    public ResponseEntity<Void> deleteExerciseById(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
) {
        // TODO - delete exercise by
        throw new RuntimeException("Not implemented.");
    }

    /**
     * Duplicates the exercise that contains the given identifier.
     * The id of the specialist, and if existent, the institution identifier
     * is added to the new exercise metadata. The visibility of the new exercise is
     * set to private, and is not associated with any course.
     * @param exerciseId exercise identifier
     * @return new exercise identifier
     */
    public ResponseEntity<String> duplicateExerciseById(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
) {
        throw new RuntimeException("Not implemented.");
    }

    public ResponseEntity<Void> updateExercise(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExercisesExerciseIdBody body
) {
        throw new RuntimeException("Not implemented.");

    }

    public ResponseEntity<Void> issueExerciseResolutionsCorrection(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.QUERY, description = "Type of correction. The correction can either be automatic or done by AI. For a given exercise, it may support either, both, or none of the correction types. " ,schema=@Schema(allowableValues={ "auto", "ai" }
)) @Valid @RequestParam(value = "correctionType", required = false) String correctionType
) {
        throw new RuntimeException("Not implemented.");
    }

    public ResponseEntity<Integer> countExerciseResolutions(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.QUERY, description = "'false' to count the number of students that made a submission. 'true' to count the total number of submissions." ,schema=@Schema( defaultValue="false")) @Valid @RequestParam(value = "total", required = false, defaultValue="false") Boolean total
) {
        throw new RuntimeException("Not implemented.");
    }

    public ResponseEntity<List<InlineResponse200>> getExerciseResolutions(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
) {
        throw new RuntimeException("Not implemented.");

    }

    public ResponseEntity<Void> createExerciseResolution(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("exerciseId") Integer exerciseId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExerciseResolution body
) {
        throw new RuntimeException("Not implemented.");

    }

    public ResponseEntity<Integer> countExerciseResolutionsByStudent(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        throw new RuntimeException("Not implemented.");

    }

    public ResponseEntity<List<String>> getStudentListOfExerciseResolutionsIdsByExercise(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.PATH, description = "student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        throw new RuntimeException("Not implemented.");

    }

    public ResponseEntity<ExerciseResolution> getLastExerciseResolutionByStudent(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        throw new RuntimeException("Not implemented.");

    }

    public ResponseEntity<Rubric> getExerciseRubric(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
) {
        throw new RuntimeException("Not implemented.");

    }

    public ResponseEntity<Void> createExerciseRubric(@Parameter(in = ParameterIn.PATH, description = "Exercise identifier", required=true, schema=@Schema()) @PathVariable("exerciseId") String exerciseId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExerciseIdRubricBody body
) {
        throw new RuntimeException("Not implemented.");

    }

    public ResponseEntity<List<Exercise>> getExercises(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
, @Parameter(in = ParameterIn.QUERY, description = "Array of identifiers from the tags that will be used to filter the exercises." ,schema=@Schema( defaultValue="[]")) @Valid @RequestParam(value = "tags", required = false, defaultValue="[]") List<String> tags
, @Parameter(in = ParameterIn.QUERY, description = "Value that defines if the exercise must have all the given tags to be retrieved." ,schema=@Schema( defaultValue="false")) @Valid @RequestParam(value = "matchAllTags", required = false, defaultValue="false") Boolean matchAllTags
, @Parameter(in = ParameterIn.QUERY, description = "Describes the type of visibility that the exercises must have.  This parameter must be paired with the parameter 'visibilityTarget'  when the value is either 'institution' or 'course'. " ,schema=@Schema(allowableValues={ "public", "institution", "course" }
, defaultValue="public")) @Valid @RequestParam(value = "visibilityType", required = false, defaultValue="public") String visibilityType
, @Parameter(in = ParameterIn.QUERY, description = "Identifier of the visibility target. For example, if visibilityType='institution',  then this parameter is the identifier of the institution. " ,schema=@Schema()) @Valid @RequestParam(value = "visibilityTarget", required = false) String visibilityTarget
, @Parameter(in = ParameterIn.QUERY, description = "Identifier of the specialist. Used when we want to retrieve the exercises created by a specific specialist." ,schema=@Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId
) {
        throw new RuntimeException("Not implemented.");

    }

    public ResponseEntity<String> createExercise(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ExercisesBody body
) {
        throw new RuntimeException("Not implemented.");

    }

    public ResponseEntity<Void> addCommentToExerciseResolution(@Parameter(in = ParameterIn.PATH, description = "Exercise resolution identifier", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Comment body
) {
        throw new RuntimeException("Not implemented.");
    }

    public ResponseEntity<ExerciseResolution> getExerciseResolution(@Parameter(in = ParameterIn.PATH, description = "Exercise resolution identifier", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
) {
        throw new RuntimeException("Not implemented.");
    }

    public ResponseEntity<Void> exerciseResolutionManualCorrection(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("resolutionId") String resolutionId
, @NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "points", required = true) Float points
) {
        throw new RuntimeException("Not implemented.");
    }

    public ResponseEntity<Void> deleteExerciseRubric(@Parameter(in = ParameterIn.PATH, description = "rubric identifier", required=true, schema=@Schema()) @PathVariable("rubricId") String rubricId
) {
        throw new RuntimeException("Not implemented.");
    }

    public ResponseEntity<Void> updateRubric(@Parameter(in = ParameterIn.PATH, description = "rubric identifier", required=true, schema=@Schema()) @PathVariable("rubricId") String rubricId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Rubric body
) {
        throw new RuntimeException("Not implemented.");
    }

    public ResponseEntity<List<Tag>> listTags(@Parameter(in = ParameterIn.QUERY, description = "Array of paths from which the tags should be retrieved." ,schema=@Schema( defaultValue="[]")) @Valid @RequestParam(value = "paths", required = false, defaultValue="[]") List<String> paths
, @Parameter(in = ParameterIn.QUERY, description = "Number of levels, starting from the given paths that should be retrieved. -1 to retrieve every tag starting at the given paths." ,schema=@Schema( defaultValue="-1")) @Valid @RequestParam(value = "levels", required = false, defaultValue="-1") Integer levels
) {
        throw new RuntimeException("Not implemented.");
    }

}
