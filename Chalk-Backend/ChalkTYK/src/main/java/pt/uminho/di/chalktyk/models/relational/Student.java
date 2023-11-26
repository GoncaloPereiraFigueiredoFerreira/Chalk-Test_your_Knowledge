package pt.uminho.di.chalktyk.models.relational;

import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Student")
public class Student implements Serializable {
	public Student() {
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
	
	@ManyToMany(targetEntity=Course.class, fetch = FetchType.LAZY)
	@JoinTable(name="Student_Course", joinColumns={ @JoinColumn(name="StudentID") }, inverseJoinColumns={ @JoinColumn(name="CourseID") })
	private java.util.Set<Course> courses = new java.util.HashSet<>();
}
