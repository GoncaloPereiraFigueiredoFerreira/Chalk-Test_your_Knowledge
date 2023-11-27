package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * OpenAnswerRubricStandards
 */
@Validated


public class OpenAnswerRubricStandards   {
  @JsonProperty("cotation")
  private Float cotation = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("title")
  private String title = null;

  public OpenAnswerRubricStandards cotation(Float cotation) {
    this.cotation = cotation;
    return this;
  }

  /**
   * Get cotation
   * @return cotation
   **/
  @Schema(description = "")
  
    public Float getCotation() {
    return cotation;
  }

  public void setCotation(Float cotation) {
    this.cotation = cotation;
  }

  public OpenAnswerRubricStandards description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   **/
  @Schema(description = "")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OpenAnswerRubricStandards title(String title) {
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
    OpenAnswerRubricStandards openAnswerRubricStandards = (OpenAnswerRubricStandards) o;
    return Objects.equals(this.cotation, openAnswerRubricStandards.cotation) &&
        Objects.equals(this.description, openAnswerRubricStandards.description) &&
        Objects.equals(this.title, openAnswerRubricStandards.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cotation, description, title);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpenAnswerRubricStandards {\n");
    
    sb.append("    cotation: ").append(toIndentedString(cotation)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
