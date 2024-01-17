package pt.uminho.di.chalktyk.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * MultipleChoiceAIDTO
 */
@Data
@AllArgsConstructor
public class MultipleChoiceAIDTO {
    private String[] answers;
    private int correct;
    private String question;
}
