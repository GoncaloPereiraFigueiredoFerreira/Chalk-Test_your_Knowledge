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
 * Schema of a basic test.
 */
@Schema(description = "Schema of a basic test.")
@Validated


public class BasicTest  implements Test {
  @JsonProperty("conclusion")
  private String conclusion = null;

  @JsonProperty("courseId")
  private String courseId = null;

  @JsonProperty("creationDate")
  private LocalDateTime creationDate = null;

  @JsonProperty("globalCotation")
  private Float globalCotation = null;

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

  public BasicTest conclusion(String conclusion) {
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

  public BasicTest courseId(String courseId) {
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

  public BasicTest creationDate(LocalDateTime creationDate) {
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

  public BasicTest globalCotation(Float globalCotation) {
    this.globalCotation = globalCotation;
    return this;
  }

  /**
   * Get globalCotation
   * @return globalCotation
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Float getGlobalCotation() {
    return globalCotation;
  }

  public void setGlobalCotation(Float globalCotation) {
    this.globalCotation = globalCotation;
  }

  public BasicTest globalInstructions(String globalInstructions) {
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

  public BasicTest groups(List<TestGroup> groups) {
    this.groups = groups;
    return this;
  }

  public BasicTest addGroupsItem(TestGroup groupsItem) {
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

  public BasicTest id(String id) {
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

  public BasicTest institutionId(String institutionId) {
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

  public BasicTest publishDate(LocalDateTime publishDate) {
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

  public BasicTest specialistId(String specialistId) {
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

  public BasicTest title(String title) {
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

  public BasicTest type(String type) {
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
    BasicTest basicTest = (BasicTest) o;
    return Objects.equals(this.conclusion, basicTest.conclusion) &&
        Objects.equals(this.courseId, basicTest.courseId) &&
        Objects.equals(this.creationDate, basicTest.creationDate) &&
        Objects.equals(this.globalCotation, basicTest.globalCotation) &&
        Objects.equals(this.globalInstructions, basicTest.globalInstructions) &&
        Objects.equals(this.groups, basicTest.groups) &&
        Objects.equals(this.id, basicTest.id) &&
        Objects.equals(this.institutionId, basicTest.institutionId) &&
        Objects.equals(this.publishDate, basicTest.publishDate) &&
        Objects.equals(this.specialistId, basicTest.specialistId) &&
        Objects.equals(this.title, basicTest.title) &&
        Objects.equals(this.type, basicTest.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conclusion, courseId, creationDate, globalCotation, globalInstructions, groups, id, institutionId, publishDate, specialistId, title, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BasicTest {\n");
    
    sb.append("    conclusion: ").append(toIndentedString(conclusion)).append("\n");
    sb.append("    courseId: ").append(toIndentedString(courseId)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    globalCotation: ").append(toIndentedString(globalCotation)).append("\n");
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
