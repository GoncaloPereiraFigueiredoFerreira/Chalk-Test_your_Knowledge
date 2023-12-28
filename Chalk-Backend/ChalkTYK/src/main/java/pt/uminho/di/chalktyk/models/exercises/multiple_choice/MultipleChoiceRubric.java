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

import java.io.Serializable;
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
public class MultipleChoiceRubric extends ExerciseRubric{

	/*
        Let's consider an exercise where each question equals to 25 points.
        If the student fails the question, he loses the 25 points and also
         gets points deducted based on the penalty. If the penalty equals to 100
         (it's a percentage of each question points), then the additional
          points deducted for each question equals to:
               (question points) * penalty / 100
              = 25 * 100 / 100
              = 25.
         */

	@Column(name = "Penalty")
	private float penalty;

	@Type(JsonBinaryType.class)
	@Column(name = "JustificationsRubrics", columnDefinition = "jsonb")
	private Map<Integer, OpenAnswerRubric> justificationsRubrics;

	public MultipleChoiceRubric(String id, Float penalty, Map<Integer, OpenAnswerRubric> justificationsRubrics) {
		super(id);
		this.penalty = penalty;
		this.justificationsRubrics = justificationsRubrics;
	}

	@Override
	public void verifyProperties() throws BadInputException {
		if (penalty < 0)
			throw new BadInputException("Cannot create MultipleChoiceRubric: The penalty of a rubric cannot be negative.");
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
		if(!(Objects.equals(multipleChoiceRubric.getPenalty(), penalty)))
			return false;
		return true;
	}

	@Override
	public ExerciseRubric clone() {
		Map<Integer, OpenAnswerRubric> jrCloned = new HashMap<>();
		for(Map.Entry<Integer, OpenAnswerRubric> entry : justificationsRubrics.entrySet())
			jrCloned.put(entry.getKey(), entry.getValue().clone());
		return new MultipleChoiceRubric(getId(), penalty, jrCloned);
	}
}