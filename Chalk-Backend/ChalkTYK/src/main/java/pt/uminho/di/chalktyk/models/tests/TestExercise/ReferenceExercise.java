package pt.uminho.di.chalktyk.models.tests.TestExercise;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonTypeName("reference")
@ToString
public class ReferenceExercise extends TestExercise {
    private String id;
    private float points;

    @Override
    public void verifyInsertProperties() throws BadInputException {
        if(id == null)
            throw new BadInputException("Id of an exercise must not be null.");
        if(points <= 0)
            throw new BadInputException("The points of an exercise must be positive.");
    }
}
