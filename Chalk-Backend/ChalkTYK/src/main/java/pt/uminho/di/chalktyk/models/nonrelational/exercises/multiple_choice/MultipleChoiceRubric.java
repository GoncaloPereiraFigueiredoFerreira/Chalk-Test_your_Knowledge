package pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.OpenAnswerRubric;

import java.util.*;

@Document(collection = "exercises_rubrics")
@JsonTypeName("MC")
public class MultipleChoiceRubric extends ExerciseRubric {
	private List<OpenAnswerRubric> justificationsRubrics;
	private Float choiceCotation;
	private Float penalty;
}