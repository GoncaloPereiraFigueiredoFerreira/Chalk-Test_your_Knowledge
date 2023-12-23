package pt.uminho.di.chalktyk.models.tests;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

/**
 * type = 'live'
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("live")
@DiscriminatorValue("live")
public class LiveTest extends Test {
	@Column(name="StartDate")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime startDate;

	@Column(name="Duration")
	private long duration; // in seconds

	@Column(name="StartTolerance")
	private long startTolerance; // in seconds

	public void verifyProperties() throws BadInputException {
		super.verifyProperties();
		if (super.getPublishDate().isAfter(getStartDate()))
			throw new BadInputException("Cannot create test: Start date is invalid - occurs before publish date");
		
		if (getDuration() <= 0)
			throw new BadInputException("Cannot create test: Test duration is invalid. The test duration is represented in seconds and must be a positive number.");
		
		if (getStartTolerance() <= 0 || getStartTolerance() > getDuration())
			throw new BadInputException("Cannot create test: Test start tolerance is invalid. The test start tolerance is represented in seconds and must be a positive number that is equal or less than the the test duration.");
	}
}