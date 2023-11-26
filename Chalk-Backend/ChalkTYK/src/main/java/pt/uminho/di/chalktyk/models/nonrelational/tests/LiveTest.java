package pt.uminho.di.chalktyk.models.nonrelational.tests;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * type = 'live'
 */
@Document(collection = "tests")
@JsonTypeName("live")
public class LiveTest extends Test {
	private LocalDateTime startDate;
	private Duration duration;
	private Duration startTolerance;

}