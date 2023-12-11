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

	/**
	 * Updates an exercise rubric. If an object is 'null' than it is considered that it should remain the same.
	 *
	 * @param rubric new exercise rubric
	 */
	@Override
	public boolean updateRubric(ExerciseRubric rubric) throws UnauthorizedException {
		if(!(rubric instanceof OpenAnswerRubric oar))
			throw new UnauthorizedException("Rubric type cannot be changed");
		boolean updated = false;

		List<OACriterion> oarCriteria = oar.getCriteria();
		if(oarCriteria!=null){
			if(oarCriteria.size()!=criteria.size()){
				criteria=  oarCriteria.stream().map(OACriterion::clone).toList();
				updated=true;
			}
			else {
				for (int i=0;i<oarCriteria.size();i++){
					if(oarCriteria.get(i)!=null){
						OACriterion oaCriterion = criteria.get(i);
						if(oaCriterion.updateOACriterion(oarCriteria.get(i))){
							updated=true;
							criteria.set(i,oaCriterion);
						}
					}
				}
			}
		}
		return updated;
	}

	public OpenAnswerRubric clone(){
		return new OpenAnswerRubric(criteria.stream().map(OACriterion::clone).toList());
    }
}