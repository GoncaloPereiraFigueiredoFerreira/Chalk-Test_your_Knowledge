package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseDAO extends JpaRepository<Exercise, String> {

    @EntityGraph(value = "Exercise.NoRubricNoSolution", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT e FROM Exercise e WHERE e.id = :exerciseId")
    Optional<Exercise> loadByIdWithoutSolutionAndRubric(@Param("exerciseId") String exerciseId);

    @Query(value = "SELECT e.tags FROM Exercise e WHERE e.id = :exerciseId")
    java.util.Set<Tag> getExerciseTags(@Param("exerciseId") String exerciseId);

    @EntityGraph(attributePaths = "tags", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT e FROM Exercise e WHERE e.id IN (" +
            "SELECT e1.id FROM Exercise e1 " +
            "JOIN e1.tags t WHERE " +
            "t.id IN :tagIDS GROUP BY e1 HAVING COUNT(t.id) = :sizeTagIDS and " +
            " (:visibilityType is null or e.visibility = :visibilityType) and" +
            " (:institutionId is null or e.institution.name=:institutionId) and" +
            " (:courseId is null or e.course.id=:courseId) and" +
            " (:specialistID is null or e.specialist.id=:specialistID) and" +
            " (:title is null or e.title LIKE :title) and" +
            " (:exerciseType is null or e.exerciseType = :exerciseType))")
    Page<Exercise> getExercisesMatchAllTags(@Param("tagIDS") List<String> tagIDS,
                                            @Param("sizeTagIDS") int sizeTagIDS,
                                            @Param("visibilityType") Visibility visibilityType,
                                            @Param("institutionId") String institutionId,
                                            @Param("courseId") String courseId,
                                            @Param("specialistID") String specialistId,
                                            @Param("title")  String title,
                                            @Param("exerciseType")  String exerciseType,
                                            Pageable pageable);

    @EntityGraph(attributePaths = "tags", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT e FROM Exercise e WHERE " +
            "(:tagIDS is null or e.id IN (SELECT e1.id FROM Exercise e1 JOIN e1.tags t WHERE t.id IN :tagIDS)) and " +
            " (:visibilityType is null or e.visibility = :visibilityType) and" +
            " (:institutionId is null or e.institution.name=:institutionId) and" +
            " (:courseId is null or e.course.id=:courseId) and" +
            " (:specialistID is null or e.specialist.id=:specialistID) and" +
            " (:title is null or e.title LIKE :title) and" +
            " (:exerciseType is null or e.exerciseType = :exerciseType)")
    Page<Exercise> getExercisesMatchAnyGivenTag(@Param("tagIDS") List<String> tagIDS,
                                                @Param("visibilityType") Visibility visibilityType,
                                                @Param("institutionId") String institutionId,
                                                @Param("courseId") String courseId,
                                                @Param("specialistID") String specialistId,
                                                @Param("title")  String title,
                                                @Param("exerciseType")  String exerciseType,
                                                Pageable pageable);

    /**
     * Get the identifier of the specialist that owns the exercise.
     * @param exerciseId identifier of the exercise
     * @return identifier of the specialist that owns the exercise.
     */
    @Query(value = "SELECT e.specialist.id FROM Exercise e WHERE e.id = :exerciseId")
    String getExerciseSpecialistId(@Param("exerciseId") String exerciseId);
}
