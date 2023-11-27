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
  @JsonProperty("choiceCotation")
  private Float choiceCotation = null;

  @JsonProperty("penalty")
  private Float penalty = null;

  @JsonProperty("type")
  private String multipleChoiceRubricType = null;

  public MultipleChoiceRubric choiceCotation(Float choiceCotation) {
    this.choiceCotation = choiceCotation;
    return this;
  }

  /**
   * Get choiceCotation
   * @return choiceCotation
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Float getChoiceCotation() {
    return choiceCotation;
  }

  public void setChoiceCotation(Float choiceCotation) {
    this.choiceCotation = choiceCotation;
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
    return Objects.equals(this.choiceCotation, multipleChoiceRubric.choiceCotation) &&
        Objects.equals(this.penalty, multipleChoiceRubric.penalty) &&
        Objects.equals(this.multipleChoiceRubricType, multipleChoiceRubric.multipleChoiceRubricType) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(choiceCotation, penalty, multipleChoiceRubricType, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MultipleChoiceRubric {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    choiceCotation: ").append(toIndentedString(choiceCotation)).append("\n");
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
