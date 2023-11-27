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
 * Test group&#x27;s schema.
 */
@Schema(description = "Test group's schema.")
@Validated


public class TestGroup   {
  @JsonProperty("exercises")
  @Valid
  private List<OneOfTestGroupExercisesItems> exercises = new ArrayList<OneOfTestGroupExercisesItems>();

  @JsonProperty("groupCotation")
  private Float groupCotation = null;

  @JsonProperty("groupInstructions")
  private String groupInstructions = null;

  public TestGroup exercises(List<OneOfTestGroupExercisesItems> exercises) {
    this.exercises = exercises;
    return this;
  }

  public TestGroup addExercisesItem(OneOfTestGroupExercisesItems exercisesItem) {
    this.exercises.add(exercisesItem);
    return this;
  }

  /**
   * Get exercises
   * @return exercises
   **/
  @Schema(required = true, description = "")
      @NotNull

    public List<OneOfTestGroupExercisesItems> getExercises() {
    return exercises;
  }

  public void setExercises(List<OneOfTestGroupExercisesItems> exercises) {
    this.exercises = exercises;
  }

  public TestGroup groupCotation(Float groupCotation) {
    this.groupCotation = groupCotation;
    return this;
  }

  /**
   * Get groupCotation
   * @return groupCotation
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Float getGroupCotation() {
    return groupCotation;
  }

  public void setGroupCotation(Float groupCotation) {
    this.groupCotation = groupCotation;
  }

  public TestGroup groupInstructions(String groupInstructions) {
    this.groupInstructions = groupInstructions;
    return this;
  }

  /**
   * Get groupInstructions
   * @return groupInstructions
   **/
  @Schema(description = "")
  
    public String getGroupInstructions() {
    return groupInstructions;
  }

  public void setGroupInstructions(String groupInstructions) {
    this.groupInstructions = groupInstructions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestGroup testGroup = (TestGroup) o;
    return Objects.equals(this.exercises, testGroup.exercises) &&
        Objects.equals(this.groupCotation, testGroup.groupCotation) &&
        Objects.equals(this.groupInstructions, testGroup.groupInstructions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(exercises, groupCotation, groupInstructions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TestGroup {\n");
    
    sb.append("    exercises: ").append(toIndentedString(exercises)).append("\n");
    sb.append("    groupCotation: ").append(toIndentedString(groupCotation)).append("\n");
    sb.append("    groupInstructions: ").append(toIndentedString(groupInstructions)).append("\n");
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
