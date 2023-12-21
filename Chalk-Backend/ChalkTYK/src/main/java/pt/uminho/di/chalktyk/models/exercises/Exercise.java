package pt.uminho.di.chalktyk.models.exercises;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "Type")
public abstract class Exercise {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name="Title")
	private String title;

	@Column(name = "Type", insertable = false, updatable = false, nullable = false)
	private String exerciseType;

	@Column(name="Points")
	private Float points;

	@Column(name = "Visibility")
	private Visibility visibility;

	@ManyToOne(targetEntity= Course.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="CourseID", referencedColumnName="ID") })
	private Course course;

	@ManyToOne(targetEntity= Specialist.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="SpecialistID", referencedColumnName="ID") })
	private Specialist specialist;

	@ManyToOne(targetEntity= Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private Institution institution;

	@ManyToMany(targetEntity= Tag.class, fetch = FetchType.LAZY)
	@JoinTable(name="Exercise_Tag", joinColumns={ @JoinColumn(name="ExerciseID") }, inverseJoinColumns={ @JoinColumn(name="TagID") })
	private Set<Tag> tags;

	public void verifyInsertProperties() throws BadInputException {
		if(points == null || points <= 0)
			throw new BadInputException("Cannot create exercise: The points of an exercise cannot be null, and must be positive.");
	}

	/**
	 * Updates the exerciseType attribute when the object is persisted.
	 */
	@PostPersist
	public void postPersist(){
		this.setExerciseType(this.getClass().getAnnotation(DiscriminatorValue.class).value());
	}
}