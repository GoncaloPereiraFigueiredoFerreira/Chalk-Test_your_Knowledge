package pt.uminho.di.chalktyk.models.nonrelational.tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * type = 'deliver_date'
 */
@Document(collection = "tests")
@JsonTypeName("deliver_date")
public class DeliverDateTest extends Test {
	private LocalDateTime deliverDate;
}