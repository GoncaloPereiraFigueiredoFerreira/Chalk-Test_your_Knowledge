package pt.uminho.di.chalktyk.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;

/**
 * OpenAnswerDTO
 */
@Getter
@Setter
@AllArgsConstructor
public class OpenAnswerDTO {

    private String question;
    private String answer;
    private String auxiliar = "";
    private OpenAnswerRubric rubric;
}
