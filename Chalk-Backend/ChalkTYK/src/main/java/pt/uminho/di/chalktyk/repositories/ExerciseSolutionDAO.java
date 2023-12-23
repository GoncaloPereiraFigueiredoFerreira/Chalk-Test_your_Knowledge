package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.ExerciseSolution;

import java.util.Optional;

@Repository
public interface ExerciseSolutionDAO extends JpaRepository<ExerciseSolution, String> {
    @Query("SELECT e.solution FROM Exercise e WHERE e.id = :exerciseId")
    Optional<ExerciseSolution> findByExerciseId(@Param("exerciseId") String exerciseId);

    @Modifying(flushAutomatically = true)
    @Query("DELETE Exercise.solution WHERE Exercise.id = :exerciseId")
    void deleteByExerciseId(@Param("exerciseId") String exerciseId);
}
