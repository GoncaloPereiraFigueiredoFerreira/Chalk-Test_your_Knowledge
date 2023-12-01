package pt.uminho.di.chalktyk.models.nonrelational.users;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
@JsonTypeName("institution_manager")
@TypeAlias("institution_manager")
public class InstitutionManager extends User {
	private String institutionId;

	/**
	 * Check if the subscription is valid.
	 *
	 * @return 'null' if the subscription is valid, or a string mentioning the error.
	 */
	@Override
	public String checkSubscription() {
		// TODO - checkSubscription
		return null;
	}
}