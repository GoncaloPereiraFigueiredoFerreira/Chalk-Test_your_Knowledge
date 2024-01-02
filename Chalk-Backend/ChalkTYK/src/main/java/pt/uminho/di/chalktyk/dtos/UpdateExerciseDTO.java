package pt.uminho.di.chalktyk.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksData;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksExercise;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksOptionsExercise;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksRubric;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceData;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceExercise;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceRubric;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerData;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerExercise;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateExerciseDTO {
    @Schema(description = "exercise body",
            oneOf = {
            MultipleChoiceExercise.class,
            OpenAnswerExercise.class,
            FillTheBlanksExercise.class,
            FillTheBlanksOptionsExercise.class
    })
    private Exercise exercise;

    @Schema(description = "exercise rubric",
            oneOf = {
            MultipleChoiceRubric.class,
            OpenAnswerRubric.class,
            FillTheBlanksRubric.class
    })
    private ExerciseRubric rubric;

    @Schema(description = "exercise solution",
            oneOf = {
            MultipleChoiceData.class,
            OpenAnswerData.class,
            FillTheBlanksData.class
    })
    private ExerciseResolutionData solution;

    @Schema(description = "exercise visibility",
            allowableValues = {"public", "not_listed", "private", "course", "institution"})
    private Visibility visibility;

    @Schema(description = "list of tags")
    private List<String> tagsIds;

}
