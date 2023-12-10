package pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FillTheBlanksData extends ExerciseResolutionData {

	private List<String> fillings;

	public void verifyInsertProperties() throws BadInputException {
		for (String fill:fillings) {
			if(fill==null)
				throw new BadInputException("Fillings from fill the blank data cannot bet null");
		}
	}
}