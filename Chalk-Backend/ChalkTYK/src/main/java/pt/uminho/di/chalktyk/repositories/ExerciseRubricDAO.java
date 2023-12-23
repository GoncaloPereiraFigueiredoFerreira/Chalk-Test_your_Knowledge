package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;

import java.util.Optional;

@Repository
public interface ExerciseRubricDAO extends JpaRepository<ExerciseRubric, String> {
    @Query("SELECT e.rubric FROM Exercise e WHERE e.id = :exerciseId")
    Optional<ExerciseRubric> findByExerciseId(@Param("exerciseId") String exerciseId);

    @Modifying(flushAutomatically = true)
    @Query("DELETE FROM ExerciseRubric r WHERE r.id = (SELECT e.rubric.id FROM Exercise e WHERE e.id = :exerciseId)")
    void deleteByExerciseId(@Param("exerciseId") String exerciseId);
}
