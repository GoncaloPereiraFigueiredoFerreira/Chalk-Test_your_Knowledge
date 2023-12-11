package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Document(collection = "exercises_rubrics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public abstract class ExerciseRubric {
	@Id
	private String id;


	public abstract void verifyProperties() throws BadInputException;

	/**
	 * Updates an exercise rubric. If an object is 'null' than it is considered that it should remain the same.
	 * @param rubric  new exercise rubric
	 */
	public abstract boolean updateRubric(ExerciseRubric rubric) throws UnauthorizedException;
}