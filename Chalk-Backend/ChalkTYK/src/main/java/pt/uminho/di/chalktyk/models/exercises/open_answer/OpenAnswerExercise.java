package pt.uminho.di.chalktyk.models.exercises.open_answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.*;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceExercise;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("OA")
@JsonTypeName("OA")
public class OpenAnswerExercise extends Exercise {
	@Override
    public void verifyResolutionProperties(ExerciseResolutionData exerciseResolutionData) throws BadInputException {
        if(!(exerciseResolutionData instanceof OpenAnswerData openAnswerData))
            throw new BadInputException("Exercise resolution does not match exercise type (open answer).");
        openAnswerData.verifyInsertProperties();
    }

    @Override
    public void verifyRubricProperties(ExerciseRubric rubric) throws BadInputException {
        if(!(rubric instanceof OpenAnswerRubric openAnswerRubric))
            throw new BadInputException("Exercise rubric does not match exercise type (open answer).");
        openAnswerRubric.verifyProperties();
    }

    @Override
    public String getExerciseType() {
        return "OA";
    }

    /**
     * Evaluates the resolution of an exercise.
     *
     * @param resolution resolution data that will be evaluated
     * @param solution   solution of the exercise
     * @param rubric     rubric of the exercise
     * @return points to be attributed to the resolution
     * @throws UnauthorizedException if the resolution cannot be evaluated automatically.
     */
    @Override
    public ExerciseResolution automaticEvaluation(ExerciseResolution resolution, ExerciseSolution solution, ExerciseRubric rubric) throws UnauthorizedException {
        throw new UnauthorizedException("Open answer exercise cannot be evaluated automatically.");
    }

    @Override
    public Exercise cloneExerciseDataOnly() {
        var exercise = new OpenAnswerExercise();
        try { copyExerciseDataOnlyTo(exercise); }
        catch (BadInputException ignored){}
        return exercise;
    }

    @Override
    public void copyExerciseDataOnlyTo(Exercise exercise) throws BadInputException {
        if(!(exercise instanceof OpenAnswerExercise oae))
            throw new BadInputException("Exercise is not of the same type.");
        _copyExerciseDataOnlyTo(oae);
    }
}