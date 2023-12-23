package pt.uminho.di.chalktyk.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.ExerciseSolution;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateExerciseDTO {
    private Exercise exercise;
    private ExerciseRubric rubric;
    private ExerciseSolution solution;
    private Visibility visibility;
    private List<String> tagsIds;
}
