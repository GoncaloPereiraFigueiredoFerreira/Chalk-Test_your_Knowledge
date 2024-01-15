package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Specialist&#x27;s Schema. The user id is not required.
 */
@Schema(description = "Specialist's Schema. The user id is not required.")
@Validated


public class SpecialistWithoutId  implements OneOfusersBodyUser, OneOfusersBody1User {
  @JsonProperty("UserWithoutId")
  private UserWithoutId userWithoutId = null;

  public SpecialistWithoutId userWithoutId(UserWithoutId userWithoutId) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SpecialistWithoutId specialistWithoutId = (SpecialistWithoutId) o;
    return Objects.equals(this.userWithoutId, specialistWithoutId.userWithoutId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userWithoutId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SpecialistWithoutId {\n");
    
    sb.append("    userWithoutId: ").append(toIndentedString(userWithoutId)).append("\n");
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
