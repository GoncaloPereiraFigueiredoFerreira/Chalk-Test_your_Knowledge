package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * MultipleChoiceExerciseWithoutIdItems
 */
@Validated


public class MultipleChoiceExerciseWithoutIdItems   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("text")
  private String text = null;

  @JsonProperty("type")
  private String type = null;

  public MultipleChoiceExerciseWithoutIdItems id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(description = "")
  
    public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public MultipleChoiceExerciseWithoutIdItems text(String text) {
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

  public MultipleChoiceExerciseWithoutIdItems type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(example = "string", description = "")
  
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
    MultipleChoiceExerciseWithoutIdItems multipleChoiceExerciseWithoutIdItems = (MultipleChoiceExerciseWithoutIdItems) o;
    return Objects.equals(this.id, multipleChoiceExerciseWithoutIdItems.id) &&
        Objects.equals(this.text, multipleChoiceExerciseWithoutIdItems.text) &&
        Objects.equals(this.type, multipleChoiceExerciseWithoutIdItems.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MultipleChoiceExerciseWithoutIdItems {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
