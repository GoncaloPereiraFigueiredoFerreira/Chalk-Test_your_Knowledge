package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* Exercise (without id) schema
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = MultipleChoiceExerciseWithoutId.class, name = "MultipleChoiceExerciseWithoutId"),
  @JsonSubTypes.Type(value = OpenAnswerExerciseWithoutId.class, name = "OpenAnswerExerciseWithoutId"),
  @JsonSubTypes.Type(value = FillTheBlanksExerciseWithoutId.class, name = "FillTheBlanksExerciseWithoutId")
})
public interface ExerciseWithoutId {

}
