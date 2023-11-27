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
 * Multiple choice resolution data.
 */
@Schema(description = "Multiple choice resolution data.")
@Validated


public class MultipleChoiceData extends ExerciseSolution implements ExerciseResolutionData {
  @JsonProperty("items")
  @Valid
  private List<MultipleChoiceDataItems> items = new ArrayList<MultipleChoiceDataItems>();

  @JsonProperty("type")
  private String type = null;

  public MultipleChoiceData items(List<MultipleChoiceDataItems> items) {
    this.items = items;
    return this;
  }

  public MultipleChoiceData addItemsItem(MultipleChoiceDataItems itemsItem) {
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
    public List<MultipleChoiceDataItems> getItems() {
    return items;
  }

  public void setItems(List<MultipleChoiceDataItems> items) {
    this.items = items;
  }

  public MultipleChoiceData type(String type) {
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
    MultipleChoiceData multipleChoiceData = (MultipleChoiceData) o;
    return Objects.equals(this.items, multipleChoiceData.items) &&
        Objects.equals(this.type, multipleChoiceData.type) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, type, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MultipleChoiceData {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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
