package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.uminho.di.chalktyk.models.relational.CourseSQL;
import pt.uminho.di.chalktyk.models.relational.SpecialistSQL;
import pt.uminho.di.chalktyk.models.relational.StudentSQL;

@Repository
public interface CourseSqlDAO extends JpaRepository<CourseSQL, String> {
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

    @Query(value = "select s from StudentSQL s join s.courses c where c.id = :courseId")
    Page<StudentSQL> getCourseStudents(@Param("courseId") String courseId, Pageable pageable);

    @Query(value = "select c.specialists from CourseSQL c where c.id = :courseId")
    Page<SpecialistSQL> getCourseSpecialists(@Param("courseId") String courseId, Pageable pageable);

    @Query(value = "select s.courses from StudentSQL s where s.id = :studentId")
    Page<CourseSQL> getStudentCourses(@Param("studentId") String studentId, Pageable pageable);

    @Query(value = "select c from CourseSQL c join c.specialists s where s.id = :specialistId")
    Page<CourseSQL> getSpecialistCourses(@Param("specialistId") String specialistId, Pageable pageable);

    @Query(value = "select c from CourseSQL c where c.institution.id = :institutionId")
    Page<CourseSQL> getInstitutionCourses(@Param("institutionId") String institutionId, Pageable pageable);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM specialist_course WHERE courseid = :courseId AND specialistid = :specialistId", nativeQuery = true)
    boolean isCourseSpecialist(@Param("courseId") String courseId, @Param("specialistId") String specialistId);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM student_course WHERE courseid = :courseId AND specialistid = :studentId", nativeQuery = true)
    boolean isCourseStudent(@Param("courseId") String courseId, @Param("studentId") String studentId);
}
