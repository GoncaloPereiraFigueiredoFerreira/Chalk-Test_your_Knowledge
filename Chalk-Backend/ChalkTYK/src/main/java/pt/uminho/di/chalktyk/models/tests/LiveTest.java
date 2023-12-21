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
	@Temporal(TemporalType.TIMESTAMP)
	private Duration duration;

	@Column(name="StartTolerance")
	@Temporal(TemporalType.TIMESTAMP)
	private Duration startTolerance;

	public void verifyProperties() throws BadInputException {
		super.verifyProperties();
		if (super.getPublishDate().isAfter(getStartDate()))
			throw new BadInputException("Cannot create test: Start date is invalid - occurs before publish date");
		
		if (getDuration().isNegative() || getDuration().isZero())
			throw new BadInputException("Cannot create test: Test duration is invalid - non-positive duration");
		
		if (getStartTolerance().isNegative())
			throw new BadInputException("Cannot create test: Test start tolerance is negative");
	}
}