package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import pt.uminho.di.chalktyk.models.relational.TagSQL;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class ConcreteExercise extends Exercise {

	private ExerciseStatement statement;
	private String title;
	private String rubricId;
	private String solutionId;
	@Transient
	private Set<TagSQL> tags; // TODO - mudar para string e fica só o nome da tag?
    public void verifyProperties() throws BadInputException {
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
}