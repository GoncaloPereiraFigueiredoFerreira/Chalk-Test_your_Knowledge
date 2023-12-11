package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Getter
@Document(collection = "exercises_solutions")
@AllArgsConstructor
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

    /**
     * Updates an exercise solution. If an object is 'null' than it is considered that it should remain the same.
     * @param exerciseSolution     new exercise solution body
     */
    public boolean updateSolution(ExerciseSolution exerciseSolution) throws UnauthorizedException {
        boolean updated = false;
        if(exerciseSolution.id!=null)
            throw new UnauthorizedException("Cannot change exercise id");
        if(exerciseSolution.getData()!=null)
            data.updateExerciseResolutionData(exerciseSolution.getData());
        return updated;
    }
}