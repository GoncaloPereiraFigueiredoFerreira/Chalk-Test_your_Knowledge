package pt.uminho.di.chalktyk.models.exercises.multiple_choice;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@DiscriminatorValue("MC")
@JsonTypeName("MC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MultipleChoiceRubric extends ExerciseRubric {
	@Column(name = "ChoicePoints")
	private Float choicePoints;

	@Column(name = "Penalty")
	private Float penalty;

	@Type(JsonBinaryType.class)
	@Column(name = "JustificationsRubrics", columnDefinition = "jsonb")
	private Map<Integer, OpenAnswerRubric> justificationsRubrics;

	public MultipleChoiceRubric(String id, Float choicePoints, Float penalty, Map<Integer, OpenAnswerRubric> justificationsRubrics) {
		super(id);
		this.choicePoints = choicePoints;
		this.penalty = penalty;
		this.justificationsRubrics = justificationsRubrics;
	}

	@Override
	public void verifyProperties() throws BadInputException {
		if (penalty == null || choicePoints == null || penalty < 0 || choicePoints < 0)
			throw new BadInputException("Cannot create MultipleChoiceRubric: The points or penalty of a rubric cannot be null or negative.");
		if(justificationsRubrics!=null){
			for (OpenAnswerRubric openAnswerRubric : justificationsRubrics.values())
				openAnswerRubric.verifyProperties();
		}
	}

	@Override
	public boolean equals(ExerciseRubric exerciseRubric) {
		if(!(exerciseRubric instanceof MultipleChoiceRubric multipleChoiceRubric))
			return false;
		if(multipleChoiceRubric.getJustificationsRubrics().size()!=justificationsRubrics.size())
			return false;
		for (int i=0;i<justificationsRubrics.size();i++){
			if(!justificationsRubrics.get(i).equals(multipleChoiceRubric.getJustificationsRubrics().get(i)))
				return false;
		}
		if(!(Objects.equals(multipleChoiceRubric.getChoicePoints(), choicePoints)))
			return false;
		if(!(Objects.equals(multipleChoiceRubric.getPenalty(), penalty)))
			return false;
		return true;
	}

	@Override
	public ExerciseRubric clone() {
		Map<Integer, OpenAnswerRubric> jrCloned = new HashMap<>();
		for(Map.Entry<Integer, OpenAnswerRubric> entry : justificationsRubrics.entrySet())
			jrCloned.put(entry.getKey(), entry.getValue().clone());
		return new MultipleChoiceRubric(getId(), choicePoints, penalty, jrCloned);
	}

	public float getMaxPointsSum() {
		return choicePoints * justificationsRubrics.size();
	}
}