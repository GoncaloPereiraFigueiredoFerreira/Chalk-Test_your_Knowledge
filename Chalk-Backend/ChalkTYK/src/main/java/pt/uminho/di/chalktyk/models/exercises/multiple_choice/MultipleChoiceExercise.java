package pt.uminho.di.chalktyk.models.exercises.multiple_choice;

import java.util.ArrayList;
import pt.uminho.di.chalktyk.models.exercises.items.Item;
import pt.uminho.di.chalktyk.models.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionData;

public class MultipleChoiceExercise extends ConcreteExercise {
	private int mctype;
	private ArrayList<Item> items = new ArrayList<Item>();

	protected boolean checkSolutionType() {
		throw new UnsupportedOperationException();
	}

	public void evaluate(ExerciseResolutionData er) {
		throw new UnsupportedOperationException();
	}
}