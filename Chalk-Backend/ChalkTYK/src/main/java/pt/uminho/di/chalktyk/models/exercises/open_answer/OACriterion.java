package pt.uminho.di.chalktyk.models.exercises.open_answer;

import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceRubric;

import java.util.ArrayList;

public class OACriterion {
	private String title;
	public ExerciseRubric unnamed_ExerciseRubric_;
	public MultipleChoiceRubric unnamed_MultipleChoiceRubric_;
	private ArrayList<OAStandard> standards = new ArrayList<OAStandard>();
}