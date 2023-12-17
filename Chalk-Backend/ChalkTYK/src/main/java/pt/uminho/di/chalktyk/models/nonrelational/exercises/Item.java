package pt.uminho.di.chalktyk.models.nonrelational.exercises;


import lombok.Getter;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Getter
@Setter

public abstract class Item {
	private Integer id;

	public abstract void verifyProperties() throws BadInputException;

	public abstract Item clone();
}