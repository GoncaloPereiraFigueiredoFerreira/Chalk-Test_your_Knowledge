package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * ExerciseBasicPropertiesStatement
 */
@Validated


public class ExerciseBasicPropertiesStatement   {
  @JsonProperty("imagePath")
  private String imagePath = null;

  @JsonProperty("text")
  private String text = null;

  public ExerciseBasicPropertiesStatement imagePath(String imagePath) {
    this.imagePath = imagePath;
    return this;
  }

  /**
   * Get imagePath
   * @return imagePath
   **/
  @Schema(example = "http://somewhere.com/canetasVerdes.jpg", required = true, description = "")
      @NotNull

    public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public ExerciseBasicPropertiesStatement text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExerciseBasicPropertiesStatement exerciseBasicPropertiesStatement = (ExerciseBasicPropertiesStatement) o;
    return Objects.equals(this.imagePath, exerciseBasicPropertiesStatement.imagePath) &&
        Objects.equals(this.text, exerciseBasicPropertiesStatement.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(imagePath, text);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExerciseBasicPropertiesStatement {\n");
    
    sb.append("    imagePath: ").append(toIndentedString(imagePath)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
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
