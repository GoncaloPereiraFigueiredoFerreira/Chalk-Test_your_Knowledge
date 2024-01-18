package pt.uminho.di.chalktyk.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GenerateQuestionAIDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateQuestionAIDTO {
    private String input;
    private String text;
}
