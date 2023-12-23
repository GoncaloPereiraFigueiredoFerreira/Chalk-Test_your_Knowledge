package pt.uminho.di.chalktyk.models.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Specialist")
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

    public Specialist clone() {
        return new Specialist(getId(),getName(),getPhotoPath(),getEmail(),getDescription(),institution.clone());
    }

    /**
     * Creates a specialist with id, where the rest of the parameters are null.
     */
    public Specialist(String id){
        super(id,null,null,null,null);
        this.institution = null;
    }
}