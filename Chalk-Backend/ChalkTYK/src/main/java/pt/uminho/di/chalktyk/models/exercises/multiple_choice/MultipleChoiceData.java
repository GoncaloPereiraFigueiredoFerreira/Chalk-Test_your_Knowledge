package pt.uminho.di.chalktyk.models.exercises.multiple_choice;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonTypeName("MC")
public class MultipleChoiceData extends ExerciseResolutionData {
	private Map<Integer, MultipleChoiceResolutionItem> items;

	@Override
	public void verifyInsertProperties() throws BadInputException {
		for(Map.Entry<Integer, MultipleChoiceResolutionItem> entry: items.entrySet()){
			MultipleChoiceResolutionItem item = entry.getValue();
			if(item == null)
				throw new BadInputException("Multiple choice resolution item cannot be null.");
			item.verifyInsertProperties();
		}
	}

	@Override
	public boolean equals(ExerciseResolutionData exerciseResolutionData) {
		if(!(exerciseResolutionData instanceof MultipleChoiceData multipleChoiceData))
			return false;
		if(multipleChoiceData.getItems().size()!=items.size())
			return false;
		for (Integer key:items.keySet()){
			if(!items.get(key).equals(multipleChoiceData.getItems().get(key)))
				return false;
		}
		return true;
	}

	@Override
	public ExerciseResolutionData clone() {
		Map<Integer, MultipleChoiceResolutionItem> itemsCloned = new HashMap<>();
		for(Map.Entry<Integer, MultipleChoiceResolutionItem> entry : items.entrySet())
			itemsCloned.put(entry.getKey(), entry.getValue().clone());
		return new MultipleChoiceData(itemsCloned);
	}

	public Set<Integer> getIds(){
		return items.keySet();
	}

	public MultipleChoiceResolutionItem getItemById(int id){
		return items != null ? items.get(id) : null;
	}

	public void putItem(int id, MultipleChoiceResolutionItem item){
		if(items == null)
			items = new HashMap<>();
		items.put(id, item);
	}
}