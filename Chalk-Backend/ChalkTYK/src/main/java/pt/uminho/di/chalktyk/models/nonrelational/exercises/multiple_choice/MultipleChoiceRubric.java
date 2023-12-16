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

	@Override
	public boolean equals(ExerciseRubric exerciseRubric) {
		if(!(exerciseRubric instanceof MultipleChoiceRubric multipleChoiceRubric))
			return false;
		if(multipleChoiceRubric.getJustificationsRubrics().size()!=justificationsRubrics.size())
			return false;
		for (int i=0;i<justificationsRubrics.size();i++){
			if(!justificationsRubrics.get(i).equals(multipleChoiceRubric.getJustificationsRubrics().get(i)))
				return false;
		}
		if(!(multipleChoiceRubric.getChoicePoints().equals(choicePoints)))
			return false;
		if(!(multipleChoiceRubric.getPenalty().equals(penalty)))
			return false;
		return true;
	}

	public float getMaxPointsSum() {
		return choicePoints * justificationsRubrics.size();
	}
}