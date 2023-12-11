package pt.uminho.di.chalktyk.models.nonrelational.exercises;


import lombok.Getter;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Getter
@Setter

public abstract class Item {
	private Integer id;

	public void VerifyProperties() throws BadInputException {
		if(id!=null)
			throw new BadInputException("Multiple choice item id is not null");
	}

	/**
	 * Updates an item. If an object is 'null' than it is considered that it should remain the same.
	 * @param item     new item
	 */
	public abstract boolean updateItem(Item item) throws UnauthorizedException;

	public abstract Item clone();
}