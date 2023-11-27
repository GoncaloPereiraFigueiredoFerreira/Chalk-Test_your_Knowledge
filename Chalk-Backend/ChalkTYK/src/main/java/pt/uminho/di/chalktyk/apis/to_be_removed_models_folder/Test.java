package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* Schema of a test
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = BasicTest.class, name = "BasicTest"),
  @JsonSubTypes.Type(value = DeliverDateTest.class, name = "DeliverDateTest"),
  @JsonSubTypes.Type(value = LiveTest.class, name = "LiveTest")
})
public interface Test {

}
