package pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Document(collection = "exercises_rubrics")
@JsonTypeName("FTB")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FillTheBlanksRubric extends ExerciseRubric {
	private Float fillingCotation;
	private Float penalty;

	@Override
	public void verifyProperties() throws BadInputException {
		if(penalty == null || fillingCotation == null || penalty < 0 || fillingCotation < 0)
			throw new BadInputException("Cannot create FillTheBlanksRubric: The cotation or penalty of a rubric cannot be null or negative.");
	}

	/**
	 * Updates an exercise rubric. If an object is 'null' than it is considered that it should remain the same.
	 *
	 * @param rubric new exercise rubric
	 */
	@Override
	public boolean updateRubric(ExerciseRubric rubric) throws UnauthorizedException {
		if(!(rubric instanceof FillTheBlanksRubric ftbr))
			throw new UnauthorizedException("Rubric type cannot be changed");
		boolean updated = false;
		if(ftbr.getFillingCotation()!=null){
			fillingCotation = ftbr.getFillingCotation();
			updated = true;
		}
		if(ftbr.getPenalty()!=null){
			penalty = ftbr.getPenalty();
			updated = true;
		}
		return updated;
	}
}