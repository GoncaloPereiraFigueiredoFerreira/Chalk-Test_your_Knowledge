package pt.uminho.di.chalktyk.models.institutions;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Institution")
public class Institution {
	@Id
	@Column(name="ID", unique=true)
	private String name;

	@Column(name="Description")
	private String description;

	@Column(name="LogoPath")
	private String logoPath;

	// TODO: there's no subscription
	@Override
	public String toString() {
		return "Institution{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", logoPath='" + logoPath + '\'' +
				//", subscription=" + subscription +
				'}';
	}

	public void verifyProperties() throws BadInputException {
		if (name == null || name.isEmpty())
			throw new BadInputException("Can't create institution: institution's identifier is null/empty.");
	}

    public Institution clone() {
		return new Institution(name,description,logoPath);
    }
}