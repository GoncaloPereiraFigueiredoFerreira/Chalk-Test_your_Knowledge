package pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Exercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Document(collection = "exercises")
@JsonTypeName("OA")
public class OpenAnswerExercise extends ConcreteExercise {
    @Override
    public void verifyResolutionProperties(ExerciseResolutionData exerciseResolutionData) throws BadInputException {
        if(!(exerciseResolutionData instanceof OpenAnswerData openAnswerData))
            throw new BadInputException("Exercise resolution does not match exercise type (open answer).");
        openAnswerData.verifyInsertProperties();
    }

    @Override
    public void verifyRubricProperties(ExerciseRubric rubric) throws BadInputException {
        if(!(rubric instanceof OpenAnswerRubric openAnswerRubric))
            throw new BadInputException("Exercise rubric does not match exercise type (open answer).");
        openAnswerRubric.verifyProperties();
    }

    @Override
    public String getExerciseType() {
        return "OA";
    }

    /**
     * Updates an exercise. If an object is 'null' than it is considered that it should remain the same.
     * @param exercise     new exercise body
     */
    @Override
    public boolean updateExercise(Exercise exercise) throws UnauthorizedException {
        if(!(exercise instanceof OpenAnswerExercise))
            throw new UnauthorizedException("The type of the exercise cannot be changed");
        return super.updateExercise(exercise);
    }
}