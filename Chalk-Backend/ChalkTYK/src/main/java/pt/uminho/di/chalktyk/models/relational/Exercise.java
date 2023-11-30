
package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Exercise")
public class Exercise implements Serializable {
	public Exercise() {
	}
	
	@Column(name="ID")
	@Id	
	private String id;
	
	@ManyToOne(targetEntity=Test.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TestID", referencedColumnName="ID")})
	private Test test;
	
	@ManyToOne(targetEntity=Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private Institution institution;
	
	@ManyToOne(targetEntity=Specialist.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="SpecialistID", referencedColumnName="ID") })
	private Specialist specialist;
	
	@ManyToOne(targetEntity=Course.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="CourseID", referencedColumnName="ID") })
	private Course course;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="ExerciseType")
	private String exerciseType;

	@Column(name = "visibility")
	private Visibility visibility;

	@Column(name="NrCopies", nullable=false)
	private int nrCopies;
	
	@ManyToMany(targetEntity=Tag.class, fetch = FetchType.LAZY)
	@JoinTable(name="Exercise_Tag", joinColumns={ @JoinColumn(name="ExerciseID") }, inverseJoinColumns={ @JoinColumn(name="TagID") })
	private java.util.Set<Tag> tags = new java.util.HashSet<>();
}
