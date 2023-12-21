package pt.uminho.di.chalktyk.models.exercises;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.users.Specialist;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonTypeName("Shallow")
@DiscriminatorValue("Shallow")
public class ShallowExercise extends Exercise {
	@ManyToOne(targetEntity = Exercise.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "OrigID", referencedColumnName = "ID")
	private Exercise originalExercise;

	public ShallowExercise(String id, String title, String exerciseType, Float points, Visibility visibility, Course course, Specialist specialist, Institution institution, Set<Tag> tags, Exercise originalExercise) {
		super(id, title, exerciseType, points, visibility, course, specialist, institution, tags);
		this.originalExercise = originalExercise;
	}

	public String getOriginalExerciseId(){
		return originalExercise != null ? originalExercise.getId() : null;
	}
}