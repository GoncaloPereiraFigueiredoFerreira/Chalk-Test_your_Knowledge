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

	public abstract Item clone();
}