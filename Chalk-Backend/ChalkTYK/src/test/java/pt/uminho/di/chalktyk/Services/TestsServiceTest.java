package pt.uminho.di.chalktyk.Services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import pt.uminho.di.chalktyk.Seed;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.ExerciseSolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseStatement;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksData;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksExercise;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksRubric;
import pt.uminho.di.chalktyk.models.exercises.items.Item;
import pt.uminho.di.chalktyk.models.exercises.items.StringItem;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.Mctype;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceData;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceExercise;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceResolutionItem;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceRubric;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OAStandard;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerData;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerExercise;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.DeliverDateTest;
import pt.uminho.di.chalktyk.models.tests.LiveTest;
import pt.uminho.di.chalktyk.models.tests.TestGroup;
import pt.uminho.di.chalktyk.models.tests.TestTag;
import pt.uminho.di.chalktyk.models.tests.TestTagPK;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ConcreteExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ReferenceExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.TestExercise;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.repositories.TestTagsDAO;
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
    private final TestTagsDAO testTagsDAO;

    @Autowired
    public TestsServiceTest(ITestsService testsService, ICoursesService coursesService, ISpecialistsService specialistsService, 
                            IExercisesService exercisesService, IInstitutionsService institutionsService, IStudentsService studentsService, 
                            ITagsService tagsService, TestTagsDAO testTagsDAO){
        this.testsService = testsService;
        this.coursesService = coursesService;
        this.specialistsService = specialistsService;
        this.exercisesService = exercisesService;
        this.institutionsService = institutionsService;
        this.studentsService = studentsService;
        this.tagsService = tagsService;
        this.seed = new Seed(institutionsService,studentsService,specialistsService,coursesService,testsService, tagsService,exercisesService);
        this.testTagsDAO = testTagsDAO;
    }

    private String specialistId, specialist2Id, courseId, course2Id, studentId, student2Id;
    private Tag tag1, tag2;

    @BeforeEach
    public void setup() throws BadInputException {
        this.specialistId = seed.addSpecialistChang();
        this.specialist2Id = seed.addSpecialistWhitman();
        this.courseId = seed.addCourse(specialistId);
        this.course2Id = seed.addCourse2(specialist2Id);
        this.studentId = seed.addStudentAnnie();
        this.student2Id = seed.addStudentGeorge();
        // create tags
        this.tag1 = tagsService.createTag("Espanol","/");
        this.tag2 = tagsService.createTag("NewEspanol","/");
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

    @Test
    public void createTest() throws BadInputException, NotFoundException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest();

        String testId = testsService.createTest(t1);
        pt.uminho.di.chalktyk.models.tests.Test test = testsService.getTestById(testId);
        assert test.getGlobalPoints() == 15.0F;
        assert test.getSpecialistId().equals(specialistId);
        assert test.getCourseId().equals(courseId);

        TestTagPK ttpk1 = new TestTagPK(tag1, test);
        TestTagPK ttpk2 = new TestTagPK(tag2, test);
        TestTag tt1 = testTagsDAO.findById(ttpk1).orElse(null);
        TestTag tt2 = testTagsDAO.findById(ttpk2).orElse(null);
        assert tt1.getNExercises() == 6;
        assert tt2.getNExercises() == 4;
    }

    @Test
    public void createLiveTest() throws BadInputException, NotFoundException {
        pt.uminho.di.chalktyk.models.tests.Test tmp = buildTest();

        LiveTest t1 = new LiveTest(null, tmp.getTitle(), tmp.getGlobalInstructions(), null, tmp.getConclusion(), tmp.getCreationDate(), 
            tmp.getPublishDate(), tmp.getSpecialist(), tmp.getVisibility(), tmp.getCourse(), tmp.getInstitution(), tmp.getGroups(),
            LocalDateTime.now().plusSeconds(100), 360, 10);

        String testId = testsService.createTest(t1);
        pt.uminho.di.chalktyk.models.tests.Test test = testsService.getTestById(testId);
        assert test instanceof LiveTest;
        assert test.getGlobalPoints() == 15.0F;
        assert test.getSpecialistId().equals(specialistId);
        assert test.getCourseId().equals(courseId);

        TestTagPK ttpk1 = new TestTagPK(tag1, test);
        TestTagPK ttpk2 = new TestTagPK(tag2, test);
        TestTag tt1 = testTagsDAO.findById(ttpk1).orElse(null);
        TestTag tt2 = testTagsDAO.findById(ttpk2).orElse(null);
        assert tt1.getNExercises() == 6;
        assert tt2.getNExercises() == 4;
    }

    @Test
    public void createDDTest() throws BadInputException, NotFoundException {
        pt.uminho.di.chalktyk.models.tests.Test tmp = buildTest();

        DeliverDateTest t1 = new DeliverDateTest(null, tmp.getTitle(), tmp.getGlobalInstructions(), null, tmp.getConclusion(), tmp.getCreationDate(), 
            tmp.getPublishDate(), tmp.getSpecialist(), tmp.getVisibility(), tmp.getCourse(), tmp.getInstitution(), tmp.getGroups(),
            LocalDateTime.now().plusDays(1));

        String testId = testsService.createTest(t1);
        pt.uminho.di.chalktyk.models.tests.Test test = testsService.getTestById(testId);
        assert test instanceof DeliverDateTest;
        assert test.getGlobalPoints() == 15.0F;
        assert test.getSpecialistId().equals(specialistId);
        assert test.getCourseId().equals(courseId);

        TestTagPK ttpk1 = new TestTagPK(tag1, test);
        TestTagPK ttpk2 = new TestTagPK(tag2, test);
        TestTag tt1 = testTagsDAO.findById(ttpk1).orElse(null);
        TestTag tt2 = testTagsDAO.findById(ttpk2).orElse(null);
        assert tt1.getNExercises() == 6;
        assert tt2.getNExercises() == 4;
    }

    
    /* ******* CREATE METHODS ******* */

    private pt.uminho.di.chalktyk.models.tests.Test buildTest() throws BadInputException, NotFoundException {
        // building exercises
        Exercise e1 = createOAExercise(specialistId, courseId);
        e1.setSolution(createOASolution());
        e1.setRubric(createOARubric());
        e1.setTags(Set.of(tag1, tag2));
        String e1_id = exercisesService.createExercise(createOAExercise(specialistId, courseId), createOASolution2(), createOARubric(), List.of(tag1.getId(), tag2.getId()));

        Exercise e2 = createMCExercise(specialistId, courseId);
        e2.setSolution(createMCSolution());
        e2.setRubric(createMCRubric());
        e2.setTags(Set.of(tag1));
        String e2_id = exercisesService.createExercise(createMCExercise(specialistId, courseId), createMCSolution(), createMCRubric(), List.of(tag1.getId()));

        Exercise e3 = createFTBExercise(specialistId, courseId);
        e3.setSolution(createFTBSolution());
        e3.setRubric(createFTBRubric());
        e3.setTags(Set.of(tag1, tag2));
        String e3_id = exercisesService.createExercise(createFTBExercise(specialistId, courseId), createFTBSolution(), createFTBRubric(), List.of(tag1.getId(), tag2.getId()));

        //Exercise e4;

        TestExercise ex1 = new ConcreteExercise(2.5F, e1);
        TestExercise ex2 = new ConcreteExercise(2.5F, e2);
        TestExercise ex3 = new ConcreteExercise(2.5F, e3);
        // TODO: chat exercise
        //TestExercise ex4 = new ConcreteExercise(2.5F, e4);
        TestExercise ex5 = new ReferenceExercise(e1_id, 2.5F);
        TestExercise ex6 = new ReferenceExercise(e2_id, 2.5F);
        TestExercise ex7 = new ReferenceExercise(e3_id, 2.5F);
        //TestExercise ex8 = new ReferenceExercise(e4_id, 2.5F);

        TestGroup tg1 = new TestGroup("instructions1", null, List.of(ex1, ex5));
        TestGroup tg2 = new TestGroup("instructions2", null, List.of(ex2, ex6));
        TestGroup tg3 = new TestGroup("instructions3", null, List.of(ex3, ex7));
        //TestGroup tg4 = new TestGroup("instructions4", null, List.of(ex4));

        Specialist s1 = specialistsService.getSpecialistById(this.specialistId);
        Course c1 = coursesService.getCourseById(this.courseId);

        pt.uminho.di.chalktyk.models.tests.Test test = new pt.uminho.di.chalktyk.models.tests.Test(null, "TEST #1", "instructions 1", 
            null, "", LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), s1, Visibility.PUBLIC, c1, null, List.of(tg1, tg2, tg3));

        return test;
    }

    private OpenAnswerExercise createOAExercise(String specialistId, String courseId){
        OpenAnswerExercise exercise = new OpenAnswerExercise();
        exercise.setStatement(new ExerciseStatement("Donde está la biblioteca","",""));
        exercise.setTitle("Pregunta de Español OA");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);
        return exercise;
    }

    private ExerciseSolution createOASolution(){
        OpenAnswerData openAnswerData = new OpenAnswerData("Ali");
        return new ExerciseSolution("id",openAnswerData);
    }

    private ExerciseSolution createOASolution2(){
        OpenAnswerData openAnswerData = new OpenAnswerData("Ali2");
        return new ExerciseSolution("id",openAnswerData);
    }

    private OpenAnswerRubric createOARubric(){
        OAStandard oaStandardMax = new OAStandard("Trató de resolver","Aquí todos pasan la prueba",100.0F);
        OAStandard oaStandardMin = new OAStandard("Ni siquiera intentó resolver","No puedo robar tanto",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandardMin,oaStandardMax);
        return new OpenAnswerRubric(List.of(new OACriterion("Há intentado", 100f, oaStandards)));
    }

    private MultipleChoiceExercise createMCExercise(String specialistId, String courseId){
        HashMap<String, Item> itemResolutions = new HashMap<>();
        itemResolutions.put("1",new StringItem("Là"));
        itemResolutions.put("2",new StringItem("Ali"));
        itemResolutions.put("3",new StringItem("There"));

        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise.setStatement(new ExerciseStatement("Donde está la biblioteca","",""));
        exercise.setTitle("Pregunta de Español MC");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);
        return exercise;
    }

    private ExerciseSolution createMCSolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        HashMap<String,MultipleChoiceResolutionItem> itemResolutions = new HashMap<>();
        itemResolutions.put("1",option1);
        itemResolutions.put("2",option2);
        itemResolutions.put("3",option3);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutions);
        return new ExerciseSolution("id",multipleChoiceData);
    }

    private ExerciseRubric createMCRubric(){
        HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        mcRubricMap.put("1",createOARubric());
        mcRubricMap.put("2",createOARubric());
        mcRubricMap.put("3",createOARubric());
        return new MultipleChoiceRubric(0.0F,mcRubricMap);
    }

    private FillTheBlanksExercise createFTBExercise(String specialistId, String courseId){
        FillTheBlanksExercise fillTheBlanksExercise = new FillTheBlanksExercise(Arrays.asList("Todos os ", " sabem bem ",""));
        fillTheBlanksExercise.setStatement(new ExerciseStatement("Preenche com a música dos patinhos","",""));
        fillTheBlanksExercise.setTitle("Patinhos sabem nadar FTB");
        fillTheBlanksExercise.setSpecialist(new Specialist(specialistId));
        fillTheBlanksExercise.setCourse(new Course(courseId));
        fillTheBlanksExercise.setVisibility(Visibility.PUBLIC);
        return fillTheBlanksExercise;
    }

    private ExerciseSolution createFTBSolution(){
        FillTheBlanksData fillTheBlanksData = new FillTheBlanksData(Arrays.asList("patinhos","nadar"));
        return new ExerciseSolution("id",fillTheBlanksData);
    }

    private ExerciseRubric createFTBRubric(){
        return new FillTheBlanksRubric(0.0F);
    }
}