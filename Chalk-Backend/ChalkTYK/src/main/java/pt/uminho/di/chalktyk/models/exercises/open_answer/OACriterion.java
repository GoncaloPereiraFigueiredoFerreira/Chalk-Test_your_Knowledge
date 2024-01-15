package pt.uminho.di.chalktyk.models.exercises.open_answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OACriterion  implements Serializable {
	/*
	The sum of the points of all OACriterion, needs to be equal to 100,
	but the points of each criterion need to be a positive value.
	Since the "grade" of each exercise is supposed to be a percentage.

	The percentage in OAStandard needs to be a value between 0 (inclusive)
	and 100 (inclusive). If a student meets a certain standard for a criterion,
	then its points for that criterion equals to:
		(criterion points) * percentage  / 100.
	 */
	private String title;
	private float points;
	private List<OAStandard> standards;
	
	public void verifyProperties() throws BadInputException {
		if (title==null)
			throw new BadInputException("Cannot create open answer criterion: The rubric title of a open answer exercise rubric cannot be null.");
		if (standards==null || standards.isEmpty()) {
			throw new BadInputException("Cannot create open answer criterion: The rubric Standards of a open answer exercise cannot be null or empty.");
		}
		if(points <= 0.0f || points > 100.0f)
			throw new BadInputException("Cannot create open answer criterion: Points need to be a value between 0 (exclusive) and 100 (inclusive).");

		for (OAStandard standard:standards){
			if(standard == null)
				throw new BadInputException("Cannot create open answer criterion: The rubric Standards cannot be null.");
			standard.verifyProperties();
		}
	}

	public boolean equals(OACriterion oaCriterion){
		if(!Objects.equals(oaCriterion.getTitle(), title))
			return false;
		if(oaCriterion.getStandards().size()!=standards.size())
			return false;
		for (int i=0;i<standards.size();i++){
			if(!standards.get(i).equals(oaCriterion.getStandards().get(i)))
				return false;
		}
		return true;
	}
	public OACriterion clone(){
		var clonedStandards = standards != null ? standards.stream().map(OAStandard::clone).toList() : null;
		return new OACriterion(title, points, clonedStandards);
	}
}