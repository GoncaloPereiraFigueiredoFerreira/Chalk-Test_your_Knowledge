package pt.uminho.di.chalktyk.models.relational;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Course")
public class CourseSQL implements Serializable {
	public CourseSQL() {
	}

	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne(targetEntity= InstitutionSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID")})
	private InstitutionSQL institution;

	@Column(name="Name", nullable=true, length=255)
	private String name;

	@ManyToMany(targetEntity= SpecialistSQL.class, fetch = FetchType.LAZY)
	@JoinTable(name="Specialist_Course", joinColumns={ @JoinColumn(name="CourseID") }, inverseJoinColumns={ @JoinColumn(name="SpecialistID") })
	private Set<SpecialistSQL> specialists = new java.util.HashSet<>();

	public void addSpecialist(SpecialistSQL s){
		if(specialists == null)
			specialists = new HashSet<>();
		specialists.add(s);
	}
}
