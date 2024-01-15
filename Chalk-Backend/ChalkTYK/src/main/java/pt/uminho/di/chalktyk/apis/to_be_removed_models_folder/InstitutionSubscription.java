package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

/**
 * Institution subscription&#x27;s schema.
 */
@Schema(description = "Institution subscription's schema.")
@Validated


public class InstitutionSubscription   {
  @JsonProperty("specialistsSub")
  private Subscription specialistsSub = null;

  @JsonProperty("studentsSub")
  private Subscription studentsSub = null;

  public InstitutionSubscription specialistsSub(Subscription specialistsSub) {
    this.specialistsSub = specialistsSub;
    return this;
  }

  /**
   * Get specialistsSub
   * @return specialistsSub
   **/
  @Schema(description = "")
  
    @Valid
    public Subscription getSpecialistsSub() {
    return specialistsSub;
  }

  public void setSpecialistsSub(Subscription specialistsSub) {
    this.specialistsSub = specialistsSub;
  }

  public InstitutionSubscription studentsSub(Subscription studentsSub) {
    this.studentsSub = studentsSub;
    return this;
  }

  /**
   * Get studentsSub
   * @return studentsSub
   **/
  @Schema(description = "")
  
    @Valid
    public Subscription getStudentsSub() {
    return studentsSub;
  }

  public void setStudentsSub(Subscription studentsSub) {
    this.studentsSub = studentsSub;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InstitutionSubscription institutionSubscription = (InstitutionSubscription) o;
    return Objects.equals(this.specialistsSub, institutionSubscription.specialistsSub) &&
        Objects.equals(this.studentsSub, institutionSubscription.studentsSub);
  }

  @Override
  public int hashCode() {
    return Objects.hash(specialistsSub, studentsSub);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstitutionSubscription {\n");
    
    sb.append("    specialistsSub: ").append(toIndentedString(specialistsSub)).append("\n");
    sb.append("    studentsSub: ").append(toIndentedString(studentsSub)).append("\n");
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
