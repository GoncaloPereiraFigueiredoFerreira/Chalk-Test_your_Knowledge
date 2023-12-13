package pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice.MultipleChoiceRubric;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.*;

@Document(collection = "exercises_rubrics")
@JsonTypeName("OA")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OpenAnswerRubric extends ExerciseRubric {
	private List<OACriterion> criteria;

	@Override
	public void verifyProperties() throws BadInputException {
		if(criteria == null || criteria.isEmpty())
			throw new BadInputException("Cannot create OpenAnswerRubric: The rubric list of a open answer exercise cannot be null or empty.");
		else {
			for (OACriterion criterion:  criteria){
				criterion.verifyProperties();
			}
		}
	}


	public OpenAnswerRubric clone(){
		return new OpenAnswerRubric(criteria.stream().map(OACriterion::clone).toList());
    }
}