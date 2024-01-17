package pt.uminho.di.chalktyk.models.exercises.items;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.io.Serializable;

@JsonTypeInfo(property = "type", include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringItem.class, name = "string")
})
public abstract class Item implements Serializable {
    public abstract void verifyProperties() throws BadInputException;
    public abstract Item clone();
}