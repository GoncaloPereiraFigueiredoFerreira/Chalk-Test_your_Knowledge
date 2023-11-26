package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="ExerciseResolution")
public class ExerciseResolution implements Serializable {
	public ExerciseResolution() {
	}

	@Column(name="ID")
	@Id	
	private String id;
	
	@ManyToOne(targetEntity=TestResolution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TestResolutionID", referencedColumnName="ID") })
	private TestResolution testResolution;
	
	@ManyToOne(targetEntity=Student.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="StudentID", referencedColumnName="ID", nullable=false) })
	private Student student;
	
	@ManyToOne(targetEntity=Exercise.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="ExerciseID", referencedColumnName="ID", nullable=false) })
	private Exercise exercise;
}
