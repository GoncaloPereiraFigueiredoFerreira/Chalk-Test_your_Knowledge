package pt.uminho.di.chalktyk.repositories.nonrelational;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionStatus;
import pt.uminho.di.chalktyk.models.relational.ExerciseResolutionSQL;

@Repository
public interface ExerciseResolutionDAO extends MongoRepository<ExerciseResolution, String> {
    long countByExerciseIdAndStatus(String exerciseId, ExerciseResolutionStatus status);

    /**
     * Finds a page of resolutions, with a given status, of a specific exercise
     * @param exerciseId identifier of the exercise
     * @param status status of the resolution
     * @param pageable page request information
     * @return page of resolutions, with a given status, of a specific exercise
     */
    Page<ExerciseResolution> findByExerciseIdAndStatus(String exerciseId, ExerciseResolutionStatus status, Pageable pageable);
}
