package pt.uminho.di.chalktyk.models.nonrelational.tests;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Document(collection = "tests_resolutions")
public class TestResolution {
	@Id
	private String id;
	private String studentId;
	private String testId;
	private TestResolutionStatus status;
	private LocalDateTime startDate;
	private LocalDateTime submissionDate;
	private int submissionNr;
	private List<TestResolutionGroup> groups;
}