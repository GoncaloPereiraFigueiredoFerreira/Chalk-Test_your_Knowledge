package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Document(collection = "exercises")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Exercise {
	@Id
	private String id;
	private String specialistId;
	private String institutionId;
	private String courseId;
	private Float cotation;



	public void verifyInsertProperties() throws BadInputException {
		setId(null);
		if(cotation == null || cotation < 0)
			throw new BadInputException("Cannot create exercise: The cotation of a exercise cannot be null or negative.");
	}
}