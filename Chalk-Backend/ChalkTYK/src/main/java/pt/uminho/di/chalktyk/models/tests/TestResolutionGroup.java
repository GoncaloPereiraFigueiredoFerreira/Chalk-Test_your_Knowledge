package pt.uminho.di.chalktyk.models.tests;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResolutionGroup {
	private Float groupPoints;
	private Map<Integer, String> resolutions;
}