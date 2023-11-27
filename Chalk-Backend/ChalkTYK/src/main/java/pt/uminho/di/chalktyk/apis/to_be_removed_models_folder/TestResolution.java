package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Test resolution&#x27;s schema.
 */
@Schema(description = "Test resolution's schema.")
@Validated


public class TestResolution   {
  @JsonProperty("groups")
  @Valid
  private List<TestResolutionGroup> groups = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("startDate")
  private LocalDateTime startDate = null;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    ONGOING("ongoing"),
    
    NOT_REVISED("not_revised"),
    
    REVISED("revised"),
    
    REVISION_ONGOING("revision_ongoing"),
    
    INVALIDATED("invalidated");

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

  @JsonProperty("submissionDate")
  private LocalDateTime submissionDate = null;

  @JsonProperty("submissionNr")
  private Integer submissionNr = null;

  @JsonProperty("testId")
  private String testId = null;

  public TestResolution groups(List<TestResolutionGroup> groups) {
    this.groups = groups;
    return this;
  }

  public TestResolution addGroupsItem(TestResolutionGroup groupsItem) {
    if (this.groups == null) {
      this.groups = new ArrayList<TestResolutionGroup>();
    }
    this.groups.add(groupsItem);
    return this;
  }

  /**
   * Get groups
   * @return groups
   **/
  @Schema(description = "")
      @Valid
    public List<TestResolutionGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<TestResolutionGroup> groups) {
    this.groups = groups;
  }

  public TestResolution id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public TestResolution startDate(LocalDateTime startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   * @return startDate
   **/
  @Schema(description = "")
  
    @Valid
    public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public TestResolution status(StatusEnum status) {
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

  public TestResolution studentId(String studentId) {
    this.studentId = studentId;
    return this;
  }

  /**
   * Get studentId
   * @return studentId
   **/
  @Schema(description = "")
  
    public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public TestResolution submissionDate(LocalDateTime submissionDate) {
    this.submissionDate = submissionDate;
    return this;
  }

  /**
   * Get submissionDate
   * @return submissionDate
   **/
  @Schema(description = "")
  
    @Valid
    public LocalDateTime getSubmissionDate() {
    return submissionDate;
  }

  public void setSubmissionDate(LocalDateTime submissionDate) {
    this.submissionDate = submissionDate;
  }

  public TestResolution submissionNr(Integer submissionNr) {
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

  public TestResolution testId(String testId) {
    this.testId = testId;
    return this;
  }

  /**
   * Get testId
   * @return testId
   **/
  @Schema(description = "")
  
    public String getTestId() {
    return testId;
  }

  public void setTestId(String testId) {
    this.testId = testId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestResolution testResolution = (TestResolution) o;
    return Objects.equals(this.groups, testResolution.groups) &&
        Objects.equals(this.id, testResolution.id) &&
        Objects.equals(this.startDate, testResolution.startDate) &&
        Objects.equals(this.status, testResolution.status) &&
        Objects.equals(this.studentId, testResolution.studentId) &&
        Objects.equals(this.submissionDate, testResolution.submissionDate) &&
        Objects.equals(this.submissionNr, testResolution.submissionNr) &&
        Objects.equals(this.testId, testResolution.testId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groups, id, startDate, status, studentId, submissionDate, submissionNr, testId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TestResolution {\n");
    
    sb.append("    groups: ").append(toIndentedString(groups)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    studentId: ").append(toIndentedString(studentId)).append("\n");
    sb.append("    submissionDate: ").append(toIndentedString(submissionDate)).append("\n");
    sb.append("    submissionNr: ").append(toIndentedString(submissionNr)).append("\n");
    sb.append("    testId: ").append(toIndentedString(testId)).append("\n");
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
