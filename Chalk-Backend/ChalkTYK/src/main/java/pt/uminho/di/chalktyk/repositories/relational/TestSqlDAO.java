package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Visibility;
import pt.uminho.di.chalktyk.models.relational.ExerciseSQL;
import pt.uminho.di.chalktyk.models.relational.TestSQL;

@Repository
public interface TestSqlDAO extends JpaRepository<TestSQL, String>{
    @Query("SELECT test FROM TestSQL test WHERE" +
            " CASE WHEN :matchAllTags = true THEN (:tagIDS is null or test.id IN (SELECT tgs.testId FROM TestTagsSQL tgs WHERE tgs.tagId IN :tagIDS GROUP BY tgs.testId HAVING COUNT(DISTINCT tgs.tagId) = :tagsSize))"+
            " ELSE (:tagIDS is null or test.id IN (SELECT tgs.testId FROM TestTagsSQL tgs WHERE tgs.tagId IN :tagIDS GROUP BY tgs.testId HAVING COUNT(DISTINCT tgs.tagId) > 0)) END and"+
            " :visibilityType is null or test.visibility = :visibilityType and" +
            " :institutionId is null or test.institution = (SELECT i FROM InstitutionSQL i WHERE i.id=:institutionId) and" +
            " :courseId is null or test.course = (SELECT c FROM CourseSQL c WHERE c.id=:courseId) and" +
            " :specialistID is null or test.specialist = (SELECT s FROM SpecialistSQL s WHERE s.id=:specialistID) and" +
            " :title is null or test.title = :title")
    Page<TestSQL> getTests(Pageable pageable,
                                   @Param("tagIDS") java.util.List<String> tagIDS, @Param("tagsSize") int tagsSize, @Param("matchAllTags")  boolean matchAllTags,
                                   @Param("visibilityType") Visibility visibilityType,
                                   @Param("institutionId") String institutionId,
                                   @Param("courseId") String courseId,
                                   @Param("specialistID") String specialistId,
                                   @Param("title")  String title);
}
