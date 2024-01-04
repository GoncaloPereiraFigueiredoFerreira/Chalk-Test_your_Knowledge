package pt.uminho.di.chalktyk.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksExercise;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksOptionsExercise;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceExercise;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ConcreteExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ReferenceExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.TestExercise;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateTestExerciseDTO {
    @Schema(description = "test exercise",
            oneOf = {
                    ConcreteExercise.class,
                    ReferenceExercise.class
            })
    private TestExercise exercise;
    private Integer groupIndex;
    private Integer exeIndex;
    private String groupInstructions;
}
