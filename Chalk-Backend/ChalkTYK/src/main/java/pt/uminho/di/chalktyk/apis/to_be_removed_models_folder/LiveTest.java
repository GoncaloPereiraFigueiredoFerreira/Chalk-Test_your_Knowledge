package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * Schema of a live test
 */
@Schema(description = "Schema of a live test")
@Validated


public class LiveTest  implements Test {
  @JsonProperty("conclusion")
  private String conclusion = null;

  @JsonProperty("courseId")
  private String courseId = null;

  @JsonProperty("creationDate")
  private LocalDateTime creationDate = null;

  @JsonProperty("globalPoints")
  private Float globalPoints = null;

  @JsonProperty("globalInstructions")
  private String globalInstructions = null;

  @JsonProperty("groups")
  @Valid
  private List<TestGroup> groups = new ArrayList<TestGroup>();

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("institutionId")
  private String institutionId = null;

  @JsonProperty("publishDate")
  private LocalDateTime publishDate = null;

  @JsonProperty("specialistId")
  private String specialistId = null;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("type")
  private String type = null;

  public LiveTest conclusion(String conclusion) {
    this.conclusion = conclusion;
    return this;
  }

  /**
   * Get conclusion
   * @return conclusion
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getConclusion() {
    return conclusion;
  }

  public void setConclusion(String conclusion) {
    this.conclusion = conclusion;
  }

  public LiveTest courseId(String courseId) {
    this.courseId = courseId;
    return this;
  }

  /**
   * Get courseId
   * @return courseId
   **/
  @Schema(description = "")
  
    public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public LiveTest creationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Get creationDate
   * @return creationDate
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public LiveTest globalPoints(Float globalPoints) {
    this.globalPoints = globalPoints;
    return this;
  }

  /**
   * Get globalPoints
   * @return globalPoints
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Float getGlobalPoints() {
    return globalPoints;
  }

  public void setGlobalPoints(Float globalPoints) {
    this.globalPoints = globalPoints;
  }

  public LiveTest globalInstructions(String globalInstructions) {
    this.globalInstructions = globalInstructions;
    return this;
  }

  /**
   * Get globalInstructions
   * @return globalInstructions
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getGlobalInstructions() {
    return globalInstructions;
  }

  public void setGlobalInstructions(String globalInstructions) {
    this.globalInstructions = globalInstructions;
  }

  public LiveTest groups(List<TestGroup> groups) {
    this.groups = groups;
    return this;
  }

  public LiveTest addGroupsItem(TestGroup groupsItem) {
    this.groups.add(groupsItem);
    return this;
  }

  /**
   * Get groups
   * @return groups
   **/
  @Schema(required = true, description = "")
      @NotNull
    @Valid
    public List<TestGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<TestGroup> groups) {
    this.groups = groups;
  }

  public LiveTest id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(description = "")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LiveTest institutionId(String institutionId) {
    this.institutionId = institutionId;
    return this;
  }

  /**
   * Get institutionId
   * @return institutionId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getInstitutionId() {
    return institutionId;
  }

  public void setInstitutionId(String institutionId) {
    this.institutionId = institutionId;
  }

  public LiveTest publishDate(LocalDateTime publishDate) {
    this.publishDate = publishDate;
    return this;
  }

  /**
   * Get publishDate
   * @return publishDate
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LocalDateTime getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(LocalDateTime publishDate) {
    this.publishDate = publishDate;
  }

  public LiveTest specialistId(String specialistId) {
    this.specialistId = specialistId;
    return this;
  }

  /**
   * Get specialistId
   * @return specialistId
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getSpecialistId() {
    return specialistId;
  }

  public void setSpecialistId(String specialistId) {
    this.specialistId = specialistId;
  }

  public LiveTest title(String title) {
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

  public LiveTest type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(description = "")
  
    public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LiveTest liveTest = (LiveTest) o;
    return Objects.equals(this.conclusion, liveTest.conclusion) &&
        Objects.equals(this.courseId, liveTest.courseId) &&
        Objects.equals(this.creationDate, liveTest.creationDate) &&
        Objects.equals(this.globalPoints, liveTest.globalPoints) &&
        Objects.equals(this.globalInstructions, liveTest.globalInstructions) &&
        Objects.equals(this.groups, liveTest.groups) &&
        Objects.equals(this.id, liveTest.id) &&
        Objects.equals(this.institutionId, liveTest.institutionId) &&
        Objects.equals(this.publishDate, liveTest.publishDate) &&
        Objects.equals(this.specialistId, liveTest.specialistId) &&
        Objects.equals(this.title, liveTest.title) &&
        Objects.equals(this.type, liveTest.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conclusion, courseId, creationDate, globalPoints, globalInstructions, groups, id, institutionId, publishDate, specialistId, title, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LiveTest {\n");
    
    sb.append("    conclusion: ").append(toIndentedString(conclusion)).append("\n");
    sb.append("    courseId: ").append(toIndentedString(courseId)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    globalPoints: ").append(toIndentedString(globalPoints)).append("\n");
    sb.append("    globalInstructions: ").append(toIndentedString(globalInstructions)).append("\n");
    sb.append("    groups: ").append(toIndentedString(groups)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    institutionId: ").append(toIndentedString(institutionId)).append("\n");
    sb.append("    publishDate: ").append(toIndentedString(publishDate)).append("\n");
    sb.append("    specialistId: ").append(toIndentedString(specialistId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
