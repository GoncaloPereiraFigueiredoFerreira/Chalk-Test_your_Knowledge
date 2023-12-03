package pt.uminho.di.chalktyk.services;

import org.springframework.stereotype.Service;
import pt.uminho.di.chalktyk.models.nonrelational.courses.Course;
import pt.uminho.di.chalktyk.models.nonrelational.users.Specialist;
import pt.uminho.di.chalktyk.models.nonrelational.users.Student;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

import java.util.List;

@Service
public interface ICoursesService {

    /**
     * Creates a course
     *
     * @param course basic properties of the course
     * @return identifier of the course
     * @throws BadInputException when a property of the course is not valid, like empty name,
     *                           invalid institution id (if given), or an invalid (specialist) owner id
     */
    String createCourse(Course course) throws BadInputException;

    /**
     * Retrieves course using its identifier
     * @param courseId course identifier
     * @return course that has the given identifier
     * @throws NotFoundException if the course does not exist
     */
    Course getCourseById(String courseId) throws NotFoundException;

    /**
     * Add specialists to course
     * @param courseId identifier of the course
     * @param specialistsIds list of specialists identifiers
     */
    void addSpecialistsToCourse(String courseId, List<String> specialistsIds) throws NotFoundException;

    /**
     * Add students to course.
     * @param courseId identifier of the course
     * @param studentsIds list of students identifiers
     */
    void addStudentsToCourse(String courseId, List<String> studentsIds) throws NotFoundException;

    /**
     * Remove specialists from course
     * @param courseId identifier of the course
     * @param specialistsIds list of specialists identifiers
     */
    void removeSpecialistsFromCourse(String courseId, List<String> specialistsIds) throws NotFoundException;

    /**
     * Remove students from course.
     * @param courseId identifier of the course
     * @param studentsIds list of students identifiers
     */
    void removeStudentsFromCourse(String courseId, List<String> studentsIds) throws NotFoundException;

    /**
     * Update course basic information
     * @param course basic course information
     */
    void updateCourse(Course course) throws BadInputException, NotFoundException;

    /**
     * Get list of students that are associated with a specific course.
     *
     * @param courseId     course identifier
     * @param page         index of the page
     * @param itemsPerPage number of items each page has
     * @return list of students that are associated with a specific course.
     */
    List<Student> getCourseStudents(String courseId, int page, int itemsPerPage) throws NotFoundException;

    /**
     * Get list of specialists that are associated with a specific course.
     *
     * @param courseId     course identifier
     * @param page         index of the page
     * @param itemsPerPage number of items each page has
     * @return list of specialists that are associated with a specific course.
     */
    List<Specialist> getCourseSpecialists(String courseId, int page, int itemsPerPage) throws NotFoundException;

    /**
     * Get list of courses that a specific student is associated with
     *
     * @param studentId    identifier of the student
     * @param page         index of the page
     * @param itemsPerPage number of courses each page has
     * @return list of courses that a specific student is associated with
     */
    List<Course> getStudentCourses(String studentId, int page, int itemsPerPage) throws NotFoundException;

    /**
     * Get list of courses that a specific specialist is associated with
     *
     * @param specialistId identifier of the specialist
     * @param page         index of the page
     * @param itemsPerPage number of courses each page has
     * @return list of courses that a specific specialist is associated with
     */
    List<Course> getSpecialistCourses(String specialistId, int page, int itemsPerPage) throws NotFoundException;

    /**
     * Get list of courses that a specific institution is associated with
     *
     * @param institutionId identifier of the institution
     * @param page          index of the page
     * @param itemsPerPage  number of courses each page has
     * @return list of courses that a specific institution is associated with
     */
    List<Course> getInstitutionCourses(String institutionId, int page, int itemsPerPage) throws NotFoundException;
}
