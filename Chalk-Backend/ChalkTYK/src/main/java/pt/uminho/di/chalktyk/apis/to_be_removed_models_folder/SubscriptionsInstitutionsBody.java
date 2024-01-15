package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * SubscriptionsInstitutionsBody
 */
@Validated


public class SubscriptionsInstitutionsBody   {
  @JsonProperty("institutionId")
  private String institutionId = null;

  @JsonProperty("subscription")
  private Subscription subscription = null;

  public SubscriptionsInstitutionsBody institutionId(String institutionId) {
    this.institutionId = institutionId;
    return this;
  }

  /**
   * Get institutionId
   * @return institutionId
   **/
  @Schema(description = "")
  
  @Pattern(regexp="/^[a-f\\d]{24}$/i") @Size(min=24,max=24)   public String getInstitutionId() {
    return institutionId;
  }

  public void setInstitutionId(String institutionId) {
    this.institutionId = institutionId;
  }

  public SubscriptionsInstitutionsBody subscription(Subscription subscription) {
    this.subscription = subscription;
    return this;
  }

  /**
   * Get subscription
   * @return subscription
   **/
  @Schema(description = "")
  
    @Valid
    public Subscription getSubscription() {
    return subscription;
  }

  public void setSubscription(Subscription subscription) {
    this.subscription = subscription;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubscriptionsInstitutionsBody subscriptionsInstitutionsBody = (SubscriptionsInstitutionsBody) o;
    return Objects.equals(this.institutionId, subscriptionsInstitutionsBody.institutionId) &&
        Objects.equals(this.subscription, subscriptionsInstitutionsBody.subscription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(institutionId, subscription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionsInstitutionsBody {\n");
    
    sb.append("    institutionId: ").append(toIndentedString(institutionId)).append("\n");
    sb.append("    subscription: ").append(toIndentedString(subscription)).append("\n");
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
