package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * CoursesCourseIdBody
 */
@Validated


public class CoursesCourseIdBody   {
  @JsonProperty("course")
  private CourseWithoutId course = null;

  public CoursesCourseIdBody course(CourseWithoutId course) {
    this.course = course;
    return this;
  }

  /**
   * Get course
   * @return course
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public CourseWithoutId getCourse() {
    return course;
  }

  public void setCourse(CourseWithoutId course) {
    this.course = course;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CoursesCourseIdBody coursesCourseIdBody = (CoursesCourseIdBody) o;
    return Objects.equals(this.course, coursesCourseIdBody.course);
  }

  @Override
  public int hashCode() {
    return Objects.hash(course);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CoursesCourseIdBody {\n");
    
    sb.append("    course: ").append(toIndentedString(course)).append("\n");
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
