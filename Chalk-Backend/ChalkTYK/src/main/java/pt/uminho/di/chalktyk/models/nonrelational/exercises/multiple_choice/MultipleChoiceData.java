package pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Item;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.OpenAnswerData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MultipleChoiceData extends ExerciseResolutionData {

	private List<MultipleChoiceResolutionItem> itemResolutions;
	public void verifyProperties(Mctype mcType) throws BadInputException {
		for(MultipleChoiceResolutionItem item:itemResolutions){
			item.verifyProperties(mcType);
		}
	}

	@Override
	public void verifyInsertProperties() throws BadInputException {
		for(MultipleChoiceResolutionItem item:itemResolutions){
			item.verifyInsertProperties();
		}
	}

	/**
	 * Updates an exercise resolution. If an object is 'null' than it is considered that it should remain the same.
	 *
	 * @param exerciseResolutionData new exercise resolution body
	 */
	@Override
	public boolean updateExerciseResolutionData(ExerciseResolutionData exerciseResolutionData) throws UnauthorizedException {
		if(!(exerciseResolutionData instanceof MultipleChoiceData multipleChoiceData))
			throw new UnauthorizedException("Exercise resolution type cannot be changed");

		boolean updated = false;
		List<MultipleChoiceResolutionItem> newItemResolutions = multipleChoiceData.getItemResolutions();
		if(newItemResolutions!=null){
			if(newItemResolutions.size()!=itemResolutions.size()){
				itemResolutions=  newItemResolutions.stream().map(MultipleChoiceResolutionItem::clone).toList();
				updated=true;
			}/*
			else {
				for (int i=0;i<newItemResolutions.size();i++){
					if(newItemResolutions.get(i)!=null){
						OACriterion oaCriterion = itemResolutions.get(i);
						if(oaCriterion.updateOACriterion(newItemResolutions.get(i))){
							updated=true;
							itemResolutions.set(i,oaCriterion);
						}
					}
				}
			}*/
		}
		return updated;
	}
}