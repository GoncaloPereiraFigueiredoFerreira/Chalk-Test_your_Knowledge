package pt.uminho.di.chalktyk.models.tests;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.tests.TestExercise.TestExercise;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.courses.Course;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Test")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("basic")
@JsonTypeName("basic")
public class Test implements Serializable {
	@Column(name="ID")
	@Id	
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name="Title")
	private String title;

	@Column(name="GlobalInstructions")
	private String globalInstructions;

	@Column(name="Points")
	private Float globalPoints;

	@Column(name="Conclusion")
	private String conclusion;

	@Column(name="CreationDate")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime creationDate;

	@Column(name="PublishDate")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime publishDate;

	@ManyToOne(targetEntity= Specialist.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="SpecialistID", referencedColumnName="ID", nullable=false) })
	private Specialist specialist;

	@Enumerated(EnumType.STRING)
	@Column(name = "Visibility")
	private Visibility visibility;

	@ManyToOne(targetEntity= Course.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="CourseID", referencedColumnName="ID") })
	private Course course;

	@ManyToOne(targetEntity= Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private Institution institution;

	@Type(JsonBinaryType.class)
    @Column(name = "Groups", columnDefinition = "jsonb")
	private List<TestGroup> groups;

	/**
	 * Calculates and updates global and group points.
	 * @throws BadInputException if the points of an exercise are not a positive number.
	 */
	public void calculatePoints() throws BadInputException {
		globalPoints = 0.0f;
		for (TestGroup group : groups)
			globalPoints += group.calculateGroupPoints();
	}

	public void verifyProperties() throws BadInputException {
        // check title
		if (title == null || title.isEmpty())
			throw new BadInputException("Cannot create test: A title of a test cannot be empty or null");

		if (publishDate != null){
			// check if creation date is before publish date
			if (publishDate.isBefore(creationDate))
        	    throw new BadInputException("Cannot create test: Publish date occurs before creation");
			
			// check publish date
			if (!publishDate.isAfter(LocalDateTime.of(2023, 12, 10, 0, 0)))
        	    throw new BadInputException("Cannot create test: Publish date is outdated");
		}

		// check global points
		if (globalPoints < 0)
			throw new BadInputException("Cannot create test: Global points must be non-negative");

		if (groups != null) {
			for (TestGroup tg : groups) {
				tg.verifyProperties();
			}
		}
    }

	public String getSpecialistId(){
		if(specialist==null)
			return null;
		else return specialist.getId();
	}

	public String getCourseId(){
		if(course==null)
			return null;
		else return course.getId();
	}

	public String getInstitutionId(){
		if(institution==null)
			return null;
		else return institution.getName();
	}

	// TODO - ver onde é que o Luis usou este metodo porque nao é suposto fazer deep clone das associacoes
	/**
	 * Clones everything except associations.
	 * @return cloned test, or 'null' if any property of the test is not valid.
	 */
	public Test cloneAllButAssociations() {
		Test duplicatedTest = new Test();
		duplicatedTest.setTitle(this.title);
		duplicatedTest.setGlobalInstructions(this.globalInstructions);
		try { calculatePoints();
		} catch (BadInputException e) { return null; }
		duplicatedTest.setGlobalPoints(this.globalPoints);
		duplicatedTest.setConclusion(this.conclusion);
		duplicatedTest.setCreationDate(this.creationDate);
		duplicatedTest.setPublishDate(this.publishDate);
		duplicatedTest.setVisibility(this.visibility);

		// Duplicate groups if present
		if (this.groups != null) {
			List<TestGroup> duplicatedGroups = new ArrayList<>();
			for (TestGroup entry : this.groups) {
				duplicatedGroups.add(entry.clone());
			}
			duplicatedTest.setGroups(duplicatedGroups);
		}
		return duplicatedTest;
	}

	public List<TestResolutionGroup> createEmptyResolutionGroups(){
		List<TestResolutionGroup> resolutionGroups = new ArrayList<>();

		if (groups != null){
			for(TestGroup entryTG: groups){
				Map<String, String> resolutionGroupAnswers = new HashMap<>();

				for(TestExercise entry : entryTG.getExercises()){
					assert entry != null; // exercise cannot be empty or null
					String exerciseId = entry.getId();
					assert exerciseId != null; // exercise id cannot be null

					resolutionGroupAnswers.put(exerciseId, "");
				}

				resolutionGroups.add(new TestResolutionGroup(null, resolutionGroupAnswers));
			}
		}

		return resolutionGroups;
	}
}