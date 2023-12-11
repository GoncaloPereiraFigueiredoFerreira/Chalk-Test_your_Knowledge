package pt.uminho.di.chalktyk.models.nonrelational.exercises;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public abstract class ExerciseResolutionData {
    public abstract void verifyInsertProperties() throws BadInputException;

    /**
     * Updates an exercise resolution. If an object is 'null' than it is considered that it should remain the same.
     * @param exerciseResolutionData     new exercise resolution body
     */
    public abstract boolean updateExerciseResolutionData(ExerciseResolutionData exerciseResolutionData) throws UnauthorizedException;
}