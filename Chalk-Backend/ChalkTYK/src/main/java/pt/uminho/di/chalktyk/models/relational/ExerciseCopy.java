package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="ExerciseCopy")
public class ExerciseCopy implements Serializable {
	public ExerciseCopy() {
	}
	
	@PrimaryKeyJoinColumn	
	@ManyToOne(targetEntity=Exercise.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="copyID", referencedColumnName="ID", nullable=false) })
	private Exercise copy;
	
	@Column(name="copyID", nullable=false, insertable=false, updatable=false)	
	@Id	
	private String copyId;

	@ManyToOne(targetEntity=Exercise.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="originalID", referencedColumnName="ID", nullable=false) })
	private Exercise original;

	@Column(name="originalID", nullable=false, insertable=false, updatable=false)
	private String originalId;
}
