package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * SubscriptionsStudentsBody
 */
@Validated


public class SubscriptionsStudentsBody   {
  @JsonProperty("SubscriptionWithPlan")
  private Subscription subscriptionWithPlan = null;

  @JsonProperty("studentId")
  private String studentId = null;

  public SubscriptionsStudentsBody subscriptionWithPlan(Subscription subscriptionWithPlan) {
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

  public SubscriptionsStudentsBody studentId(String studentId) {
    this.studentId = studentId;
    return this;
  }

  /**
   * Get studentId
   * @return studentId
   **/
  @Schema(description = "")
  
  @Pattern(regexp="/^[a-f\\d]{24}$/i") @Size(min=24,max=24)   public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubscriptionsStudentsBody subscriptionsStudentsBody = (SubscriptionsStudentsBody) o;
    return Objects.equals(this.subscriptionWithPlan, subscriptionsStudentsBody.subscriptionWithPlan) &&
        Objects.equals(this.studentId, subscriptionsStudentsBody.studentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionWithPlan, studentId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionsStudentsBody {\n");
    
    sb.append("    subscriptionWithPlan: ").append(toIndentedString(subscriptionWithPlan)).append("\n");
    sb.append("    studentId: ").append(toIndentedString(studentId)).append("\n");
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
