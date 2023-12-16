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
  @JsonProperty("points")
  private Float points = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("title")
  private String title = null;

  public OpenAnswerRubricStandards points(Float points) {
    this.points = points;
    return this;
  }

  /**
   * Get points
   * @return points
   **/
  @Schema(description = "")
  
    public Float getPoints() {
    return points;
  }

  public void setPoints(Float points) {
    this.points = points;
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
    return Objects.equals(this.points, openAnswerRubricStandards.points) &&
        Objects.equals(this.description, openAnswerRubricStandards.description) &&
        Objects.equals(this.title, openAnswerRubricStandards.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(points, description, title);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpenAnswerRubricStandards {\n");
    
    sb.append("    points: ").append(toIndentedString(points)).append("\n");
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
