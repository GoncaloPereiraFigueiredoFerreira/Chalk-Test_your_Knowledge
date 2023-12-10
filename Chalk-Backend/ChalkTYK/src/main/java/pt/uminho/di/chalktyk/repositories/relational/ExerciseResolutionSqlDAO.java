package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.relational.ExerciseResolution;
import pt.uminho.di.chalktyk.models.relational.Test;

@Repository
public interface ExerciseResolutionSqlDAO  extends JpaRepository<ExerciseResolution, String> {
}
