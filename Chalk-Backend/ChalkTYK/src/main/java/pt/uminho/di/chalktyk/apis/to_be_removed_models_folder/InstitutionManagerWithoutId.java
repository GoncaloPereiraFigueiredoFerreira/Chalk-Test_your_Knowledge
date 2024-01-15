package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Institution Manager&#x27;s Schema. The user id is not required.
 */
@Schema(description = "Institution Manager's Schema. The user id is not required.")
@Validated


public class InstitutionManagerWithoutId  implements OneOfusersBodyUser, OneOfusersBody1User {
  @JsonProperty("UserWithoutId")
  private UserWithoutId userWithoutId = null;

  @JsonProperty("institutionId")
  private String institutionId = null;

  public InstitutionManagerWithoutId userWithoutId(UserWithoutId userWithoutId) {
    this.userWithoutId = userWithoutId;
    return this;
  }

  /**
   * Get userWithoutId
   * @return userWithoutId
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public UserWithoutId getUserWithoutId() {
    return userWithoutId;
  }

  public void setUserWithoutId(UserWithoutId userWithoutId) {
    this.userWithoutId = userWithoutId;
  }

  public InstitutionManagerWithoutId institutionId(String institutionId) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InstitutionManagerWithoutId institutionManagerWithoutId = (InstitutionManagerWithoutId) o;
    return Objects.equals(this.userWithoutId, institutionManagerWithoutId.userWithoutId) &&
        Objects.equals(this.institutionId, institutionManagerWithoutId.institutionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userWithoutId, institutionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstitutionManagerWithoutId {\n");
    
    sb.append("    userWithoutId: ").append(toIndentedString(userWithoutId)).append("\n");
    sb.append("    institutionId: ").append(toIndentedString(institutionId)).append("\n");
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
