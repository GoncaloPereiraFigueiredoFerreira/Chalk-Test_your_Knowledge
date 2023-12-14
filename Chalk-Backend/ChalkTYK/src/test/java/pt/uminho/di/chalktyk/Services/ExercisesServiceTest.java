package pt.uminho.di.chalktyk.Services;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import pt.uminho.di.chalktyk.Seed;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.*;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks.FillTheBlanksData;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks.FillTheBlanksExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks.FillTheBlanksRubric;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice.*;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.*;
import pt.uminho.di.chalktyk.models.relational.ExerciseSQL;
import pt.uminho.di.chalktyk.models.relational.VisibilitySQL;
import pt.uminho.di.chalktyk.services.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private OpenAnswerExercise createOAExercise(String specialistId,String courseId){
        OpenAnswerExercise exercise = new OpenAnswerExercise();
        exercise.setStatement(new ExerciseStatement("Donde está la biblioteca","",""));
        exercise.setTitle("Pregunta de Español OA");
        exercise.setCotation(2.0F);
        exercise.setSpecialistId(specialistId);
        exercise.setCourseId(courseId);
        return exercise;
    }

    private ExerciseSolution createOASolution(){
        OpenAnswerData openAnswerData = new OpenAnswerData("Ali");
        return new ExerciseSolution(null,openAnswerData);
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
        exercise.setCotation(3.0F);
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

    private ExerciseRubric createMCRubric(){
        return new MultipleChoiceRubric(Arrays.asList(createOARubric(),createOARubric(),createOARubric()),1.0F,0.0F);
    }

    private FillTheBlanksExercise createFTBExercise(String specialistId, String courseId){
        FillTheBlanksExercise fillTheBlanksExercise = new FillTheBlanksExercise(Arrays.asList("Todos os ", " sabem bem ",""));
        fillTheBlanksExercise.setStatement(new ExerciseStatement("Preenche com a música dos patinhos","",""));
        fillTheBlanksExercise.setTitle("Patinhos sabem nadar FTB");
        fillTheBlanksExercise.setCotation(2.0F);
        fillTheBlanksExercise.setSpecialistId(specialistId);
        fillTheBlanksExercise.setCourseId(courseId);
        return fillTheBlanksExercise;
    }

    private ExerciseSolution createFTBSolution(){
        FillTheBlanksData fillTheBlanksData = new FillTheBlanksData(Arrays.asList("patinhos","nadar"));
        return new ExerciseSolution(null,fillTheBlanksData);
    }

    private ExerciseRubric createFTBRubric(){
        return new FillTheBlanksRubric(1.0F,0.0F);
    }

    
    @Test
    public void createOAExercise() throws BadInputException {
        String specialistId = seed.addSpecialistChang();
        String courseId = seed.addCourse(specialistId);
        ExerciseSolution exerciseSolution = createOASolution();
        ExerciseRubric exerciseRubric = createOARubric();
        ConcreteExercise exercise = createOAExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,new ArrayList<>(), VisibilitySQL.PUBLIC);
        assertTrue(exercisesService.exerciseExists(exerciseId));
    }

    @Test
    public void createMCExercise() throws BadInputException {
        String specialistId = seed.addSpecialistChang();
        String courseId = seed.addCourse(specialistId);
        ExerciseSolution exerciseSolution = createMCSolution();
        ExerciseRubric exerciseRubric = createMCRubric();
        ConcreteExercise exercise = createMCExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,new ArrayList<>(), VisibilitySQL.PUBLIC);
        assertTrue(exercisesService.exerciseExists(exerciseId));
    }

    @Test
    public void createFTBExercise() throws BadInputException {
        String specialistId = seed.addSpecialistChang();
        String courseId = seed.addCourse(specialistId);
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        ConcreteExercise exercise = createFTBExercise(specialistId,courseId);

        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,Arrays.asList("26c3e51a-bb31-4807-a3d1-b29d566fe7e1"), VisibilitySQL.PUBLIC);
        assertTrue(exercisesService.exerciseExists(exerciseId));
    }

    @Test
    public void createShallowCopy() throws BadInputException, UnauthorizedException, NotFoundException {
        String specialistId = seed.addSpecialistChang();
        String courseId = seed.addCourse(specialistId);
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        ConcreteExercise exercise = createFTBExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseRubric,exerciseSolution,new ArrayList<>(), VisibilitySQL.PUBLIC);
        String shallowId = exercisesService.duplicateExerciseById(specialistId,exerciseId);
        assertFalse(exercisesService.exerciseIsShallow(exerciseId));
        assertTrue(exercisesService.exerciseIsShallow(shallowId));
    }
}
