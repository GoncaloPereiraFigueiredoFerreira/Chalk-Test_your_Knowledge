package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="TestResolution")
public class TestResolutionSQL implements Serializable {
	public TestResolutionSQL() {
	}

	@Column(name="ID")
	@Id	
	private String id;
	
	@ManyToOne(targetEntity= TestSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TestID", referencedColumnName="ID", nullable=false) })
	private TestSQL test;
	
	@ManyToOne(targetEntity= StudentSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="StudentID", referencedColumnName="ID", nullable=false) })
	private StudentSQL student;
}
