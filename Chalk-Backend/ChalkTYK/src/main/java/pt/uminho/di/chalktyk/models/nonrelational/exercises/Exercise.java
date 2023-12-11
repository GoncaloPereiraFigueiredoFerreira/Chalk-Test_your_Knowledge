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

	/**
	 * Updates an exercise. If an object is 'null' than it is considered that it should remain the same.
	 * @param exercise     new exercise body
	 */
	public boolean updateExercise(Exercise exercise) throws UnauthorizedException {
		boolean updated = false;
		if(exercise.id!=null)
			throw new UnauthorizedException("Cannot change exercise id");
		if(exercise.getSpecialistId()!=null)
			throw new UnauthorizedException("Cannot change exercise specialist owner");
		if(exercise.getInstitutionId()!=null)
			throw new UnauthorizedException("Cannot change exercise institution");
		if(exercise.getCourseId()!=null) {
			this.courseId = exercise.getCourseId();
			updated = true;
		}
		if (exercise.getCotation()!=null) {
			this.cotation = exercise.getCotation();
			updated = true;
		}
		return updated;
	}
}