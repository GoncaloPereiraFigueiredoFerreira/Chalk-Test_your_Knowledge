package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Institution Manager&#x27;s Schema
 */
@Schema(description = "Institution Manager's Schema")
@Validated


public class InstitutionManager extends UserBasicProperties implements InlineResponse2002 {
  @JsonProperty("institutionId")
  private String institutionId = null;

  public InstitutionManager institutionId(String institutionId) {
    this.institutionId = institutionId;
    return this;
  }

  /**
   * Get institutionId
   * @return institutionId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getInstitutionId() {
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
    InstitutionManager institutionManager = (InstitutionManager) o;
    return Objects.equals(this.institutionId, institutionManager.institutionId) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(institutionId, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstitutionManager {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
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
