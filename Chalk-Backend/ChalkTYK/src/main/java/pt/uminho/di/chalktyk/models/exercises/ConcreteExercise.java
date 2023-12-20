package pt.uminho.di.chalktyk.models.exercises;

public abstract class ConcreteExercise extends Exercise {
	private String rubricId;
	private String solutionId;
	private ExerciseStatement statement;
	private ExerciseSolution solution;
	private ExerciseRubric rubric;

	protected abstract boolean checkSolutionType();

	public abstract void evaluate(ExerciseResolutionData er);
}