package pt.uminho.di.chalktyk.models.nonrelational.tests;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestResolutionGroup {
	private List<ExerciseResolution> resolutions;
	private Float groupPoints;
}