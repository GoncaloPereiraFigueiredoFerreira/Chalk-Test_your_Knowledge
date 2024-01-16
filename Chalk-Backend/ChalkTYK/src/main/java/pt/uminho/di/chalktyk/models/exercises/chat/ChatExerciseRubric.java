package pt.uminho.di.chalktyk.models.exercises.chat;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceRubric;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.List;

/**
 * ChatExerciseRubric
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("CE")
@JsonTypeName("CE")
public class ChatExerciseRubric extends OpenAnswerRubric {
    public ChatExerciseRubric(String id,  List<OACriterion> criteria) {
        super(id,criteria);
    }


    @Override
    public boolean equals(ExerciseRubric exerciseRubric) {
        if(!(exerciseRubric instanceof ChatExerciseRubric chatExerciseRubric))
            return false;
        return super.equals(chatExerciseRubric);
    }
    
    @Override
    public ChatExerciseRubric clone() {
        return new ChatExerciseRubric(getId(), getCriteria());
    }
}
