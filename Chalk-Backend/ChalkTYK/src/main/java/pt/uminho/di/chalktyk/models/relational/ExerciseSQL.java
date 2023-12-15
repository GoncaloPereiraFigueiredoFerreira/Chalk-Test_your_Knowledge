
package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Exercise")
public class ExerciseSQL implements Serializable {
	public ExerciseSQL() {
	}

	public ExerciseSQL(String id, InstitutionSQL institution, SpecialistSQL specialist, CourseSQL course, String title, String exerciseType, VisibilitySQL visibility, int nrCopies, Set<TagSQL> tags) {
		this.id = id;
		this.institution = institution;
		this.specialist = specialist;
		this.course = course;
		this.title = title;
		this.exerciseType = exerciseType;
		this.visibility = visibility;
		this.nrCopies = nrCopies;
		this.tags = tags;
	}

	@Column(name="ID")
	@Id	
	private String id;

	// TODO - remover esta ligacao quando estiver certo que nao é usada
	@ManyToOne(targetEntity= TestSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TestID", referencedColumnName="ID")})
	private TestSQL test;
	
	@ManyToOne(targetEntity= InstitutionSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private InstitutionSQL institution;
	
	@ManyToOne(targetEntity= SpecialistSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="SpecialistID", referencedColumnName="ID") })
	private SpecialistSQL specialist;
	
	@ManyToOne(targetEntity= CourseSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="CourseID", referencedColumnName="ID") })
	private CourseSQL course;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="ExerciseType")
	private String exerciseType;

	@Column(name = "visibility")
	private VisibilitySQL visibility;

	@Column(name="NrCopies", nullable=false)
	private int nrCopies;
	
	@ManyToMany(targetEntity= TagSQL.class, fetch = FetchType.LAZY)
	@JoinTable(name="Exercise_Tag", joinColumns={ @JoinColumn(name="ExerciseID") }, inverseJoinColumns={ @JoinColumn(name="TagID") })
	private java.util.Set<TagSQL> tags = new java.util.HashSet<>();

	public void increaseNrCopies(){
		nrCopies++;
	}

	public void decreaseNrCopies(){
		nrCopies--;
	}

	public ExerciseSQL createShallow(String newId, InstitutionSQL institution, SpecialistSQL specialist, CourseSQL course){
		return new ExerciseSQL(newId,null,institution,specialist,course,title,exerciseType,visibility,0,tags);
	}
}
