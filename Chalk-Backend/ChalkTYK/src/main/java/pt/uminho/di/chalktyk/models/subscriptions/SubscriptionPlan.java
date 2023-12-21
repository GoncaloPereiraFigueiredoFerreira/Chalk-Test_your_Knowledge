package pt.uminho.di.chalktyk.models.subscriptions;

import java.util.List;

public abstract class SubscriptionPlan {
	private String id;
	private String name;
	private List<String> targetAudience;
	private List<String> perks;
	private Float priceMonth;
	private boolean availability;
}