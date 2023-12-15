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
 * Test resolution group&#x27;s schema.
 */
@Schema(description = "Test resolution group's schema.")
@Validated


public class TestResolutionGroup   {
  @JsonProperty("groupPoints")
  private Float groupPoints = null;

  @JsonProperty("resolutions")
  @Valid
  private List<ExerciseResolution> resolutions = null;

  public TestResolutionGroup groupPoints(Float groupPoints) {
    this.groupPoints = groupPoints;
    return this;
  }

  /**
   * Get groupPoints
   * @return groupPoints
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Float getGroupPoints() {
    return groupPoints;
  }

  public void setGroupPoints(Float groupPoints) {
    this.groupPoints = groupPoints;
  }

  public TestResolutionGroup resolutions(List<ExerciseResolution> resolutions) {
    this.resolutions = resolutions;
    return this;
  }

  public TestResolutionGroup addResolutionsItem(ExerciseResolution resolutionsItem) {
    if (this.resolutions == null) {
      this.resolutions = new ArrayList<ExerciseResolution>();
    }
    this.resolutions.add(resolutionsItem);
    return this;
  }

  /**
   * Get resolutions
   * @return resolutions
   **/
  @Schema(description = "")
      @Valid
    public List<ExerciseResolution> getResolutions() {
    return resolutions;
  }

  public void setResolutions(List<ExerciseResolution> resolutions) {
    this.resolutions = resolutions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestResolutionGroup testResolutionGroup = (TestResolutionGroup) o;
    return Objects.equals(this.groupPoints, testResolutionGroup.groupPoints) &&
        Objects.equals(this.resolutions, testResolutionGroup.resolutions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupPoints, resolutions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TestResolutionGroup {\n");
    
    sb.append("    groupPoints: ").append(toIndentedString(groupPoints)).append("\n");
    sb.append("    resolutions: ").append(toIndentedString(resolutions)).append("\n");
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
