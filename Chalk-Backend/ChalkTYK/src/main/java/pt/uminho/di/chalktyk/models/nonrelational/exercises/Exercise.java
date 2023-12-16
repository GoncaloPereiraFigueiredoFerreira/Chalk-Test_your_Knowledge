package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

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
	private Float points;

	public void verifyInsertProperties() throws BadInputException {
		if(points == null || points <= 0)
			throw new BadInputException("Cannot create exercise: The points of an exercise cannot be null, and must be positive.");
	}
}