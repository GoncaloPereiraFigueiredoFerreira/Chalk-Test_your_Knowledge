package pt.uminho.di.chalktyk.models.exercises;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import pt.uminho.di.chalktyk.models.exercises.chat.ChatExerciseData;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksData;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceData;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "MC", value = MultipleChoiceData.class),
        @JsonSubTypes.Type(name = "OA", value = OpenAnswerData.class),
        @JsonSubTypes.Type(name = "FTB", value = FillTheBlanksData.class),
        @JsonSubTypes.Type(name = "CE", value = ChatExerciseData.class)
})
public abstract class ExerciseResolutionData implements Serializable {
    public abstract void verifyInsertProperties() throws BadInputException;
    public abstract boolean equals(ExerciseResolutionData exerciseResolutionData);
    public abstract ExerciseResolutionData clone();
    public String getType() {
        return this.getClass().getAnnotation(JsonTypeName.class).value();
    }
}