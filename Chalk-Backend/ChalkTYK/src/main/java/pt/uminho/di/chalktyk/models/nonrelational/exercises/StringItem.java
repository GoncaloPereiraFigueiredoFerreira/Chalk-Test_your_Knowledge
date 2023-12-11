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

	/**
	 * Updates an item. If an object is 'null' than it is considered that it should remain the same.
	 *
	 * @param item new item
	 */
	@Override
	public boolean updateItem(Item item) throws UnauthorizedException {
		if(!(item instanceof StringItem stringItem))
			throw new UnauthorizedException("Cannot change item type"); //TODO allow this
		boolean updated = false;
		if(stringItem.getText()!=null){
			text=stringItem.text;
			updated=true;
		}
		return updated;
	}

	@Override
	public Item clone() {
		return new StringItem(text);
	}


}