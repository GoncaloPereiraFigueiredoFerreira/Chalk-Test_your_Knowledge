package pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer;

import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

public class OpenAnswerData extends ExerciseResolutionData {
	private String text;

	public void verifyInsertProperties() throws BadInputException {
		if (text==null)
			throw new BadInputException("Open answer data text cannot be null");
	}
}