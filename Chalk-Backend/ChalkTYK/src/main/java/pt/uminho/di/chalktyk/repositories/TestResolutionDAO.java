package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.tests.TestResolution;

import java.util.List;

@Repository
public interface TestResolutionDAO extends JpaRepository<TestResolution,String> {
    @Query(value = "select tr from TestResolution tr where tr.test.id = :testId")
    Page<TestResolution> getTestResolutions(@Param("testId") String testId, Pageable pageable);

    @Query(value = "select tr from TestResolution tr where tr.test.id = :testId")
    List<TestResolution> getTestResolutions(@Param("testId") String testId);

    @Query(value = "SELECT COUNT(*) FROM TestResolution tr where tr.test.id = :testId")
    int countTotalSubmissionsForTest(@Param("testId") String testId);

    @Query(value = "SELECT COUNT(DISTINCT studentid) FROM test_resolution WHERE testid = :testId", nativeQuery = true)
    int countDistinctSubmissionsForTest(@Param("testId") String testId);

    @Query(value = "SELECT COUNT(*) FROM test_resolution WHERE studentid = :studentId AND testid = :testId", nativeQuery = true)
    int countStudentSubmissionsForTest(@Param("studentId") String studentId, @Param("testId") String testId);

    @Query(value = "SELECT id FROM test_resolution WHERE studentid = :studentId AND testid = :testId", nativeQuery = true)
    List<String> getStudentTestResolutionsIds(@Param("testId") String testId, @Param("studentId") String studentId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM TestResolution r WHERE r.test.id = :testId")
    boolean existsTestResolutions(@Param("testId") String testId);

    @Query(value = "SELECT DISTINCT studentid FROM test_resolution WHERE testid = :testId", nativeQuery = true)
    List<String> getDistinctStudentsForTest(@Param("testId") String testId);
    
    @Query("SELECT r FROM TestResolution r JOIN FETCH r.student s WHERE r.student.id = :studentId AND r.test.id = :testId AND r.submissionNr = (SELECT MAX(r2.submissionNr) FROM TestResolution r2 WHERE r2.test.id = :testId AND r.student.id = r2.student.id)")
    TestResolution getStudentLastResolution(@Param("studentId") String studentId, @Param("testId") String testId);

}
