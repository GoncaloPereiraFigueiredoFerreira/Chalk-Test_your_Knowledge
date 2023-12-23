package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionStatus;
import pt.uminho.di.chalktyk.models.users.Student;

import java.util.List;

@Repository
public interface ExerciseResolutionDAO extends JpaRepository<ExerciseResolution, String> {
    @Query("SELECT COUNT(r) FROM ExerciseResolution r WHERE r.exercise.id = :exerciseId AND r.status = :status")
    long countByExerciseIdAndStatus(@Param("exerciseId") String exerciseId, @Param("status") ExerciseResolutionStatus status);

    /**
     * Finds a page of resolutions, with a given status, of a specific exercise
     * @param exerciseId identifier of the exercise
     * @param status status of the resolution
     * @param pageable page request information
     * @return page of resolutions, with a given status, of a specific exercise
     */
    Page<ExerciseResolution> findAllByExerciseIdAndStatus(String exerciseId, ExerciseResolutionStatus status, Pageable pageable);
    
    /**
     * @param exerciseId identifier of the exercise
     * @return total number of resolutions for a specific exercise
     */
    int countAllByExercise_Id(String exerciseId);

    /**
     * Counts the number of students that have at least one resolution for a specific exercise.
     * @param exerciseId identifier of the exercise
     * @return the number of students that have at least one resolution for a specific exercise.
     */
    @Query("SELECT COUNT(DISTINCT e.student.id) FROM ExerciseResolution e WHERE e.exercise.id = :exerciseId")
    int countStudentsWithResolutionForExercise(@Param("exerciseId") String exerciseId);

    /**
     * Lists students that have a resolution for an exercise.
     * @param exerciseId identifier of the exercise
     * @return List of students that have a resolution for an exercise.
     */
    @Query("SELECT e.student FROM ExerciseResolution e WHERE e.exercise.id = :exerciseId")
    List<Student> listStudentsWithResolutionForExercise(@Param("exerciseId") String exerciseId);

    /**
     * Finds all exercise resolutions for a specific exercise.
     * @param exerciseId identifier of the exercise
     * @return all exercise resolutions for a specific exercise
     */
    List<ExerciseResolution> findAllByExercise_Id(String exerciseId);

    /**
     * Finds a page of exercise resolutions for a specific exercise.
     * @param exerciseId identifier of the exercise
     * @param pageable information of the page
     * @return specific page of exercise resolutions for a given exercise
     */
    Page<ExerciseResolution> findAllByExercise_Id(String exerciseId, Pageable pageable);

    /**
     * Finds a page of the exercise resolutions for a specific exercise.
     * A page obtained with this method can only be contained by the last resolution of each student.
     * @param exerciseId identifier of the exercise
     * @param pageable information of the page
     * @return specific page of exercise resolutions for a given exercise
     */
    @Query("SELECT r FROM ExerciseResolution r JOIN FETCH r.student s WHERE r.exercise.id = :exerciseId AND r.submissionNr = (SELECT MAX (r2.submissionNr) FROM ExerciseResolution r2 WHERE r2.exercise.id = :exerciseId AND r.student.id = r2.student.id)")
    Page<ExerciseResolution> findLatestResolutionsByExercise_Id(@Param("exerciseId") String exerciseId, Pageable pageable);
    //@Query("SELECT r FROM Student s JOIN ExerciseResolution r ON s.id = r.student.id WHERE r.exercise.id = :exerciseId AND r.submissionNr = (SELECT MAX (r2.submissionNr) FROM ExerciseResolution r2 WHERE r.student.id = r2.student.id)")
    //Page<ExerciseResolution> findLatestExerciseResolutionSByExercise_Id(@Param("exerciseId") String exerciseId, Pageable pageable);

    /**
     * Deletes all resolutions of an exercise.
     * @param exerciseId exercise identifier
     */
    @Modifying
    void deleteAllByExercise_Id(String exerciseId);

    /**
     * Gets the last resolution a specific student
     * made for a specific exercise.
     * @param studentId identifier of the student
     * @param exerciseId identifier of the exercise
     * @return the submission number of the last resolution a specific student
     *         made for a specific exercise.
     */
    @Query("SELECT r FROM ExerciseResolution r JOIN FETCH r.student s WHERE r.student.id = :studentId AND r.exercise.id = :exerciseId AND r.submissionNr = (SELECT MAX(r2.submissionNr) FROM ExerciseResolution r2 WHERE r2.exercise.id = :exerciseId AND r.student.id = r2.student.id)")
    ExerciseResolution getStudentLastResolution(@Param("studentId") String studentId, @Param("exerciseId") String exerciseId);

    /**
     * Counts the number of resolutions made by a student for a specific exercise.
     * @param exerciseId identifier of the exercise
     * @param studentId identifier of the student
     * @return the number of resolutions made by a student for a specific exercise.
     */
    int countAllByExercise_IdAndStudent_Id(String exerciseId, String studentId);

    /**
     * Finds the resolutions made by a student for a specific exercise.
     * @param exerciseId identifier of the exercise
     * @param studentId identifier of the student
     * @return the resolutions made by a student for a specific exercise.
     */
    Page<ExerciseResolution> findByExercise_IdAndStudent_Id(String exerciseId, String studentId, Pageable pageable);
    List<ExerciseResolution> findAllByExercise_IdAndStudent_Id(String exerciseId, String studentId);
}
