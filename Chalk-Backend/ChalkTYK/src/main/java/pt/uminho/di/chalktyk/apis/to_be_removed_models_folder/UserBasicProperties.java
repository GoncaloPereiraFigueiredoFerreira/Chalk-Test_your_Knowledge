package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

/**
 * UserBasicProperties
 */
@Validated


public class UserBasicProperties   {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("photoPath")
  private String photoPath = null;

  public UserBasicProperties description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   **/
  @Schema(example = "descricao", description = "")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UserBasicProperties email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(example = "luisinho2001@gmail.com", description = "")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserBasicProperties id(String id) {
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

  public UserBasicProperties name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(example = "Luis Silva", description = "")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserBasicProperties photoPath(String photoPath) {
    this.photoPath = photoPath;
    return this;
  }

  /**
   * Get photoPath
   * @return photoPath
   **/
  @Schema(example = "https://upload.wikimedia.org/wikipedia/commons/c/c7/Osama_bin_Laden%2C_portr%C3%A6t.jpg", description = "")
  
    public String getPhotoPath() {
    return photoPath;
  }

  public void setPhotoPath(String photoPath) {
    this.photoPath = photoPath;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserBasicProperties userBasicProperties = (UserBasicProperties) o;
    return Objects.equals(this.description, userBasicProperties.description) &&
        Objects.equals(this.email, userBasicProperties.email) &&
        Objects.equals(this.id, userBasicProperties.id) &&
        Objects.equals(this.name, userBasicProperties.name) &&
        Objects.equals(this.photoPath, userBasicProperties.photoPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, email, id, name, photoPath);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserBasicProperties {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    photoPath: ").append(toIndentedString(photoPath)).append("\n");
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
