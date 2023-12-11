package pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

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

	/**
	 * Updates an exercise rubric criterion standard. If an object is 'null' than it is considered that it should remain the same.
	 *
	 * @param newOAStandard new criterion standard body
	 */
	public boolean updateOAStandard(OAStandard newOAStandard) throws UnauthorizedException {
		boolean updated = false;
		if(newOAStandard.getTitle()!=null){
			title= newOAStandard.getTitle();
			updated = true;
		}
		if(newOAStandard.getDescription()!=null){
			description= newOAStandard.getDescription();
			updated = true;
		}
		if(newOAStandard.getCotation()!=null){
			cotation= newOAStandard.getCotation();
			updated = true;
		}
		return updated;
	}
	public OAStandard clone(){
		return new OAStandard(title,description,cotation);
	}
}