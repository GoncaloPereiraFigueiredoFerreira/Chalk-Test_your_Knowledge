package pt.uminho.di.chalktyk.models.relational;

import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="TestResolution")
public class TestResolution implements Serializable {
	public TestResolution() {
	}

	@Column(name="ID")
	@Id	
	private String id;
	
	@ManyToOne(targetEntity=Test.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TestID", referencedColumnName="ID", nullable=false) })
	private Test test;
	
	@ManyToOne(targetEntity=Student.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="StudentID", referencedColumnName="ID", nullable=false) })
	private Student student;
}
