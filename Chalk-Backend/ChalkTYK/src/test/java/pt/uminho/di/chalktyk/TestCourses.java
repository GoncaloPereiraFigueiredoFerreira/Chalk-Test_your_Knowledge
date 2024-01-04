package pt.uminho.di.chalktyk;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.services.ICoursesService;
import pt.uminho.di.chalktyk.services.IInstitutionsService;
import pt.uminho.di.chalktyk.services.ISpecialistsService;
import pt.uminho.di.chalktyk.services.IStudentsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class TestCourses {
    private final EntityManager entityManager;
    private final IInstitutionsService institutionsService;
    private final ICoursesService coursesService;
    private final ISpecialistsService specialistsService;
    private final IStudentsService studentsService;

    @Autowired
    public TestCourses(EntityManager entityManager, IInstitutionsService institutionsService, ICoursesService coursesService, ISpecialistsService specialistsService, IStudentsService studentsService) {
        this.entityManager = entityManager;
        this.institutionsService = institutionsService;
        this.coursesService = coursesService;
        this.specialistsService = specialistsService;
        this.studentsService = studentsService;
    }

    @Test
    void createCourse() throws BadInputException, NotFoundException {
        try {
            // creates an institution
            Institution institution = new Institution("U. Minho", "Qualquer coisa...", "uminho.pt/images/logo.png");
            InstitutionManager institutionManager = new InstitutionManager(null, "InstManager1", "instmanager1/photo.png", "instmanager1@gmail.com", "instmanager1 description", null);
            institutionsService.createInstitutionAndManager(institution, institutionManager);
            String institutionId = institution.getName();

            // creates a specialist
            Specialist specialist = new Specialist(null, "Ze", "ze.png", "ze@hotmail.com", "Lindo");
            specialistsService.createSpecialist(specialist);

            // adds the specialist to the institution
            institutionsService.addSpecialistsToInstitution(institutionId, List.of(specialist.getId()));

            // creates the course
            Course course = new Course(null, "Basic Japanese", "hiragana, katakana and basic sentences.", specialist.getId(), Set.of(specialist), institution);
            String courseId = coursesService.createCourse(course);

            // gets course
            try {
                course = coursesService.getCourseById(courseId);
            } catch (NotFoundException nfe) {
                // should not enter here
                assert false;
            }

            assert course != null
                    && course.getName().equals("Basic Japanese")
                    && Objects.equals(course.getOwnerId(), specialist.getId())
                    && Objects.equals(institution.getName(), course.getInstitutionId())
                    && Set.of(courseId).containsAll(coursesService.getInstitutionCourses(institutionId, 0, 10).stream().map(Course::getId).collect(Collectors.toSet()));
        }catch (Exception ignored){
            assert false;
        }
    }

    @Test
    void createCourseNoInstitution() {
        try {
            Specialist specialist = new Specialist(null, "Ze", "ze.png", "ze@hotmail.com", "Lindo", null);
            specialistsService.createSpecialist(specialist);
            Course course = new Course(null, "Basic Japanese", "hiragana, katakana and basic sentences.", specialist.getId(), Set.of(specialist));
            String courseId = coursesService.createCourse(course);

            // gets course
            try {
                course = coursesService.getCourseById(courseId);
            } catch (NotFoundException nfe) {
                // should not enter here
                assert false;
            }

            assert course != null
                    && course.getName().equals("Basic Japanese")
                    && Objects.equals(course.getOwnerId(), specialist.getId())
                    && Objects.equals(null, course.getInstitutionId());
        }catch (BadInputException bie){
            assert false;
        }
    }

    @Test
    void existsCourse(){
        try {
            Specialist specialist = new Specialist(null, "Ze", "ze.png", "ze@hotmail.com", "Lindo", null);
            specialistsService.createSpecialist(specialist);
            Course course = new Course(null, "Basic Japanese", "hiragana, katakana and basic sentences.", specialist.getId(), Set.of(specialist));
            String courseId = coursesService.createCourse(course);
            assert coursesService.existsCourseById(courseId);
        }catch (Exception ignored){
            assert false;
        }
    }

    @Test
    void addSpecialistToAndRemoveSpecialistFromCourse(){
        try {
            Specialist specialist = new Specialist(null, "Ze", "ze.png", "ze@hotmail.com", "Lindo", null);
            String specialistId = specialistsService.createSpecialist(specialist);
            Course course = new Course(null, "Basic Japanese", "hiragana, katakana and basic sentences.", specialistId, Set.of(specialist));
            String courseId = coursesService.createCourse(course);
            assert coursesService.existsCourseById(courseId);

            // checks that the first specialist is associated with the course
            assert coursesService.checkSpecialistInCourse(courseId, specialistId);

            // create two new specialists
            Specialist specialist2 = new Specialist(null, "Luis", "luis.png", "luis@hotmail.com", "...", null);
            Specialist specialist3 = new Specialist(null, "Xiko", "xiko.png", "xiko@hotmail.com", "......", null);
            String specialist2Id = specialistsService.createSpecialist(specialist2);
            String specialist3Id = specialistsService.createSpecialist(specialist3);

            // creates another course with specialist2 as its owner
            Course course2 = new Course(null, "Advanced Japanese", "advanced stuff from japanese.", specialist2Id, Set.of(specialist2));
            String course2Id = coursesService.createCourse(course2);

            // check that specialist2 and specialist3 are not associated with the course
            assert !coursesService.checkSpecialistInCourse(courseId, specialist2Id)
                && !coursesService.checkSpecialistInCourse(courseId, specialist3Id);

            // adds specialist2 and specialist3 to the course
            coursesService.addSpecialistsToCourse(courseId, List.of(specialist2Id, specialist3Id));

            // check that all 3 specialists are associated with the course
            assert coursesService.checkSpecialistInCourse(courseId, specialistId)
                && coursesService.checkSpecialistInCourse(courseId, specialist2Id)
                && coursesService.checkSpecialistInCourse(courseId, specialist3Id);

            // same check but using another functions
            entityManager.clear();
            course = coursesService.getCourseById(courseId);
            Set<String> specialistIds = course.getSpecialists().stream().map(User::getId).collect(Collectors.toSet());
            Set<String> specialistIds2 = coursesService.getCourseSpecialists(courseId, 0, 10).stream().map(User::getId).collect(Collectors.toSet());
            assert specialistIds.size() == 3;
            assert specialistIds.containsAll(specialistIds2);
            assert specialistIds2.containsAll(specialistIds);

            // checks courses of specialist2
            entityManager.clear();
            Set<String> specialist2Courses = coursesService.getSpecialistCourses(specialist2Id, 0, 10).stream().map(Course::getId).collect(Collectors.toSet()),
                        specialist2CoursesHeShouldHave = Set.of(courseId, course2Id);
            assert specialist2Courses.containsAll(specialist2CoursesHeShouldHave) && specialist2CoursesHeShouldHave.containsAll(specialist2Courses);

            // checks courses of specialist3
            Set<String> specialist3Courses = coursesService.getSpecialistCourses(specialist3Id, 0, 10).stream().map(Course::getId).collect(Collectors.toSet()),
                        specialist3CoursesItShouldHave = Set.of(courseId);
            assert specialist3Courses.containsAll(specialist3CoursesItShouldHave) && specialist3CoursesItShouldHave.containsAll(specialist3Courses);

            // removes specialist2 and specialist3
            coursesService.removeSpecialistsFromCourse(courseId, List.of(specialist2Id, specialist3Id));
            // checks that they were effectively removed
            assert !coursesService.checkSpecialistInCourse(courseId, specialist2Id)
                    && !coursesService.checkSpecialistInCourse(courseId, specialist3Id);

            // cannot remove specialist that is the owner
            coursesService.removeSpecialistsFromCourse(courseId, List.of(specialistId));
            // checks that the specialist was not removed
            assert coursesService.checkSpecialistInCourse(courseId, specialistId);
        }catch (Exception ignored){
            assert false;
        }
    }

    @Test
    void addStudentToAndRemoveStudentFromCourse(){
        try {
            Specialist specialist = new Specialist(null, "Ze", "ze.png", "ze@hotmail.com", "Lindo", null);
            String specialistId = specialistsService.createSpecialist(specialist);
            Course course = new Course(null, "Basic Japanese", "hiragana, katakana and basic sentences.", specialistId, Set.of(specialist));
            String courseId = coursesService.createCourse(course);
            Course course2 = new Course(null, "Advanced Japanese", "advanced stuff from japanese.", specialistId, Set.of(specialist));
            String course2Id = coursesService.createCourse(course2);
            assert coursesService.existsCourseById(courseId) && coursesService.existsCourseById(course2Id);

            // create two new students
            Student student = new Student(null, "Luis", "luis.png", "luis@hotmail.com", "...");
            Student student2 = new Student(null, "Xiko", "xiko.png", "xiko@hotmail.com", "......");
            String studentId = studentsService.createStudent(student);
            String student2Id = studentsService.createStudent(student2);

            // check that student and student2 are not associated with the course
            assert !coursesService.checkStudentInCourse(courseId, studentId)
                    && !coursesService.checkStudentInCourse(courseId, student2Id);

            // adds student and student2 to the course
            coursesService.addStudentsToCourse(courseId, List.of(studentId, student2Id));

            // add student2 to course2
            coursesService.addStudentsToCourse(course2Id, List.of(student2Id));

            // check that the 2 students are associated with the course
            assert coursesService.checkStudentInCourse(courseId, studentId)
                    && coursesService.checkStudentInCourse(courseId, student2Id)
                    && Set.of(studentId, student2Id).containsAll(
                            coursesService.getCourseStudents(courseId, 0, 10).stream()
                                                                             .map(User::getId)
                                                                             .collect(Collectors.toSet()));

            entityManager.clear();

            // checks student courses
            Set<String> studentCourses = coursesService.getStudentCourses(studentId, 0, 10).stream().map(Course::getId).collect(Collectors.toSet()),
                        studentCoursesHeShouldHave = Set.of(courseId);
            assert studentCourses.containsAll(studentCoursesHeShouldHave) && studentCoursesHeShouldHave.containsAll(studentCourses);

            // checks student2 courses
            Set<String> student2Courses = coursesService.getStudentCourses(student2Id, 0, 10).stream().map(Course::getId).collect(Collectors.toSet()),
                        student2CoursesHeShouldHave = Set.of(courseId, course2Id);
            assert student2Courses.containsAll(student2CoursesHeShouldHave) && student2CoursesHeShouldHave.containsAll(student2Courses);

            // removes student and student2 from course
            coursesService.removeStudentsFromCourse(courseId, List.of(studentId, student2Id));
            // checks that they were effectively removed
            assert !coursesService.checkStudentInCourse(courseId, studentId)
                    && !coursesService.checkStudentInCourse(courseId, student2Id);
        }catch (Exception ignored){
            assert false;
        }
    }

    @Test
    void updateCourseBasicProperties() throws BadInputException, NotFoundException {
        try {
            // creates a specialist
            Specialist specialist = new Specialist(null, "Ze", "ze.png", "ze@hotmail.com", "Lindo");
            specialistsService.createSpecialist(specialist);

            // creates the course
            Course course = new Course(null, "Basic Japanese", "hiragana, katakana and basic sentences.", specialist.getId(), Set.of(specialist), null);
            String courseId = coursesService.createCourse(course);

            assert coursesService.existsCourseById(courseId);

            // update course
            String newName = "BASIC JAPANESE", newDescription = "some description.";
            coursesService.updateCourseBasicProperties(courseId, newName, newDescription);

            // gets course
            try {
                course = coursesService.getCourseById(courseId);
            } catch (NotFoundException nfe) {
                // should not enter here
                assert false;
            }

            assert course != null;
            System.out.println(course.getName());
            assert course.getName().equals(newName);
            assert course.getDescription().equals(newDescription);
            assert Objects.equals(course.getOwnerId(), specialist.getId());
            assert course.getInstitution() == null;
        }catch (Exception ignored){
            assert false;
        }
    }
}

