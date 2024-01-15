package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Subscription&#x27;s schema.
 */
@Schema(description = "Subscription's schema.")
@Validated


public class Subscription   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("nextPayment")
  private String nextPayment = null;

  @JsonProperty("payment")
  private Payment payment = null;

  @JsonProperty("planId")
  private String planId = null;

  public Subscription id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(description = "")
  
  @Pattern(regexp="/^[a-f\\d]{24}$/i") @Size(min=24,max=24)   public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Subscription nextPayment(String nextPayment) {
    this.nextPayment = nextPayment;
    return this;
  }

  /**
   * Get nextPayment
   * @return nextPayment
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getNextPayment() {
    return nextPayment;
  }

  public void setNextPayment(String nextPayment) {
    this.nextPayment = nextPayment;
  }

  public Subscription payment(Payment payment) {
    this.payment = payment;
    return this;
  }

  /**
   * Get payment
   * @return payment
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public Subscription planId(String planId) {
    this.planId = planId;
    return this;
  }

  /**
   * Get planId
   * @return planId
   **/
  @Schema(required = true, description = "")
      @NotNull

  @Pattern(regexp="/^[a-f\\d]{24}$/i") @Size(min=24,max=24)   public String getPlanId() {
    return planId;
  }

  public void setPlanId(String planId) {
    this.planId = planId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Subscription subscription = (Subscription) o;
    return Objects.equals(this.id, subscription.id) &&
        Objects.equals(this.nextPayment, subscription.nextPayment) &&
        Objects.equals(this.payment, subscription.payment) &&
        Objects.equals(this.planId, subscription.planId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nextPayment, payment, planId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Subscription {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nextPayment: ").append(toIndentedString(nextPayment)).append("\n");
    sb.append("    payment: ").append(toIndentedString(payment)).append("\n");
    sb.append("    planId: ").append(toIndentedString(planId)).append("\n");
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
