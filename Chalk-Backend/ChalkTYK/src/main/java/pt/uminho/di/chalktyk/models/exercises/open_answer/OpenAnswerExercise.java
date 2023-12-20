package pt.uminho.di.chalktyk.models.exercises.open_answer;

import pt.uminho.di.chalktyk.models.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionData;

public class OpenAnswerExercise extends ConcreteExercise {

	protected boolean checkSolutionType() {
		throw new UnsupportedOperationException();
	}

	public void evaluate(ExerciseResolutionData er) {
		throw new UnsupportedOperationException();
	}
}