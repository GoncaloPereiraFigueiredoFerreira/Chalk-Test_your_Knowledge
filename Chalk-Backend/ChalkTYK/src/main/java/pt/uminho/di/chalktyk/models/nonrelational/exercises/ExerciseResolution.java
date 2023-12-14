package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "exercises_resolutions")
public class ExerciseResolution {
	@Id
	private String id;
	private String studentId;
	private String exerciseId;
	private Float cotation;
	private ExerciseResolutionData data;
	private ExerciseResolutionStatus status;
	private Comment comment;
	private Integer submissionNr;

	/**
	 * Checks if the resolution inserted properties is correctly formed.
	 * @throws BadInputException if any property is not correct
	 */
	public void verifyInsertProperties() throws BadInputException {
		if(studentId == null)
			throw new BadInputException("An exercise resolution needs to have a student associated.");

		if(exerciseId == null)
			throw new BadInputException("An exercise resolution needs to have a student associated.");

		if(data == null)
			throw new BadInputException("An exercise resolution needs to have resolution data.");
		else
			data.verifyInsertProperties();
	}

	/**
	 * Checks if the resolution is correctly formed.
	 * @throws BadInputException if any property is not correct
	 */
	public void verifyProperties() throws BadInputException {
		if(id == null)
			throw new BadInputException("An exercise resolution needs to have an id.");

		if(studentId == null)
			throw new BadInputException("An exercise resolution needs to have a student associated.");

		if(exerciseId == null)
			throw new BadInputException("An exercise resolution needs to have a student associated.");

		if(status == null)
			throw new BadInputException("An exercise resolution needs to have a status.");

		if(submissionNr == null)
			throw new BadInputException("An exercise resolution needs to have a submission number.");

		if(data == null)
			throw new BadInputException("An exercise resolution needs to have resolution data.");
		else
			data.verifyInsertProperties();
	}
}