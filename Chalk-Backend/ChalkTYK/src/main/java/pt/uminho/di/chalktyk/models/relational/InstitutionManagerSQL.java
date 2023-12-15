package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="InstitutionManager")
public class InstitutionManagerSQL implements Serializable {
	public InstitutionManagerSQL() {
	}

	@Column(name="ID")
	@Id	
	private String id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Email")
	private String email;

	@ManyToOne(targetEntity= InstitutionSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private InstitutionSQL institution;
}
