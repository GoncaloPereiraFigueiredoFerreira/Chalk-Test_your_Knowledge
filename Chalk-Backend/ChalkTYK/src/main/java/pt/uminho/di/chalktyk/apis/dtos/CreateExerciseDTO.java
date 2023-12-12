package pt.uminho.di.chalktyk.apis.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Exercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseSolution;
import pt.uminho.di.chalktyk.models.relational.VisibilitySQL;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateExerciseDTO {
    private Exercise exercise;
    private ExerciseRubric rubric;
    private ExerciseSolution solution;
    private VisibilitySQL visibility;
    private List<String> tagsIds;
}
