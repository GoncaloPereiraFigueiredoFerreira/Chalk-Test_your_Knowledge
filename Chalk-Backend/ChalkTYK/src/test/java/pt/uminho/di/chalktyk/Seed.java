package pt.uminho.di.chalktyk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pt.uminho.di.chalktyk.models.nonrelational.courses.Course;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseSolution;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseStatement;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Item;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.StringItem;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice.Mctype;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice.MultipleChoiceData;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice.MultipleChoiceExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice.MultipleChoiceResolutionItem;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice.MultipleChoiceRubric;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.OAStandard;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.models.nonrelational.tests.DeliverDateTest;
import pt.uminho.di.chalktyk.models.nonrelational.tests.LiveTest;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestGroup;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolution;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolutionGroup;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolutionStatus;
import pt.uminho.di.chalktyk.models.nonrelational.users.Specialist;
import pt.uminho.di.chalktyk.models.nonrelational.users.Student;
import pt.uminho.di.chalktyk.models.relational.TagSQL;
import pt.uminho.di.chalktyk.models.relational.VisibilitySQL;
import pt.uminho.di.chalktyk.services.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@SpringBootTest
public class Seed {
    private final IInstitutionsService institutionsService;
    private final IStudentsService studentsService;
    private final ISpecialistsService specialistsService;
    private final ICoursesService coursesService;
    private final ITestsService testsService;
    private final ITagsService tagsService;
    private final IExercisesService exercisesService;

    @Autowired
    public Seed(IInstitutionsService institutionsService, IStudentsService studentsService, ISpecialistsService specialistsService, ICoursesService coursesService,
                ITestsService testsService, ITagsService tagsService, IExercisesService exercisesService){
        this.institutionsService = institutionsService;
        this.studentsService = studentsService;
        this.specialistsService = specialistsService;
        this.coursesService = coursesService;
        this.testsService = testsService;
        this.tagsService = tagsService;
        this.exercisesService = exercisesService;
    }

    @Test 
    public void seed() throws BadInputException, NotFoundException {
        Institution inst = new Institution("Greendale");
        institutionsService.createInstitution(inst);

        //addInstitution();
        Student st1 = new Student(null, "Jeff Winger", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "jwinger@gmail.com", 
                "none #1", null);
        Student st3 = new Student(null, "Abed Nadir", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "coolabedfilms@gmail.com", 
                "none #3", null);
        String student1 = studentsService.createStudent(st1);
        String student2 = addStudentAnnie();
        String student3 = studentsService.createStudent(st3);
        List<String> l1 = new ArrayList<>(); l1.add(student1);
        List<String> l2 = new ArrayList<>(); l2.add(student2);
        // TODO: this doesn't work
        //institutionsService.addStudentsToInstitution("Greendale", l1);

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
        coursesService.addStudentsToCourse(course2, l1);
        coursesService.addStudentsToCourse(course3, l2);

        // exercise 
        TagSQL tag1 = tagsService.createTag("Espanol","/");
        TagSQL tag2 = tagsService.createTag("NewEspanol","/");
        TagSQL tag3 = tagsService.createTag("OldEspanol","/");

        ExerciseSolution exerciseSolution = createMCSolution();
        ExerciseRubric exerciseRubric = createMCRubric();
        ConcreteExercise ex1 = createMCExercise(specialist1, course1, tag1);
        ConcreteExercise ex2 = createMCExercise(specialist1, course1, tag2);
        ConcreteExercise ex3 = createMCExercise(specialist1, course1, tag3);
        exercisesService.createExercise(ex1, exerciseRubric, exerciseSolution, new ArrayList<>(), VisibilitySQL.PUBLIC);
        exercisesService.createExercise(ex2, exerciseRubric, exerciseSolution, new ArrayList<>(), VisibilitySQL.PUBLIC);
        exercisesService.createExercise(ex3, exerciseRubric, exerciseSolution, new ArrayList<>(), VisibilitySQL.PUBLIC);

        // tests
        TestGroup tg1 = new TestGroup(List.of(ex1, ex2), "instructions1", Float.valueOf(1));
        TestGroup tg2 = new TestGroup(List.of(ex3), "instructions2", Float.valueOf(2));

        pt.uminho.di.chalktyk.models.nonrelational.tests.Test t1 = new pt.uminho.di.chalktyk.models.nonrelational.tests.Test(null, specialist1,
                        null, course1, "TEST #1", null, Float.valueOf(20), 
                        "?", LocalDateTime.now(), LocalDateTime.now(), List.of(tg1, tg2));
        LiveTest t2 = new LiveTest(null, specialist2,
                        null, course2, "TEST #2", null, Float.valueOf(100), 
                        "?", LocalDateTime.now(), LocalDateTime.now().plusHours(1), null,
                        LocalDateTime.now().plusDays(1), Duration.ofHours(1), Duration.ofMinutes(5));
        DeliverDateTest t3 = new DeliverDateTest(null, specialist3,
                        null, course3, "TEST #3", null, Float.valueOf(5), 
                        "?", LocalDateTime.now(), LocalDateTime.now().plusHours(1), null, 
                        LocalDateTime.now().plusDays(1));
        String test1 = testsService.createTest(VisibilitySQL.PUBLIC, t1);
        String test2 = testsService.createTest(VisibilitySQL.PRIVATE, t2);
        String test3 = testsService.createTest(VisibilitySQL.NOT_LISTED, t3);

        // test resolutions
        TestResolutionGroup trg = new TestResolutionGroup(null, 4.0F);

        testsService.startTest(test1, student1);
        testsService.startTest(test2, student2);
        testsService.startTest(test3, student3);

        /* 
        TestResolution tr1 = new TestResolution(null, student1, test1, TestResolutionStatus.ONGOING, LocalDateTime.now(), null, 0, List.of(trg, trg), Float.valueOf(0));
        TestResolution tr2 = new TestResolution(null, student2, test2, TestResolutionStatus.REVISED, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 1, List.of(), Float.valueOf(0));
        TestResolution tr3 = new TestResolution(null, student3, test3, TestResolutionStatus.NOT_REVISED, LocalDateTime.now(), LocalDateTime.now().plusDays(1), 2, List.of(trg), Float.valueOf(0));
        testsService.createTestResolution(test1, tr1);
        testsService.createTestResolution(test2, tr2);
        testsService.createTestResolution(test3, tr3);
        */
    }

