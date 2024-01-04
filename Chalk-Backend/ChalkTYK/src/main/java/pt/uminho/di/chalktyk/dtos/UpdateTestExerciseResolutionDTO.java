package pt.uminho.di.chalktyk.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksData;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceData;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerData;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateTestExerciseResolutionDTO {
    private String exeId;
    private ExerciseResolution resolution;
}
