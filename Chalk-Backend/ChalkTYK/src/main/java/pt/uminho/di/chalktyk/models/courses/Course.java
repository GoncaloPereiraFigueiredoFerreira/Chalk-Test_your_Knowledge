package pt.uminho.di.chalktyk.models.courses;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.users.Specialist;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Course")
public class Course {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name="Name", nullable=false, length=255)
	private String name;
	
	@Column(name="Description", nullable=true, length=1000)
	private String description;
	
	@ManyToMany(targetEntity= Specialist.class, fetch = FetchType.LAZY)
	@JoinTable(name="Specialist_Course", joinColumns={ @JoinColumn(name="CourseID") }, inverseJoinColumns={ @JoinColumn(name="SpecialistID") })
	private Set<Specialist> specialists = new HashSet<>();
	
	@ManyToOne(targetEntity= Institution.class, fetch=FetchType.LAZY)
	@JoinColumn(name="InstitutionID", referencedColumnName="ID")
	private Institution institution;

	public void addSpecialist(Specialist s){
		if(specialists == null)
			specialists = new HashSet<>();
		specialists.add(s);
	}
}