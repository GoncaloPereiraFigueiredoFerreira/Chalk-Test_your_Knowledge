package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * MultipleChoiceRubric
 */
@Validated


public class MultipleChoiceRubric extends RubricBasicProperties implements ExerciseIdRubricBody, Rubric {
  @JsonProperty("choicePoints")
  private Float choicePoints = null;

  @JsonProperty("penalty")
  private Float penalty = null;

  @JsonProperty("type")
  private String multipleChoiceRubricType = null;

  public MultipleChoiceRubric choicePoints(Float choicePoints) {
    this.choicePoints = choicePoints;
    return this;
  }

  /**
   * Get choicePoints
   * @return choicePoints
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Float getChoicePoints() {
    return choicePoints;
  }

  public void setChoicePoints(Float choicePoints) {
    this.choicePoints = choicePoints;
  }

  public MultipleChoiceRubric penalty(Float penalty) {
    this.penalty = penalty;
    return this;
  }

  /**
   * Get penalty
   * @return penalty
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Float getPenalty() {
    return penalty;
  }

  public void setPenalty(Float penalty) {
    this.penalty = penalty;
  }

  public MultipleChoiceRubric multipleChoiceRubricType(String multipleChoiceRubricType) {
    this.multipleChoiceRubricType = multipleChoiceRubricType;
    return this;
  }

  /**
   * Get multipleChoiceRubricType
   * @return multipleChoiceRubricType
   **/
  @Schema(description = "")
  
    public String getMultipleChoiceRubricType() {
    return multipleChoiceRubricType;
  }

  public void setMultipleChoiceRubricType(String multipleChoiceRubricType) {
    this.multipleChoiceRubricType = multipleChoiceRubricType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultipleChoiceRubric multipleChoiceRubric = (MultipleChoiceRubric) o;
    return Objects.equals(this.choicePoints, multipleChoiceRubric.choicePoints) &&
        Objects.equals(this.penalty, multipleChoiceRubric.penalty) &&
        Objects.equals(this.multipleChoiceRubricType, multipleChoiceRubric.multipleChoiceRubricType) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(choicePoints, penalty, multipleChoiceRubricType, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MultipleChoiceRubric {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    choicePoints: ").append(toIndentedString(choicePoints)).append("\n");
    sb.append("    penalty: ").append(toIndentedString(penalty)).append("\n");
    sb.append("    multipleChoiceRubricType: ").append(toIndentedString(multipleChoiceRubricType)).append("\n");
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
