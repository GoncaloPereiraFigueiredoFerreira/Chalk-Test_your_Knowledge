package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * InstitutionsInstitutionIdBody1
 */
@Validated


public class InstitutionsInstitutionIdBody1   {
  @JsonProperty("SubscriptionWithPlan")
  private Subscription subscriptionWithPlan = null;

  public InstitutionsInstitutionIdBody1 subscriptionWithPlan(Subscription subscriptionWithPlan) {
    this.subscriptionWithPlan = subscriptionWithPlan;
    return this;
  }

  /**
   * Get subscriptionWithPlan
   * @return subscriptionWithPlan
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Subscription getSubscriptionWithPlan() {
    return subscriptionWithPlan;
  }

  public void setSubscriptionWithPlan(Subscription subscriptionWithPlan) {
    this.subscriptionWithPlan = subscriptionWithPlan;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InstitutionsInstitutionIdBody1 institutionsInstitutionIdBody1 = (InstitutionsInstitutionIdBody1) o;
    return Objects.equals(this.subscriptionWithPlan, institutionsInstitutionIdBody1.subscriptionWithPlan);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionWithPlan);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InstitutionsInstitutionIdBody1 {\n");
    
    sb.append("    subscriptionWithPlan: ").append(toIndentedString(subscriptionWithPlan)).append("\n");
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
