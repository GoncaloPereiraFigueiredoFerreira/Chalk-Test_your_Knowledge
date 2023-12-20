package pt.uminho.di.chalktyk.models.exercises;

import pt.uminho.di.chalktyk.models.users.Student;

public class ExerciseResolution {
	private String id;
	private Float points;
	private int submissionNr;
	private Exercise exercise;
	private ExerciseResolutionData data;
	private Student student;
	private ExerciseResolutionStatus status;
	private Comment comment;
}