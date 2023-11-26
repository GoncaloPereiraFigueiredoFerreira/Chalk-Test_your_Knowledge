package pt.uminho.di.chalktyk.models.nonrelational.subscriptions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subscriptions")
@JsonTypeName("institution")
public class InstitutionSubscription extends Subscription {
	private Subscription studentsSub;
	private Subscription specialistsSub;
}