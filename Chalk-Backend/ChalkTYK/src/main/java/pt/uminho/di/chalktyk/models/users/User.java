package pt.uminho.di.chalktyk.models.users;

import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
	@Column(name="Name")
	private String name;

	@Column(name="PhotoPath")
	private String photoPath;

	@Column(name="Email")
	private String email;

	@Column(name="Description")
	private String description;

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
				", name='" + name + '\'' +
				", photoPath='" + photoPath + '\'' +
				", email='" + email + '\'' +
				", description='" + description + '\'' +
				//", subscription=" + subscription +
				'}';
	}
}