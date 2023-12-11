package pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@AllArgsConstructor
@Getter
public class OpenAnswerData extends ExerciseResolutionData {
	private String text;

	public void verifyInsertProperties() throws BadInputException {
		if (text==null)
			throw new BadInputException("Open answer data text cannot be null");
	}

	/**
	 * Updates an exercise resolution. If an object is 'null' than it is considered that it should remain the same.
	 *
	 * @param exerciseResolutionData new exercise resolution body
	 */
	@Override
	public boolean updateExerciseResolutionData(ExerciseResolutionData exerciseResolutionData) throws UnauthorizedException {
		if(!(exerciseResolutionData instanceof OpenAnswerData openAnswerData))
			throw new UnauthorizedException("Exercise resolution type cannot be changed");

		boolean updated = false;
		if(openAnswerData.getText()!=null){
			text = openAnswerData.getText();
			updated = true;
		}
		return updated;
	}
}