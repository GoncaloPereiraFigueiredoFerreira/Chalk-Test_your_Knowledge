package pt.uminho.di.chalktyk.models.relational;

import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Specialist")
public class Specialist implements Serializable {
	public Specialist() {
	}
	
	@Column(name="ID")
	@Id	
	private String id;
	
	@ManyToOne(targetEntity=Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private Institution institution;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Email")
	private String email;
}
