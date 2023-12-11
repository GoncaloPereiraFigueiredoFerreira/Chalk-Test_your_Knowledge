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

	/**
	 * Updates an exercise rubric criterion. If an object is 'null' than it is considered that it should remain the same.
	 *
	 * @param oaCriterion new criterion body
	 */
	public boolean updateOACriterion(OACriterion oaCriterion) throws UnauthorizedException {
		boolean updated = false;
		List<OAStandard> newStandards = oaCriterion.getStandards();
		if(newStandards!=null){
			if(newStandards.size()!=standards.size()){
				standards=  newStandards.stream().map(OAStandard::clone).toList();
				updated=true;
			}
			else {
				for (int i=0;i<newStandards.size();i++){
					if(newStandards.get(i)!=null){
						OAStandard oaStandard = standards.get(i);
						if(oaStandard.updateOAStandard(newStandards.get(i))){
							updated=true;
							standards.set(i,oaStandard);
						}
					}
				}
			}
		}
		return updated;
	}
	public OACriterion clone(){
		return new OACriterion(standards.stream().map(OAStandard::clone).toList(),title);
	}
}