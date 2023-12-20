package pt.uminho.di.chalktyk.models.exercises.fill_the_blanks;

import java.util.List;

import pt.uminho.di.chalktyk.models.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionData;

public class FillTheBlanksExercise extends ConcreteExercise {
	private List<String> textSegments;

	protected boolean checkSolutionType() {
		throw new UnsupportedOperationException();
	}

	public void evaluate(ExerciseResolutionData er) {
		throw new UnsupportedOperationException();
	}
}