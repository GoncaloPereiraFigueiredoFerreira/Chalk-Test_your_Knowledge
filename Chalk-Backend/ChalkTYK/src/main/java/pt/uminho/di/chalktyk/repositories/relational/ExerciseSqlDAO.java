package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Visibility;
import pt.uminho.di.chalktyk.models.relational.ExerciseSQL;
import pt.uminho.di.chalktyk.models.relational.InstitutionSQL;
import pt.uminho.di.chalktyk.models.relational.StudentSQL;
import pt.uminho.di.chalktyk.models.relational.TagSQL;

import java.util.List;
import java.util.Set;

@Repository
public interface ExerciseSqlDAO extends JpaRepository<ExerciseSQL, String> {
    @Modifying
    @Query(value = "UPDATE ExerciseSQL e SET e.nrCopies=e.nrCopies+1 WHERE e.id = :exerciseId")
    void increaseExerciseCopies(@Param("exerciseId") String exerciseId);

    @Modifying
    @Query(value = "UPDATE ExerciseSQL e SET e.nrCopies=e.nrCopies-1 WHERE e.id = :exerciseId")
    void decreaseExerciseCopies(@Param("exerciseId") String exerciseId);

    @Query(value = "SELECT e.tags FROM ExerciseSQL e WHERE e.id = :exerciseId")
    java.util.Set<TagSQL> getExerciseTags(@Param("exerciseId") String exerciseId);

    @Modifying
    @Query(value = "UPDATE ExerciseSQL e SET e.tags = (SELECT t FROM TagSQL t WHERE t.id IN :tagIDS) WHERE e.id = :exerciseId")
    void updateExerciseTagsByIds(@Param("exerciseId") String exerciseId, @Param("tagIDS") java.util.Set<String> tagIDS);
    @Modifying
    @Query(value = "UPDATE ExerciseSQL e SET e.course = (SELECT c FROM CourseSQL c WHERE c.id=:courseId) WHERE e.id = :exerciseId")
    void updateExerciseCourseById(@Param("exerciseId") String exerciseId,@Param("courseId") String courseId);

    @Query("SELECT e FROM ExerciseSQL e WHERE" +
            " CASE WHEN :matchAllTags THEN (:tags is null or e.tags = (SELECT t FROM TagSQL t WHERE t.id IN :tagIDS))"+
            " ELSE (:tags is null or e.tags IN (SELECT t FROM TagSQL t WHERE t.id IN :tagIDS)) END and"+
            " :visibilityType is null or e.visibility = :visibilityType and" +
            " :visibilityType is null or e.visibility = :visibilityType and" +
            " :institutionId is null or e.institution = (SELECT i FROM InstitutionSQL i WHERE i.id=:institutionId) and" +
            " :courseId is null or e.course = (SELECT c FROM CourseSQL c WHERE c.id=:courseId) and" +
            " :specialistID is null or e.specialist = (SELECT s FROM SpecialistSQL s WHERE s.id=:specialistID) and" +
            " :title is null or e.title = :title and" +
            " :exerciseType is null or e.exerciseType = :exerciseType")
    Page<ExerciseSQL> getExercises(Pageable pageable,
                                   @Param("tags") List<String> tags, @Param("matchAllTags")  boolean matchAllTags,
                                   @Param("visibilityType") Visibility visibilityType,
                                   @Param("institutionId") String institutionId,
                                   @Param("courseId") String courseId,
                                   @Param("specialistID") String specialistId,
                                   @Param("title")  String title,
                                   @Param("exerciseType")  String exerciseType);
    /**
     * Get the identifier of the specialist that owns the exercise.
     * @param exerciseId identifier of the exercise
     * @return identifier of the specialist that owns the exercise.
     */
    @Query(value = "SELECT e.specialist.id FROM ExerciseSQL e WHERE e.id = :exerciseId")
    String getExerciseSpecialistId(@Param("exerciseId") String exerciseId);

    @Query(value = "SELECT COUNT(e) FROM ExerciseCopySQL e WHERE e.original.id = :exerciseId")
    int countExerciseCopies(@Param("exerciseId") String exerciseId);
}
