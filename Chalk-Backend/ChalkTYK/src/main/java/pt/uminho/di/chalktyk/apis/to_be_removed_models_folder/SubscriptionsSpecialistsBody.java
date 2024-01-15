package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * SubscriptionsSpecialistsBody
 */
@Validated


public class SubscriptionsSpecialistsBody   {
  @JsonProperty("SubscriptionWithPlan")
  private Subscription subscriptionWithPlan = null;

  @JsonProperty("specialistId")
  private String specialistId = null;

  public SubscriptionsSpecialistsBody subscriptionWithPlan(Subscription subscriptionWithPlan) {
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

  public SubscriptionsSpecialistsBody specialistId(String specialistId) {
    this.specialistId = specialistId;
    return this;
  }

  /**
   * Get specialistId
   * @return specialistId
   **/
  @Schema(description = "")
  
  @Pattern(regexp="/^[a-f\\d]{24}$/i") @Size(min=24,max=24)   public String getSpecialistId() {
    return specialistId;
  }

  public void setSpecialistId(String specialistId) {
    this.specialistId = specialistId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubscriptionsSpecialistsBody subscriptionsSpecialistsBody = (SubscriptionsSpecialistsBody) o;
    return Objects.equals(this.subscriptionWithPlan, subscriptionsSpecialistsBody.subscriptionWithPlan) &&
        Objects.equals(this.specialistId, subscriptionsSpecialistsBody.specialistId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionWithPlan, specialistId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionsSpecialistsBody {\n");
    
    sb.append("    subscriptionWithPlan: ").append(toIndentedString(subscriptionWithPlan)).append("\n");
    sb.append("    specialistId: ").append(toIndentedString(specialistId)).append("\n");
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
