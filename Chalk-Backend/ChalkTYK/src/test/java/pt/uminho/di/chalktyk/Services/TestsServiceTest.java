package pt.uminho.di.chalktyk.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import pt.uminho.di.chalktyk.Seed;
import pt.uminho.di.chalktyk.services.ICoursesService;
import pt.uminho.di.chalktyk.services.IExercisesService;
import pt.uminho.di.chalktyk.services.IInstitutionsService;
import pt.uminho.di.chalktyk.services.ISpecialistsService;
import pt.uminho.di.chalktyk.services.IStudentsService;
import pt.uminho.di.chalktyk.services.ITagsService;
import pt.uminho.di.chalktyk.services.ITestsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@SpringBootTest
@Transactional
public class TestsServiceTest {
    private final Seed seed;
    private final ITestsService testsService;
    private final ICoursesService coursesService;
    private final ISpecialistsService specialistsService;
    private final IExercisesService exercisesService;
    private final IInstitutionsService institutionsService;
    private final IStudentsService studentsService;
    private final ITagsService tagsService;

    @Autowired
    public TestsServiceTest(ITestsService testsService, ICoursesService coursesService, ISpecialistsService specialistsService, 
                            IExercisesService exercisesService, IInstitutionsService institutionsService, IStudentsService studentsService, 
                            ITagsService tagsService){
        this.testsService = testsService;
        this.coursesService = coursesService;
        this.specialistsService = specialistsService;
        this.exercisesService = exercisesService;
        this.institutionsService = institutionsService;
        this.studentsService = studentsService;
        this.tagsService = tagsService;
        this.seed = new Seed(institutionsService,studentsService,specialistsService,coursesService,testsService, tagsService,exercisesService);
    }

    private String specialistId, specialist2Id, courseId, course2Id, studentId, student2Id;

    @BeforeEach
    public void setup() throws BadInputException {
        this.specialistId = seed.addSpecialistChang();
        this.specialist2Id = seed.addSpecialistWhitman();
        this.courseId = seed.addCourse(specialistId);
        this.course2Id = seed.addCourse2(specialist2Id);
        this.studentId = seed.addStudentAnnie();
        this.student2Id = seed.addStudentGeorge();
    }


    /* ****** TESTS ****** */

    @Test
    public void getTestFailWithNotFound(){
        try {
            testsService.getTestById("DoesNotExistID");
            assert false;
        } catch (NotFoundException e) {
            assert true;
        }
    }
}
