package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.relational.ExerciseResolutionSQL;

import java.util.List;

@Repository
public interface ExerciseResolutionSqlDAO  extends JpaRepository<ExerciseResolutionSQL, String> {

    int countExerciseResolutionSQLSByExercise_Id(String exerciseId);

    List<ExerciseResolutionSQL> findExerciseResolutionSQLSByExercise_Id(String exerciseId);

    @Modifying
    void deleteExerciseResolutionSQLSByExercise_Id(String exerciseId);
}
