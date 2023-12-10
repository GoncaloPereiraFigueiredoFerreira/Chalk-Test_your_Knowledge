package pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Item;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MultipleChoiceData extends ExerciseResolutionData {

	private List<MultipleChoiceResolutionItem> itemResolutions;
	public void verifyProperties(int mcType) throws BadInputException {
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
}