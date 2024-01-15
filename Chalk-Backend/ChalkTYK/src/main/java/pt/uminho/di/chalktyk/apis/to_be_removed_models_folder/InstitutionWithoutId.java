package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Basic properties of a institution.
 */
@Schema(description = "Basic properties of a institution.")
@Validated


public class InstitutionWithoutId   {
  @JsonProperty("logopath")
  private String logopath = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("photoPath")
  private String photoPath = null;

  public InstitutionWithoutId logopath(String logopath) {
    this.logopath = logopath;
    return this;
  }

  /**
   * The structure of accepted strings
   * @return logopath
   **/
  @Schema(required = true, description = "The structure of accepted strings")
      @NotNull

  @Pattern(regexp="^[A-Za-z0-9]{3,10}$") @Size(min=1,max=500)   public String getLogopath() {
    return logopath;
  }

  public void setLogopath(String logopath) {
    this.logopath = logopath;
  }

  public InstitutionWithoutId name(String name) {
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

  public InstitutionWithoutId photoPath(String photoPath) {
    this.photoPath = photoPath;
    return this;
  }

  /**
   * The structure of accepted strings
   * @return photoPath
   **/
  @Schema(required = true, description = "The structure of accepted strings")
      @NotNull

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
    InstitutionWithoutId institutionWithoutId = (InstitutionWithoutId) o;
    return Objects.equals(this.logopath, institutionWithoutId.logopath) &&
        Objects.equals(this.name, institutionWithoutId.name) &&
        Objects.equals(this.photoPath, institutionWithoutId.photoPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logopath, name, photoPath);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstitutionWithoutId {\n");
    
    sb.append("    logopath: ").append(toIndentedString(logopath)).append("\n");
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
