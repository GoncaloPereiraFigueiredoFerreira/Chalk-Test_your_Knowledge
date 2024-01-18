package pt.uminho.di.chalktyk.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * OpenAnswerAIDTO
 */
@Data
@AllArgsConstructor
public class OpenAnswerAIDTO {

    private String question;
    private String[] Topics;
}
