package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import lombok.Getter;

@Getter
public enum ExerciseResolutionStatus {
	NOT_REVISED(1, "not_revised"),
	REVISED(2, "revised");

	private final int number;
	private final String string;

	ExerciseResolutionStatus(int number, String string) {
		this.number = number;
		this.string = string;
	}

}