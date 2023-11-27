package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

/**
 * OpenAnswerRubricCriteria
 */
@Validated


public class OpenAnswerRubricCriteria   {
  @JsonProperty("standards")
  @Valid
  private List<OpenAnswerRubricStandards> standards = null;

  @JsonProperty("title")
  private String title = null;

  public OpenAnswerRubricCriteria standards(List<OpenAnswerRubricStandards> standards) {
    this.standards = standards;
    return this;
  }

  public OpenAnswerRubricCriteria addStandardsItem(OpenAnswerRubricStandards standardsItem) {
    if (this.standards == null) {
      this.standards = new ArrayList<OpenAnswerRubricStandards>();
    }
    this.standards.add(standardsItem);
    return this;
  }

  /**
   * Get standards
   * @return standards
   **/
  @Schema(description = "")
      @Valid
    public List<OpenAnswerRubricStandards> getStandards() {
    return standards;
  }

  public void setStandards(List<OpenAnswerRubricStandards> standards) {
    this.standards = standards;
  }

  public OpenAnswerRubricCriteria title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   **/
  @Schema(description = "")
  
    public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OpenAnswerRubricCriteria openAnswerRubricCriteria = (OpenAnswerRubricCriteria) o;
    return Objects.equals(this.standards, openAnswerRubricCriteria.standards) &&
        Objects.equals(this.title, openAnswerRubricCriteria.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(standards, title);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpenAnswerRubricCriteria {\n");
    
    sb.append("    standards: ").append(toIndentedString(standards)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
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
