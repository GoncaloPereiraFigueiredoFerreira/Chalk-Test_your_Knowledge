package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Subscription Plan&#x27;s schema.
 */
@Schema(description = "Subscription Plan's schema.")
@Validated


public class SubscriptionPlan   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("perks")
  @Valid
  private List<Object> perks = new ArrayList<Object>();

  @JsonProperty("priceMonth")
  private Float priceMonth = null;

  @JsonProperty("targetAudience")
  @Valid
  private List<Object> targetAudience = new ArrayList<Object>();

  public SubscriptionPlan id(String id) {
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

  public SubscriptionPlan name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SubscriptionPlan perks(List<Object> perks) {
    this.perks = perks;
    return this;
  }

  public SubscriptionPlan addPerksItem(Object perksItem) {
    this.perks.add(perksItem);
    return this;
  }

  /**
   * Get perks
   * @return perks
   **/
  @Schema(required = true, description = "")
      @NotNull

    public List<Object> getPerks() {
    return perks;
  }

  public void setPerks(List<Object> perks) {
    this.perks = perks;
  }

  public SubscriptionPlan priceMonth(Float priceMonth) {
    this.priceMonth = priceMonth;
    return this;
  }

  /**
   * Get priceMonth
   * @return priceMonth
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Float getPriceMonth() {
    return priceMonth;
  }

  public void setPriceMonth(Float priceMonth) {
    this.priceMonth = priceMonth;
  }

  public SubscriptionPlan targetAudience(List<Object> targetAudience) {
    this.targetAudience = targetAudience;
    return this;
  }

  public SubscriptionPlan addTargetAudienceItem(Object targetAudienceItem) {
    this.targetAudience.add(targetAudienceItem);
    return this;
  }

  /**
   * Get targetAudience
   * @return targetAudience
   **/
  @Schema(required = true, description = "")
      @NotNull

    public List<Object> getTargetAudience() {
    return targetAudience;
  }

  public void setTargetAudience(List<Object> targetAudience) {
    this.targetAudience = targetAudience;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubscriptionPlan subscriptionPlan = (SubscriptionPlan) o;
    return Objects.equals(this.id, subscriptionPlan.id) &&
        Objects.equals(this.name, subscriptionPlan.name) &&
        Objects.equals(this.perks, subscriptionPlan.perks) &&
        Objects.equals(this.priceMonth, subscriptionPlan.priceMonth) &&
        Objects.equals(this.targetAudience, subscriptionPlan.targetAudience);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, perks, priceMonth, targetAudience);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionPlan {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    perks: ").append(toIndentedString(perks)).append("\n");
    sb.append("    priceMonth: ").append(toIndentedString(priceMonth)).append("\n");
    sb.append("    targetAudience: ").append(toIndentedString(targetAudience)).append("\n");
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
