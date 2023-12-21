package pt.uminho.di.chalktyk.models.exercises;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class ConcreteExercise extends Exercise {
	@Column(name="NrCopies", nullable=false)
	private int nrCopies;

	@Type(JsonBinaryType.class)
	@Column(name = "Statement", columnDefinition = "jsonb")
	private ExerciseStatement statement;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = ExerciseSolution.class)
	@JoinColumn(name = "SolutionID", referencedColumnName = "ID")
	private ExerciseSolution solution;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = ExerciseRubric.class)
	@JoinColumn(name = "RubricID", referencedColumnName = "ID")
	private ExerciseRubric rubric;

	public void verifyProperties() throws BadInputException {
		String title = getTitle();
		if(title == null || title.isEmpty())
			throw new BadInputException("Cannot create concrete exercise: A title of a exercise cannot be empty or null.");
		if(statement!=null)
			statement.verifyProperties();

		super.verifyInsertProperties();
	}

	//TODO verificar ids
	public abstract void verifyResolutionProperties(ExerciseResolutionData exerciseResolutionData) throws BadInputException;

	//TODO verificar ids
	public abstract void verifyRubricProperties(ExerciseRubric rubric) throws BadInputException;

	/**
	 * @return string that represents the type of the exercise.
	 */
	public abstract String getExerciseType();

	public String getRubricId(){
		return rubric == null ? null : rubric.getId();
	}

	public String getSolutionId(){
		return solution == null ? null : solution.getId();
	}

	/**
	 * Sets the id of the rubric, if rubric is not null.
	 * @param rubricId id that the rubric should have
	 */
	public void setRubricIdIfExists(String rubricId){
		if(rubric != null)
			rubric.setId(rubricId);
	}

	/**
	 * Sets the id of the solution, if solution is not null.
	 * @param solutionId id that the solution should have
	 */
	public void setSolutionIdIfExists(String solutionId){
		if(solution != null)
			solution.setId(solutionId);
	}

	/**
	 * Evaluates the resolution of an exercise.
	 *
	 * @param resolution resolution data that will be evaluated
	 * @param solution   solution of the exercise
	 * @param rubric     rubric of the exercise
	 * @return points to be attributed to the resolution
	 * @throws UnauthorizedException if the resolution cannot be evaluated automatically.
	 */
	public abstract ExerciseResolution automaticEvaluation(ExerciseResolution resolution, ExerciseSolution solution, ExerciseRubric rubric) throws UnauthorizedException;

	public void increaseNrCopies(){
		nrCopies++;
	}

	public void decreaseNrCopies(){
		nrCopies--;
	}
}