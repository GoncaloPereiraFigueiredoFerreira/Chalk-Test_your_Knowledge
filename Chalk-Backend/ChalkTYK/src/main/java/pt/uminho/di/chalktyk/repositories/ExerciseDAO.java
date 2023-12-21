package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;

@Repository
public interface ExerciseDAO extends JpaRepository<Exercise, String> {
    @Query(value = "SELECT e.tags FROM Exercise e WHERE e.id = :exerciseId")
    java.util.Set<Tag> getExerciseTags(@Param("exerciseId") String exerciseId);

    @Query("SELECT e FROM Exercise e JOIN e.tags t WHERE" +
            " CASE WHEN :matchAllTags = true THEN (:tagIDS is null or t = (SELECT t FROM Tag t WHERE t.id IN :tagIDS))"+
            " ELSE (:tagIDS is null or t IN (SELECT t FROM Tag t WHERE t.id IN :tagIDS)) END and"+
            " :visibilityType is null or e.visibility = :visibilityType and" +
            " :institutionId is null or e.institution = (SELECT i FROM Institution i WHERE i.name=:institutionId) and" +
            " :courseId is null or e.course = (SELECT c FROM Course c WHERE c.id=:courseId) and" +
            " :specialistID is null or e.specialist = (SELECT s FROM Specialist s WHERE s.id=:specialistID) and" +
            " :title is null or e.title = :title and" +
            " :exerciseType is null or e.exerciseType = :exerciseType")
    Page<Exercise> getExercises(Pageable pageable,
                                   @Param("tagIDS") java.util.List<String> tagIDS, @Param("matchAllTags")  boolean matchAllTags,
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
    @Query(value = "SELECT e.specialist.id FROM Exercise e WHERE e.id = :exerciseId")
    String getExerciseSpecialistId(@Param("exerciseId") String exerciseId);
}
