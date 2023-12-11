package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import lombok.Getter;

@Getter
public class ShallowExercise extends Exercise {
	private String originalExerciseId;

	public ShallowExercise(String originalExerciseId,String specialistId, String institutionId, String courseId, Float cotation){
		super(null,specialistId,institutionId,courseId,cotation);
		this.originalExerciseId=originalExerciseId;
	}
}