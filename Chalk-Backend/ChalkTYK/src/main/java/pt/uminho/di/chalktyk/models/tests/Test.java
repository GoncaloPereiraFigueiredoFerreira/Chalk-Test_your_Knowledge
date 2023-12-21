package pt.uminho.di.chalktyk.models.tests;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.annotations.Type;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.courses.Course;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.hibernate.type.descriptor.jdbc.JsonJdbcType;

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
public class Test {
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
	private Map<Integer,TestGroup> groups;

	public void verifyProperties() throws BadInputException {
        // check title
		if (title == null || title.isEmpty())
			throw new BadInputException("Cannot create test: A title of a test cannot be empty or null.");

		// check if creation date is before publish date
		if (creationDate.isAfter(publishDate))
            throw new BadInputException("Cannot create test: Publish date occurs before creation.");

		// check publish date
		if (!publishDate.isAfter(LocalDateTime.of(2023, 12, 10, 0, 0)))
            throw new BadInputException("Cannot create test: Publish date is outdated.");

		// check global points
		if (globalPoints < 0)
			throw new BadInputException("Cannot create test: Global points can't be negative.");

		if (groups != null) {
			for (TestGroup tg : groups.values()) {
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

	public Test clone() {
		Test duplicatedTest = new Test();
		duplicatedTest.setTitle(this.title);
		duplicatedTest.setGlobalInstructions(this.globalInstructions);
		duplicatedTest.setGlobalPoints(this.globalPoints);
		duplicatedTest.setConclusion(this.conclusion);
		duplicatedTest.setCreationDate(this.creationDate);
		duplicatedTest.setPublishDate(this.publishDate);
		duplicatedTest.setSpecialist(this.specialist.clone());
		duplicatedTest.setVisibility(this.visibility);
		duplicatedTest.setCourse(this.course.clone());
		duplicatedTest.setInstitution(this.institution.clone());

		// Duplicate groups if present
		if (this.groups != null) {
			Map<Integer, TestGroup> duplicatedGroups = new HashMap<>();
			for (Map.Entry<Integer, TestGroup> entry : this.groups.entrySet()) {
				duplicatedGroups.put(entry.getKey(), entry.getValue().clone());
			}
			duplicatedTest.setGroups(duplicatedGroups);
		}
		return duplicatedTest;
	}

	public Map<Integer, TestResolutionGroup> createEmptyResolutionGroups(){
		Map<Integer, TestResolutionGroup> resolutionGroups = new HashMap<>();
		if (groups != null){
			for(Map.Entry<Integer,TestGroup> entryTG: groups.entrySet()){
				Map<Integer, Pair<String, String>> resolutionGroupAnswers = entryTG.getValue().getExercises()
						.entrySet()
						.stream()
						.collect(Collectors.toMap(
								Map.Entry::getKey,
								val -> {
									int randomIndex = new Random().nextInt(val.getValue().size());
									return new MutablePair<>(val.getValue().get(randomIndex), null);
								}
						));
				resolutionGroups.put(entryTG.getKey(),new TestResolutionGroup(null,resolutionGroupAnswers));
			}
		}
		return resolutionGroups;
	}
}