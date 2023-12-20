package pt.uminho.di.chalktyk.models.tests;

import java.time.LocalDateTime;

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
 * type = 'deliver_date'
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("DeliverDate")
public class DeliverDateTest extends Test {
	@Column(name="DeliverDate")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime deliverDate;

	@Override
	public void verifyProperties() throws BadInputException {
		super.verifyProperties();
		if (super.getPublishDate().isAfter(getDeliverDate()))
			throw new BadInputException("Cannot create test: Deliver date is invalid - occurs before publish date");
	}
}