package pt.uminho.di.chalktyk.models.exercises.open_answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OAStandard  implements Serializable {
	private String title;
	private String description;
	private Float points;
	
	public void verifyProperties() throws BadInputException {
		if(title==null)
			throw new BadInputException("Cannot create exercise: The rubric Standard's title cannot be null.");
		else if(description==null)
			throw new BadInputException("Cannot create exercise: The rubric Standard's description cannot be null.");
		else if(points <0)
			throw new BadInputException("Cannot create exercise: The rubric Standard's points cannot be negative.");
	}

	public boolean equals(OAStandard oaStandard){
		if(!Objects.equals(oaStandard.getTitle(), title))
			return false;
		if(!Objects.equals(oaStandard.getDescription(), description))
			return false;
		if(!Objects.equals(oaStandard.getPoints(), points))
			return false;
		return true;
	}
	public OAStandard clone(){
		return new OAStandard(title,description, points);
	}
}