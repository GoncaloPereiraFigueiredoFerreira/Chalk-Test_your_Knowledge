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
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ChatExerciseRubric
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("CE")
@JsonTypeName("CE")
public class ChatExerciseRubric extends ExerciseRubric {

    @Type(JsonBinaryType.class)
    @Column(name = "openAnswerRubric", columnDefinition = "jsonb")
    private OpenAnswerRubric openAnswerRubric;

    public ChatExerciseRubric(String id,  OpenAnswerRubric openAnswerRubric) {
        super(id);
        this.openAnswerRubric = openAnswerRubric;
    }

    @Override
    public void verifyProperties() throws BadInputException {
        if(openAnswerRubric!=null){
            openAnswerRubric.verifyProperties();
        } else throw new BadInputException("Open Answer Rubric inside Chat Exercise Rubric cannot be null");
    }

    @Override
    public boolean equals(ExerciseRubric exerciseRubric) {
        if(!(exerciseRubric instanceof MultipleChoiceRubric multipleChoiceRubric))
            return false;
        if(!openAnswerRubric.equals(multipleChoiceRubric))
            return false;
        return true;
    }

    @Override
    public ExerciseRubric clone() {
        return new ChatExerciseRubric(getId(), openAnswerRubric.clone());
    }

    
}
