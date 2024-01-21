package pt.uminho.di.chalktyk.Services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionStatus;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.ExerciseSolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseStatement;
import pt.uminho.di.chalktyk.models.exercises.chat.ChatExercise;
import pt.uminho.di.chalktyk.models.exercises.chat.ChatExerciseData;
import pt.uminho.di.chalktyk.models.exercises.chat.ChatExerciseRubric;
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
import pt.uminho.di.chalktyk.models.tests.TestGroup;
import pt.uminho.di.chalktyk.models.tests.TestResolution;
import pt.uminho.di.chalktyk.models.tests.TestResolutionStatus;
import pt.uminho.di.chalktyk.models.tests.TestTag;
import pt.uminho.di.chalktyk.models.tests.TestTagPK;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ConcreteExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ReferenceExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.TestExercise;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.repositories.TestTagsDAO;
import pt.uminho.di.chalktyk.services.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.ForbiddenException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.ServiceException;

@SpringBootTest
@Transactional(noRollbackFor = ServiceException.class)
public class TestsServiceTest {
    private final ISeedService seedService;
    private final ITestsService testsService;
    private final ICoursesService coursesService;
    private final ISpecialistsService specialistsService;
    private final IExercisesService exercisesService;
    private final IInstitutionsService institutionsService;
    private final IStudentsService studentsService;
    private final ITagsService tagsService;
    private final TestTagsDAO testTagsDAO;

    @Autowired
    public TestsServiceTest(ISeedService seedService, ITestsService testsService, ICoursesService coursesService, ISpecialistsService specialistsService,
                            IExercisesService exercisesService, IInstitutionsService institutionsService, IStudentsService studentsService,
                            ITagsService tagsService, TestTagsDAO testTagsDAO){
        this.seedService = seedService;
        this.testsService = testsService;
        this.coursesService = coursesService;
        this.specialistsService = specialistsService;
        this.exercisesService = exercisesService;
        this.institutionsService = institutionsService;
        this.studentsService = studentsService;
        this.tagsService = tagsService;
        this.testTagsDAO = testTagsDAO;
    }

    private String specialistId, specialist2Id, courseId, course2Id, studentId, student2Id;
    private Tag tag1, tag2;

    @BeforeEach
    public void setup() throws BadInputException {
        this.specialistId = seedService.addSpecialistChang();
        this.specialist2Id = seedService.addSpecialistWhitman();
        this.courseId = seedService.addCourse(specialistId);
        this.course2Id = seedService.addCourse2(specialist2Id);
        this.studentId = seedService.addStudentAnnie();
        this.student2Id = seedService.addStudentGeorge();

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
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest(false,75);

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

    /*
    @Test
    public void createLiveTest() throws BadInputException, NotFoundException {
        pt.uminho.di.chalktyk.models.tests.Test tmp = buildTest(false,75);

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
        pt.uminho.di.chalktyk.models.tests.Test tmp = buildTest(false,75);

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
     */

    @Test
    public void duplicateTest() throws BadInputException, NotFoundException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest(false,75);

        String testId = testsService.createTest(t1);
        String duplicateId = testsService.duplicateTestById(this.specialistId, testId, Visibility.PRIVATE, this.courseId);

        // trigger exceptions
        pt.uminho.di.chalktyk.models.tests.Test tmp1 = testsService.getTestById(testId);
        assert tmp1 != null;
        pt.uminho.di.chalktyk.models.tests.Test tmp_dup = testsService.getTestById(duplicateId);
        assert tmp_dup != null;
    }

