package pt.uminho.di.chalktyk.repositories.relational;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.uminho.di.chalktyk.models.relational.TestTagsPkSQL;
import pt.uminho.di.chalktyk.models.relational.TestTagsSQL;

@Repository
public interface TestTagsSqlDAO extends JpaRepository<TestTagsSQL, TestTagsPkSQL> {
    @Query(value = "select * from test_tags where testid = :testId", nativeQuery = true)
    List<TestTagsSQL> getTestTags(@Param("testId") String testId);
}
