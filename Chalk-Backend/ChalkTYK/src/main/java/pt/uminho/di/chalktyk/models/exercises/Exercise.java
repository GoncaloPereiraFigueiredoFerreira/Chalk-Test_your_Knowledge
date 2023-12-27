package pt.uminho.di.chalktyk.models.exercises;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksExercise;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksOptionsExercise;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceExercise;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerExercise;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "Type")
@JsonSubTypes({
		@JsonSubTypes.Type(name = "MC", value = MultipleChoiceExercise.class),
		@JsonSubTypes.Type(name = "OA", value = OpenAnswerExercise.class),
		@JsonSubTypes.Type(name = "FTB", value = FillTheBlanksExercise.class),
		@JsonSubTypes.Type(name = "FTBO", value = FillTheBlanksOptionsExercise.class)
})
@NamedEntityGraph(
		name = "Exercise.NoRubricNoSolution",
		attributeNodes = {
				@NamedAttributeNode("course"),
				@NamedAttributeNode("specialist"),
				@NamedAttributeNode("institution"),
				@NamedAttributeNode("tags")
		}
)
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

	@Type(JsonBinaryType.class)
	@Column(name = "Statement", columnDefinition = "jsonb")
	private ExerciseStatement statement;

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

	@OneToOne(fetch = FetchType.LAZY, targetEntity = ExerciseSolution.class, orphanRemoval = true)
	@JoinColumn(name = "SolutionID", referencedColumnName = "ID")
	private ExerciseSolution solution;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = ExerciseRubric.class, orphanRemoval = true)
	@JoinColumn(name = "RubricID", referencedColumnName = "ID")
	private ExerciseRubric rubric;

	public void verifyInsertProperties() throws BadInputException {
		if(points == null || points <= 0)
			throw new BadInputException("Cannot create exercise: The points of an exercise cannot be null, and must be positive.");
		if(title == null || title.isEmpty())
			throw new BadInputException("Cannot create concrete exercise: A title of a exercise cannot be empty or null.");
		if(statement!=null)
			statement.verifyProperties();
	}

	public String getSpecialistId(){
		return specialist == null ? null : specialist.getId();
	}

	public String getCourseId(){
		return course == null ? null : course.getId();
	}

	public String getInstitutionId(){
		return institution == null ? null : institution.getName();
	}


	public String getRubricId(){
		return rubric == null ? null : rubric.getId();
	}

	public String getSolutionId(){
		return solution == null ? null : solution.getId();
	}

	/**
	 * Sets the id of the rubric, if rubric is not null.
	 * @param rubricId id that the rubric should have
	 */
	public void setRubricIdIfExists(String rubricId){
		if(rubric != null)
			rubric.setId(rubricId);
	}

	/**
	 * Sets the id of the solution, if solution is not null.
	 * @param solutionId id that the solution should have
	 */
	public void setSolutionIdIfExists(String solutionId){
		if(solution != null)
			solution.setId(solutionId);
	}

	/**
	 * Evaluates the resolution of an exercise.
	 *
	 * @param resolution resolution data that will be evaluated
	 * @param solution   solution of the exercise
	 * @param rubric     rubric of the exercise
	 * @return points to be attributed to the resolution
	 * @throws UnauthorizedException if the resolution cannot be evaluated automatically.
	 */
	public abstract ExerciseResolution automaticEvaluation(ExerciseResolution resolution, ExerciseSolution solution, ExerciseRubric rubric) throws UnauthorizedException;

	public abstract void verifyResolutionProperties(ExerciseResolutionData exerciseResolutionData) throws BadInputException;

	public abstract void verifyRubricProperties(ExerciseRubric rubric) throws BadInputException;

	/**
	 * Updates the exerciseType attribute when the object is persisted.
	 */
	@PostPersist
	public void postPersist(){
		this.setExerciseType(this.getClass().getAnnotation(DiscriminatorValue.class).value());
	}

	protected void _copyExerciseDataOnlyTo(Exercise exercise){
		assert exercise != null;
		exercise.setExerciseType(exerciseType);
		exercise.setTitle(title);
		exercise.setPoints(points);
		exercise.setStatement(statement.clone());
	}

	/**
	 * Copies exercise data to the instance given as argument.
	 * The exercise instance that calls this method needs to
	 * be of the same class as the instance given as argument.
	 * Subclasses should call the '_copyExerciseDataOnlyTo(Exercise exercise)' method from the super class.
	 * @param exercise exercise that will receive the data.
	 * @throws BadInputException If the exercise instance given as argument is not of the same class.
	 */
	public abstract void copyExerciseDataOnlyTo(Exercise exercise) throws BadInputException;

	/**
	 * Should clone everything that is related to the data of the exercise: title, statement, exercise type, points, and specific data of each exercise.
	 * Properties like identifier, associations with other entities, visibility, should be null, or empty.
	 * Subclasses should call the '_copyExerciseDataOnlyTo(Exercise exercise)' method from the super class.
	 * @return exercise of the same class, with data of the exercise cloned, ignoring metadata or association related information.
	 */
	public abstract Exercise cloneExerciseDataOnly();

	@Override
	public String toString() {
		return "Exercise{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", exerciseType='" + exerciseType + '\'' +
				", visibility=" + visibility +
				'}';
	}
}