package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * Fill the blanks resolution data.
 */
@Schema(description = "Fill the blanks resolution data.")
@Validated


public class FillTheBlanksData extends ExerciseSolution implements ExerciseResolutionData {
  @JsonProperty("text")
  private String text = null;

  @JsonProperty("type")
  private String type = null;

  public FillTheBlanksData text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
   **/
  @Schema(description = "")
  
    public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public FillTheBlanksData type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(description = "")
  
    public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FillTheBlanksData fillTheBlanksData = (FillTheBlanksData) o;
    return Objects.equals(this.text, fillTheBlanksData.text) &&
        Objects.equals(this.type, fillTheBlanksData.type) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, type, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FillTheBlanksData {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
