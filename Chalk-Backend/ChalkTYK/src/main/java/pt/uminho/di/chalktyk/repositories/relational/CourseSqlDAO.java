package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.relational.Course;

@Repository
public interface CourseSqlDAO extends JpaRepository<Course, String> {
    @Modifying
    @Query(value = "delete from Specialist_Course where specialistid = :specialistId and courseid = :courseId", nativeQuery = true)
    void removeSpecialistFromCourse(@Param("specialistId") String specialistId, @Param("courseId") String courseId);

    @Modifying
    @Query(value = "delete from Student_Course where studentid = :studentId and courseid = :courseId", nativeQuery = true)
    void removeStudentFromCourse(@Param("studentId") String studentId, @Param("courseId") String courseId);

    @Modifying
    @Query(value = "insert into specialist_course (courseid, specialistid) values (:courseId, :specialistId)", nativeQuery = true)
    void addSpecialistToCourse(@Param("specialistId") String specialistId, @Param("courseId") String courseId);

    @Modifying
    @Query(value = "insert into student_course (courseid, studentid) values (:courseId, :studentId)", nativeQuery = true)
    void addStudentToCourse(@Param("studentId") String studentId, @Param("courseId") String courseId);
}
