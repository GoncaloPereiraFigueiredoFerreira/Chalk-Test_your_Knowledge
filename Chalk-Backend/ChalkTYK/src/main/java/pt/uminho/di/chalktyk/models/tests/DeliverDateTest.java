package pt.uminho.di.chalktyk.models.tests;

import java.time.LocalDateTime;
import java.util.List;
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
 * type = 'deliver_date'
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("deliver_date")
@DiscriminatorValue("deliver_date")
public class DeliverDateTest extends Test {
	@Column(name="DeliverDate")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime deliverDate;

	public DeliverDateTest(String id, String title, String globalInstructions, Float globalPoints, String conclusion, LocalDateTime creationDate, LocalDateTime publishDate, Specialist specialist, Visibility visibility, Course course, Institution institution, Map<Integer,TestGroup> groups, LocalDateTime deliverDate) {
		super(id, title, globalInstructions, globalPoints, conclusion, creationDate, publishDate, specialist, visibility, course, institution, groups);
		this.deliverDate = deliverDate;
	}

	@Override
	public void verifyProperties() throws BadInputException {
		super.verifyProperties();
		if (super.getPublishDate().isAfter(getDeliverDate()))
			throw new BadInputException("Cannot create test: Deliver date is invalid - occurs before publish date");
	}
}