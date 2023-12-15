package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Specialist")
public class SpecialistSQL implements Serializable {
	public SpecialistSQL() {
	}
	
	@Column(name="ID")
	@Id	
	private String id;
	
	@ManyToOne(targetEntity= InstitutionSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private InstitutionSQL institution;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Email")
	private String email;
}
