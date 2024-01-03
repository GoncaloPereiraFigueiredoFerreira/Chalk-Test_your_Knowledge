package pt.uminho.di.chalktyk.models.exercises;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import pt.uminho.di.chalktyk.models.exercises.chat.ChatExercise;
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
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(name = "MC", value = MultipleChoiceExercise.class),
		@JsonSubTypes.Type(name = "OA", value = OpenAnswerExercise.class),
		@JsonSubTypes.Type(name = "FTB", value = FillTheBlanksExercise.class),
		@JsonSubTypes.Type(name = "FTBO", value = FillTheBlanksOptionsExercise.class),
        @JsonSubTypes.Type(name = "CE", value = ChatExercise.class)
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
	private String exerciseType = getExerciseType();

	@Column(name = "Visibility")
	@Enumerated(value = EnumType.STRING)
	private Visibility visibility;

	@Type(JsonBinaryType.class)
	@Column(name = "Statement", columnDefinition = "jsonb")
	private ExerciseStatement statement;

	@JsonIgnore
	@ManyToOne(targetEntity= Course.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="CourseID", referencedColumnName="ID") })
	private Course course;

	@Column(name = "CourseID", insertable = false, updatable = false)
	@Setter(AccessLevel.NONE)
	private String courseId;

	@JsonIgnore
	@ManyToOne(targetEntity= Specialist.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="SpecialistID", referencedColumnName="ID") })
	private Specialist specialist;

	@Column(name = "SpecialistID", insertable = false, updatable = false)
	@Setter(AccessLevel.NONE)
	private String specialistId;

	@JsonIgnore
	@ManyToOne(targetEntity= Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private Institution institution;

	@Column(name = "InstitutionID", insertable = false, updatable = false)
	@Setter(AccessLevel.NONE)
	private String institutionId;

	@ManyToMany(targetEntity= Tag.class, fetch = FetchType.LAZY)
	@JoinTable(name="Exercise_Tag", joinColumns={ @JoinColumn(name="ExerciseID") }, inverseJoinColumns={ @JoinColumn(name="TagID") })
	private Set<Tag> tags;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, targetEntity = ExerciseSolution.class, orphanRemoval = true)
	@JoinColumn(name = "SolutionID", referencedColumnName = "ID")
	private ExerciseSolution solution;

	@Column(name = "SolutionID", insertable = false, updatable = false)
	@Setter(AccessLevel.NONE)
	private String solutionId;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, targetEntity = ExerciseRubric.class, orphanRemoval = true)
	@JoinColumn(name = "RubricID", referencedColumnName = "ID")
	private ExerciseRubric rubric;

	@Column(name = "RubricID", insertable = false, updatable = false)
	@Setter(AccessLevel.NONE)
	private String rubricId;

	public Exercise(String id, String title, Visibility visibility, ExerciseStatement statement, Course course, Specialist specialist, Institution institution, Set<Tag> tags, ExerciseSolution solution, ExerciseRubric rubric) {
		this.id = id;
		this.title = title;
		this.exerciseType = getExerciseType();
		this.visibility = visibility;
		this.statement = statement;
		setCourse(course);
		setSpecialist(specialist);
		setInstitution(institution);
		this.tags = tags;
		setSolution(solution);
		setRubric(rubric);
	}

	public void verifyInsertProperties() throws BadInputException {
		if(title == null || title.isEmpty())
			throw new BadInputException("Cannot create concrete exercise: A title of a exercise cannot be empty or null.");
		if(statement!=null)
			statement.verifyProperties();
	}

	public void setCourse(Course course) {
		this.course = course;
		this.courseId = course != null ? course.getId() : null;
	}

	public void setSpecialist(Specialist specialist) {
		this.specialist = specialist;
		this.specialistId = specialist != null ? specialist.getId() : null;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
		this.institutionId = institution != null ? institution.getName() : null;
	}

	public void setSolution(ExerciseSolution solution) {
		this.solution = solution;
		this.solutionId = solution != null ? solution.getId() : null;
	}

	public void setRubric(ExerciseRubric rubric) {
		this.rubric = rubric;
		this.rubricId = rubric != null ? rubric.getId() : null;
	}

	/**
	 * Sets the id of the rubric, if rubric is not null.
	 * @param rubricId id that the rubric should have
	 */
	@JsonIgnore
	public void setRubricIdIfExists(String rubricId){
		if(rubric != null)
			rubric.setId(rubricId);
	}

	/**
	 * Sets the id of the solution, if solution is not null.
	 * @param solutionId id that the solution should have
	 */
	@JsonIgnore
	public void setSolutionIdIfExists(String solutionId){
		if(solution != null)
			solution.setId(solutionId);
	}

	public void setExerciseType(String ignored) {
		this.exerciseType = getExerciseType();
	}

	/**
	 * Evaluates the resolution of an exercise.
	 *
	 * @param resolution resolution data that will be evaluated
	 * @param solution   solution of the exercise
	 * @param rubric     rubric of the exercise
	 * @return resolution with its points updated. The points can range from 0 (inclusive) to 100 (inclusive).
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

	/**
	 * Copies exercise data and its identifier to the instance given as argument.
	 * Exercise data refers to the data needed by a student to start solving the exercise
	 * ( visibility are examples of information that is not required).
	 * @param exercise exercise that will receive the data.
	 */
	protected void _copyExerciseDataOnlyTo(Exercise exercise){
		assert exercise != null;
		exercise.setId(id);
		exercise.setExerciseType(exerciseType);
		exercise.setTitle(title);
		exercise.setStatement(statement.clone());
	}

	/**
	 * Copies exercise data and its identifier to the instance given as argument.
	 * Exercise data refers to the data needed by a student to start solving the exercise
	 * ( visibility are examples of information that is not required).
	 * The exercise instance that calls this method needs to
	 * be of the same class as the instance given as argument.
	 * Subclasses should call the method '_copyExerciseDataOnlyTo(Exercise exercise)' from the super class.
	 * @param exercise exercise that will receive the data.
	 * @throws BadInputException If the exercise instance given as argument is not of the same class, or if it is null.
	 */
	public abstract void copyExerciseDataOnlyTo(Exercise exercise) throws BadInputException;

	/**
	 * Clones exercise data and its identifier.
	 * Exercise data refers to the data needed by a student to start solving the exercise
	 * ( visibility are example of information that is not required).
	 * Properties like associations with other entities,  visibility, should be null, or empty.
	 * Subclasses should call the '_copyExerciseDataOnlyTo(Exercise exercise)' method from the super class.
	 * @return exercise of the same class, with data of the exercise cloned, ignoring metadata or association related information.
	 */
	public abstract Exercise cloneExerciseDataOnly();

	/**
	 * Copies additional properties to the exercise instance given.
	 * Properties that are considered additional data are: visibility.
	 * @param exercise exercise that should get the additional data copied
	 */
	protected void _copyAdditionalPropertiesTo(Exercise exercise){
		exercise.setVisibility(visibility);
	}

	/**
	 * Copies exercise data, identifier and additional properties to the instance given as argument.
	 * Exercise data refers to the data needed by a student to start solving the exercise
	 * ( visibility are examples of information that is not required).
	 * Additional properties refers to everything that is not considered 'exercise data' and is not an association.
	 * The exercise instance that calls this method needs to
	 * be of the same class as the instance given as argument.
	 * Subclasses should call the method '_copyWithAdditionalDataTo(Exercise exercise)' from the super class.
	 * @param exercise exercise that will receive the data.
	 * @throws BadInputException If the exercise instance given as argument is not of the same class, or if it is null.
	 */
	public void copyWithAdditionalPropertiesTo(Exercise exercise) throws BadInputException{
		copyExerciseDataOnlyTo(exercise);
		_copyAdditionalPropertiesTo(exercise);
	}

	/**
	 * Clones exercise data, identifier and additional properties.
	 * Exercise data refers to the data needed by a student to start solving the exercise
	 * ( visibility are examples of information that is not required).
	 * Additional properties refers to everything that is not considered 'exercise data' and is not an association.
	 */
	public Exercise cloneWithAdditionalDataTo(){
		Exercise exercise = this.cloneExerciseDataOnly();
		_copyAdditionalPropertiesTo(exercise);
		return exercise;
	}

	@Override
	public String toString() {
		return "Exercise{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", exerciseType='" + exerciseType + '\'' +
				", visibility=" + visibility +
				'}';
	}

	public boolean equalsDataOnly(Object o){
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Exercise exercise = (Exercise) o;

		if (!Objects.equals(id, exercise.id)) return false;
		if (!Objects.equals(title, exercise.title)) return false;
		if (!Objects.equals(exerciseType, exercise.exerciseType))
			return false;
		return Objects.equals(statement, exercise.statement);
	}

	public boolean equalsWithoutAssociations(Object o) {
		if (!equalsDataOnly(o)) return false;
		Exercise exercise = (Exercise) o;
		if (visibility != exercise.visibility) return false;
        return Objects.equals(statement, exercise.statement);
    }
}
