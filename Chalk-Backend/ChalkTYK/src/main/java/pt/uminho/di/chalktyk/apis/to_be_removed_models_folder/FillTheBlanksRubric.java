package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * FillTheBlanksRubric
 */
@Validated


public class FillTheBlanksRubric extends RubricBasicProperties implements ExerciseIdRubricBody, Rubric {
  @JsonProperty("fillingPoints")
  private Float fillingPoints = null;

  @JsonProperty("penalty")
  private Float penalty = null;

  @JsonProperty("type")
  private String fillTheBlanksRubricType = null;

  public FillTheBlanksRubric fillingPoints(Float fillingPoints) {
    this.fillingPoints = fillingPoints;
    return this;
  }

  /**
   * Get fillingPoints
   * @return fillingPoints
   **/
  @Schema(description = "")
  
    public Float getFillingPoints() {
    return fillingPoints;
  }

  public void setFillingPoints(Float fillingPoints) {
    this.fillingPoints = fillingPoints;
  }

  public FillTheBlanksRubric penalty(Float penalty) {
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

  public FillTheBlanksRubric fillTheBlanksRubricType(String fillTheBlanksRubricType) {
    this.fillTheBlanksRubricType = fillTheBlanksRubricType;
    return this;
  }

  /**
   * Get fillTheBlanksRubricType
   * @return fillTheBlanksRubricType
   **/
  @Schema(description = "")
  
    public String getFillTheBlanksRubricType() {
    return fillTheBlanksRubricType;
  }

  public void setFillTheBlanksRubricType(String fillTheBlanksRubricType) {
    this.fillTheBlanksRubricType = fillTheBlanksRubricType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FillTheBlanksRubric fillTheBlanksRubric = (FillTheBlanksRubric) o;
    return Objects.equals(this.fillingPoints, fillTheBlanksRubric.fillingPoints) &&
        Objects.equals(this.penalty, fillTheBlanksRubric.penalty) &&
        Objects.equals(this.fillTheBlanksRubricType, fillTheBlanksRubric.fillTheBlanksRubricType) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fillingPoints, penalty, fillTheBlanksRubricType, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FillTheBlanksRubric {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    fillingPoints: ").append(toIndentedString(fillingPoints)).append("\n");
    sb.append("    penalty: ").append(toIndentedString(penalty)).append("\n");
    sb.append("    fillTheBlanksRubricType: ").append(toIndentedString(fillTheBlanksRubricType)).append("\n");
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
