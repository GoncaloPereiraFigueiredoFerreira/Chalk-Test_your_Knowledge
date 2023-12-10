package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Getter
@Document(collection = "exercises_solutions")
public class ExerciseSolution {
	@Id
	private String id;
	private ExerciseResolutionData data;

    public void verifyInsertProperties() throws BadInputException {
        id=null;
        if(data==null)
            throw new BadInputException("The solution's exercise resolution data cannot be null");
        data.verifyInsertProperties();
    }
}