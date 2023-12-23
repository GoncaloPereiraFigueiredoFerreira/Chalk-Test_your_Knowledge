package pt.uminho.di.chalktyk.models.exercises.open_answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OACriterion  implements Serializable {
	private String title;
	private List<OAStandard> standards;
	
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
		return new OACriterion(title, standards.stream().map(OAStandard::clone).toList());
	}
}