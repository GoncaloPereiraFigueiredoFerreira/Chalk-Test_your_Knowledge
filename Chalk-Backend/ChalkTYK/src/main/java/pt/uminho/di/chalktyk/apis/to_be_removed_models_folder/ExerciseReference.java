package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Used when duplicating an existing exercise.  This schema allows to include the new metadata,  and refer the identifier of the exercise that  contains the exercise data to be copied.
 */
@Schema(description = "Used when duplicating an existing exercise.  This schema allows to include the new metadata,  and refer the identifier of the exercise that  contains the exercise data to be copied.")
@Validated


public class ExerciseReference extends ExerciseMetadata implements OneOfTestGroupExercisesItems {
  @JsonProperty("originalExerciseId")
  private String originalExerciseId = null;

  public ExerciseReference originalExerciseId(String originalExerciseId) {
    this.originalExerciseId = originalExerciseId;
    return this;
  }

  /**
   * Get originalExerciseId
   * @return originalExerciseId
   **/
  @Schema(description = "")
  
  @Pattern(regexp="/^[a-f\\d]{24}$/i") @Size(min=24,max=24)   public String getOriginalExerciseId() {
    return originalExerciseId;
  }

  public void setOriginalExerciseId(String originalExerciseId) {
    this.originalExerciseId = originalExerciseId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExerciseReference exerciseReference = (ExerciseReference) o;
    return Objects.equals(this.originalExerciseId, exerciseReference.originalExerciseId) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(originalExerciseId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExerciseReference {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    originalExerciseId: ").append(toIndentedString(originalExerciseId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
