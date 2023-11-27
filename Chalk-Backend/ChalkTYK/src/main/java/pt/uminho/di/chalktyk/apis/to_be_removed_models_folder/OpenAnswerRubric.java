package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * OpenAnswerRubric
 */
@Validated


public class OpenAnswerRubric extends RubricBasicProperties implements ExerciseIdRubricBody, Rubric {
  @JsonProperty("criteria")
  @Valid
  private List<OpenAnswerRubricCriteria> criteria = new ArrayList<OpenAnswerRubricCriteria>();

  @JsonProperty("type")
  private String openAnswerRubricType = null;

  public OpenAnswerRubric criteria(List<OpenAnswerRubricCriteria> criteria) {
    this.criteria = criteria;
    return this;
  }

  public OpenAnswerRubric addCriteriaItem(OpenAnswerRubricCriteria criteriaItem) {
    this.criteria.add(criteriaItem);
    return this;
  }

  /**
   * Get criteria
   * @return criteria
   **/
  @Schema(required = true, description = "")
      @NotNull
    @Valid
    public List<OpenAnswerRubricCriteria> getCriteria() {
    return criteria;
  }

  public void setCriteria(List<OpenAnswerRubricCriteria> criteria) {
    this.criteria = criteria;
  }

  public OpenAnswerRubric openAnswerRubricType(String openAnswerRubricType) {
    this.openAnswerRubricType = openAnswerRubricType;
    return this;
  }

  /**
   * Get openAnswerRubricType
   * @return openAnswerRubricType
   **/
  @Schema(description = "")
  
    public String getOpenAnswerRubricType() {
    return openAnswerRubricType;
  }

  public void setOpenAnswerRubricType(String openAnswerRubricType) {
    this.openAnswerRubricType = openAnswerRubricType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OpenAnswerRubric openAnswerRubric = (OpenAnswerRubric) o;
    return Objects.equals(this.criteria, openAnswerRubric.criteria) &&
        Objects.equals(this.openAnswerRubricType, openAnswerRubric.openAnswerRubricType) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(criteria, openAnswerRubricType, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpenAnswerRubric {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    criteria: ").append(toIndentedString(criteria)).append("\n");
    sb.append("    openAnswerRubricType: ").append(toIndentedString(openAnswerRubricType)).append("\n");
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
