package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Service
public class ExercisesTestsService /*implements IExercisesTestsService*/{

    //private final IExercisesService exercisesService;
    //private final ITestsService testsService;
//
    //@Autowired
    //public ExercisesTestsService(IExercisesService exercisesService, ITestsService testsService) {
    //    this.exercisesService = exercisesService;
    //    this.testsService = testsService;
    //}
//
    ///**
    // * Delete exercise by id. If the exercise belongs to a test,
    // * it can't be deleted using this method.
    // *
    // * @param exerciseId identifier of the exercise
    // * @throws NotFoundException if the exercise was not found
    // * @throws UnauthorizedException if the exercise belongs to a test
    // */
    //@Override
    //public void deleteExerciseById(String exerciseId) throws NotFoundException, UnauthorizedException {
    //    String vis = exercisesService.getExerciseVisibility(exerciseId);
    //    if(Visibility.fromValue(vis) == Visibility.TEST)
    //        throw new UnauthorizedException("Cannot delete exercise: exercise belongs to a test.");
    //    exercisesService.deleteExerciseById(exerciseId);
    //}
}
