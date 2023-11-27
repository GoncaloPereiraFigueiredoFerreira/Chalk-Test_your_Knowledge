package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* Exercise resolution data schema.
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = MultipleChoiceData.class, name = "MultipleChoiceData"),
  @JsonSubTypes.Type(value = OpenAnswerData.class, name = "OpenAnswerData"),
  @JsonSubTypes.Type(value = FillTheBlanksData.class, name = "FillTheBlanksData")
})
public interface ExerciseResolutionData {

}
