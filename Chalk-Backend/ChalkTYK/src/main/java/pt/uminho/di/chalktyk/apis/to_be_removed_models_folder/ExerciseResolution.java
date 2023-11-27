package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * ExerciseResolution
 */
@Validated


public class ExerciseResolution extends ExerciseSolution  {
  @JsonProperty("comment")
  private Comment comment = null;

  @JsonProperty("cotation")
  private Float cotation = null;

  @JsonProperty("data")
  private ExerciseResolutionData exerciseResolutionData = null;

  @JsonProperty("exerciseId")
  private String exerciseId = null;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    NOT_REVISED("not_revised"),
    
    REVISED("revised");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("status")
  private StatusEnum status = null;

  @JsonProperty("studentId")
  private String studentId = null;

  @JsonProperty("submissionNr")
  private Integer submissionNr = null;

  public ExerciseResolution comment(Comment comment) {
    this.comment = comment;
    return this;
  }

  /**
   * Get comment
   * @return comment
   **/
  @Schema(description = "")
  
    @Valid
    public Comment getComment() {
    return comment;
  }

  public void setComment(Comment comment) {
    this.comment = comment;
  }

  public ExerciseResolution cotation(Float cotation) {
    this.cotation = cotation;
    return this;
  }

  /**
   * Get cotation
   * @return cotation
   **/
  @Schema(description = "")
  
    public Float getCotation() {
    return cotation;
  }

  public void setCotation(Float cotation) {
    this.cotation = cotation;
  }

  public ExerciseResolution exerciseResolutionData(ExerciseResolutionData exerciseResolutionData) {
    this.exerciseResolutionData = exerciseResolutionData;
    return this;
  }

  /**
   * Get exerciseResolutionData
   * @return exerciseResolutionData
   **/
  @Schema(description = "")
  
    @Valid
    public ExerciseResolutionData getExerciseResolutionData() {
    return exerciseResolutionData;
  }

  public void setExerciseResolutionData(ExerciseResolutionData exerciseResolutionData) {
    this.exerciseResolutionData = exerciseResolutionData;
  }

  public ExerciseResolution exerciseId(String exerciseId) {
    this.exerciseId = exerciseId;
    return this;
  }

  /**
   * Get exerciseId
   * @return exerciseId
   **/
  @Schema(description = "")
  
  @Pattern(regexp="/^[a-f\\d]{24}$/i") @Size(min=24,max=24)   public String getExerciseId() {
    return exerciseId;
  }

  public void setExerciseId(String exerciseId) {
    this.exerciseId = exerciseId;
  }

  public ExerciseResolution status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   **/
  @Schema(description = "")
  
    public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public ExerciseResolution studentId(String studentId) {
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

  public ExerciseResolution submissionNr(Integer submissionNr) {
    this.submissionNr = submissionNr;
    return this;
  }

  /**
   * Get submissionNr
   * @return submissionNr
   **/
  @Schema(description = "")
  
    public Integer getSubmissionNr() {
    return submissionNr;
  }

  public void setSubmissionNr(Integer submissionNr) {
    this.submissionNr = submissionNr;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExerciseResolution exerciseResolution = (ExerciseResolution) o;
    return Objects.equals(this.comment, exerciseResolution.comment) &&
        Objects.equals(this.cotation, exerciseResolution.cotation) &&
        Objects.equals(this.exerciseResolutionData, exerciseResolution.exerciseResolutionData) &&
        Objects.equals(this.exerciseId, exerciseResolution.exerciseId) &&
        Objects.equals(this.status, exerciseResolution.status) &&
        Objects.equals(this.studentId, exerciseResolution.studentId) &&
        Objects.equals(this.submissionNr, exerciseResolution.submissionNr) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comment, cotation, exerciseResolutionData, exerciseId, status, studentId, submissionNr, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExerciseResolution {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    cotation: ").append(toIndentedString(cotation)).append("\n");
    sb.append("    exerciseResolutionData: ").append(toIndentedString(exerciseResolutionData)).append("\n");
    sb.append("    exerciseId: ").append(toIndentedString(exerciseId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    studentId: ").append(toIndentedString(studentId)).append("\n");
    sb.append("    submissionNr: ").append(toIndentedString(submissionNr)).append("\n");
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
