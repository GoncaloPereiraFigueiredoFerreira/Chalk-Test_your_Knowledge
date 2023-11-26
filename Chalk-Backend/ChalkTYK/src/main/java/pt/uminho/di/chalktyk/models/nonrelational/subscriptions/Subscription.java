package pt.uminho.di.chalktyk.models.nonrelational.subscriptions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.payments.Payment;

import java.time.LocalDateTime;

@Document(collection = "subscriptions")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("user")
public class Subscription {
	@Id
	private String id;
	private Payment payment;
	private LocalDateTime nextPayment;
	private String planId;
}