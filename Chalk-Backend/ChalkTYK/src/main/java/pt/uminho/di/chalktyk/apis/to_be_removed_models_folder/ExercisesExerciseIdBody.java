package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

/**
 * If a rubric and a solution are provided, their type must match the type of the exercise. The given properties will be updated. The hidden properties will not be modified. If a property is null, then it is considered that it should be deleted.
 */
@Schema(description = "If a rubric and a solution are provided, their type must match the type of the exercise. The given properties will be updated. The hidden properties will not be modified. If a property is null, then it is considered that it should be deleted.")
@Validated


public class ExercisesExerciseIdBody   {
  @JsonProperty("exercise")
  private ExerciseWithoutId exercise = null;

  @JsonProperty("rubric")
  private Rubric rubric = null;

  @JsonProperty("solution")
  private ExerciseSolution solution = null;

  @JsonProperty("tagsIds")
  @Valid
  private List<String> tagsIds = null;

  @JsonProperty("visibility")
  private Visibility visibility = null;

  public ExercisesExerciseIdBody exercise(ExerciseWithoutId exercise) {
    this.exercise = exercise;
    return this;
  }

  /**
   * Get exercise
   * @return exercise
   **/
  @Schema(description = "")
  
    @Valid
    public ExerciseWithoutId getExercise() {
    return exercise;
  }

  public void setExercise(ExerciseWithoutId exercise) {
    this.exercise = exercise;
  }

  public ExercisesExerciseIdBody rubric(Rubric rubric) {
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

  public ExercisesExerciseIdBody solution(ExerciseSolution solution) {
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

  public ExercisesExerciseIdBody tagsIds(List<String> tagsIds) {
    this.tagsIds = tagsIds;
    return this;
  }

  public ExercisesExerciseIdBody addTagsIdsItem(String tagsIdsItem) {
    if (this.tagsIds == null) {
      this.tagsIds = new ArrayList<String>();
    }
    this.tagsIds.add(tagsIdsItem);
    return this;
  }

  /**
   * Get tagsIds
   * @return tagsIds
   **/
  @Schema(description = "")
  
    public List<String> getTagsIds() {
    return tagsIds;
  }

  public void setTagsIds(List<String> tagsIds) {
    this.tagsIds = tagsIds;
  }

  public ExercisesExerciseIdBody visibility(Visibility visibility) {
    this.visibility = visibility;
    return this;
  }

  /**
   * Get visibility
   * @return visibility
   **/
  @Schema(description = "")
  
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
    ExercisesExerciseIdBody exercisesExerciseIdBody = (ExercisesExerciseIdBody) o;
    return Objects.equals(this.exercise, exercisesExerciseIdBody.exercise) &&
        Objects.equals(this.rubric, exercisesExerciseIdBody.rubric) &&
        Objects.equals(this.solution, exercisesExerciseIdBody.solution) &&
        Objects.equals(this.tagsIds, exercisesExerciseIdBody.tagsIds) &&
        Objects.equals(this.visibility, exercisesExerciseIdBody.visibility);
  }

  @Override
  public int hashCode() {
    return Objects.hash(exercise, rubric, solution, tagsIds, visibility);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExercisesExerciseIdBody {\n");
    
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
