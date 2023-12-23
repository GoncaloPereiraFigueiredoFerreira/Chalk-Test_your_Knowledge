package pt.uminho.di.chalktyk.models.exercises.open_answer;

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
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("OA")
@JsonTypeName("OA")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OpenAnswerRubric extends ExerciseRubric  implements Serializable {
	@Type(JsonBinaryType.class)
	@Column(name = "Criteria", columnDefinition = "jsonb")
	private List<OACriterion> criteria;

	public OpenAnswerRubric(String id, List<OACriterion> criteria) {
		super(id);
		this.criteria = criteria;
	}

	@Override
	public void verifyProperties() throws BadInputException {
		if(criteria == null || criteria.isEmpty())
			throw new BadInputException("Cannot create OpenAnswerRubric: The rubric list of a open answer exercise cannot be null or empty.");
		else {
			for (OACriterion criterion:  criteria){
				criterion.verifyProperties();
			}
		}
	}

	@Override
	public boolean equals(ExerciseRubric exerciseRubric) {
		if(!(exerciseRubric instanceof OpenAnswerRubric openAnswerRubric))
			return false;
		if(openAnswerRubric.getCriteria().size()!=criteria.size())
			return false;
		for (int i=0;i<criteria.size();i++){
			if(!criteria.get(i).equals(openAnswerRubric.getCriteria().get(i)))
				return false;
		}
		return true;
	}

	public OpenAnswerRubric clone(){
		return new OpenAnswerRubric(getId(), criteria.stream().map(OACriterion::clone).toList());
    }
}