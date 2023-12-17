package pt.uminho.di.chalktyk.models.nonrelational.tests;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * type = 'deliver_date'
 */
@Document(collection = "tests")
@Getter
@Setter
@JsonTypeName("deliver_date")
public class DeliverDateTest extends Test {
	private LocalDateTime deliverDate;

	@Override
	public void verifyProperties() throws BadInputException {
		super.verifyInsertProperties();
		if (super.getPublishDate().isAfter(getDeliverDate()))
			throw new BadInputException("Cannot create test: Deliver date is invalid - occurs before publish date");
	}
}