package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* Exercise (with id) schema
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = MultipleChoiceExercise.class, name = "MultipleChoiceExercise"),
  @JsonSubTypes.Type(value = OpenAnswerExercise.class, name = "OpenAnswerExercise"),
  @JsonSubTypes.Type(value = FillTheBlanksExercise.class, name = "FillTheBlanksExercise")
})
public interface Exercise {

}
