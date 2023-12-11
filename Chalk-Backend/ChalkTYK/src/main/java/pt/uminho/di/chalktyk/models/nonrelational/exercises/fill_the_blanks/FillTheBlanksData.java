package pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

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

	/**
	 * Updates an exercise resolution. If an object is 'null' than it is considered that it should remain the same.
	 *
	 * @param exerciseResolutionData new exercise resolution body
	 */
	@Override
	public boolean updateExerciseResolutionData(ExerciseResolutionData exerciseResolutionData) throws UnauthorizedException {
		return false;
	}
}