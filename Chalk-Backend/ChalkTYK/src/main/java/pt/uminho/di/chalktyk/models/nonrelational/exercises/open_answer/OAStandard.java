package pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OAStandard {

	private String title;
	private String description;
	private Float cotation;

	public void verifyProperties() throws BadInputException {
		if(title==null)
			throw new BadInputException("Cannot create exercise: The rubric Standard's title cannot be null.");
		else if(description==null)
			throw new BadInputException("Cannot create exercise: The rubric Standard's description cannot be null.");
		else if(cotation<0)
			throw new BadInputException("Cannot create exercise: The rubric Standard's cotation cannot be negative.");
	}

}