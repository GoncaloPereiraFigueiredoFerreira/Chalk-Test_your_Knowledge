package pt.uminho.di.chalktyk.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TrueOrFalseAIDTO
 */
@Data
@AllArgsConstructor
public class TrueOrFalseAIDTO {

    private String question;
    private boolean correct;
}
