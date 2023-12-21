package pt.uminho.di.chalktyk.models.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@AllArgsConstructor
@Getter
public class ExerciseStatement {
	private String text;
	private String imagePath;
	private String imagePosition;
	public void verifyProperties() throws BadInputException {
		if(text == null)
			throw new BadInputException("Cannot create exercise statement: text cannot be null.");
		if(imagePath == null ^ imagePosition==null)
			throw new BadInputException("Cannot create exercise statement: imagePath == null ^ imagePosition==null ");
	}
}