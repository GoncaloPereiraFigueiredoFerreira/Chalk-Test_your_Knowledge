package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.Test;

@Repository
public interface TestDAO extends JpaRepository<Test, String> {
    @Query("SELECT test FROM Test test WHERE" +
            " CASE WHEN :matchAllTags = true THEN (:tagIDS is null or test.id IN (SELECT tgs.testTagPK.test.id FROM TestTag tgs WHERE tgs.testTagPK.tag.id IN :tagIDS GROUP BY tgs.testTagPK.test.id HAVING COUNT(DISTINCT tgs.testTagPK.test.id) = :tagsSize))"+
            " ELSE (:tagIDS is null or test.id IN (SELECT tgs.testTagPK.test.id FROM TestTag tgs WHERE tgs.testTagPK.tag.id IN :tagIDS GROUP BY tgs.testTagPK.test.id HAVING COUNT(DISTINCT tgs.testTagPK.test.id) > 0)) END and"+
            " :visibilityType is null or test.visibility = :visibilityType and" +
            " :institutionId is null or test.institution = (SELECT i FROM Institution i WHERE i.name=:institutionId) and" +
            " :courseId is null or test.course = (SELECT c FROM Course c WHERE c.id=:courseId) and" +
            " :specialistID is null or test.specialist = (SELECT s FROM Specialist s WHERE s.id=:specialistID) and" +
            " :title is null or test.title = :title")
    Page<Test> getTests(Pageable pageable,
                           @Param("tagIDS") java.util.List<String> tagIDS, @Param("tagsSize") int tagsSize, @Param("matchAllTags")  boolean matchAllTags,
                           @Param("visibilityType") Visibility visibilityType,
                           @Param("institutionId") String institutionId,
                           @Param("courseId") String courseId,
                           @Param("specialistID") String specialistId,
                           @Param("title")  String title);
}
