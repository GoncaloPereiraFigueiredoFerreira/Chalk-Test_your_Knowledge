package pt.uminho.di.chalktyk;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pt.uminho.di.chalktyk.models.nonrelational.courses.Course;
import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolution;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolutionStatus;
import pt.uminho.di.chalktyk.models.nonrelational.users.Specialist;
import pt.uminho.di.chalktyk.models.nonrelational.users.Student;
import pt.uminho.di.chalktyk.models.relational.Visibility;
import pt.uminho.di.chalktyk.services.ICoursesService;
import pt.uminho.di.chalktyk.services.IInstitutionsService;
import pt.uminho.di.chalktyk.services.ISpecialistsService;
import pt.uminho.di.chalktyk.services.IStudentsService;
import pt.uminho.di.chalktyk.services.ITestResolutionsService;
import pt.uminho.di.chalktyk.services.ITestsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@SpringBootTest
public class Seed {
    private final IInstitutionsService institutionsService;
    private final IStudentsService studentsService;
    private final ISpecialistsService specialistsService;
    private final ICoursesService coursesService;
    private final ITestsService testsService;
    private final ITestResolutionsService resolutionsService;

    @Autowired
    public Seed(IInstitutionsService institutionsService, IStudentsService studentsService, ISpecialistsService specialistsService, ICoursesService coursesService,
            ITestsService testsService, ITestResolutionsService resolutionsService){
        this.institutionsService = institutionsService;
        this.studentsService = studentsService;
        this.specialistsService = specialistsService;
        this.coursesService = coursesService;
        this.testsService = testsService;
        this.resolutionsService = resolutionsService;
    }

    @Test 
    public void seed() throws BadInputException{
        addInstitution();
        Student st1 = new Student(null, "Jeff Winger", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "jwinger@gmail.com", 
                "none #1", null);
        Student st2 = new Student(null, "Annie Edison", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "annie_edison@gmail.com", 
                "none #2", null);
        Student st3 = new Student(null, "Abed Nadir", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "coolabedfilms@gmail.com", 
                "none #3", null);
        String student1 = studentsService.createStudent(st1);
        String student2 = studentsService.createStudent(st2);
        String student3 = studentsService.createStudent(st3);

        // specialists
        Specialist s2 = new Specialist(null, "Professor Ian Duncan", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg", 
            "iduncan@gmail.com", "#2", null);
        String specialist1 = this.addSpecialistChang();
        String specialist2 = specialistsService.createSpecialist(s2);
        String specialist3 = addSpecialistWhitman();

        // courses
        Course c2 = new Course(null, "Anthropology", "Greendale Community College", "#2", specialist2);
        Course c3 = new Course(null, "Seize the Day", "Greendale Community College", "#3", specialist3);
        String course1 = this.addCourse(specialist1);
        String course2 = coursesService.createCourse(c2);
        String course3 = coursesService.createCourse(c3);

        // tests
        pt.uminho.di.chalktyk.models.nonrelational.tests.Test t1 = new pt.uminho.di.chalktyk.models.nonrelational.tests.Test(null, specialist1,
                        null, course1, "TEST #1", null, Float.valueOf(20), 
                        "?", LocalDateTime.now(), LocalDateTime.now().plusHours(1), null);
        pt.uminho.di.chalktyk.models.nonrelational.tests.Test t2 = new pt.uminho.di.chalktyk.models.nonrelational.tests.Test(null, specialist2,
                        null, course2, "TEST #2", null, Float.valueOf(100), 
                        "?", LocalDateTime.now(), LocalDateTime.now().plusHours(1), null);
        pt.uminho.di.chalktyk.models.nonrelational.tests.Test t3 = new pt.uminho.di.chalktyk.models.nonrelational.tests.Test(null, specialist3,
                        null, course3, "TEST #3", null, Float.valueOf(5), 
                        "?", LocalDateTime.now(), LocalDateTime.now().plusHours(1), null);
        String test1 = testsService.createTest(Visibility.PUBLIC, t1);
        String test2 = testsService.createTest(Visibility.PRIVATE, t2);
        String test3 = testsService.createTest(Visibility.NOT_LISTED, t3);


        // test resolutions
        TestResolution tr1 = new TestResolution(null, student1, test1, TestResolutionStatus.ONGOING, LocalDateTime.now(), null, 0, null);
        TestResolution tr2 = new TestResolution(null, student2, test2, TestResolutionStatus.REVISED, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 1, null);
        TestResolution tr3 = new TestResolution(null, student3, test3, TestResolutionStatus.NOT_REVISED, LocalDateTime.now(), LocalDateTime.now().plusDays(1), 2, null);
        resolutionsService.createTestResolution(tr1);
        resolutionsService.createTestResolution(tr2);
        resolutionsService.createTestResolution(tr3);
    }

    @Test
    public void addInstitution() throws BadInputException {
        Institution inst = new Institution("Greendale Community College", "não quero", "some_image.jpg", null);
        institutionsService.createInstitution(inst);
    }
    public String addSpecialistChang() throws BadInputException {
        Specialist s = new Specialist(null, "Senor Chang", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg",
                "senor@chang.com", "#1", null);
        return specialistsService.createSpecialist(s);
    }

    public String addSpecialistWhitman() throws BadInputException {
        Specialist s = new Specialist(null, "Professor Whitman", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg",
                "whitman@yahoo.com", "#3", null);
        return specialistsService.createSpecialist(s);
    }

    public String addCourse(String specialistId) throws BadInputException {
        Course c1 = new Course(null, "Spanish 101", "Greendale Community College", "#1", specialistId);
        return coursesService.createCourse(c1);
    }

    @Test void test() throws BadInputException{
    }
}
