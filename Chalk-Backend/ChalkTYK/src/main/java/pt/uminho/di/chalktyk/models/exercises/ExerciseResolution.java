package pt.uminho.di.chalktyk.models.exercises;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseResolution {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(name = "Points")
	private Float points;
	
	@Column(name = "SubmissionNr")
	private Integer submissionNr;

	@Type(JsonBinaryType.class)
	@Column(name = "Data", columnDefinition = "jsonb")
	private ExerciseResolutionData data;
	
	@Column(name = "Status")
	private ExerciseResolutionStatus status;

	@Type(JsonBinaryType.class)
	@Column(name = "Comment", columnDefinition = "jsonb")
	private Comment comment;
	
	@ManyToOne(targetEntity= Exercise.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="ExerciseID", referencedColumnName="ID", nullable=false) })
	private Exercise exercise;

	@ManyToOne(targetEntity= Student.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="StudentID", referencedColumnName="ID", nullable=false) })
	private Student student;
	

	/**
	 * Checks if the resolution inserted properties is correctly formed.
	 * @throws BadInputException if any property is not correct
	 */
	public void verifyInsertProperties() throws BadInputException {
		if(student == null)
			throw new BadInputException("An exercise resolution needs to have a student associated.");

		if(exercise == null)
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

		if(student == null)
			throw new BadInputException("An exercise resolution needs to have a student associated.");

		if(exercise == null)
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