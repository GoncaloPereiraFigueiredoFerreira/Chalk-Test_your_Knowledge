package pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;

import java.util.*;

@Document(collection = "exercises_rubrics")
@JsonTypeName("OA")
public class OpenAnswerRubric extends ExerciseRubric {
	private List<OACriterion> criteria;
}