package pt.uminho.di.chalktyk.models.nonrelational.tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

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