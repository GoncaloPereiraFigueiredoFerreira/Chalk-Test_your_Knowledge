package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* InlineResponse2002
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Student.class, name = "Student"),
  @JsonSubTypes.Type(value = Specialist.class, name = "Specialist"),
  @JsonSubTypes.Type(value = InstitutionManager.class, name = "InstitutionManager")
})
public interface InlineResponse2002 {

}
