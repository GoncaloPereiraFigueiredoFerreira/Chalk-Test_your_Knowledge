package pt.uminho.di.chalktyk.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ChatExerciseDTO
 */
@Data
public class ChatExerciseDTO {

    private List<String> topics = new ArrayList<>();
    private List<String> chat = new ArrayList<>();
    
}
