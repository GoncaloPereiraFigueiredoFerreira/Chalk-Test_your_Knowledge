package pt.uminho.di.chalktyk.models.tests;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestGroup {
	private String groupInstructions;
	private Float groupPoints;
	private Map<Integer, List<String>> exercises;

	public void verifyProperties() throws BadInputException {
		if (groupPoints == null || groupPoints < 0)
			throw new BadInputException("Can't create test: The points of a group can't be null, and must be non-negative.");
	}

	public TestGroup clone() {
		TestGroup duplicatedGroup = new TestGroup();
		duplicatedGroup.setGroupInstructions(this.groupInstructions);
		duplicatedGroup.setGroupPoints(this.groupPoints);

		// Duplicate exercises if present
		if (this.exercises != null) {
			Map<Integer, List<String>> duplicatedExercises = new HashMap<>();
			for (Map.Entry<Integer, List<String>> entry : this.exercises.entrySet()) {
				duplicatedExercises.put(entry.getKey(), new ArrayList<>(entry.getValue()));
			}
			duplicatedGroup.setExercises(duplicatedExercises);
		}

		return duplicatedGroup;
	}
}