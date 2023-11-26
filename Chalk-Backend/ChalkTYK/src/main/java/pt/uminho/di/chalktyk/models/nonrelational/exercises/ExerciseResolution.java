package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exercises_resolutions")
public class ExerciseResolution {
	@Id
	private String id;
	private String studentId;
	private String exerciseId;
	private Float cotation;
	private ExerciseResolutionData data;
	private ExerciseResolutionStatus status;
	private Comment comment;
	private Exercise exercise;
	private int submissionNr;
}