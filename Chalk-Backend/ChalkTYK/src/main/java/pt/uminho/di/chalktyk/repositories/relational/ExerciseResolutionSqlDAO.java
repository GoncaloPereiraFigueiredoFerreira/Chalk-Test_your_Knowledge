package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.relational.ExerciseResolutionSQL;

@Repository
public interface ExerciseResolutionSqlDAO  extends JpaRepository<ExerciseResolutionSQL, String> {
}
