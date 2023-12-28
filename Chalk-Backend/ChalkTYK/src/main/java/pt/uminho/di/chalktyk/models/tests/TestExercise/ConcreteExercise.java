package pt.uminho.di.chalktyk.models.tests.TestExercise;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("concrete")
@ToString
public class ConcreteExercise extends TestExercise {
    private Exercise exercise;

    public ConcreteExercise(float points, Exercise exercise) {
        super(points);
        this.exercise = exercise;
    }

    @Override
    public String getId() {
        String id = null;
        if(exercise != null)
            id = exercise.getId();
        return id;
    }

    @Override
    public void verifyInsertProperties() throws BadInputException {
        if(exercise == null)
            throw new BadInputException("The concrete exercise data must not be null.");
        if(getPoints() <= 0)
            throw new BadInputException("The points of an exercise must be positive.");
        exercise.verifyInsertProperties();
    }
}
