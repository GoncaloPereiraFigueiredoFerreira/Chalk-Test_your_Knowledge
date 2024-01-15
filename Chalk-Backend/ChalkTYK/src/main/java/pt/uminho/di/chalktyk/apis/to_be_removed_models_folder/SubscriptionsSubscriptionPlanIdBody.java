package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

/**
 * SubscriptionsSubscriptionPlanIdBody
 */
@Validated


public class SubscriptionsSubscriptionPlanIdBody   {
  @JsonProperty("exercise")
  private SubscriptionPlan exercise = null;

  public SubscriptionsSubscriptionPlanIdBody exercise(SubscriptionPlan exercise) {
    this.exercise = exercise;
    return this;
  }

  /**
   * Get exercise
   * @return exercise
   **/
  @Schema(description = "")
  
    @Valid
    public SubscriptionPlan getExercise() {
    return exercise;
  }

  public void setExercise(SubscriptionPlan exercise) {
    this.exercise = exercise;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubscriptionsSubscriptionPlanIdBody subscriptionsSubscriptionPlanIdBody = (SubscriptionsSubscriptionPlanIdBody) o;
    return Objects.equals(this.exercise, subscriptionsSubscriptionPlanIdBody.exercise);
  }

  @Override
  public int hashCode() {
    return Objects.hash(exercise);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionsSubscriptionPlanIdBody {\n");
    
    sb.append("    exercise: ").append(toIndentedString(exercise)).append("\n");
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
