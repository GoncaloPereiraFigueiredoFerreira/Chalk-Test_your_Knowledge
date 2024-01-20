package pt.uminho.di.chalktyk.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * OpenAnswerEvaluationDTO
 */
@Data
@AllArgsConstructor
public class OpenAnswerEvaluationDTO {
    
    private float evaluation;
    private String[] comment;
    
}
