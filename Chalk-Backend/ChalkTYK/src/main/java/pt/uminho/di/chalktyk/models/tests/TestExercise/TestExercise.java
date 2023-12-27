package pt.uminho.di.chalktyk.models.tests.TestExercise;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "Type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ReferenceExercise.class, name = "reference"),
        @JsonSubTypes.Type(value = ConcreteExercise.class, name = "concrete")
})
public abstract class TestExercise implements Serializable {
    public abstract String getId();
    public abstract float getPoints();
    public abstract void verifyInsertProperties() throws BadInputException;
}
