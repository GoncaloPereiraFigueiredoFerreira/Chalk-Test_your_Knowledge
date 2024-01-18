package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.Test;

import java.util.List;

@Repository
public interface TestDAO extends JpaRepository<Test, String> {
    @Query("SELECT test FROM Test test WHERE" +
            " CASE WHEN :matchAllTags = true THEN (:tagIDS is null or :tagsSize = 0 or test.id IN (SELECT tgs.testTagPK.test.id FROM TestTag tgs WHERE tgs.testTagPK.tag.id IN :tagIDS GROUP BY tgs.testTagPK.test.id HAVING COUNT(tgs.testTagPK.tag.id) = :tagsSize))"+
            " ELSE (:tagIDS is null or :tagsSize = 0 or test.id IN (SELECT tgs.testTagPK.test.id FROM TestTag tgs WHERE tgs.testTagPK.tag.id IN :tagIDS)) END and"+
            " (:visibilityType is null or test.visibility = :visibilityType) and" +
            " (:institutionId is null or test.institution.name = :institutionId) and" +
            " (:courseId is null or test.course.id = :courseId) and" +
            " (:specialistID is null or test.specialist.id = :specialistID) and" +
            " (:title is null or test.title = :title)")
    Page<Test> getTests(Pageable pageable,
                           @Param("tagIDS") java.util.List<String> tagIDS, @Param("tagsSize") int tagsSize, @Param("matchAllTags")  boolean matchAllTags,
                           @Param("visibilityType") Visibility visibilityType,
                           @Param("institutionId") String institutionId,
                           @Param("courseId") String courseId,
                           @Param("specialistID") String specialistId,
                           @Param("title")  String title);

    @Query("SELECT e FROM MultipleChoiceExercise e WHERE " +
            "(:tagIDS is null or e.id IN (SELECT e1.id FROM Exercise e1 JOIN e1.tags t WHERE t.id IN :tagIDS)) and " +
            " e.visibility = 'PUBLIC' and" +
            " (e.mctype = 0 or e.mctype = 4)" +
            " ORDER BY FUNCTION('RANDOM')")
    Page<Exercise> getExercisesForAutoEvalTest(@Param("tagIDS") List<String> tagIDS,
                                               Pageable pageable);
}
