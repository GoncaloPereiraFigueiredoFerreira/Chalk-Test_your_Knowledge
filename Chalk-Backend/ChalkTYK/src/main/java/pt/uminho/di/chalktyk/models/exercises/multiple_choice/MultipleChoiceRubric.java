package pt.uminho.di.chalktyk.models.exercises.multiple_choice;

import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;

import java.util.ArrayList;

public class MultipleChoiceRubric extends ExerciseRubric {
	private Float choicePoints;
	private Float penalty;
	public OACriterion unnamed_OACriterion_;
	private ArrayList<OpenAnswerRubric> justificationsRubrics = new ArrayList<OpenAnswerRubric>();
}