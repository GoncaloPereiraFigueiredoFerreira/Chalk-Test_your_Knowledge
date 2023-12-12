package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Student")
public class StudentSQL implements Serializable {
	public StudentSQL() {
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
	
	@ManyToMany(targetEntity= CourseSQL.class, fetch = FetchType.LAZY)
	@JoinTable(name="Student_Course", joinColumns={ @JoinColumn(name="StudentID") }, inverseJoinColumns={ @JoinColumn(name="CourseID") })
	private java.util.Set<CourseSQL> courses = new java.util.HashSet<>();
}
