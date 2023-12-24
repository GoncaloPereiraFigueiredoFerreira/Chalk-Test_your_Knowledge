package pt.uminho.di.chalktyk.models.exercises.items;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@JsonTypeInfo(property = "type", include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringItem.class, name = "string")
})
public abstract class Item {
    private Integer id;

    public abstract void verifyProperties() throws BadInputException;

    public abstract Item clone();

    public String getType() {
        return this.getClass().getAnnotation(JsonTypeName.class).value();
    }
}