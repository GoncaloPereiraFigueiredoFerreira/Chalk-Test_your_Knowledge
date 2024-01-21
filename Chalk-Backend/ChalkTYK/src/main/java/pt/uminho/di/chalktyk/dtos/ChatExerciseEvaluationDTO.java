package pt.uminho.di.chalktyk.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ChatExerciseEvaluationDTO
 */

@Data
@AllArgsConstructor
public class ChatExerciseEvaluationDTO {

    private float evaluation;
    private String comment;
}
