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

import java.util.Optional;

@Repository
public interface ExerciseDAO extends JpaRepository<Exercise, String> {

    @EntityGraph(value = "Exercise.NoRubricNoSolution", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT e FROM Exercise e WHERE e.id = :exerciseId")
    Optional<Exercise> loadByIdWithoutSolutionAndRubric(@Param("exerciseId") String exerciseId);

    @Query(value = "SELECT e.tags FROM Exercise e WHERE e.id = :exerciseId")
    java.util.Set<Tag> getExerciseTags(@Param("exerciseId") String exerciseId);

    /* TODO - está mal!
    Usar esta query como exemplo:
    select id from (select products.id as id,COUNT(tags.id) FROM products join products_tags on products.id = products_tags.product_id
                                                                join tags on products_tags.tag_id = tags.id
                                                                WHERE tags.id IN :tags_ids_list
                                                                GROUP BY products.id
                                                                HAVING COUNT(tags.id) = :tags_list_size) s LIMIT :pageSize OFFSET (:page - 1) * :pageSize;
     */
    @Query("SELECT e FROM Exercise e JOIN e.tags t WHERE" +
            " CASE WHEN :matchAllTags = true THEN (:tagIDS is null or t = (SELECT t FROM Tag t WHERE t.id IN :tagIDS))"+
            " ELSE (:tagIDS is null or t IN (SELECT t FROM Tag t WHERE t.id IN :tagIDS)) END and"+
            " (:visibilityType is null or e.visibility = :visibilityType) and" +
            " (:institutionId is null or e.institution.name=:institutionId) and" +
            " (:courseId is null or e.course.id=:courseId) and" +
            " (:specialistID is null or e.specialist.id=:specialistID) and" +
            " (:title is null or e.title = :title) and" +
            " (:exerciseType is null or e.exerciseType = :exerciseType)")
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
