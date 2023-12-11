package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class ConcreteExercise extends Exercise {

	private ExerciseStatement statement;
	private String title;
	private String rubricId;
	private String solutionId;

    public void verifyProperties() throws BadInputException {
		if(title == null || title.isEmpty())
			throw new BadInputException("Cannot create concrete exercise: A title of a exercise cannot be empty or null.");
		if(statement==null)
			throw new BadInputException("Cannot create concrete exercise: Statement cannot be null.");
		statement.verifyProperties();
		if(rubricId != null)
			throw new BadInputException("Cannot create concrete exercise: RubricId must be null.");
		if(solutionId != null)
			throw new BadInputException("Cannot create concrete exercise: SolutionId must be null.");

		super.verifyInsertProperties();
    }

	public abstract void verifyResolutionProperties(ExerciseResolutionData exerciseResolutionData) throws BadInputException;
	public abstract void verifyRubricProperties(ExerciseRubric rubric) throws BadInputException;

	public abstract String getExerciseType();

	/**
	 * Updates an exercise. If an object is 'null' than it is considered that it should remain the same.
	 * @param exercise     new exercise body
	 */
	@Override
	public boolean updateExercise(Exercise exercise) throws UnauthorizedException {
		if(!(exercise instanceof ConcreteExercise ce))
			throw new UnauthorizedException("The type of the exercise cannot be changed");
		boolean updated = super.updateExercise(exercise);

		if(ce.getStatement()!=null){
			updated = updated || statement.updateStatement(ce.getStatement());
		}
		if(ce.getTitle()!=null){
			ce.title = ce.getTitle();
			updated = true;
		}
		if(ce.getRubricId()!=null)
			throw new UnauthorizedException("Cannot change exercise rubric id");
		if(ce.getSolutionId()!=null) {
			throw new UnauthorizedException("Cannot change exercise solution id");
		}
		return updated;
	}


    //TODO - uncomment methods

	/**
	 * 
	 * @param er
	 */
	//public abstract void evaluate(ExerciseResolutionData er);

}