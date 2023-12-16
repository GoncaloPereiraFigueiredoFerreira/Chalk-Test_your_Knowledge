package pt.uminho.di.chalktyk.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.Seed;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.*;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks.FillTheBlanksData;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks.FillTheBlanksExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks.FillTheBlanksRubric;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice.*;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.*;
import pt.uminho.di.chalktyk.models.relational.TagSQL;
import pt.uminho.di.chalktyk.models.relational.VisibilitySQL;
import pt.uminho.di.chalktyk.services.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExercisesServiceTest {
    private final ICoursesService coursesService;
    private final ISpecialistsService specialistsService;
    private final IExercisesService exercisesService;
    private final IInstitutionsService institutionsService;
    private final IStudentsService studentsService;
    private final ITagsService iTagsService;
    private final Seed seed;

    @Autowired
    public ExercisesServiceTest(ICoursesService coursesService, ISpecialistsService specialistsService, IExercisesService exercisesService, IInstitutionsService institutionsService, IStudentsService studentsService, ITestsService testsService, ITagsService iTagsService){
        this.coursesService = coursesService;
        this.specialistsService = specialistsService;
        this.exercisesService = exercisesService;
        this.institutionsService = institutionsService;
        this.studentsService = studentsService;
        this.iTagsService = iTagsService;
        this.seed = new Seed(institutionsService,studentsService,specialistsService,coursesService,testsService, iTagsService);
    }


    private String specialistId,courseId,studentId;

    @BeforeEach
    public void setup() throws BadInputException {
        this.specialistId = seed.addSpecialistChang();
        this.courseId = seed.addCourse(specialistId);
        this.studentId = seed.addStudentAnnie();
    }

    private OpenAnswerExercise createOAExercise(String specialistId,String courseId){
        OpenAnswerExercise exercise = new OpenAnswerExercise();
        exercise.setStatement(new ExerciseStatement("Donde está la biblioteca","",""));
        exercise.setTitle("Pregunta de Español OA");
        exercise.setPoints(2.0F);
        exercise.setSpecialistId(specialistId);
        exercise.setCourseId(courseId);
        return exercise;
    }

    private ExerciseSolution createOASolution(){
        OpenAnswerData openAnswerData = new OpenAnswerData("Ali");
        return new ExerciseSolution(null,openAnswerData);
    }

    private ExerciseResolutionData createRightOAResolution(){
        return new OpenAnswerData("Ali");
    }

    private ExerciseResolutionData createWrongOAResolution(){
        return new OpenAnswerData("Aqui");
    }

    private OpenAnswerRubric createOARubric(){
        OAStandard oaStandardMax = new OAStandard("Trató de resolver","Aquí todos pasan la prueba",2.0F);
        OAStandard oaStandardMin = new OAStandard("Ni siquiera intentó resolver","No puedo robar tanto",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandardMin,oaStandardMax);
        return new OpenAnswerRubric(List.of(new OACriterion(oaStandards, "Há intentado")));
    }


    private MultipleChoiceExercise createMCExercise(String specialistId, String courseId){
        List<Item> items = Arrays.asList(new StringItem("Là"),new StringItem("Ali"),new StringItem("There"));
        MultipleChoiceExercise exercise = new MultipleChoiceExercise(items, Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION);
        exercise.setStatement(new ExerciseStatement("Donde está la biblioteca","",""));
        exercise.setTitle("Pregunta de Español MC");
        exercise.setPoints(3.0F);
        exercise.setSpecialistId(specialistId);
        exercise.setCourseId(courseId);
        return exercise;
    }

    private ExerciseSolution createMCSolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(null,0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(null,0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(null,0.0F,null,false);
        List<MultipleChoiceResolutionItem> itemResolutions = Arrays.asList(option1,option2,option3);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutions);
        return new ExerciseSolution(null,multipleChoiceData);
    }

    private ExerciseResolutionData createRightMCResolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(null,0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(null,0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(null,0.0F,null,false);
        List<MultipleChoiceResolutionItem> itemResolutions = Arrays.asList(option1,option2,option3);
        return new MultipleChoiceData(itemResolutions);
    }

    private ExerciseResolutionData createWrongMCResolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(null,0.0F,null,true);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(null,0.0F,null,false);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(null,0.0F,null,false);
        List<MultipleChoiceResolutionItem> itemResolutions = Arrays.asList(option1,option2,option3);
        return new MultipleChoiceData(itemResolutions);
    }

    private ExerciseResolutionData createHalfWrongMCResolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(null,0.0F,null,true);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(null,0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(null,0.0F,null,false);
        List<MultipleChoiceResolutionItem> itemResolutions = Arrays.asList(option1,option2,option3);
        return new MultipleChoiceData(itemResolutions);
    }

    private ExerciseRubric createMCRubric(){
        return new MultipleChoiceRubric(Arrays.asList(createOARubric(),createOARubric(),createOARubric()),1.0F,0.0F);
    }

    private FillTheBlanksExercise createFTBExercise(String specialistId, String courseId){
        FillTheBlanksExercise fillTheBlanksExercise = new FillTheBlanksExercise(Arrays.asList("Todos os ", " sabem bem ",""));
        fillTheBlanksExercise.setStatement(new ExerciseStatement("Preenche com a música dos patinhos","",""));
        fillTheBlanksExercise.setTitle("Patinhos sabem nadar FTB");
        fillTheBlanksExercise.setPoints(2.0F);
        fillTheBlanksExercise.setSpecialistId(specialistId);
        fillTheBlanksExercise.setCourseId(courseId);
        return fillTheBlanksExercise;
    }

    private ExerciseSolution createFTBSolution(){
        FillTheBlanksData fillTheBlanksData = new FillTheBlanksData(Arrays.asList("patinhos","nadar"));
        return new ExerciseSolution(null,fillTheBlanksData);
    }

    private ExerciseResolutionData createRightFTBResolution(){
        return new FillTheBlanksData(Arrays.asList("patinhos","nadar"));
    }
    private ExerciseResolutionData createWrongFTBResolution(){
        return new FillTheBlanksData(Arrays.asList("nadar","patinhos"));
    }
    private ExerciseResolutionData createHalfWrongFTBResolution(){
        return new FillTheBlanksData(Arrays.asList("patinhos","patinhos"));
    }

    private ExerciseRubric createFTBRubric(){
        return new FillTheBlanksRubric(1.0F,0.0F);
    }

    
    @Test
    @Transactional
    public void createOAExercise() throws BadInputException {
        ExerciseSolution exerciseSolution = createOASolution();
        ExerciseRubric exerciseRubric = createOARubric();
        ConcreteExercise exercise = createOAExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,new ArrayList<>(), VisibilitySQL.PUBLIC);
        assertTrue(exercisesService.exerciseExists(exerciseId));
    }

    @Test
    @Transactional
    public void createMCExercise() throws BadInputException {
        ExerciseSolution exerciseSolution = createMCSolution();
        ExerciseRubric exerciseRubric = createMCRubric();
        ConcreteExercise exercise = createMCExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,new ArrayList<>(), VisibilitySQL.PUBLIC);
        assertTrue(exercisesService.exerciseExists(exerciseId));
    }

    @Test
    @Transactional
    public void createFTBExercise() throws BadInputException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        ConcreteExercise exercise = createFTBExercise(specialistId,courseId);

        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,Arrays.asList("26c3e51a-bb31-4807-a3d1-b29d566fe7e1"), VisibilitySQL.PUBLIC);
        assertTrue(exercisesService.exerciseExists(exerciseId));
    }

    @Test
    @Transactional
    public void createShallowCopy() throws BadInputException, NotFoundException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        ConcreteExercise exercise = createFTBExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,new ArrayList<>(), VisibilitySQL.PUBLIC);
        String shallowId = exercisesService.duplicateExerciseById(specialistId,exerciseId);
        assertFalse(exercisesService.exerciseIsShallow(exerciseId));
        assertTrue(exercisesService.exerciseIsShallow(shallowId));
    }

    @Test
    @Transactional
    public void createShallowOfShallowCopy() throws BadInputException, NotFoundException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        ConcreteExercise exercise = createFTBExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,new ArrayList<>(), VisibilitySQL.PUBLIC);
        String shallowId1 = exercisesService.duplicateExerciseById(specialistId,exerciseId);
        String shallowId2 = exercisesService.duplicateExerciseById(specialistId,shallowId1);
        assertFalse(exercisesService.exerciseIsShallow(exerciseId));
        assertTrue(exercisesService.exerciseIsShallow(shallowId1));
        assertTrue(exercisesService.exerciseIsShallow(shallowId2));
        assert exercisesService.getExerciseById(shallowId2) instanceof ConcreteExercise;
    }

    @Test
    @Transactional
    public void deleteConcreteExercise() throws BadInputException, NotFoundException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        ConcreteExercise exercise = createFTBExercise(specialistId,courseId);

        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,Arrays.asList("26c3e51a-bb31-4807-a3d1-b29d566fe7e1"), VisibilitySQL.PUBLIC);
        assertTrue(exercisesService.exerciseExists(exerciseId));

        exercisesService.deleteExerciseById(exerciseId);
        assertFalse(exercisesService.exerciseExists(exerciseId));
    }

    @Test
    @Transactional
    public void deleteShallowExercise() throws BadInputException, NotFoundException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        ConcreteExercise exercise = createFTBExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,new ArrayList<>(), VisibilitySQL.PUBLIC);
        String shallowId = exercisesService.duplicateExerciseById(specialistId,exerciseId);

        assertTrue(exercisesService.exerciseExists(shallowId));

        exercisesService.deleteExerciseById(shallowId);
        assertFalse(exercisesService.exerciseExists(shallowId));
    }

    @Test
    @Transactional
    public void deleteConcreteExerciseWithCopies() throws BadInputException, NotFoundException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        ConcreteExercise exercise = createFTBExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,new ArrayList<>(), VisibilitySQL.PUBLIC);
        String shallowId = exercisesService.duplicateExerciseById(specialistId,exerciseId);

        assertTrue(exercisesService.exerciseExists(shallowId));
        assertTrue(exercisesService.exerciseExists(exerciseId));

        exercisesService.deleteExerciseById(exerciseId);
        assertTrue(exercisesService.exerciseExists(exerciseId));
        assertTrue(exercisesService.exerciseExists(shallowId));
    }

    @Test
    @Transactional
    public void updateExerciseFail() throws NotFoundException {
        boolean exceptionOcurred = false;
        try {
            ExerciseSolution exerciseSolution = createFTBSolution();
            ExerciseRubric exerciseRubric = createFTBRubric();
            ConcreteExercise exercise = createFTBExercise(specialistId,courseId);
            String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,new ArrayList<>(), VisibilitySQL.PUBLIC);
            exercisesService.updateAllOnExercise(exerciseId,null,null,createOASolution(),null,null);
        } catch (BadInputException bie){
            if(Objects.equals(bie.getMessage(), "Exercise resolution does not match exercise type (fill the blanks)."))
                exceptionOcurred=true;
        }
        assertTrue(exceptionOcurred);
    }

    @Test
    @Transactional
    public void updateExerciseFTBtoMCOnAShallow() throws NotFoundException, BadInputException {
        String tagId = iTagsService.createTag("Espanol","/").getId();
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        ConcreteExercise exercise = createFTBExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution, new ArrayList<>(), VisibilitySQL.PUBLIC);

        ExerciseRubric oaRubric = createOARubric();
        ExerciseSolution oaSolution = createOASolution();
        String shallowId = exercisesService.duplicateExerciseById(specialistId,exerciseId);
        exercisesService.updateAllOnExercise(
                shallowId,
                createOAExercise(specialistId,courseId),
                oaRubric,
                oaSolution, List.of(tagId),VisibilitySQL.COURSE);

        Exercise updatedExercise = exercisesService.getExerciseById(shallowId);
        assert updatedExercise instanceof ConcreteExercise;
        assertEquals("OA", ((ConcreteExercise) updatedExercise).getExerciseType());
        assertEquals(List.of("Espanol"), ((ConcreteExercise) updatedExercise).getTags().stream().map(TagSQL::getName).toList());
        assertTrue(oaRubric.equals(exercisesService.getExerciseRubric(shallowId)));
        assertTrue(oaSolution.getData().equals(exercisesService.getExerciseSolution(shallowId).getData()));

        //Test if original didn't change
        assertTrue(exerciseRubric.equals(exercisesService.getExerciseRubric(exerciseId)));
        assertTrue(exerciseSolution.getData().equals(exercisesService.getExerciseSolution(exerciseId).getData()));
    }

    @Test
    @Transactional
    public void deleteRubricAndSolutionFromExerciseShallow() throws NotFoundException, BadInputException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        ConcreteExercise exercise = createFTBExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution, new ArrayList<>(), VisibilitySQL.PUBLIC);

        String shallowId = exercisesService.duplicateExerciseById(specialistId,exerciseId);
        exercisesService.deleteExerciseRubric(shallowId);
        exercisesService.deleteExerciseSolution(shallowId);

        Exercise updatedExercise = exercisesService.getExerciseById(shallowId);
        assert updatedExercise instanceof ConcreteExercise;
        assertNull(exercisesService.getExerciseRubric(shallowId));
        assertNull(exercisesService.getExerciseSolution(shallowId));

        //Test if original didn't change
        assertTrue(exerciseRubric.equals(exercisesService.getExerciseRubric(exerciseId)));
        assertTrue(exerciseSolution.getData().equals(exercisesService.getExerciseSolution(exerciseId).getData()));
    }

    @Test
    @Transactional
    public void createResolutionAndCorrectForFTBShallow() throws BadInputException, NotFoundException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        ConcreteExercise exercise = createFTBExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,new ArrayList<>(), VisibilitySQL.PUBLIC);
        String shallowId = exercisesService.duplicateExerciseById(specialistId,exerciseId);

        exercisesService.createExerciseResolution(studentId,shallowId,createRightFTBResolution());
    }

    @Test
    @Transactional
    public void alexTest() throws BadInputException, NotFoundException {
        //System.out.println(seed.addSpecialistWhitman());
        //System.out.println(specialistsService.getSpecialistById("657d7ecce74d6353504d0d13"));
    }
}
