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

}