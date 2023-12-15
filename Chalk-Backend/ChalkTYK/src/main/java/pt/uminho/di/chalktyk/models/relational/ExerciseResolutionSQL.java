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

	// TODO - remover esta ligacao quando estiver certo que nao é usada
	@ManyToOne(targetEntity= TestResolutionSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TestResolutionID", referencedColumnName="ID") })
	private TestResolutionSQL testResolution;
	
	@ManyToOne(targetEntity= StudentSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="StudentID", referencedColumnName="ID", nullable=false) })
	private StudentSQL student;
	
	@ManyToOne(targetEntity= ExerciseSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="ExerciseID", referencedColumnName="ID", nullable=false) })
	private ExerciseSQL exercise;

	@Column(name = "SubmissionNr")
	private int submissionNr;

	public ExerciseResolutionSQL(String id, StudentSQL student, ExerciseSQL exercise, int submissionNr) {
		this.id = id;
		this.student = student;
		this.exercise = exercise;
		this.submissionNr = submissionNr;
		this.testResolution = null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ExerciseResolutionSQL{");
        sb.append("id='").append(id).append('\'');
		String testResolutionId = testResolution != null ? testResolution.getId() : null;
		sb.append(", testResolutionId='").append(testResolutionId).append('\'');
		String studentId = student != null ? student.getId() : null;
		sb.append(", studentId='").append(studentId).append('\'');
		String exerciseId = exercise != null ? exercise.getId() : null;
		sb.append(", exerciseId='").append(exerciseId).append('\'');
		sb.append("}");
		return sb.toString();
	}
}
