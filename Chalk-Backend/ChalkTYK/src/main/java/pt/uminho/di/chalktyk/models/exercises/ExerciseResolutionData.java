package pt.uminho.di.chalktyk.models.exercises;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "Type")
public abstract class ExerciseResolutionData {
    public abstract void verifyInsertProperties() throws BadInputException;
    public abstract boolean equals(ExerciseResolutionData exerciseResolutionData);
}