    public String addSpecialistChang() throws BadInputException {
        Specialist s = new Specialist(null, "Senor Chang", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg",
                "senor@chang.com", "#1", null);
        return specialistsService.createSpecialist(s);
    }

    public String addStudentAnnie() throws BadInputException {
        Student st = new Student(null, "Annie Edison", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "annie_edison@gmail.com",
                "none #2", null);
       return studentsService.createStudent(st);
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







    @Test
    public void test() throws BadInputException, NotFoundException {
        testsService.manualCorrectionForExercise("6581d414229d355837e85bba", "6581d414229d355837e85bb5", 4.0F, null);
    }




















    // exercício
    private ExerciseSolution createMCSolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        HashMap<Integer,MultipleChoiceResolutionItem> itemResolutions = new HashMap<>();
        itemResolutions.put(1,option1);
        itemResolutions.put(2,option2);
        itemResolutions.put(3,option3);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutions);
        return new ExerciseSolution(null,multipleChoiceData);
    }

    private ExerciseRubric createMCRubric(){
        HashMap<Integer,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        mcRubricMap.put(1,createOARubric());
        mcRubricMap.put(2,createOARubric());
        mcRubricMap.put(3,createOARubric());
        return new MultipleChoiceRubric(mcRubricMap,1.0F,0.0F);
    }

    private OpenAnswerRubric createOARubric(){
        OAStandard oaStandardMax = new OAStandard("Trató de resolver","Aquí todos pasan la prueba",2.0F);
        OAStandard oaStandardMin = new OAStandard("Ni siquiera intentó resolver","No puedo robar tanto",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandardMin,oaStandardMax);
        return new OpenAnswerRubric(List.of(new OACriterion(oaStandards, "Há intentado")));
    }

    private MultipleChoiceExercise createMCExercise(String specialistId, String courseId, TagSQL tag){
        HashMap<Integer,Item> itemResolutions = new HashMap<>();
        itemResolutions.put(1,new StringItem("Là"));
        itemResolutions.put(2,new StringItem("Ali"));
        itemResolutions.put(3,new StringItem("There"));

        MultipleChoiceExercise exercise = new MultipleChoiceExercise(itemResolutions, Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION);
        exercise.setStatement(new ExerciseStatement("Donde está la biblioteca","",""));
        exercise.setTitle("Pregunta de Español MC");
        exercise.setPoints(3.0F);
        exercise.setSpecialistId(specialistId);
        exercise.setCourseId(courseId);
        exercise.setTags(Set.of(tag));
        return exercise;
    }
}
