package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Getter
@Setter
@Document(collection = "exercises_solutions")
@AllArgsConstructor
public class ExerciseSolution {
	@Id
	private String id;
	private ExerciseResolutionData data;

    public void verifyInsertProperties() throws BadInputException {
        if(data==null)
            throw new BadInputException("The solution's exercise resolution data cannot be null");
        data.verifyInsertProperties();
    }
}