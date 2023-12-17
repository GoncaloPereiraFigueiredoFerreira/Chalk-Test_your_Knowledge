package pt.uminho.di.chalktyk.models.relational;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Test")
public class TestSQL implements Serializable {
	@Column(name="ID")
	@Id	
	private String id;
	
	@ManyToOne(targetEntity= InstitutionSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private InstitutionSQL institution;
	
	@ManyToOne(targetEntity= CourseSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="CourseID", referencedColumnName="ID") })
	private CourseSQL course;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "visibility")
	private VisibilitySQL visibility;
	
	@ManyToOne(targetEntity= SpecialistSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="SpecialistID", referencedColumnName="ID", nullable=false) })
	private SpecialistSQL specialist;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="PublishDate")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime publishDate;
}
