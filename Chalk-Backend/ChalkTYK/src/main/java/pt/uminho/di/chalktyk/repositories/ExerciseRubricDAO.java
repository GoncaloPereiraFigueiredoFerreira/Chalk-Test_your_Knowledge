package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;

@Repository
public interface ExerciseRubricDAO extends JpaRepository<ExerciseRubric, String> {
}
