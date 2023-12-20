package pt.uminho.di.chalktyk.models.tests;

import java.util.List;
import java.util.Map;

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
}