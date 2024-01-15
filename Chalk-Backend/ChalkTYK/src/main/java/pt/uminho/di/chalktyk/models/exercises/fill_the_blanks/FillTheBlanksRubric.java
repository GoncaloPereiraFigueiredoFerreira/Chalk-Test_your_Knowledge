package pt.uminho.di.chalktyk.models.exercises.fill_the_blanks;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("FTB")
@JsonTypeName("FTB")
public class FillTheBlanksRubric extends ExerciseRubric {
	/*
	Let's consider an exercise where each filling equals to 25 points.
	If the student fails the filling, he loses the 25 points and also
	 gets points deducted based on the penalty. If the penalty equals to 100
	 (it's a percentage of each filling points), then the additional
	  points deducted for each filling equals to:
	  	 (filling points) * penalty / 100
	  	= 25 * 100 / 100
	  	= 25.
	 */
	private float penalty;

	public FillTheBlanksRubric(String id, Float penalty) {
		super(id);
		this.penalty = penalty;
	}

	@Override
	public void verifyProperties() throws BadInputException {
		if(penalty < 0)
			throw new BadInputException("Cannot create FillTheBlanksRubric: The penalty of a rubric cannot be negative.");
	}

	@Override
	public boolean equals(ExerciseRubric exerciseRubric) {
		if(!(exerciseRubric instanceof FillTheBlanksRubric fillTheBlanksRubric))
			return false;
		if(!(Objects.equals(fillTheBlanksRubric.getPenalty(), penalty)))
			return false;
		return true;
	}

	@Override
	public ExerciseRubric clone() {
		return new FillTheBlanksRubric(getId(), penalty);
	}
}