package pt.uminho.di.chalktyk.models.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.institutions.Institution;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Specialist")
@DiscriminatorValue("Specialist")
public class Specialist extends User {
    public Specialist(String id, String name, String photoPath, String email, String description) {
        super(id, name, photoPath, email, description);
    }

    public Specialist(String id, String name, String photoPath, String email, String description, Institution institution) {
        super(id, name, photoPath, email, description);
        this.institution = institution;
    }

    @ManyToOne(targetEntity= Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private Institution institution;

    @Override
    public String checkSubscription() {
        // TODO - checkSubscription
        return null;
    }
}