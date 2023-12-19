package pt.uminho.di.chalktyk.models.nonrelational.tests;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * type = 'deliver_date'
 */
@Document(collection = "tests")
@Getter
@Setter
@JsonTypeName("deliver_date")
public class DeliverDateTest extends Test {
	private LocalDateTime deliverDate;

	public DeliverDateTest(String id, String specialistId, String institutionId, String courseId, String title, String globalInstructions,
					Float globalPoints, String conclusion, LocalDateTime creationDate, LocalDateTime publishDate, List<TestGroup> groups,
					LocalDateTime deliverDate){
		super(id, specialistId, institutionId, courseId, title, globalInstructions, globalPoints, conclusion, creationDate, publishDate, groups);
		this.setDeliverDate(deliverDate);
	}

	@Override
	public void verifyProperties() throws BadInputException {
		super.verifyProperties();
		if (super.getPublishDate().isAfter(getDeliverDate()))
			throw new BadInputException("Cannot create test: Deliver date is invalid - occurs before publish date");
	}
}