package pt.uminho.di.chalktyk.repositories.relational;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.uminho.di.chalktyk.models.relational.TestResolution;

@Repository
public interface TestResolutionSqlDAO extends JpaRepository<TestResolution, String> {
    @Query(value = "SELECT COUNT(*) FROM test_resolution WHERE studentid = :studentId AND testid = :testId", nativeQuery = true)
    int countStudentSubmissionsForTest(@Param("studentId") String studentId, @Param("testId") String testId);

    @Query(value = "SELECT id FROM test_resolution WHERE studentid = :studentId AND testid = :testId", nativeQuery = true)
    List<String> getStudentTestResolutionsIds(@Param("testId") String testId, @Param("studentId") String studentId);
}