    /* 
    @Test
    public void duplicateLiveTest() throws BadInputException, NotFoundException {
        pt.uminho.di.chalktyk.models.tests.Test tmp = buildTest(false,75);
        LiveTest t1 = new LiveTest(null, tmp.getTitle(), tmp.getGlobalInstructions(), null, tmp.getConclusion(), tmp.getCreationDate(), 
            tmp.getPublishDate(), tmp.getSpecialist(), tmp.getVisibility(), tmp.getCourse(), tmp.getInstitution(), tmp.getGroups(),
            LocalDateTime.now().plusSeconds(100), 360, 10);

        String testId = testsService.createTest(t1);
        String duplicateId = testsService.duplicateTestById(this.specialistId, testId, Visibility.PRIVATE, this.courseId);

        // trigger exceptions
        pt.uminho.di.chalktyk.models.tests.Test tmp1 = testsService.getTestById(testId);
        assert tmp1 != null;
        pt.uminho.di.chalktyk.models.tests.Test tmp_dup = testsService.getTestById(duplicateId);
        assert tmp_dup != null;
        assert tmp_dup instanceof LiveTest;
    }

    @Test
    public void duplicateDDTest() throws BadInputException, NotFoundException {
        pt.uminho.di.chalktyk.models.tests.Test tmp = buildTest(false,75);
        DeliverDateTest t1 = new DeliverDateTest(null, tmp.getTitle(), tmp.getGlobalInstructions(), null, tmp.getConclusion(), tmp.getCreationDate(), 
            tmp.getPublishDate(), tmp.getSpecialist(), tmp.getVisibility(), tmp.getCourse(), tmp.getInstitution(), tmp.getGroups(),
            LocalDateTime.now().plusDays(1));

        String testId = testsService.createTest(t1);
        String duplicateId = testsService.duplicateTestById(this.specialistId, testId, Visibility.PRIVATE, this.courseId);

        // trigger exceptions
        pt.uminho.di.chalktyk.models.tests.Test tmp1 = testsService.getTestById(testId);
        assert tmp1 != null;
        pt.uminho.di.chalktyk.models.tests.Test tmp_dup = testsService.getTestById(duplicateId);
        assert tmp_dup != null;
        assert tmp_dup instanceof DeliverDateTest;
    }
    */

    @Test
    public void deleteTest() throws BadInputException, NotFoundException, InterruptedException, ForbiddenException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest(false,75);
        String testId = testsService.createTest(t1);

        pt.uminho.di.chalktyk.models.tests.Test tmp1 = testsService.getTestById(testId);
        assert tmp1 != null;

        Thread.sleep(200); // wait some time before starting a test.
        String tr_id = testsService.startTest(testId, this.studentId);
        TestResolution tr = testsService.getTestResolutionById(tr_id);
        assert tr != null;

