package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Getter
@Setter
@AllArgsConstructor
public class StringItem extends Item {

	private String text;

	@Override
	public void VerifyProperties() throws BadInputException {
		if(text!=null)
			throw new BadInputException("Multiple choice string item text cannot be null");
	}

	@Override
	public Item clone() {
		return new StringItem(text);
	}


}