package pt.uminho.di.chalktyk.models.relational;

import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="InstitutionManager")
public class InstitutionManager implements Serializable {
	public InstitutionManager() {
	}

	@Column(name="ID")
	@Id	
	private String id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Email")
	private String email;

	@ManyToOne(targetEntity=Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private Institution institution;
}
