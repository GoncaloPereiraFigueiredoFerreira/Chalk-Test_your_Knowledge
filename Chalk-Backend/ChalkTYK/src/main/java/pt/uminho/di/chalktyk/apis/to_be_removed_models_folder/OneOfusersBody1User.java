package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfusersBody1User
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = StudentWithoutId.class, name = "StudentWithoutId"),
  @JsonSubTypes.Type(value = SpecialistWithoutId.class, name = "SpecialistWithoutId"),
  @JsonSubTypes.Type(value = InstitutionManagerWithoutId.class, name = "InstitutionManagerWithoutId")
})
public interface OneOfusersBody1User {

}
