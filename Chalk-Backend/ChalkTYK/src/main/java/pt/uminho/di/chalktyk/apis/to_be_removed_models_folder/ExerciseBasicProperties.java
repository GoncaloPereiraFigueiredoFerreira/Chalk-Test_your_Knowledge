package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Basic properties of an exercise.
 */
@Schema(description = "Basic properties of an exercise.")
@Validated


public class ExerciseBasicProperties extends ExerciseMetadata  {
  @JsonProperty("statement")
  private ExerciseBasicPropertiesStatement statement = null;

  @JsonProperty("title")
  private String title = null;

  public ExerciseBasicProperties statement(ExerciseBasicPropertiesStatement statement) {
    this.statement = statement;
    return this;
  }

  /**
   * Get statement
   * @return statement
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ExerciseBasicPropertiesStatement getStatement() {
    return statement;
  }

  public void setStatement(ExerciseBasicPropertiesStatement statement) {
    this.statement = statement;
  }

  public ExerciseBasicProperties title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExerciseBasicProperties exerciseBasicProperties = (ExerciseBasicProperties) o;
    return Objects.equals(this.statement, exerciseBasicProperties.statement) &&
        Objects.equals(this.title, exerciseBasicProperties.title) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statement, title, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExerciseBasicProperties {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    statement: ").append(toIndentedString(statement)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
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