        testsService.deleteTestById(testId);
        try {
            testsService.getTestResolutionById(tr_id);
            assert false;
        }
        catch (NotFoundException e) {
            try {
                testsService.getTestById(testId);
            }
            catch (NotFoundException e2) {
                assert true;
            }
        }
    }

    @Test
    public void startTest() throws NotFoundException, BadInputException, ForbiddenException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest(false,75);

        String testId = testsService.createTest(t1);
        String tr_id = testsService.startTest(testId, this.studentId);
        TestResolution tr = testsService.getTestResolutionById(tr_id);
        assert tr != null;
        assert tr.getStudentId().equals(this.studentId);
        assert tr.getStatus().equals(TestResolutionStatus.ONGOING);
        assert tr.getSubmissionNr() == 1;
    }

    @Test
    public void submitTestNoResolution() throws NotFoundException, BadInputException, InterruptedException, ForbiddenException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest(false,75);

        String testId = testsService.createTest(t1);
        Thread.sleep(200); // wait some time before starting a test.
        String tr_id = testsService.startTest(testId, this.studentId);

        testsService.submitTestResolution(tr_id);
        TestResolution tr = testsService.getTestResolutionById(tr_id);
        assert tr != null;
        assert tr.getStudentId().equals(this.studentId);
        assert tr.getSubmissionNr() == 1;
    }

    @Test
    public void deleteTestResolution() throws BadInputException, NotFoundException, InterruptedException, ForbiddenException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest(false,75);
        String testId = testsService.createTest(t1);

        pt.uminho.di.chalktyk.models.tests.Test tmp1 = testsService.getTestById(testId);
        assert tmp1 != null;

        Thread.sleep(200); // wait some time before starting a test.
        String tr_id = testsService.startTest(testId, this.studentId);
        TestResolution tr = testsService.getTestResolutionById(tr_id);
        assert tr != null;

        testsService.deleteTestResolutionById(tr_id);
        try {
            testsService.getTestResolutionById(tr_id);
            assert false;
        }
        catch (NotFoundException e) {
            assert true;
        }
    }

    @Test
    public void getTestResolutions() throws NotFoundException, BadInputException, InterruptedException, ForbiddenException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest(false,75);
        String testId = testsService.createTest(t1);

        pt.uminho.di.chalktyk.models.tests.Test tmp1 = testsService.getTestById(testId);
        assert tmp1 != null;

        Thread.sleep(200); // wait some time before starting a test.
        String tr_id1 = testsService.startTest(testId, this.studentId);
        String tr_id2 = testsService.startTest(testId, this.student2Id);
        String tr_id3 = testsService.startTest(testId, this.studentId);
        List<TestResolution> trs1 = testsService.getTestResolutions(testId, 0, 2).getContent();
        List<TestResolution> trs2 = testsService.getTestResolutions(testId, 1, 2).getContent();

        assert trs1.size() == 2;
        assert trs2.size() == 1;
        assert trs1.get(0).getId().equals(tr_id1);
        assert trs1.get(1).getId().equals(tr_id2);
        assert trs2.get(0).getId().equals(tr_id3);
    }

    @Test
    public void updateAllOnTest(){
        // TODO
    }

    @Test
    public void uploadResolution() throws NotFoundException, BadInputException, InterruptedException, ForbiddenException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest(false,75);
        String testId = testsService.createTest(t1);

        pt.uminho.di.chalktyk.models.tests.Test test = testsService.getTestById(testId);
        TestGroup tg = test.getGroups().get(1);
        TestExercise exe = tg.getExercises().get(1);

        Thread.sleep(200); // wait some time before starting a test.
        String testResId = testsService.startTest(testId, this.studentId);

        ExerciseResolutionData rightMC = createRightMCResolution();
        ExerciseResolution er1 = new ExerciseResolution();
        er1.setData(rightMC.clone());
        String exeResId = testsService.uploadResolution(testResId, exe.getId(), er1);

        ExerciseResolution er = exercisesService.getExerciseResolution(exeResId);
        assert er != null;

        // trigger fail
        try {
            testsService.uploadResolution(testResId, "non-existent exercise", er1);
            assert false;
        }
        catch (NotFoundException e) {
            assert true;
        }
    }

    @Test
    public void automaticCorrectionCorrect() throws NotFoundException, BadInputException, InterruptedException, ForbiddenException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildMCTest();

        String testId = testsService.createTest(t1);
        Thread.sleep(200); // wait some time before starting a test.
        String tr_id = testsService.startTest(testId, this.studentId);

        pt.uminho.di.chalktyk.models.tests.Test test = testsService.getTestById(testId);
        List<TestGroup> tg = test.getGroups();

        ExerciseResolutionData rightMC = createRightMCResolution();

        ExerciseResolution er1 = new ExerciseResolution(null,null,null, rightMC, 
                                ExerciseResolutionStatus.NOT_REVISED, null, null, null);

        testsService.uploadResolution(tr_id, tg.get(0).getExercises().get(0).getId(), er1);
        testsService.uploadResolution(tr_id, tg.get(1).getExercises().get(0).getId(), er1);
        testsService.submitTestResolution(tr_id);
        TestResolution tr = testsService.getTestResolutionById(tr_id);
        assert tr.getTotalPoints() == 6.0F;
        assert tr.getStatus() == TestResolutionStatus.REVISED;
        assert tr.getSubmissionNr() == 1;

        tr_id = testsService.startTest(testId, this.studentId);
        ExerciseResolution er2 = new ExerciseResolution(null,null,null, createRightMCResolution(), 
                                ExerciseResolutionStatus.NOT_REVISED, null, null, null);
        testsService.uploadResolution(tr_id, tg.get(1).getExercises().get(1).getId(), er2);
        testsService.submitTestResolution(tr_id);
        tr = testsService.getTestResolutionById(tr_id);
        assert tr.getTotalPoints() == 2.0F;
        assert tr.getStatus() == TestResolutionStatus.REVISED;
        assert tr.getSubmissionNr() == 2;
    }

    /* 
    @Test
    public void automaticCorrection() throws NotFoundException, BadInputException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildMCTest();

        String testId = testsService.createTest(t1);
        Thread.sleep(200); // wait some time before starting a test.
        String tr_id = testsService.startTest(testId, this.studentId);

        pt.uminho.di.chalktyk.models.tests.Test test = testsService.getTestById(testId);
        List<TestGroup> tg = test.getGroups();

        ExerciseResolutionData rightMC = createRightMCResolution();
        ExerciseResolutionData wrongMC = createRightMCResolution();
        ExerciseResolutionData halfRightMC = createRightMCResolution();

        ExerciseResolution er1 = new ExerciseResolution(null,null,null, rightMC, 
                                ExerciseResolutionStatus.NOT_REVISED, null, null, null);
        ExerciseResolution er2 = new ExerciseResolution(null,null,null, wrongMC, 
                                ExerciseResolutionStatus.NOT_REVISED, null, null, null);
        ExerciseResolution er3 = new ExerciseResolution(null,null,null, halfRightMC, 
                                ExerciseResolutionStatus.NOT_REVISED, null, null, null);

        testsService.uploadResolution(tr_id, tg.get(0).getExercises().get(0).getId(), er1);
        testsService.uploadResolution(tr_id, tg.get(1).getExercises().get(0).getId(), er2);
        testsService.uploadResolution(tr_id, tg.get(1).getExercises().get(1).getId(), er3);

        testsService.submitTestResolution(tr_id);
        TestResolution tr = testsService.getTestResolutionById(tr_id);

        assert tr.getTotalPoints() == 4.0F;
        assert tr.getStatus() == TestResolutionStatus.REVISED;
    }
    */

    @Test
    public void manualCorrection() throws NotFoundException, BadInputException, InterruptedException, ForbiddenException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildMCTest();

        String testId = testsService.createTest(t1);
        Thread.sleep(200); // wait some time before starting a test.
        String tr_id = testsService.startTest(testId, this.studentId);

        pt.uminho.di.chalktyk.models.tests.Test test = testsService.getTestById(testId);
        List<TestGroup> tg = test.getGroups();

        ExerciseResolutionData rightMC = createRightMCResolution();

        ExerciseResolution er1 = new ExerciseResolution(null,null,null, rightMC, 
                                ExerciseResolutionStatus.NOT_REVISED, null, null, null);

        String res1 = testsService.uploadResolution(tr_id, tg.get(0).getExercises().get(0).getId(), er1);
        String res2 = testsService.uploadResolution(tr_id, tg.get(1).getExercises().get(0).getId(), er1);
        String res3 = testsService.uploadResolution(tr_id, tg.get(1).getExercises().get(1).getId(), er1);

        testsService.manualCorrectionForExercise(res1, tr_id, 3.0F, "bruh1");
        testsService.manualCorrectionForExercise(res2, tr_id, 3.0F, "bruh2");
        try {
            // Maximum points for the exercise is 2.0f, so it should result in an exception
            testsService.manualCorrectionForExercise(res3, tr_id, 3.0F, "bruh3");
            assert false;
        }
        catch (BadInputException e){
            testsService.manualCorrectionForExercise(res3, tr_id, 2.0F, "bruh3");
            TestResolution tr = testsService.getTestResolutionById(tr_id);
            assert tr.getTotalPoints() == 8.0F;
        }
    }

    @Test
    public void createTestExercise() throws NotFoundException, BadInputException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest(true,100000);

        String testId = testsService.createTest(t1);

        TestExercise exe = new ConcreteExercise(4.0F, createFTBExercise(this.specialistId, this.courseId));
        String exe_id = testsService.createTestExercise(testId, exe, 2, 1, "new instruction");

        pt.uminho.di.chalktyk.models.tests.Test test = testsService.getTestById(testId);
        TestGroup group = test.getGroups().get(2);
        assert group.getExercises().get(1).getId().equals(exe_id);
        assert group.getGroupPoints() == 9.0F;
        assert test.getGroups().size() == 3;
        assert group.getExercises().size() == 3;

        TestExercise exe2 = new ConcreteExercise(4.0F, createFTBExercise(this.specialistId, this.courseId));
        String exe2_id = testsService.createTestExercise(testId, exe2, 3, 1, "new instruction 2");

        test = testsService.getTestById(testId);
        group = test.getGroups().get(3);
        assert group.getExercises().get(0).getId().equals(exe2_id);
        assert group.getGroupPoints() == 4.0F;
        assert test.getGroups().size() == 4;
        assert group.getExercises().size() == 1;
    }

    @Test
    public void deleteTestExercise() throws NotFoundException, BadInputException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest(true,150);

        String testId = testsService.createTest(t1);
        pt.uminho.di.chalktyk.models.tests.Test test = testsService.getTestById(testId);

        Map<Integer, List<String>> ogExeIds = new HashMap<>();
        Map<Integer, Float> ogPoints = new HashMap<>();
        List<TestGroup> groups = test.getGroups();
        for (int i = 0; i < groups.size(); i++){
            TestGroup tg = groups.get(i);
            List<String> ids = new ArrayList<>();
            for (TestExercise exe: tg.getExercises()){
                ids.add(exe.getId());
            }
            ogExeIds.put(i, ids);
            ogPoints.put(i, tg.getGroupPoints());                 
        }

        testsService.deleteExerciseFromTest(testId, ogExeIds.get(2).get(0));
        try {
            exercisesService.getExerciseById(ogExeIds.get(2).get(0));
            assert false;
        }
        catch (NotFoundException e){}
        
        test = testsService.getTestById(testId);
        TestGroup group = test.getGroups().get(2);
        assert group.getExercises().size() == 1;
        assert group.getExercises().get(0).getId().equals(ogExeIds.get(2).get(1));
        assert group.getGroupPoints() == 2.0F;
        assert test.getGroups().size() == 3;

        testsService.deleteExerciseFromTest(testId, ogExeIds.get(2).get(1));
        try {
            exercisesService.getExerciseById(ogExeIds.get(2).get(1));
            assert false;
        }
        catch (NotFoundException e){}

        test = testsService.getTestById(testId);
        assert test.getGroups().size() == 2;
    }

    @Test
    public void removeTestExercise() throws NotFoundException, BadInputException {
        pt.uminho.di.chalktyk.models.tests.Test t1 = buildTest(true,150);

        String testId = testsService.createTest(t1);
        pt.uminho.di.chalktyk.models.tests.Test test = testsService.getTestById(testId);

        Map<Integer, List<String>> ogExeIds = new HashMap<>();
        Map<Integer, Float> ogPoints = new HashMap<>();
        List<TestGroup> groups = test.getGroups();
        for (int i = 0; i < groups.size(); i++){
            TestGroup tg = groups.get(i);
            List<String> ids = new ArrayList<>();
            for (TestExercise exe: tg.getExercises()){
                ids.add(exe.getId());
            }
            ogExeIds.put(i, ids);
            ogPoints.put(i, tg.getGroupPoints());                 
        }

        testsService.removeExerciseFromTest(testId, ogExeIds.get(2).get(0));
        try {
            exercisesService.getExerciseById(ogExeIds.get(2).get(0));
        }
        catch (NotFoundException e){
            assert false;
        }
        
        test = testsService.getTestById(testId);
        TestGroup group = test.getGroups().get(2);
        assert group.getExercises().size() == 1;
        assert group.getExercises().get(0).getId().equals(ogExeIds.get(2).get(1));
        assert group.getGroupPoints() == 2.0F;
        assert test.getGroups().size() == 3;

        testsService.removeExerciseFromTest(testId, ogExeIds.get(2).get(1));
        try {
            exercisesService.getExerciseById(ogExeIds.get(2).get(1));
        }
        catch (NotFoundException e){
            assert false;
        }

        test = testsService.getTestById(testId);
        assert test.getGroups().size() == 2;
    }

    
    /* ******* CREATE METHODS ******* */

    private pt.uminho.di.chalktyk.models.tests.Test buildTest(boolean latePublishDate, long delayPublishDateInMs) throws BadInputException, NotFoundException {
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

        /* 
        Exercise e4 = createChatExercise(specialistId, courseId);
        e4.setSolution(createChatSolution());
        e4.setRubric(createChatRubric());
        e4.setTags(Set.of(tag2));
        String e4_id = exercisesService.createExercise(createChatExercise(specialistId, courseId), createChatSolution(), createChatRubric(), List.of(tag2.getId()));
        */

        TestExercise ex1 = new ConcreteExercise(3.0F, e1);
        TestExercise ex2 = new ConcreteExercise(3.0F, e2);
        TestExercise ex3 = new ConcreteExercise(3.0F, e3);
        //TestExercise ex4 = new ConcreteExercise(3.0F, e4);
        TestExercise ex5 = new ReferenceExercise(e1_id, 2.0F);
        TestExercise ex6 = new ReferenceExercise(e2_id, 2.0F);
        TestExercise ex7 = new ReferenceExercise(e3_id, 2.0F);
        //TestExercise ex8 = new ReferenceExercise(e4_id, 2.0F);

        TestGroup tg1 = new TestGroup("instructions1", null, List.of(ex1, ex5));
        TestGroup tg2 = new TestGroup("instructions2", null, List.of(ex2, ex6));
        TestGroup tg3 = new TestGroup("instructions3", null, List.of(ex3, ex7));
        //TestGroup tg4 = new TestGroup("instructions4", null, List.of(ex4, ex8));

        Specialist s1 = specialistsService.getSpecialistById(this.specialistId);
        Course c1 = coursesService.getCourseById(this.courseId);

        LocalDateTime date = LocalDateTime.now();
        LocalDateTime publishDate;
        if (latePublishDate)
            publishDate = date.plus(delayPublishDateInMs, ChronoUnit.MILLIS);
        else
            publishDate = date.plusNanos(100000000);
        pt.uminho.di.chalktyk.models.tests.Test test = new pt.uminho.di.chalktyk.models.tests.Test(null, "TEST #1", "instructions 1", 
            null, "", date, publishDate, s1, Visibility.PUBLIC, c1, null, List.of(tg1, tg2, tg3));//, tg4));

        return test;
    }

    private pt.uminho.di.chalktyk.models.tests.Test buildMCTest() throws BadInputException, NotFoundException {
        // building exercises
        Exercise e1 = createMCExercise(specialistId, courseId);
        e1.setSolution(createMCSolution());
        e1.setRubric(createMCRubric());
        e1.setTags(Set.of(tag1, tag2));

        Exercise e2 = createMCExercise(specialistId, courseId);
        e2.setSolution(createMCSolution());
        e2.setRubric(createMCRubric());
        e2.setTags(Set.of(tag1));
        String e2_id = exercisesService.createExercise(createMCExercise(specialistId, courseId), createMCSolution(), createMCRubric(), List.of(tag1.getId()));

        TestExercise ex1 = new ConcreteExercise(3.0F, e1);
        TestExercise ex2 = new ConcreteExercise(3.0F, e2);
        TestExercise ex3 = new ReferenceExercise(e2_id, 2.0F);

        TestGroup tg1 = new TestGroup("instructions1", null, List.of(ex1));
        TestGroup tg2 = new TestGroup("instructions2", null, List.of(ex2, ex3));

        Specialist s1 = specialistsService.getSpecialistById(this.specialistId);
        Course c1 = coursesService.getCourseById(this.courseId);

        LocalDateTime date = LocalDateTime.now();
        pt.uminho.di.chalktyk.models.tests.Test test = new pt.uminho.di.chalktyk.models.tests.Test(null, "TEST #1", "instructions 1", 
            null, "", date, date.plusNanos(100000000), s1, Visibility.PUBLIC, c1, null, List.of(tg1, tg2));

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

    private ExerciseResolutionData createRightMCResolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        HashMap<String,MultipleChoiceResolutionItem> itemResolutions = new HashMap<>();
        itemResolutions.put("1",option1);
        itemResolutions.put("2",option2);
        itemResolutions.put("3",option3);
        return new MultipleChoiceData(itemResolutions);
    }

    private ExerciseResolutionData createWrongMCResolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,true);
        HashMap<String,MultipleChoiceResolutionItem> itemResolutions = new HashMap<>();
        itemResolutions.put("1",option1);
        itemResolutions.put("2",option2);
        itemResolutions.put("3",option3);
        return new MultipleChoiceData(itemResolutions);
    }

    private ExerciseResolutionData createHalfWrongMCResolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        HashMap<String,MultipleChoiceResolutionItem> itemResolutions = new HashMap<>();
        itemResolutions.put("1",option1);
        itemResolutions.put("2",option2);
        itemResolutions.put("3",option3);
        return new MultipleChoiceData(itemResolutions);
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

    private ChatExercise createChatExercise(String specialistId,String courseId){
        ChatExercise exercise = new ChatExercise();
        exercise.setStatement(new ExerciseStatement("Quais as vantagens de usar sistemas distribuidos?",null,null));
        exercise.setTitle("Vantages de Sistemas distribuidos");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        List<String> topics = new ArrayList<>();
        topics.add("Vantages de sistemas distribuidos");
        topics.add("Dificuldades de utilização de sistemas distribuidos");
        topics.add("Porque utilizar sistemas distribuidos");

        exercise.setTopics(topics);

        return exercise;
    }

    private ExerciseSolution createChatSolution(){
        List<String> chat = new ArrayList<>();
        chat.add("Quais as vantagens de usar sistemas distribuidos");
        chat.add("E bue fixe");
        chat.add("Mas porque que os descreves como bue fixes");
        chat.add("Permitem a distribuição trabalhos por varios computadores");

        ChatExerciseData chatExerciseData = new ChatExerciseData(chat);
        return  new ExerciseSolution(null,chatExerciseData);
    }

    private ChatExerciseRubric createChatRubric(){
        return new ChatExerciseRubric(); 
    }
}