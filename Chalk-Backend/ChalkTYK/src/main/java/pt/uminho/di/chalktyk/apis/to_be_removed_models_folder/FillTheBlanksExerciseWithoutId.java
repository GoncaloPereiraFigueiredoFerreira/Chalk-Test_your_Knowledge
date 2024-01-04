package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

/**
 * Fill the blanks exercise schema. The exercise id is not required.
 */
@Schema(description = "Fill the blanks exercise schema. The exercise id is not required.")
@Validated


public class FillTheBlanksExerciseWithoutId extends ExerciseBasicProperties implements ExerciseWithoutId {
  @JsonProperty("options")
  @Valid
  private List<List<String>> options = null;

  @JsonProperty("textSegments")
  @Valid
  private List<String> textSegments = null;

  @JsonProperty("type")
  private String fillTheBlanksExerciseWithoutIdType = null;

  public FillTheBlanksExerciseWithoutId options(List<List<String>> options) {
    this.options = options;
    return this;
  }

  public FillTheBlanksExerciseWithoutId addOptionsItem(List<String> optionsItem) {
    if (this.options == null) {
      this.options = new ArrayList<List<String>>();
    }
    this.options.add(optionsItem);
    return this;
  }

  /**
   * Get options
   * @return options
   **/
  @Schema(description = "")
      @Valid
    public List<List<String>> getOptions() {
    return options;
  }

  public void setOptions(List<List<String>> options) {
    this.options = options;
  }

  public FillTheBlanksExerciseWithoutId textSegments(List<String> textSegments) {
    this.textSegments = textSegments;
    return this;
  }

  public FillTheBlanksExerciseWithoutId addTextSegmentsItem(String textSegmentsItem) {
    if (this.textSegments == null) {
      this.textSegments = new ArrayList<String>();
    }
    this.textSegments.add(textSegmentsItem);
    return this;
  }

  /**
   * Get textSegments
   * @return textSegments
   **/
  @Schema(description = "")
  
    public List<String> getTextSegments() {
    return textSegments;
  }

  public void setTextSegments(List<String> textSegments) {
    this.textSegments = textSegments;
  }

  public FillTheBlanksExerciseWithoutId fillTheBlanksExerciseWithoutIdType(String fillTheBlanksExerciseWithoutIdType) {
    this.fillTheBlanksExerciseWithoutIdType = fillTheBlanksExerciseWithoutIdType;
    return this;
  }

  /**
   * Get fillTheBlanksExerciseWithoutIdType
   * @return fillTheBlanksExerciseWithoutIdType
   **/
  @Schema(description = "")
  
    public String getFillTheBlanksExerciseWithoutIdType() {
    return fillTheBlanksExerciseWithoutIdType;
  }

  public void setFillTheBlanksExerciseWithoutIdType(String fillTheBlanksExerciseWithoutIdType) {
    this.fillTheBlanksExerciseWithoutIdType = fillTheBlanksExerciseWithoutIdType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FillTheBlanksExerciseWithoutId fillTheBlanksExerciseWithoutId = (FillTheBlanksExerciseWithoutId) o;
    return Objects.equals(this.options, fillTheBlanksExerciseWithoutId.options) &&
        Objects.equals(this.textSegments, fillTheBlanksExerciseWithoutId.textSegments) &&
        Objects.equals(this.fillTheBlanksExerciseWithoutIdType, fillTheBlanksExerciseWithoutId.fillTheBlanksExerciseWithoutIdType) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(options, textSegments, fillTheBlanksExerciseWithoutIdType, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FillTheBlanksExerciseWithoutId {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    options: ").append(toIndentedString(options)).append("\n");
    sb.append("    textSegments: ").append(toIndentedString(textSegments)).append("\n");
    sb.append("    fillTheBlanksExerciseWithoutIdType: ").append(toIndentedString(fillTheBlanksExerciseWithoutIdType)).append("\n");
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
