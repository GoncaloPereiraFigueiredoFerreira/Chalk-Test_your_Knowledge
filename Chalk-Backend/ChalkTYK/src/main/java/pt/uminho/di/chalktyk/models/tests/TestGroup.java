package pt.uminho.di.chalktyk.models.tests;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ReferenceExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.TestExercise;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestGroup {
	private String groupInstructions;
	private Float groupPoints;
	private Map<Integer, TestExercise> exercises;

	/**
	 * Calculates and updates the group points.
	 * @return group points
	 * @throws BadInputException if the points of an exercise are not a positive number.
	 */
	public float calculateGroupPoints() throws BadInputException {
		float points = 0;
		for (TestExercise te: exercises.values()) {
			float tePoints = te.getPoints();
			if(tePoints <= 0)
				throw new BadInputException("The points of an exercise must be a positive number.");
			points += tePoints;
		}
		groupPoints = points;
		return points;
	}

	public void verifyProperties() throws BadInputException {
		if (groupPoints == null || groupPoints < 0)
			throw new BadInputException("The points of a group can't be null, and must be non-negative.");
	}

	/**
	 * Clones the test group. If the test group is composed by concrete exercises,
	 * they are converted to reference exercises.
	 * @return cloned test group
	 */
	public TestGroup clone() {
		TestGroup duplicatedGroup = new TestGroup();
		duplicatedGroup.setGroupInstructions(this.groupInstructions);
		duplicatedGroup.setGroupPoints(this.groupPoints);

		// Duplicate exercises if present
		if (this.exercises != null) {
			Map<Integer, TestExercise> duplicatedExercises = new HashMap<>();

			// clones reference exercises and converts concrete exercises into reference exercises
			for (Map.Entry<Integer, TestExercise> entry : this.exercises.entrySet()) {
				TestExercise t = entry.getValue();
				assert t != null;
				String id = t.getId();
				assert id != null;
				duplicatedExercises.put(entry.getKey(), new ReferenceExercise(id, t.getPoints()));
			}
			duplicatedGroup.setExercises(duplicatedExercises);
		}

		return duplicatedGroup;
	}
}