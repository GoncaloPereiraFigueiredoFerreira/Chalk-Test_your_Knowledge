package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * InstitutionsBody
 */
@Validated


public class InstitutionsBody   {
  @JsonProperty("institution")
  private InstitutionWithoutId institution = null;

  public InstitutionsBody institution(InstitutionWithoutId institution) {
    this.institution = institution;
    return this;
  }

  /**
   * Get institution
   * @return institution
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public InstitutionWithoutId getInstitution() {
    return institution;
  }

  public void setInstitution(InstitutionWithoutId institution) {
    this.institution = institution;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InstitutionsBody institutionsBody = (InstitutionsBody) o;
    return Objects.equals(this.institution, institutionsBody.institution);
  }

  @Override
  public int hashCode() {
    return Objects.hash(institution);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstitutionsBody {\n");
    
    sb.append("    institution: ").append(toIndentedString(institution)).append("\n");
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
