package pt.uminho.di.chalktyk.models.tests;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

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
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.users.Specialist;
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
	private Time duration;

	@Column(name="StartTolerance")
	private Time startTolerance;

	public LiveTest(String id, String title, String globalInstructions, Float globalPoints, String conclusion, LocalDateTime creationDate, LocalDateTime publishDate,
					Specialist specialist, Visibility visibility, Course course, Institution institution, Map<Integer,TestGroup> groups,
					LocalDateTime startDate, Time duration, Time startTolerance){
		super(id, title, globalInstructions, globalPoints, conclusion, creationDate, publishDate,
					specialist, visibility, course, institution, groups);
		this.setStartDate(startDate);
		this.setDuration(duration);
		this.setStartTolerance(startTolerance);
	}

	public void verifyProperties() throws BadInputException {
		super.verifyProperties();
		if (super.getPublishDate().isAfter(getStartDate()))
			throw new BadInputException("Cannot create test: Start date is invalid - occurs before publish date");

		// TODO: convert to duration
		/* 
		if (getDuration().isNegative() || getDuration().isZero())
			throw new BadInputException("Cannot create test: Test duration is invalid - non-positive duration");
		
		if (getStartTolerance().isNegative())
			throw new BadInputException("Cannot create test: Test start tolerance is negative");
		*/
	}
}