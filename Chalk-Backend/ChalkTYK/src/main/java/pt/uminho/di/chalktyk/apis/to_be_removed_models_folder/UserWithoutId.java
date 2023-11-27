package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Basic properties of an user.
 */
@Schema(description = "Basic properties of an user.")
@Validated


public class UserWithoutId   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("photoPath")
  private String photoPath = null;

  public UserWithoutId name(String name) {
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

  public UserWithoutId photoPath(String photoPath) {
    this.photoPath = photoPath;
    return this;
  }

  /**
   * The structure of accepted strings
   * @return photoPath
   **/
  @Schema(description = "The structure of accepted strings")
  
  @Pattern(regexp="^[A-Za-z0-9]{3,10}$") @Size(min=1,max=500)   public String getPhotoPath() {
    return photoPath;
  }

  public void setPhotoPath(String photoPath) {
    this.photoPath = photoPath;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserWithoutId userWithoutId = (UserWithoutId) o;
    return Objects.equals(this.name, userWithoutId.name) &&
        Objects.equals(this.photoPath, userWithoutId.photoPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, photoPath);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserWithoutId {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    photoPath: ").append(toIndentedString(photoPath)).append("\n");
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
