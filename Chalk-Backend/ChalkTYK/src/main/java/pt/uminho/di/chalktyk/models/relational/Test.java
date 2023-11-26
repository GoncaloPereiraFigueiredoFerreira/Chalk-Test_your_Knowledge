package pt.uminho.di.chalktyk.models.relational;

import java.time.LocalDateTime;
import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Test")
public class Test implements Serializable {
	public Test() {
	}

	@Column(name="ID")
	@Id	
	private String id;
	
	@ManyToOne(targetEntity=Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private Institution institution;
	
	@ManyToOne(targetEntity=Course.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="CourseID", referencedColumnName="ID", nullable=false) })
	private Course course;
	
	@ManyToOne(targetEntity=Visibility.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="VisibilityID", referencedColumnName="ID", nullable=false) })
	private Visibility visibility;
	
	@ManyToOne(targetEntity=Specialist.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="SpecialistID", referencedColumnName="ID", nullable=false) })
	private Specialist specialist;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="PublishDate")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime publishDate;
}
