package pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Item;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks.FillTheBlanksData;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.OpenAnswerData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MultipleChoiceData extends ExerciseResolutionData {

	private Map<Integer, MultipleChoiceResolutionItem> items;

	@Override
	public void verifyInsertProperties() throws BadInputException {
		for(Map.Entry<Integer,MultipleChoiceResolutionItem> entry: items.entrySet()){
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
		if(multipleChoiceData.getItemResolutions().size()!=itemResolutions.size())
			return false;
		for (int i=0;i<itemResolutions.size();i++){
			if(!itemResolutions.get(i).equals(multipleChoiceData.getItemResolutions().get(i)))
				return false;
		}
		return true;
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