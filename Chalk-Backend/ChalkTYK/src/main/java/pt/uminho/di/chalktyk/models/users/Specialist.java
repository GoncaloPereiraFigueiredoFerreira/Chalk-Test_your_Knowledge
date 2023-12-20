package pt.uminho.di.chalktyk.models.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.institutions.Institution;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Specialist")
public class Specialist extends User {
	@Column(name="ID")
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@ManyToOne(targetEntity= Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private Institution institution;

    @Override
    public String checkSubscription() {
        // TODO - checkSubscription
        return null;
    }
}