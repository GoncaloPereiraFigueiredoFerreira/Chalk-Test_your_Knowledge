package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * Schema of an open answer exercise. The exercise id is not required.
 */
@Schema(description = "Schema of an open answer exercise. The exercise id is not required.")
@Validated


public class OpenAnswerExerciseWithoutId extends ExerciseBasicProperties implements ExerciseWithoutId {
  @JsonProperty("type")
  private String openAnswerExerciseWithoutIdType = null;

  public OpenAnswerExerciseWithoutId openAnswerExerciseWithoutIdType(String openAnswerExerciseWithoutIdType) {
    this.openAnswerExerciseWithoutIdType = openAnswerExerciseWithoutIdType;
    return this;
  }

  /**
   * Get openAnswerExerciseWithoutIdType
   * @return openAnswerExerciseWithoutIdType
   **/
  @Schema(description = "")
  
    public String getOpenAnswerExerciseWithoutIdType() {
    return openAnswerExerciseWithoutIdType;
  }

  public void setOpenAnswerExerciseWithoutIdType(String openAnswerExerciseWithoutIdType) {
    this.openAnswerExerciseWithoutIdType = openAnswerExerciseWithoutIdType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OpenAnswerExerciseWithoutId openAnswerExerciseWithoutId = (OpenAnswerExerciseWithoutId) o;
    return Objects.equals(this.openAnswerExerciseWithoutIdType, openAnswerExerciseWithoutId.openAnswerExerciseWithoutIdType) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(openAnswerExerciseWithoutIdType, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpenAnswerExerciseWithoutId {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    openAnswerExerciseWithoutIdType: ").append(toIndentedString(openAnswerExerciseWithoutIdType)).append("\n");
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
