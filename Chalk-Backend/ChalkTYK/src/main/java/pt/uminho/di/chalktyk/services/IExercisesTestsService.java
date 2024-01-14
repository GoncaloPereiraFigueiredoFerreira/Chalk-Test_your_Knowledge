package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.ForbiddenException;

public interface IExercisesTestsService {
    /**
     * Delete exercise by id.
     *
     * @param exerciseId identifier of the exercise
     * @throws NotFoundException     if the exercise was not found
     */
    void deleteExerciseById(String exerciseId) throws NotFoundException, ForbiddenException;
}
