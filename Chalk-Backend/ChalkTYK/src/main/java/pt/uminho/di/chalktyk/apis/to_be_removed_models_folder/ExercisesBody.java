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
 * If a rubric and a solution are provided, their type must match the type of the exercise.
 */
@Schema(description = "If a rubric and a solution are provided, their type must match the type of the exercise.")
@Validated


public class ExercisesBody   {
  @JsonProperty("exercise")
  private ExerciseWithoutId exercise = null;

  @JsonProperty("rubric")
  private Rubric rubric = null;

  @JsonProperty("solution")
  private ExerciseSolution solution = null;

  @JsonProperty("tagsIds")
  @Valid
  private List<String> tagsIds = new ArrayList<String>();

  @JsonProperty("visibility")
  private Visibility visibility = null;

  public ExercisesBody exercise(ExerciseWithoutId exercise) {
    this.exercise = exercise;
    return this;
  }

  /**
   * Get exercise
   * @return exercise
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ExerciseWithoutId getExercise() {
    return exercise;
  }

  public void setExercise(ExerciseWithoutId exercise) {
    this.exercise = exercise;
  }

  public ExercisesBody rubric(Rubric rubric) {
    this.rubric = rubric;
    return this;
  }

  /**
   * Get rubric
   * @return rubric
   **/
  @Schema(description = "")
  
    @Valid
    public Rubric getRubric() {
    return rubric;
  }

  public void setRubric(Rubric rubric) {
    this.rubric = rubric;
  }

  public ExercisesBody solution(ExerciseSolution solution) {
    this.solution = solution;
    return this;
  }

  /**
   * Get solution
   * @return solution
   **/
  @Schema(description = "")
  
    @Valid
    public ExerciseSolution getSolution() {
    return solution;
  }

  public void setSolution(ExerciseSolution solution) {
    this.solution = solution;
  }

  public ExercisesBody tagsIds(List<String> tagsIds) {
    this.tagsIds = tagsIds;
    return this;
  }

  public ExercisesBody addTagsIdsItem(String tagsIdsItem) {
    this.tagsIds.add(tagsIdsItem);
    return this;
  }

  /**
   * Get tagsIds
   * @return tagsIds
   **/
  @Schema(required = true, description = "")
      @NotNull

    public List<String> getTagsIds() {
    return tagsIds;
  }

  public void setTagsIds(List<String> tagsIds) {
    this.tagsIds = tagsIds;
  }

  public ExercisesBody visibility(Visibility visibility) {
    this.visibility = visibility;
    return this;
  }

  /**
   * Get visibility
   * @return visibility
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public Visibility getVisibility() {
    return visibility;
  }

  public void setVisibility(Visibility visibility) {
    this.visibility = visibility;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExercisesBody exercisesBody = (ExercisesBody) o;
    return Objects.equals(this.exercise, exercisesBody.exercise) &&
        Objects.equals(this.rubric, exercisesBody.rubric) &&
        Objects.equals(this.solution, exercisesBody.solution) &&
        Objects.equals(this.tagsIds, exercisesBody.tagsIds) &&
        Objects.equals(this.visibility, exercisesBody.visibility);
  }

  @Override
  public int hashCode() {
    return Objects.hash(exercise, rubric, solution, tagsIds, visibility);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExercisesBody {\n");
    
    sb.append("    exercise: ").append(toIndentedString(exercise)).append("\n");
    sb.append("    rubric: ").append(toIndentedString(rubric)).append("\n");
    sb.append("    solution: ").append(toIndentedString(solution)).append("\n");
    sb.append("    tagsIds: ").append(toIndentedString(tagsIds)).append("\n");
    sb.append("    visibility: ").append(toIndentedString(visibility)).append("\n");
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
