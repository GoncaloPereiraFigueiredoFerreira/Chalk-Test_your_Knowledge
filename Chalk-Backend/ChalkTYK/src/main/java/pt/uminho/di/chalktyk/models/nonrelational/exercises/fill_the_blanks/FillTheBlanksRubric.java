package pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Document(collection = "exercises_rubrics")
@JsonTypeName("FTB")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FillTheBlanksRubric extends ExerciseRubric {
	private Float fillingPoints;
	private Float penalty;

	@Override
	public void verifyProperties() throws BadInputException {
		if(penalty == null || fillingPoints == null || penalty < 0 || fillingPoints < 0)
			throw new BadInputException("Cannot create FillTheBlanksRubric: The points or penalty of a rubric cannot be null or negative.");
	}
}