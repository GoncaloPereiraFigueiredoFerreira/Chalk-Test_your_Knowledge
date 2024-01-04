package pt.uminho.di.chalktyk.models.exercises.chat;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

/**
 * ChatExerciseRubric
 */
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("CE")
@JsonTypeName("CE")
public class ChatExerciseRubric extends ExerciseRubric {

    @Override
    public void verifyProperties() throws BadInputException {
    }

    @Override
    public boolean equals(ExerciseRubric exerciseRubric) {
        return true;
    }

    @Override
    public ExerciseRubric clone() {
        return new ChatExerciseRubric();
    }

    
}
