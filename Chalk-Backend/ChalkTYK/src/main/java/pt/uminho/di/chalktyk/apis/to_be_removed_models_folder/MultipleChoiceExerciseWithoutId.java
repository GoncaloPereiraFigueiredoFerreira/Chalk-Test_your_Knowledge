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
 * Multiple Choice or True/False exercise schema. The exercise id is not required.
 */
@Schema(description = "Multiple Choice or True/False exercise schema. The exercise id is not required.")
@Validated


public class MultipleChoiceExerciseWithoutId extends ExerciseBasicProperties implements ExerciseWithoutId {
  @JsonProperty("items")
  @Valid
  private List<MultipleChoiceExerciseWithoutIdItems> items = new ArrayList<MultipleChoiceExerciseWithoutIdItems>();

  @JsonProperty("mctype")
  private Integer mctype = null;

  @JsonProperty("type")
  private String type = null;

  public MultipleChoiceExerciseWithoutId items(List<MultipleChoiceExerciseWithoutIdItems> items) {
    this.items = items;
    return this;
  }

  public MultipleChoiceExerciseWithoutId addItemsItem(MultipleChoiceExerciseWithoutIdItems itemsItem) {
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Get items
   * @return items
   **/
  @Schema(required = true, description = "")
      @NotNull
    @Valid
    public List<MultipleChoiceExerciseWithoutIdItems> getItems() {
    return items;
  }

  public void setItems(List<MultipleChoiceExerciseWithoutIdItems> items) {
    this.items = items;
  }

  public MultipleChoiceExerciseWithoutId mctype(Integer mctype) {
    this.mctype = mctype;
    return this;
  }

  /**
   * Defines the type of multiple choice exercise. Should follow the format \"XX\". Options:   1X -> multiple choice /   2X -> true or false /    X0 means 'no justification' /    X1 means 'justify all items' /    X2 means 'justify false/unmarked items' /    X3 means 'justify true/marked items'\" 
   * @return mctype
   **/
  @Schema(required = true, description = "Defines the type of multiple choice exercise. Should follow the format \"XX\". Options:   1X -> multiple choice /   2X -> true or false /    X0 means 'no justification' /    X1 means 'justify all items' /    X2 means 'justify false/unmarked items' /    X3 means 'justify true/marked items'\" ")
      @NotNull

    public Integer getMctype() {
    return mctype;
  }

  public void setMctype(Integer mctype) {
    this.mctype = mctype;
  }

  public MultipleChoiceExerciseWithoutId type(String type) {
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
    MultipleChoiceExerciseWithoutId multipleChoiceExerciseWithoutId = (MultipleChoiceExerciseWithoutId) o;
    return Objects.equals(this.items, multipleChoiceExerciseWithoutId.items) &&
        Objects.equals(this.mctype, multipleChoiceExerciseWithoutId.mctype) &&
        Objects.equals(this.type, multipleChoiceExerciseWithoutId.type) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, mctype, type, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MultipleChoiceExerciseWithoutId {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("    mctype: ").append(toIndentedString(mctype)).append("\n");
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
