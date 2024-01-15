package pt.uminho.di.chalktyk.models.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExerciseStatement {
	private String text;
	private String imagePath;
	private String imagePosition;
	public void verifyProperties() throws BadInputException {
		if(text == null)
			throw new BadInputException("Cannot create exercise statement: text cannot be null.");
		if(imagePath == null ^ imagePosition==null)
			throw new BadInputException("Cannot create exercise statement: if an image path is given, then the position of the image is also needed.");
	}

	@Override
	public ExerciseStatement clone(){
		return new ExerciseStatement(text, imagePath, imagePosition);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExerciseStatement statement = (ExerciseStatement) o;

		if (!Objects.equals(text, statement.text)) return false;
		if (!Objects.equals(imagePath, statement.imagePath)) return false;
        return Objects.equals(imagePosition, statement.imagePosition);
    }
}