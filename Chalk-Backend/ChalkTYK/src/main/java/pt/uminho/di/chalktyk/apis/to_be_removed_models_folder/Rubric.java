package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* Rubric schema.
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = MultipleChoiceRubric.class, name = "MultipleChoiceRubric"),
  @JsonSubTypes.Type(value = OpenAnswerRubric.class, name = "OpenAnswerRubric"),
  @JsonSubTypes.Type(value = FillTheBlanksRubric.class, name = "FillTheBlanksRubric")
})
public interface Rubric {

}
