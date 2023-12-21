package pt.uminho.di.chalktyk.models.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.institutions.Institution;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="InstitutionManager")
@DiscriminatorValue("InstitutionManager")
public class InstitutionManager extends User {
	@ManyToOne(targetEntity= Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private Institution institution;

	@Override
	public String checkSubscription() {
		// TODO - checkSubscription
		return null;
	}
}