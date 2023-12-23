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
	private Float fillingPoints;
	private Float penalty;

	public FillTheBlanksRubric(String id, Float fillingPoints, Float penalty) {
		super(id);
		this.fillingPoints = fillingPoints;
		this.penalty = penalty;
	}

	@Override
	public void verifyProperties() throws BadInputException {
		if(penalty == null || fillingPoints == null || penalty < 0 || fillingPoints < 0)
			throw new BadInputException("Cannot create FillTheBlanksRubric: The points or penalty of a rubric cannot be null or negative.");
	}

	@Override
	public boolean equals(ExerciseRubric exerciseRubric) {
		if(!(exerciseRubric instanceof FillTheBlanksRubric fillTheBlanksRubric))
			return false;
		if(!(Objects.equals(fillTheBlanksRubric.getFillingPoints(), fillingPoints)))
			return false;
		if(!(Objects.equals(fillTheBlanksRubric.getPenalty(), penalty)))
			return false;
		return true;
	}

	@Override
	public ExerciseRubric clone() {
		return new FillTheBlanksRubric(getId(), fillingPoints, penalty);
	}
}