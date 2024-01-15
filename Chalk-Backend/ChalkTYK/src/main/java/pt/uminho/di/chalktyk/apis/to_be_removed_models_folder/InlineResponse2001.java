package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

/**
 * InlineResponse2001
 */
@Validated


public class InlineResponse2001   {
  @JsonProperty("resolution")
  private TestResolution resolution = null;

  @JsonProperty("student")
  private StudentSimplified student = null;

  public InlineResponse2001 resolution(TestResolution resolution) {
    this.resolution = resolution;
    return this;
  }

  /**
   * Get resolution
   * @return resolution
   **/
  @Schema(description = "")
  
    @Valid
    public TestResolution getResolution() {
    return resolution;
  }

  public void setResolution(TestResolution resolution) {
    this.resolution = resolution;
  }

  public InlineResponse2001 student(StudentSimplified student) {
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
    InlineResponse2001 inlineResponse2001 = (InlineResponse2001) o;
    return Objects.equals(this.resolution, inlineResponse2001.resolution) &&
        Objects.equals(this.student, inlineResponse2001.student);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resolution, student);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2001 {\n");
    
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
