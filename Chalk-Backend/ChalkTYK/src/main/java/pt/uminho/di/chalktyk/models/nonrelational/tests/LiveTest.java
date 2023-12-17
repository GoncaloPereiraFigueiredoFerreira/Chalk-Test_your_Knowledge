package pt.uminho.di.chalktyk.models.nonrelational.tests;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * type = 'live'
 */
@Document(collection = "tests")
@Getter
@Setter
@JsonTypeName("live")
public class LiveTest extends Test {
	private LocalDateTime startDate;
	private Duration duration;
	private Duration startTolerance;

	@Override
	public void verifyProperties() throws BadInputException {
		super.verifyInsertProperties();
		if (super.getPublishDate().isAfter(getStartDate()))
			throw new BadInputException("Cannot create test: Start date is invalid - occurs before publish date");
		
		if (getDuration().isNegative() || getDuration().isZero())
			throw new BadInputException("Cannot create test: Test duration is invalid - non-positive duration");
		
		if (getStartTolerance().isNegative())
			throw new BadInputException("Cannot create test: Test start tolerance is negative");
	}

}