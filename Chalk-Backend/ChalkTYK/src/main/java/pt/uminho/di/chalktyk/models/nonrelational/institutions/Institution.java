package pt.uminho.di.chalktyk.models.nonrelational.institutions;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.subscriptions.InstitutionSubscription;

@Document(collection = "institutions")
public class Institution {
	@Id
	private String name;
	private String description;
	private String logoPath;
	private InstitutionSubscription subscription;
}