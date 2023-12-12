package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="ExerciseResolution")
public class ExerciseResolutionSQL implements Serializable {
	public ExerciseResolutionSQL() {
	}

	@Column(name="ID")
	@Id	
	private String id;
	
	@ManyToOne(targetEntity= TestResolutionSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TestResolutionID", referencedColumnName="ID") })
	private TestResolutionSQL testResolution;
	
	@ManyToOne(targetEntity= StudentSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="StudentID", referencedColumnName="ID", nullable=false) })
	private StudentSQL student;
	
	@ManyToOne(targetEntity= ExerciseSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="ExerciseID", referencedColumnName="ID", nullable=false) })
	private ExerciseSQL exercise;
}
