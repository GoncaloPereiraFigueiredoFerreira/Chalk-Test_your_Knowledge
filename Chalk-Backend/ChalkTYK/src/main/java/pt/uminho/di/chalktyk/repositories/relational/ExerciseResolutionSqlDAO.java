package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.relational.ExerciseResolutionSQL;
import pt.uminho.di.chalktyk.models.relational.StudentSQL;

import java.util.List;

@Repository
public interface ExerciseResolutionSqlDAO  extends JpaRepository<ExerciseResolutionSQL, String> {

    /**
     * @param exerciseId identifier of the exercise
     * @return total number of resolutions for a specific exercise
     */
    int countExerciseResolutionSQLSByExercise_Id(String exerciseId);

    /**
     * Counts the number of students that have at least one resolution for a specific exercise.
     * @param exerciseId identifier of the exercise
     * @return the number of students that have at least one resolution for a specific exercise.
     */
    @Query("SELECT COUNT(DISTINCT e.student.id) FROM ExerciseResolutionSQL e WHERE e.exercise.id = :exerciseId")
    int countStudentsWithResolutionForExercise(@Param("exerciseId") String exerciseId);

    @Query("SELECT e.student FROM ExerciseResolutionSQL e WHERE e.exercise.id = :exerciseId")
    List<StudentSQL> listStudentsWithResolutionForExercise(@Param("exerciseId") String exerciseId);

    /**
     * Finds all exercise resolutions for a specific exercise.
     * @param exerciseId identifier of the exercise
     * @return all exercise resolutions for a specific exercise
     */
    List<ExerciseResolutionSQL> findExerciseResolutionSQLSByExercise_Id(String exerciseId);

    /**
     * Finds a page of exercise resolutions for a specific exercise.
     * @param exerciseId identifier of the exercise
     * @param pageable information of the page
     * @return specific page of exercise resolutions for a given exercise
     */
    Page<ExerciseResolutionSQL> findExerciseResolutionSQLSByExercise_Id(String exerciseId, Pageable pageable);

    /**
     * Finds a page of the exercise resolutions for a specific exercise.
     * A page obtained with this method can only be contained by the last resolution of each student.
     * @param exerciseId identifier of the exercise
     * @param pageable information of the page
     * @return specific page of exercise resolutions for a given exercise
     */
    @Query("SELECT r FROM ExerciseResolutionSQL r JOIN FETCH r.student s WHERE r.exercise.id = :exerciseId AND r.submissionNr = (SELECT MAX (r2.submissionNr) FROM ExerciseResolutionSQL r2 WHERE r.student.id = r2.student.id)")
    Page<ExerciseResolutionSQL> findLatestExerciseResolutionSQLSByExercise_Id(@Param("exerciseId") String exerciseId, Pageable pageable);
    //@Query("SELECT r FROM StudentSQL s JOIN ExerciseResolutionSQL r ON s.id = r.student.id WHERE r.exercise.id = :exerciseId AND r.submissionNr = (SELECT MAX (r2.submissionNr) FROM ExerciseResolutionSQL r2 WHERE r.student.id = r2.student.id)")
    //Page<ExerciseResolutionSQL> findLatestExerciseResolutionSQLSByExercise_Id(@Param("exerciseId") String exerciseId, Pageable pageable);

    @Modifying
    void deleteExerciseResolutionSQLSByExercise_Id(String exerciseId);
}
