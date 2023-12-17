package pt.uminho.di.chalktyk.models.nonrelational.users;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.subscriptions.Subscription;

import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
@JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class User {
	@Id
	private String id;
	@Indexed(unique = true)
	private String name;
	private String photoPath;
	private String email;
	private String description;

	@DBRef(lazy = true)
	private Subscription subscription;

	/**
	 * Checks basic user properties.
	 * @return 'null' if all properties are valid, or a string mentioning the criteria that was not passed.
	 */
	public String checkInsertProperties(){
		if(name == null || name.isEmpty())
			return "Not a valid name.";

		String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		boolean validEmail = Pattern.compile(regexPattern)
									.matcher(this.email)
									.matches();
		if (!validEmail)
			return "Not a valid email format.";

        return null;
    }

	/**
	 * Check if the subscription is valid.
	 * @return 'null' if the subscription is valid, or a string mentioning the error.
	 */
	protected abstract String checkSubscription();

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", photoPath='" + photoPath + '\'' +
				", email='" + email + '\'' +
				", description='" + description + '\'' +
				", subscription=" + subscription +
				'}';
	}
}