package pt.uminho.di.chalktyk.models.nonrelational.users;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.subscriptions.Subscription;

@Document(collection = "users")
@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class User {
	@Id
	private String id;
	private String name;
	private String photoPath;
	private String email;
	private String description;
	private Subscription subscription;
}