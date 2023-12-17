package pt.uminho.di.chalktyk.models.nonrelational.tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

/**
 * type = "basic"
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tests")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("basic")
public abstract class Test {
	@Id
	private String id;
	private String specialistId;
	private String institutionId;
	private String courseId;
	private String title;
	private String globalInstructions;
	private Float globalPoints;
	private String conclusion;
	private LocalDateTime creationDate;
	private LocalDateTime publishDate;
	private List<TestGroup> groups;

	public void verifyInsertProperties() throws BadInputException {
        // check title
		if (title == null || title.isEmpty())
			throw new BadInputException("Cannot create test: A title of a test cannot be empty or null.");

		// check publish date
		if (!publishDate.isAfter(LocalDateTime.of(2023, 12, 10, 0, 0)))
            throw new BadInputException("Cannot create test: Publish date is outdated.");

		// check global points
		if (globalPoints < 0)
			throw new BadInputException("Cannot create test: Global points can't be negative.");
    }

	public abstract void verifyProperties() throws BadInputException;
}