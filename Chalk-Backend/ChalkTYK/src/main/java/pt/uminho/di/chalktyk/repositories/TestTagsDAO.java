package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.tests.TestTag;
import pt.uminho.di.chalktyk.models.tests.TestTagPK;

import java.util.List;

@Repository
public interface TestTagsDAO extends JpaRepository<TestTag, TestTagPK> {
    @Query(value = "select tg from TestTag tg where tg.testTagPK.test.id = :testId")
    List<TestTag> getTestTags(@Param("testId") String testId);
}
