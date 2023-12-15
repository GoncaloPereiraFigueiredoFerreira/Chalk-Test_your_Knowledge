package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Metadata of an exercise.
 */
@Schema(description = "Metadata of an exercise.")
@Validated


public class ExerciseMetadata   {
  @JsonProperty("points")
  private Float points = null;

  @JsonProperty("courseId")
  private String courseId = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("institutionId")
  private String institutionId = null;

  @JsonProperty("specialistId")
  private String specialistId = null;

  @JsonProperty("type")
  private String type = null;

  public ExerciseMetadata points(Float points) {
    this.points = points;
    return this;
  }

  /**
   * Get points
   * @return points
   **/
  @Schema(description = "")
  
    public Float getPoints() {
    return points;
  }

  public void setPoints(Float points) {
    this.points = points;
  }

  public ExerciseMetadata courseId(String courseId) {
    this.courseId = courseId;
    return this;
  }

  /**
   * Get courseId
   * @return courseId
   **/
  @Schema(required = true, description = "")
      @NotNull

  @Pattern(regexp="/^[a-f\\d]{24}$/i") @Size(min=24,max=24)   public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public ExerciseMetadata id(String id) {
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

  public ExerciseMetadata institutionId(String institutionId) {
    this.institutionId = institutionId;
    return this;
  }

  /**
   * Get institutionId
   * @return institutionId
   **/
  @Schema(required = true, description = "")
      @NotNull

  @Pattern(regexp="/^[a-f\\d]{24}$/i") @Size(min=24,max=24)   public String getInstitutionId() {
    return institutionId;
  }

  public void setInstitutionId(String institutionId) {
    this.institutionId = institutionId;
  }

  public ExerciseMetadata specialistId(String specialistId) {
    this.specialistId = specialistId;
    return this;
  }

  /**
   * Get specialistId
   * @return specialistId
   **/
  @Schema(required = true, description = "")
      @NotNull

  @Pattern(regexp="/^[a-f\\d]{24}$/i") @Size(min=24,max=24)   public String getSpecialistId() {
    return specialistId;
  }

  public void setSpecialistId(String specialistId) {
    this.specialistId = specialistId;
  }

  public ExerciseMetadata type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(required = true, description = "")
      @NotNull

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
    ExerciseMetadata exerciseMetadata = (ExerciseMetadata) o;
    return Objects.equals(this.points, exerciseMetadata.points) &&
        Objects.equals(this.courseId, exerciseMetadata.courseId) &&
        Objects.equals(this.id, exerciseMetadata.id) &&
        Objects.equals(this.institutionId, exerciseMetadata.institutionId) &&
        Objects.equals(this.specialistId, exerciseMetadata.specialistId) &&
        Objects.equals(this.type, exerciseMetadata.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(points, courseId, id, institutionId, specialistId, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExerciseMetadata {\n");
    
    sb.append("    points: ").append(toIndentedString(points)).append("\n");
    sb.append("    courseId: ").append(toIndentedString(courseId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    institutionId: ").append(toIndentedString(institutionId)).append("\n");
    sb.append("    specialistId: ").append(toIndentedString(specialistId)).append("\n");
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
