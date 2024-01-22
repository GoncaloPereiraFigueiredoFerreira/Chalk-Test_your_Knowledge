package pt.uminho.di.chalktyk.models.exercises.chat;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

/**
 * ChatExerciseData
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("CE")
public class ChatExerciseData extends ExerciseResolutionData{
    private List<String> chat;

    @Override
    public void verifyInsertProperties() throws BadInputException {
        if(chat == null){
            throw new BadInputException("Chat exercise (re)solution data must not have a 'null' chat.");
        }else{
            for (String string : chat) {
                if(string == null){
                    throw new BadInputException("Chat exercise (re)solution data must not have a 'null' chat elements.");
                } 
            }
        }
    }

    @Override
    public boolean equals(ExerciseResolutionData exerciseResolutionData) {
        if(!(exerciseResolutionData instanceof ChatExerciseData chatExerciseData))
            return false;
        if(chat.equals(chatExerciseData.getChat()))
            return false;
        return true;
    }

    @Override
    public ExerciseResolutionData clone() {
        List<String> chatList = chat != null ? new ArrayList<>(chat) : null;
        return new ChatExerciseData(chatList);
    }
}
