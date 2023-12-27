package pt.uminho.di.chalktyk;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.ExerciseSolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseStatement;
import pt.uminho.di.chalktyk.models.exercises.items.Item;
import pt.uminho.di.chalktyk.models.exercises.items.StringItem;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.Mctype;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceData;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceExercise;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceResolutionItem;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceRubric;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OAStandard;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.DeliverDateTest;
import pt.uminho.di.chalktyk.models.tests.LiveTest;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ReferenceExercise;
import pt.uminho.di.chalktyk.models.tests.TestGroup;
import pt.uminho.di.chalktyk.models.tests.TestResolution;
import pt.uminho.di.chalktyk.models.tests.TestResolutionGroup;
import pt.uminho.di.chalktyk.models.tests.TestResolutionStatus;
import pt.uminho.di.chalktyk.models.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;
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
        Institution inst = new Institution("Greendale", null, null);
        InstitutionManager instMan = new InstitutionManager(null, "Dean Pelton", null, "dpelton@gmail.com", "i'm a peanut bar", inst);
        institutionsService.createInstitutionAndManager(inst,instMan);

        //addInstitution();
        
        Student st1 = new Student(null, "Jeff Winger", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "jwinger@gmail.com", "none #1");
        Student st2 = new Student(null, "Annie Edison", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "annie_edison@gmail.com", "none #2");
        Student st3 = new Student(null, "Abed Nadir", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "coolabedfilms@gmail.com", "none #3");
        String student1 = studentsService.createStudent(st1);
        String student2 = studentsService.createStudent(st2);
        String student3 = studentsService.createStudent(st3);
        List<String> l1 = new ArrayList<>(); l1.add(student1);
        List<String> l2 = new ArrayList<>(); l2.add(student2);
        // TODO: this doesn't work
        //institutionsService.addStudentsToInstitution("Greendale", l1);

        // specialists
        Specialist s1 = new Specialist(null, "Senor Chang", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg","senor@chang.com", "#1", null);
        Specialist s2 = new Specialist(null, "Professor Ian Duncan", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg", "iduncan@gmail.com", "#2", null);
        Specialist s3 = new Specialist(null, "Professor Whitman", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg","whitman@yahoo.com", "#3", null);
        String specialist1 = specialistsService.createSpecialist(s1);
        String specialist2 = specialistsService.createSpecialist(s2);
        String specialist3 = specialistsService.createSpecialist(s3);

        // courses
        Course c1 = new Course(null, "Spanish 101", "#1", specialist1, null, null);
        Course c2 = new Course(null, "Anthropology", "#2", specialist2, null, null);
        Course c3 = new Course(null, "Seize the Day", "#3", specialist3, null, null);
        String course1 = coursesService.createCourse(c1);
        String course2 = coursesService.createCourse(c2);
        String course3 = coursesService.createCourse(c3);
        coursesService.addStudentsToCourse(course2, l1);
        coursesService.addStudentsToCourse(course3, l2);

        // exercise 
        Tag tag1 = tagsService.createTag("Espanol","/");
        Tag tag2 = tagsService.createTag("NewEspanol","/");
        Tag tag3 = tagsService.createTag("OldEspanol","/");

        ExerciseSolution exerciseSolution = createMCSolution();
        ExerciseRubric exerciseRubric = createMCRubric();
        Exercise ex1 = createMCExercise(s1, c1, tag1);
        Exercise ex2 = createMCExercise(s1, c1, tag2);
        Exercise ex3 = createMCExercise(s1, c1, tag3);
        exercisesService.createExercise(ex1, new ExerciseSolution(null, exerciseSolution.getData().clone()), exerciseRubric.clone(), Visibility.PUBLIC, List.of(tag1.getId()));
        exercisesService.createExercise(ex2, new ExerciseSolution(null, exerciseSolution.getData().clone()), exerciseRubric.clone(), Visibility.PUBLIC, List.of(tag1.getId(), tag3.getId()));
        exercisesService.createExercise(ex3, new ExerciseSolution(null, exerciseSolution.getData().clone()), exerciseRubric.clone(), Visibility.PUBLIC, List.of(tag1.getId(), tag2.getId(), tag3.getId()));

        /* // TODO - fix the seed
        // tests
        TestGroup tg1 = new TestGroup("instructions1", Float.valueOf(1), Map.of(1, new ReferenceExercise(ex1.getId(), 10.0f)));
        TestGroup tg2 = new TestGroup("instructions2", Float.valueOf(2), Map.of(1, new ReferenceExercise(ex2.getId(), 10.0f)));

        pt.uminho.di.chalktyk.models.tests.Test t1 = new pt.uminho.di.chalktyk.models.tests.Test(null, "TEST #1", "instructions 1", 
            20.0F, "", LocalDateTime.now(), LocalDateTime.now().plusDays(1), s1, Visibility.PUBLIC, c1, null, Map.of(0, tg1, 1, tg2));


        LiveTest t2 = new LiveTest(null, "TEST #2", "instructions 2", 
                    10.0F, "", LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), s2, Visibility.PRIVATE, c2, null, Map.of(0, tg1),
                    LocalDateTime.now().plusDays(1), 60, 5);//Duration.ofHours(1), Duration.ofMinutes(5));

        DeliverDateTest t3 = new DeliverDateTest(null, "TEST #3", "instructions 3", 
                    10.0F, "", LocalDateTime.now(), LocalDateTime.now().plusHours(1), s3, Visibility.NOT_LISTED, c3, null, Map.of(0, tg2),
                    LocalDateTime.now().plusDays(4));
        String test1 = testsService.createTest(t1);
        String test2 = testsService.createTest(t2);
        String test3 = testsService.createTest(t3);

        // test resolutions
        TestResolutionGroup trg = new TestResolutionGroup(4.0F, null);

        testsService.startTest(test1, student1);
        testsService.startTest(test2, student2);
        testsService.startTest(test3, student3);

        TestResolution tr1 = new TestResolution(null, LocalDateTime.now(), null, 0, null, st1, t1, TestResolutionStatus.ONGOING, Map.of(0, trg, 1, trg));
        TestResolution tr2 = new TestResolution(null, LocalDateTime.now(), LocalDateTime.now().plusHours(1), 1, 0.0F, st2, t2, TestResolutionStatus.REVISED, new HashMap<>());
        TestResolution tr3 = new TestResolution(null, LocalDateTime.now(), LocalDateTime.now().plusDays(1), 2, 0.0F, st3, t3, TestResolutionStatus.NOT_REVISED, Map.of(0, trg));
        testsService.createTestResolution(test1, tr1);
        testsService.createTestResolution(test2, tr2);
        testsService.createTestResolution(test3, tr3);
        */
    }




    
    @Test
    public void test() throws BadInputException, NotFoundException {
        testsService.deleteTestById("7cd29983-2f07-4e16-8e76-45a0de48cc23");
    }








    public String addSpecialistChang() throws BadInputException {
        Specialist s = new Specialist(null, "Senor Chang", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg",
                "senor@chang.com", "#1", null);
        return specialistsService.createSpecialist(s);
    }

    public String addStudentAnnie() throws BadInputException {
        Student st = new Student(null, "Annie Edison", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "annie_edison@gmail.com", "none #2");
        return studentsService.createStudent(st);
    }

    public String addStudentGeorge() throws BadInputException {
        Student st = new Student(null, "George Janko", "grugle.com/images/george_janko.jpg", "george_janko@gmail.com", "student #2");
        return studentsService.createStudent(st);
    }

    public String addSpecialistWhitman() throws BadInputException {
        Specialist s = new Specialist(null, "Professor Whitman", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg",
                "whitman@yahoo.com", "#3", null);
        return specialistsService.createSpecialist(s);
    }

    public String addCourse(String specialist) throws BadInputException {
        Course c1 = new Course(null, "Spanish 101", "#1", specialist, null, null);
        return coursesService.createCourse(c1);
    }

    public String addCourse2(String specialist) throws BadInputException {
        Course c = new Course(null, "Espanol", "no hablo espanol", specialist, null, null);
        return coursesService.createCourse(c);
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

        return new MultipleChoiceRubric(1.0F, 0.0F, mcRubricMap);
    }

    private OpenAnswerRubric createOARubric(){
        OAStandard oaStandardMax = new OAStandard("Trató de resolver","Aquí todos pasan la prueba",2.0F);
        OAStandard oaStandardMin = new OAStandard("Ni siquiera intentó resolver","No puedo robar tanto",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandardMin,oaStandardMax);
        return new OpenAnswerRubric(List.of(new OACriterion("Há intentado", oaStandards)));
    }

    private MultipleChoiceExercise createMCExercise(Specialist specialist, Course course, Tag tag){
        HashMap<Integer,Item> itemResolutions = new HashMap<>();
        itemResolutions.put(1,new StringItem("Là"));
        itemResolutions.put(2,new StringItem("Ali"));
        itemResolutions.put(3,new StringItem("There"));

        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION, itemResolutions);
        exercise.setStatement(new ExerciseStatement("Donde está la biblioteca","",""));
        exercise.setTitle("Pregunta de Español MC");
        exercise.setPoints(3.0F);
        exercise.setSpecialist(specialist);
        exercise.setCourse(course);
        exercise.setTags(Set.of(tag));
        return exercise;
    }
}
