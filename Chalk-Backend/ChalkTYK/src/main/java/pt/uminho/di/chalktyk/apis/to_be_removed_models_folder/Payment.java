package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * Payment&#x27;s schema.
 */
@Schema(description = "Payment's schema.")
@Validated


public class Payment   {
  @JsonProperty("payMethod")
  private OneOfPaymentPayMethod payMethod = null;

  public Payment payMethod(OneOfPaymentPayMethod payMethod) {
    this.payMethod = payMethod;
    return this;
  }

  /**
   * Get payMethod
   * @return payMethod
   **/
  @Schema(required = true, description = "")
      @NotNull

    public OneOfPaymentPayMethod getPayMethod() {
    return payMethod;
  }

  public void setPayMethod(OneOfPaymentPayMethod payMethod) {
    this.payMethod = payMethod;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Payment payment = (Payment) o;
    return Objects.equals(this.payMethod, payment.payMethod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(payMethod);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Payment {\n");
    
    sb.append("    payMethod: ").append(toIndentedString(payMethod)).append("\n");
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
