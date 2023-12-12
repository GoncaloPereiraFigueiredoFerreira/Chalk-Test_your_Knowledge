package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.relational.ExerciseSQL;
import pt.uminho.di.chalktyk.models.relational.InstitutionSQL;

@Repository
public interface ExerciseSqlDAO extends JpaRepository<ExerciseSQL, String> {
    @Modifying
    @Query(value = "UPDATE ExerciseSQL e SET e.nrCopies=e.nrCopies+1 WHERE e.id = :exerciseId")
    void increaseExerciseCopies(@Param("exerciseId") String exerciseId);

    @Modifying
    @Query(value = "UPDATE ExerciseSQL e SET e.nrCopies=e.nrCopies-1 WHERE e.id = :exerciseId")
    void decreaseExerciseCopies(@Param("exerciseId") String exerciseId);

    @Query(value = "SELECT e.tags FROM ExerciseSQL e WHERE e.id = :exerciseId")
    InstitutionSQL getExerciseTags(@Param("exerciseId") String exerciseId);
}
