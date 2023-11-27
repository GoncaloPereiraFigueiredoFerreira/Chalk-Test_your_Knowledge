package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Course&#x27;s Schema. Course Id is not required
 */
@Schema(description = "Course's Schema. Course Id is not required")
@Validated


public class CourseWithoutId   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("institutionId")
  private String institutionId = null;

  @JsonProperty("name")
  private String name = null;

  public CourseWithoutId description(String description) {
    this.description = description;
    return this;
  }

  /**
   * The structure of accepted strings
   * @return description
   **/
  @Schema(required = true, description = "The structure of accepted strings")
      @NotNull

  @Pattern(regexp="^[A-Za-z0-9]{3,10}$") @Size(min=1,max=500)   public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CourseWithoutId institutionId(String institutionId) {
    this.institutionId = institutionId;
    return this;
  }

  /**
   * The structure of accepted strings
   * @return institutionId
   **/
  @Schema(required = true, description = "The structure of accepted strings")
      @NotNull

  @Pattern(regexp="^[A-Za-z0-9]{3,10}$") @Size(min=1,max=500)   public String getInstitutionId() {
    return institutionId;
  }

  public void setInstitutionId(String institutionId) {
    this.institutionId = institutionId;
  }

  public CourseWithoutId name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The structure of accepted strings
   * @return name
   **/
  @Schema(required = true, description = "The structure of accepted strings")
      @NotNull

  @Pattern(regexp="^[A-Za-z0-9]{3,10}$") @Size(min=1,max=500)   public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CourseWithoutId courseWithoutId = (CourseWithoutId) o;
    return Objects.equals(this.description, courseWithoutId.description) &&
        Objects.equals(this.institutionId, courseWithoutId.institutionId) &&
        Objects.equals(this.name, courseWithoutId.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, institutionId, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CourseWithoutId {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    institutionId: ").append(toIndentedString(institutionId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
