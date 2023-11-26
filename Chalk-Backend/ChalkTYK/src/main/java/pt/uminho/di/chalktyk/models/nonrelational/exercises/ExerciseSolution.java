package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exercises_solutions")
public class ExerciseSolution {
	@Id
	private String id;
	private ExerciseResolutionData data;
}