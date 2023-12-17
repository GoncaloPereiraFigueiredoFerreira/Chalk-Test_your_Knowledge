package pt.uminho.di.chalktyk.models.nonrelational.tests;

import java.util.*;

import lombok.Getter;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Getter
@Setter
public class TestGroup {
	private List<Exercise> exercises;
	private String groupInstructions;
	private Float groupPoints;

	public void verifyProperties() throws BadInputException {
		if (groupPoints == null || groupPoints < 0)
			throw new BadInputException("Can't create test: The points of a group can't be null, and must be non-negative.");
	}
}