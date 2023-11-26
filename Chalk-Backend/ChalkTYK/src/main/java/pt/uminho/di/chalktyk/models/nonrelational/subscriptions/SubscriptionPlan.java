package pt.uminho.di.chalktyk.models.nonrelational.subscriptions;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "subscription_plans")
public abstract class SubscriptionPlan {
	@Id
	private String id;
	private String name;
	private List<String> targetAudience;
	private List<String> perks;
	private Float priceMonth;
	private boolean availability;
}