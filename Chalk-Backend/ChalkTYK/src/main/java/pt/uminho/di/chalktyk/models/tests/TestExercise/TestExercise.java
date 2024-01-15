package pt.uminho.di.chalktyk.models.tests.TestExercise;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ReferenceExercise.class, name = "reference"),
        @JsonSubTypes.Type(value = ConcreteExercise.class, name = "concrete")
})
public abstract class TestExercise implements Serializable {
    float points;
    public abstract String getId();
    public abstract void verifyInsertProperties() throws BadInputException;
}
