package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Getter
@Setter
@AllArgsConstructor
public class StringItem extends Item {

	private String text;

	@Override
	public void verifyProperties() throws BadInputException {
		if(text!=null)
			throw new BadInputException("The text of a string item cannot be null.");
	}

	@Override
	public Item clone() {
		return new StringItem(text);
	}


}