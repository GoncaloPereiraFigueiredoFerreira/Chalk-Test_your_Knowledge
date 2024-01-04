package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

/**
 * InlineResponse200
 */
@Validated


public class InlineResponse200   {
  @JsonProperty("resolution")
  private ExerciseResolution resolution = null;

  @JsonProperty("student")
  private StudentSimplified student = null;

  public InlineResponse200 resolution(ExerciseResolution resolution) {
    this.resolution = resolution;
    return this;
  }

  /**
   * Get resolution
   * @return resolution
   **/
  @Schema(description = "")
  
    @Valid
    public ExerciseResolution getResolution() {
    return resolution;
  }

  public void setResolution(ExerciseResolution resolution) {
    this.resolution = resolution;
  }

  public InlineResponse200 student(StudentSimplified student) {
    this.student = student;
    return this;
  }

  /**
   * Get student
   * @return student
   **/
  @Schema(description = "")
  
    @Valid
    public StudentSimplified getStudent() {
    return student;
  }

  public void setStudent(StudentSimplified student) {
    this.student = student;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse200 inlineResponse200 = (InlineResponse200) o;
    return Objects.equals(this.resolution, inlineResponse200.resolution) &&
        Objects.equals(this.student, inlineResponse200.student);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resolution, student);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200 {\n");
    
    sb.append("    resolution: ").append(toIndentedString(resolution)).append("\n");
    sb.append("    student: ").append(toIndentedString(student)).append("\n");
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
