package pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.*;

@Document(collection = "exercises_rubrics")
@JsonTypeName("MC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MultipleChoiceRubric extends ExerciseRubric {
	private Map<Integer,OpenAnswerRubric> justificationsRubrics;
	private Float choicePoints;
	private Float penalty;

	@Override
	public void verifyProperties() throws BadInputException {
		if (penalty == null || choicePoints == null || penalty < 0 || choicePoints < 0)
			throw new BadInputException("Cannot create MultipleChoiceRubric: The points or penalty of a rubric cannot be null or negative.");
		if(justificationsRubrics!=null){
			for (OpenAnswerRubric openAnswerRubric : justificationsRubrics.values())
				openAnswerRubric.verifyProperties();
		}
	}

	public float getMaxPointsSum() {
		return choicePoints * justificationsRubrics.size();
	}
}