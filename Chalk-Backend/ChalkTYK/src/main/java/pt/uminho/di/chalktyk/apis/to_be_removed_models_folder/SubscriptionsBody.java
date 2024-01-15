package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * SubscriptionsBody
 */
@Validated


public class SubscriptionsBody   {
  @JsonProperty("subscriptionPlan")
  private SubscriptionPlan subscriptionPlan = null;

  public SubscriptionsBody subscriptionPlan(SubscriptionPlan subscriptionPlan) {
    this.subscriptionPlan = subscriptionPlan;
    return this;
  }

  /**
   * Get subscriptionPlan
   * @return subscriptionPlan
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public SubscriptionPlan getSubscriptionPlan() {
    return subscriptionPlan;
  }

  public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
    this.subscriptionPlan = subscriptionPlan;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubscriptionsBody subscriptionsBody = (SubscriptionsBody) o;
    return Objects.equals(this.subscriptionPlan, subscriptionsBody.subscriptionPlan);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionPlan);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionsBody {\n");
    
    sb.append("    subscriptionPlan: ").append(toIndentedString(subscriptionPlan)).append("\n");
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
