package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Exercise solution schema.
 */
@Schema(description = "Exercise solution schema.")
@Validated


public class ExerciseSolution   {
  @JsonProperty("data")
  private ExerciseResolutionData data = null;

  @JsonProperty("id")
  private String id = null;

  public ExerciseSolution data(ExerciseResolutionData data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * @return data
   **/
  @Schema(description = "")
  
    @Valid
    public ExerciseResolutionData getData() {
    return data;
  }

  public void setData(ExerciseResolutionData data) {
    this.data = data;
  }

  public ExerciseSolution id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(description = "")
  
  @Pattern(regexp="/^[a-f\\d]{24}$/i") @Size(min=24,max=24)   public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExerciseSolution exerciseSolution = (ExerciseSolution) o;
    return Objects.equals(this.data, exerciseSolution.data) &&
        Objects.equals(this.id, exerciseSolution.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExerciseSolution {\n");
    
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
