package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfCommentItemsItems
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
})
public interface OneOfCommentItemsItems {

}
