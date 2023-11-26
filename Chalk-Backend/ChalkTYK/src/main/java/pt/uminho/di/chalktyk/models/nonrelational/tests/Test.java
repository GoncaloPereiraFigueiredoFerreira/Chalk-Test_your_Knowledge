package pt.uminho.di.chalktyk.models.nonrelational.tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

/**
 * type = "basic"
 */
@Document(collection = "tests")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("basic")
public class Test {
	@Id
	private String id;
	private String specialistId;
	private String instituitionId;
	private String courseId;
	private String title;
	private String globalInstructions;
	private Float globalCotation;
	private String conclusion;
	private LocalDateTime creationDate;
	private LocalDateTime publishDate;
	private List<TestGroup> groups;
}