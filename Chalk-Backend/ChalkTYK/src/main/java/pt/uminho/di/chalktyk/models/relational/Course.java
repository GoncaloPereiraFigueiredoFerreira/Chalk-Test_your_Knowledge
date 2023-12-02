package pt.uminho.di.chalktyk.models.relational;

import java.util.ArrayList;
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
public class Course implements Serializable {
	public Course() {
	}

	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne(targetEntity=Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID")})
	private Institution institution;

	@Column(name="Name", nullable=true, length=255)
	private String name;

	@ManyToMany(targetEntity=Specialist.class, fetch = FetchType.LAZY)
	@JoinTable(name="Specialist_Course", joinColumns={ @JoinColumn(name="CourseID") }, inverseJoinColumns={ @JoinColumn(name="SpecialistID") })
	private Set<Specialist> specialists = new java.util.HashSet<>();

	public void addSpecialist(Specialist s){
		if(specialists == null)
			specialists = new HashSet<>();
		specialists.add(s);
	}
}
