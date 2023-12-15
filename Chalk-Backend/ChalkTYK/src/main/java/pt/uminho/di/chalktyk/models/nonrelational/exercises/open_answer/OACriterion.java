package pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OACriterion {

	private List<OAStandard> standards;
	private String title;

	public void verifyProperties() throws BadInputException {
		if (title==null)
			throw new BadInputException("Cannot create OACriterion: The rubric title of a open answer exercise rubric cannot be null.");
		else if (standards==null || standards.isEmpty()) {
			throw new BadInputException("Cannot create OACriterion: The rubric Standards of a open answer exercise cannot be null or empty.");
		}
		for (OAStandard standard:standards){
			if(standard == null)
				throw new BadInputException("Cannot create OACriterion: The rubric Standards cannot be null.");
			else standard.verifyProperties();
		}
	}
	public OACriterion clone(){
		return new OACriterion(standards.stream().map(OAStandard::clone).toList(),title);
	}
}