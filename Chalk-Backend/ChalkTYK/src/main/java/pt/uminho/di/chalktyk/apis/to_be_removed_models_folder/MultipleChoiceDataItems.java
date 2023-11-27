package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * MultipleChoiceDataItems
 */
@Validated


public class MultipleChoiceDataItems   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("value")
  private Boolean value = null;

  public MultipleChoiceDataItems id(Integer id) {
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

  public MultipleChoiceDataItems value(Boolean value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
   **/
  @Schema(description = "")
  
    public Boolean isValue() {
    return value;
  }

  public void setValue(Boolean value) {
    this.value = value;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultipleChoiceDataItems multipleChoiceDataItems = (MultipleChoiceDataItems) o;
    return Objects.equals(this.id, multipleChoiceDataItems.id) &&
        Objects.equals(this.value, multipleChoiceDataItems.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MultipleChoiceDataItems {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
