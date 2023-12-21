package pt.uminho.di.chalktyk.models.subscriptions;

import java.time.LocalDateTime;

public class Subscription {
	private String id;
	private LocalDateTime nextPayment;
	private String planId;
	public SubscriptionPlan unnamed_SubscriptionPlan_;
	private Payment payment;
}