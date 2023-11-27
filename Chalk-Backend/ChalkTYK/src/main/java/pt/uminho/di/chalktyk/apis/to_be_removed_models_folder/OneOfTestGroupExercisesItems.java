package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfTestGroupExercisesItems
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Exercise.class, name = "Exercise"),
  @JsonSubTypes.Type(value = ExerciseReference.class, name = "ExerciseReference")
})
public interface OneOfTestGroupExercisesItems {

}
