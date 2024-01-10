package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.ServiceException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

/**
 * Service that knowing about both tests and exercises, limits or enhances the behavior of a method.
 */
@Service
public class ExercisesTestsService implements IExercisesTestsService{

    private final IExercisesService exercisesService;

    @Autowired
    public ExercisesTestsService(IExercisesService exercisesService) {
        this.exercisesService = exercisesService;
    }

    /**
     * Delete exercise by id. If the exercise belongs to a test,
     * it can't be deleted using this method.
     *
     * @param exerciseId identifier of the exercise
     * @throws NotFoundException if the exercise was not found
     * @throws UnauthorizedException if the exercise belongs to a test
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteExerciseById(String exerciseId) throws NotFoundException, UnauthorizedException {
        Visibility vis = exercisesService.getExerciseVisibility(exerciseId);
        if(vis.equals(Visibility.TEST))
            throw new UnauthorizedException("Cannot delete exercise: exercise belongs to a test.");
        exercisesService.deleteExerciseById(exerciseId);
    }

    // TODO - methods related to exercise correction
}
