package pt.uminho.di.chalktyk.Services;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.Seed;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.exercises.*;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksData;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksExercise;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksRubric;
import pt.uminho.di.chalktyk.models.exercises.items.Item;
import pt.uminho.di.chalktyk.models.exercises.items.StringItem;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.*;
import pt.uminho.di.chalktyk.models.exercises.open_answer.*;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.repositories.ExerciseDAO;
import pt.uminho.di.chalktyk.repositories.SpecialistDAO;
import pt.uminho.di.chalktyk.repositories.StudentDAO;
import pt.uminho.di.chalktyk.services.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ExercisesServiceTest {
    private final ICoursesService coursesService;
    private final ISpecialistsService specialistsService;
    private final IExercisesService exercisesService;
    private final IInstitutionsService institutionsService;
    private final IStudentsService studentsService;
    private final ITagsService tagsService;
    private final Seed seed;
    private final EntityManager entityManager;

    @Autowired
    public ExercisesServiceTest(ICoursesService coursesService, ISpecialistsService specialistsService, IExercisesService exercisesService, IInstitutionsService institutionsService, IStudentsService studentsService, ITestsService testsService, ITagsService tagsService, EntityManager entityManager){
        this.coursesService = coursesService;
        this.specialistsService = specialistsService;
        this.exercisesService = exercisesService;
        this.institutionsService = institutionsService;
        this.studentsService = studentsService;
        this.tagsService = tagsService;
        this.entityManager = entityManager;
        this.seed = new Seed(institutionsService,studentsService,specialistsService,coursesService,testsService, tagsService,exercisesService);
    }


    private String specialistId, specialist2Id,
                   courseId, course2Id,
                   studentId, student2Id;
    @Autowired
    private SpecialistDAO specialistDAO;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private ExerciseDAO exerciseDAO;

    @BeforeEach
    public void setup() throws BadInputException {
        this.specialistId = seed.addSpecialistChang();
        this.specialist2Id = seed.addSpecialistWhitman();
        this.courseId = seed.addCourse(specialistId);
        this.course2Id = seed.addCourse2(specialist2Id);
        this.studentId = seed.addStudentAnnie();
        this.student2Id = seed.addStudentGeorge();
    }

    /* ******* CREATE METHODS ******* */

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
        return new ExerciseSolution(null,openAnswerData);
    }

    private ExerciseResolutionData createRightOAResolution(){
        return new OpenAnswerData("Ali");
    }

    private ExerciseResolutionData createWrongOAResolution(){
        return new OpenAnswerData("Aqui");
    }

    private OpenAnswerRubric createOARubric(){
        OAStandard oaStandardMax = new OAStandard("Trató de resolver","Aquí todos pasan la prueba",100.0F);
        OAStandard oaStandardMin = new OAStandard("Ni siquiera intentó resolver","No puedo robar tanto",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandardMin,oaStandardMax);
        return new OpenAnswerRubric(List.of(new OACriterion("Há intentado", 100f, oaStandards)));
    }

    private OpenAnswerExercise createOA2Exercise(String specialistId, String courseId){
        OpenAnswerExercise exercise = new OpenAnswerExercise();
        exercise.setStatement(new ExerciseStatement("¿dos más dos?",null,null));
        exercise.setTitle("Pregunta 2 de Español OA");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);
        return exercise;
    }

    private ExerciseSolution createOA2Solution(){
        OpenAnswerData openAnswerData = new OpenAnswerData("cuatro");
        return new ExerciseSolution(null,openAnswerData);
    }

    private OpenAnswerRubric createOA2Rubric(){
        OAStandard oaStandardMax = new OAStandard("Acertou","Se escreveu 'cuatro', recebe todos os pontos.",100.0F);
        OAStandard oaStandardMin = new OAStandard("Falhou","Se não escreveu 'cuatro', não recebe pontos",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandardMin,oaStandardMax);
        return new OpenAnswerRubric(List.of(new OACriterion("Desempenho",100f, oaStandards)));
    }


    private MultipleChoiceExercise createMCExercise(String specialistId, String courseId){
        HashMap<Integer, Item> itemResolutions = new HashMap<>();
        itemResolutions.put(1,new StringItem("Là"));
        itemResolutions.put(2,new StringItem("Ali"));
        itemResolutions.put(3,new StringItem("There"));




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
        HashMap<Integer,MultipleChoiceResolutionItem> itemResolutions = new HashMap<>();
        itemResolutions.put(1,option1);
        itemResolutions.put(2,option2);
        itemResolutions.put(3,option3);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutions);
        return new ExerciseSolution(null,multipleChoiceData);
    }

    private ExerciseResolutionData createRightMCResolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        HashMap<Integer,MultipleChoiceResolutionItem> itemResolutions = new HashMap<>();
        itemResolutions.put(1,option1);
        itemResolutions.put(2,option2);
        itemResolutions.put(3,option3);
        return new MultipleChoiceData(itemResolutions);
    }

    private ExerciseResolutionData createWrongMCResolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,true);
        HashMap<Integer,MultipleChoiceResolutionItem> itemResolutions = new HashMap<>();
        itemResolutions.put(1,option1);
        itemResolutions.put(2,option2);
        itemResolutions.put(3,option3);
        return new MultipleChoiceData(itemResolutions);
    }

    private ExerciseResolutionData createHalfWrongMCResolution(){
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        HashMap<Integer,MultipleChoiceResolutionItem> itemResolutions = new HashMap<>();
        itemResolutions.put(1,option1);
        itemResolutions.put(2,option2);
        itemResolutions.put(3,option3);
        return new MultipleChoiceData(itemResolutions);
    }

    private ExerciseRubric createMCRubric(){
        HashMap<Integer,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        mcRubricMap.put(1,createOARubric());
        mcRubricMap.put(2,createOARubric());
        mcRubricMap.put(3,createOARubric());
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
        return new FillTheBlanksRubric(0.0F);
    }

    /* ****** TESTS ****** */

    @Test
    public void getExerciseFailWithNotFound(){
        try {
            exercisesService.getExerciseById("DoesNotExistID");
            assert false;
        } catch (NotFoundException e) {
            assert true;
        }
    }

    @Test
    public void createOAExercise() throws BadInputException {
        ExerciseSolution exerciseSolution = createOASolution();
        ExerciseRubric exerciseRubric = createOARubric();
        Exercise exercise = createOAExercise(specialistId,courseId);
        Tag tag1 = tagsService.createTag("Espanol","/");
        Tag tag2 = tagsService.createTag("NewEspanol","/");
        String exerciseId = exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, List.of(tag1.getId(), tag2.getId()));
        assertTrue(exercisesService.exerciseExists(exerciseId));
        assert exercisesService.isExerciseOwner(exerciseId, specialistId);
        Set<String> s1 = Set.of(tag1.getId(), tag2.getId()),
                    s2 = exercisesService.getExerciseTags(exerciseId)
                                         .stream().map(Tag::getId)
                                         .collect(Collectors.toSet());
        assert s1.containsAll(s2) && s2.containsAll(s1);
    }

    @Test
    public void createOAExerciseThenSolutionAndRubric() throws BadInputException, NotFoundException {
        ExerciseSolution exerciseSolution = createOASolution();
        ExerciseRubric exerciseRubric = createOARubric();
        Exercise exercise = createOAExercise(specialistId,courseId);
        Tag tag1 = tagsService.createTag("Espanol","/");
        Tag tag2 = tagsService.createTag("NewEspanol","/");
        String exerciseId = exercisesService.createExercise(exercise,null,null, List.of(tag1.getId(), tag2.getId()));
        assertTrue(exercisesService.exerciseExists(exerciseId));
        exercisesService.createExerciseSolution(exerciseId, exerciseSolution);
        exercisesService.createExerciseRubric(exerciseId, exerciseRubric);
    }

    @Test
    public void createExerciseAndCheckInstitution() throws BadInputException, NotFoundException {
        Institution institution = new Institution("UM", "Melhor universidade do país", "uminho.pt/images/logo.png");
        InstitutionManager manager = new InstitutionManager(null, "Rogerio", "uminho.pt/images/fotoDoRogerio.png", "rogerio@uminho.pt", "Manager da UM");
        institutionsService.createInstitutionAndManager(institution, manager);
        institutionsService.addSpecialistsToInstitution(institution.getName(), List.of(specialistId));

        entityManager.flush();
        entityManager.clear();

        Exercise exercise = createOAExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,null,null, new ArrayList<>());
        assert exercisesService.getExerciseById(exerciseId).getInstitution().getName().equals("UM");
        assert exercisesService.getExerciseInstitution(exerciseId).equals("UM");
    }

    @Test
    public void createMCExercise() throws BadInputException {
        ExerciseSolution exerciseSolution = createMCSolution();
        ExerciseRubric exerciseRubric = createMCRubric();
        Exercise exercise = createMCExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, new ArrayList<>());
        assertTrue(exercisesService.exerciseExists(exerciseId));
    }

    @Test
    public void createFTBExercise() throws BadInputException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        Exercise exercise = createFTBExercise(specialistId,courseId);

        String exerciseId = exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, Arrays.asList());
        assertTrue(exercisesService.exerciseExists(exerciseId));
    }

    @Test
    public void createDuplicate() throws BadInputException, NotFoundException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        Exercise exercise = createFTBExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, new ArrayList<>());
        String duplicateId = exercisesService.duplicateExerciseById(specialistId,exerciseId);
        assertTrue(exercisesService.exerciseExists(duplicateId));
        assertTrue(exercisesService.exerciseExists(exerciseId));
    }

    @Test
    public void deleteExercise() throws BadInputException, NotFoundException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        Exercise exercise = createFTBExercise(specialistId,courseId);

        String exerciseId = exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, List.of());
        assertTrue(exercisesService.exerciseExists(exerciseId));

        exercisesService.deleteExerciseById(exerciseId);
        assertFalse(exercisesService.exerciseExists(exerciseId));
    }

    @Test
    public void updateAllOnExercise(){
        try {
            // creates exercise
            Exercise exercise = createOAExercise(specialistId, courseId);
            ExerciseSolution solution = createOASolution();
            ExerciseRubric rubric = createOARubric();
            String exerciseId = exercisesService.createExercise(exercise, solution, rubric, new ArrayList<>());

            // create some tags
            Tag tag1 = tagsService.createTag("Espanol", "/");
            String tag1Id = tag1.getId();

            // updates everything about the exercise
            Exercise exercise2 = createOA2Exercise(specialist2Id, course2Id);
            ExerciseSolution solution2 = createOA2Solution();
            ExerciseRubric rubric2 = createOA2Rubric();

            // sets up a backup to prove the updates are correct
            Exercise exerciseBackup = exercise2.cloneExerciseDataOnly();
            exerciseBackup.setId(exerciseId);
            ExerciseRubric rubricBackup = rubric2.clone();
            ExerciseResolutionData solutionBackup = solution2.getData().clone();

            // assert that the exercises are different
            exercise = exercisesService.getExerciseById(exerciseId);
            rubric = exercisesService.getExerciseRubric(exerciseId);
            solution = exercisesService.getExerciseSolution(exerciseId);
            assert !exercise.equalsWithoutAssociations(exerciseBackup);
            assert !rubric.equals(rubricBackup);
            assert !solution.getData().equals(solutionBackup);

            // updates the exercise
            exercisesService.updateAllOnExercise(exerciseId, exercise2, rubric2, solution2, List.of(tag1Id), Visibility.PRIVATE);

            // flush and clear entity manager
            // to cleanly retrieve the exercise from the database
            entityManager.flush();
            entityManager.clear();

            // retrieves the exercise
            exercise = exercisesService.getExerciseById(exerciseId);
            rubric = exercisesService.getExerciseRubric(exerciseId);
            solution = exercisesService.getExerciseSolution(exerciseId);

            // owner and course should not have changed
            // even tho it was sent on the exercise2 body
            assert exercise.getSpecialistId().equals(specialistId);
            assert exercise.getCourseId().equals(courseId);

            // assert that it has a new tag
            List<String> tagsList = exercise.getTags().stream().map(Tag::getId).toList();
            assert tagsList.size() == 1 && tagsList.get(0).equals(tag1Id);

            // assert that the updates were successful
            assert exercise.equalsDataOnly(exerciseBackup);
            assert exercise.getVisibility().equals(Visibility.PRIVATE);
            assert exercisesService.getExerciseVisibility(exerciseId).equals(Visibility.PRIVATE);
            assert rubric.clone().equals(rubricBackup);
            assert solution.getData().clone().equals(solutionBackup);

            // update exercise course
            exercisesService.updateExerciseCourse(exerciseId, course2Id);

            // flush and clear entity manager
            // to cleanly retrieve the exercise from the database
            entityManager.flush();
            entityManager.clear();

            // assert that the course changed
            assert exercisesService.getExerciseCourse(exerciseId).equals(course2Id) &&
                   exercisesService.getExerciseById(exerciseId).getCourseId().equals(course2Id) ;

        }catch (BadInputException | NotFoundException bie){
            assert false;
        }
    }

    @Test
    public void updateAllOnExerciseIndividualMethods(){
        try {
            // creates exercise
            Exercise exercise = createOAExercise(specialistId, courseId);
            ExerciseSolution solution = createOASolution();
            ExerciseRubric rubric = createOARubric();
            String exerciseId = exercisesService.createExercise(exercise, solution, rubric, new ArrayList<>());

            // create some tags
            Tag tag1 = tagsService.createTag("Espanol", "/");
            String tag1Id = tag1.getId();

            // updates everything about the exercise
            Exercise exercise2 = createOA2Exercise(specialist2Id, course2Id);
            ExerciseSolution solution2 = createOA2Solution();
            ExerciseRubric rubric2 = createOA2Rubric();

            // sets up a backup to prove the updates are correct
            Exercise exerciseBackup = exercise2.cloneExerciseDataOnly();
            exerciseBackup.setId(exerciseId);
            ExerciseRubric rubricBackup = rubric2.clone();
            ExerciseResolutionData solutionBackup = solution2.getData().clone();

            // assert that the exercises are different
            exercise = exercisesService.getExerciseById(exerciseId);
            rubric = exercisesService.getExerciseRubric(exerciseId);
            solution = exercisesService.getExerciseSolution(exerciseId);
            assert !exercise.equalsWithoutAssociations(exerciseBackup);
            assert !rubric.equals(rubricBackup);
            assert !solution.getData().equals(solutionBackup);

            // updates the exercise
            exercisesService.updateExerciseBody(exerciseId, exercise2);
            exercisesService.updateExerciseRubric(exerciseId, rubric2);
            exercisesService.updateExerciseSolution(exerciseId, solution2);
            exercisesService.updateExerciseTags(exerciseId, List.of(tag1Id));
            exercisesService.updateExerciseVisibility(exerciseId, Visibility.PRIVATE);

            // flush and clear entity manager
            // to cleanly retrieve the exercise from the database
            entityManager.flush();
            entityManager.clear();

            // retrieves the exercise
            exercise = exercisesService.getExerciseById(exerciseId);
            rubric = exercisesService.getExerciseRubric(exerciseId);
            solution = exercisesService.getExerciseSolution(exerciseId);

            // owner and course should not have changed
            // even tho it was sent on the exercise2 body
            assert exercise.getSpecialistId().equals(specialistId);
            assert exercise.getCourseId().equals(courseId);

            // assert that it has a new tag
            List<String> tagsList = exercise.getTags().stream().map(Tag::getId).toList();
            assert tagsList.size() == 1 && tagsList.get(0).equals(tag1Id);

            // assert that the updates were successful
            assert exercise.equalsDataOnly(exerciseBackup);
            assert exercise.getVisibility().equals(Visibility.PRIVATE);
            assert rubric.clone().equals(rubricBackup);
            assert solution.getData().clone().equals(solutionBackup);

            // update exercise course
            exercisesService.updateExerciseCourse(exerciseId, course2Id);

            // flush and clear entity manager
            // to cleanly retrieve the exercise from the database
            entityManager.flush();
            entityManager.clear();

            // assert that the course changed
            assert exercisesService.getExerciseCourse(exerciseId).equals(course2Id) &&
                    exercisesService.getExerciseById(exerciseId).getCourseId().equals(course2Id) ;

        }catch (BadInputException | NotFoundException bie){
            assert false;
        }
    }

    @Test
    public void updateExerciseBody(){
        try {
            // creates exercise
            Exercise exercise = createOAExercise(specialistId, courseId);
            ExerciseSolution solution = createOASolution();
            ExerciseRubric rubric = createOARubric();
            ExerciseStatement statementBackup = exercise.getStatement().clone();
            String exerciseId = exercisesService.createExercise(exercise, solution, rubric, new ArrayList<>());

            // updates everything about the exercise
            Exercise exercise2 = createOA2Exercise(specialistId, courseId);
            ExerciseStatement statement2Backup = exercise2.getStatement().clone();
            exercisesService.updateAllOnExercise(exerciseId, exercise2, null, null, null, null);

            // flush and clear entity manager
            // to cleanly retrieve the exercise from the database
            entityManager.flush();
            entityManager.clear();

            // retrieves the exercise
            exercise = exercisesService.getExerciseById(exerciseId);

            // assert that the updates were successful
            assert !exercise.getStatement().equals(statementBackup) &&
                    exercise.getStatement().equals(statement2Backup);
        }catch (BadInputException | NotFoundException bie){
            assert false;
        }
    }

    @Test
    public void updateExerciseFail() throws NotFoundException {
        boolean exceptionOcurred = false;
        try {
            ExerciseSolution exerciseSolution = createFTBSolution();
            ExerciseRubric exerciseRubric = createFTBRubric();
            Exercise exercise = createFTBExercise(specialistId,courseId);
            String exerciseId = exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, new ArrayList<>());
            exercisesService.updateAllOnExercise(exerciseId,null,null,createOASolution(),null,null);
        } catch (BadInputException bie){
            System.out.println(bie.getMessage());
            if(Objects.equals(bie.getMessage(), "Exercise solution/resolution does not match exercise type (fill the blanks)."))
                exceptionOcurred=true;
        }
        assertTrue(exceptionOcurred);
    }

    @Test
    public void deleteRubricAndSolution() throws NotFoundException, BadInputException {
        ExerciseSolution exerciseSolution = createFTBSolution();
        ExerciseRubric exerciseRubric = createFTBRubric();
        Exercise exercise = createFTBExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, new ArrayList<>());
        exercisesService.deleteExerciseRubric(exerciseId);
        exercisesService.deleteExerciseSolution(exerciseId);

        assertTrue(exercisesService.exerciseExists(exerciseId));
        assertNull(exercisesService.getExerciseRubric(exerciseId));
        assertNull(exercisesService.getExerciseSolution(exerciseId));
    }

    @Test
    public void createResolutionAndAutoCorrectAndDelete() throws BadInputException, NotFoundException, UnauthorizedException {
        ExerciseSolution exerciseSolution = createMCSolution();
        ExerciseRubric exerciseRubric = createMCRubric();
        Exercise exercise = createMCExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, new ArrayList<>());

        ExerciseResolutionData rightMC = createRightMCResolution();
        var er1 = exercisesService.createExerciseResolution(studentId,exerciseId,rightMC.clone());

        //Verify that the resolution was correctly inserted
        ExerciseResolution exerciseResolution = exercisesService.getLastExerciseResolutionByStudent(exerciseId,studentId);
        assertTrue(rightMC.equals(exerciseResolution.getData()));
        assertNull(exerciseResolution.getPoints());
        assertEquals(1,exerciseResolution.getSubmissionNr());
        assertEquals(ExerciseResolutionStatus.NOT_REVISED,exerciseResolution.getStatus());

        //Issue the correction of this exercise resolution specifically
        exercisesService.issueExerciseResolutionCorrection(exerciseResolution.getId(),"auto");

        // checks that the points were correctly assigned
        exerciseResolution = exercisesService.getLastExerciseResolutionByStudent(exerciseId,studentId);
        assertEquals(100.0F,exerciseResolution.getPoints());
        assertEquals(ExerciseResolutionStatus.REVISED,exerciseResolution.getStatus());

        // deletes the resolution
        String resId = exerciseResolution.getId();
        exercisesService.deleteExerciseResolutionById(resId);
        try{
            exercisesService.getExerciseResolution(resId);
            assert false; // should not reach this point
        }catch (Exception e){
            assert true;
        }
    }

    @Test
    public void createResolutionsAndAutoCorrect() throws BadInputException, NotFoundException, UnauthorizedException {
        ExerciseSolution exerciseSolution = createMCSolution();
        ExerciseRubric exerciseRubric = createMCRubric();
        Exercise exercise = createMCExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, new ArrayList<>());
        float maxPoints = 100f;

        ExerciseResolutionData wrongMC = createWrongMCResolution();
        var er1 = exercisesService.createExerciseResolution(studentId,exerciseId,wrongMC.clone());
        String er1Id = er1.getId();
        ExerciseResolutionData rightMC = createRightMCResolution();
        var er2 = exercisesService.createExerciseResolution(studentId,exerciseId,rightMC.clone());
        String er2Id = er2.getId();
        assertEquals(2,exercisesService.countExerciseResolutions(exerciseId,true));
        assertEquals(1,exercisesService.countExerciseResolutions(exerciseId,false));

        // same verifications as above but using different methods
        var listOfResolutionsIdsWithGet = exercisesService.getStudentListOfExerciseResolutions(exerciseId, studentId).stream().map(ExerciseResolution::getId).toList();
        var listOfResolutionsIds = List.of(er1.getId(), er2.getId());
        assert listOfResolutionsIds.containsAll(listOfResolutionsIdsWithGet) && listOfResolutionsIdsWithGet.containsAll(listOfResolutionsIds);

        var pairsOfStudentAndResolutions = exercisesService.getExerciseResolutions(exerciseId, 0, 5, false);
        assert pairsOfStudentAndResolutions.size() == 2;
        listOfResolutionsIdsWithGet =
                pairsOfStudentAndResolutions.stream().filter(p -> p.getLeft().getId().equals(studentId))
                                                     .map(p -> p.getRight().getId()).toList();
        assert listOfResolutionsIds.containsAll(listOfResolutionsIdsWithGet) && listOfResolutionsIdsWithGet.containsAll(listOfResolutionsIds);

        pairsOfStudentAndResolutions = exercisesService.getExerciseResolutions(exerciseId, 0, 5, true);
        assert pairsOfStudentAndResolutions.size() == 1;
        assert pairsOfStudentAndResolutions.get(0).getRight().getId().equals(er2.getId());

        assert exercisesService.countExerciseResolutionsByStudent(exerciseId, studentId) == 2;

        // check that the id of the last resolution of the student for the exercise
        assert exercisesService.getLastExerciseResolutionByStudent(exerciseId, studentId).getId().equals(er2.getId());

        //Verify that the resolution was correctly inserted
        ExerciseResolution exerciseResolution = exercisesService.getLastExerciseResolutionByStudent(exerciseId,studentId);
        assertTrue(rightMC.equals(exerciseResolution.getData()));
        assertNull(exerciseResolution.getPoints());
        assertEquals(2,exerciseResolution.getSubmissionNr());
        assertEquals(ExerciseResolutionStatus.NOT_REVISED,exerciseResolution.getStatus());

        // issues automatic correction of the exercise
        exercisesService.issueExerciseResolutionsCorrection(exerciseId,"auto");
        exerciseResolution = exercisesService.getLastExerciseResolutionByStudent(exerciseId,studentId);
        assert exerciseResolution.getId().equals(er2Id);
        assertFalse(rightMC.equals(exerciseResolution.getData()));
        assertEquals(maxPoints,exerciseResolution.getPoints());
        assertEquals(2,exerciseResolution.getSubmissionNr());
        assertEquals(ExerciseResolutionStatus.REVISED,exerciseResolution.getStatus());

        // checks that the points of the first resolution equal to 0
        exerciseResolution = exercisesService.getExerciseResolution(er1Id);
        assertEquals(0.0f, exerciseResolution.getPoints());
    }

    @Test
    public void createResolutionAndManuallyCorrect() throws BadInputException, NotFoundException, UnauthorizedException {
        ExerciseSolution exerciseSolution = createMCSolution();
        ExerciseRubric exerciseRubric = createMCRubric();
        Exercise exercise = createMCExercise(specialistId,courseId);
        String exerciseId = exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, new ArrayList<>());
        ExerciseResolutionData rightMC = createRightMCResolution();
        var er1 = exercisesService.createExerciseResolution(studentId,exerciseId,rightMC.clone());
        String resolutionId = er1.getId();
        Comment comment = new Comment(List.of(new StringItem("Muito bem!")));

        // comment and points should be null. And the resolution cannot be already REVISED.
        assert er1.getComment() == null;
        assert er1.getPoints() == null;
        assert er1.getStatus().equals(ExerciseResolutionStatus.NOT_REVISED);

        float maxPoints = 100f;

        exercisesService.addCommentToExerciseResolution(resolutionId, comment.clone());
        exercisesService.manuallyCorrectExerciseResolution(resolutionId, maxPoints);

        entityManager.flush();
        entityManager.clear();

        er1 = exercisesService.getExerciseResolution(resolutionId);
        // asserts that the comment exists and that it is equal to the one that was created above.
        assert comment.equals(er1.getComment());
        // asserts that the points are now equal to the maxPoints of the exercise
        assert er1.getPoints() == maxPoints;
        // asserts that the exercise is now revised
        assert er1.getStatus() == ExerciseResolutionStatus.REVISED;

        // removes comment
        exercisesService.removeCommentFromExerciseResolution(resolutionId);

        entityManager.flush();
        entityManager.clear();

        // asserts that there is no comment anymore
        assert exercisesService.getExerciseResolution(resolutionId).getComment() == null;
    }

    @Test
    public void testGetExercises() throws BadInputException, NotFoundException {
        Tag tag1 = tagsService.createTag("tag1","/"),
            tag2 = tagsService.createTag("tag2","/"),
            tag3 = tagsService.createTag("tag3","/");
        Exercise exercise1 = createOAExercise(specialistId, courseId),
                 exercise2 = createMCExercise(specialist2Id, course2Id),
                 exercise3 = createFTBExercise(specialistId, null),
                 exercise4 = createOA2Exercise(specialist2Id, course2Id);
        exercise4.setVisibility(Visibility.COURSE);
        String exercise1Id = exercisesService.createExercise(exercise1, null, null, List.of(tag1.getId(), tag2.getId(), tag3.getId()));
        String exercise2Id = exercisesService.createExercise(exercise2, null, null, List.of(tag1.getId(), tag2.getId()));
        String exercise3Id = exercisesService.createExercise(exercise3, null, null, List.of(tag1.getId()));
        String exercise4Id = exercisesService.createExercise(exercise4, null, null, List.of(tag3.getId()));

        // no filters. All 4 exercises should be received
        List<String> list = exercisesService.getExercises(0, 10, null, false, null, null, null, null, null, null, false)
                                            .stream().map(Exercise::getId).toList();
        assert list.size() == 4 && list.containsAll(List.of(exercise1Id, exercise2Id, exercise3Id, exercise4Id));

        // exercises should have all 3 tags. Only ex1 should be received
        list = exercisesService.getExercises(0, 10, List.of(tag1.getId(), tag2.getId(), tag3.getId()), true, null, null, null, null, null, null, false)
                               .stream().map(Exercise::getId).toList();
        assert list.size() == 1 && list.contains(exercise1Id);

        // exercises should have tag1 and tag2. ex1 and ex2 should be received
        list = exercisesService.getExercises(0, 10, List.of(tag1.getId(), tag2.getId()), true, null, null, null, null, null, null, false)
                               .stream().map(Exercise::getId).toList();
        assert list.size() == 2 && list.containsAll(List.of(exercise1Id, exercise2Id));

        // exercises should have tag1. ex1, ex2 and ex3 should be received
        list = exercisesService.getExercises(0, 10, List.of(tag1.getId()), true, null, null, null, null, null, null, false)
                               .stream().map(Exercise::getId).toList();
        assert list.size() == 3 && list.containsAll(List.of(exercise1Id, exercise2Id, exercise3Id));

        // exercises should have one of the following tags: tag2, tag3. ex1, ex2 and ex4 should be received
        list = exercisesService.getExercises(0, 10, List.of(tag2.getId(), tag3.getId()), false, null, null, null, null, null, null, false)
                               .stream().map(Exercise::getId).toList();
        assert list.size() == 3 && list.containsAll(List.of(exercise1Id, exercise2Id, exercise4Id));

        // should have visibility "COURSE". ex4 should be received
        list = exercisesService.getExercises(0, 10, null, false, Visibility.COURSE, null, null, null, null, null, false)
                .stream().map(Exercise::getId).toList();
        String vis = Visibility.COURSE.toString();
        assert list.size() == 1 && list.contains(exercise4Id);

        // should have visibility "PUBLIC". ex1, ex2 and ex3 should be received
        list = exercisesService.getExercises(0, 10, null, false, Visibility.PUBLIC, null, null, null, null, null, false)
                .stream().map(Exercise::getId).toList();
        assert list.size() == 3 && list.containsAll(List.of(exercise1Id, exercise2Id, exercise3Id));

        // should belong to course2. ex2 and ex4 should be received
        list = exercisesService.getExercises(0, 10, null, false, null, course2Id, null, null, null, null, false)
                .stream().map(Exercise::getId).toList();
        assert list.size() == 2 && list.containsAll(List.of(exercise2Id, exercise4Id));

        // should have as owner the specialist with id equal to specialistId. ex1 and ex3 should be received
        list = exercisesService.getExercises(0, 10, null, false, null, null, null, specialistId, null, null, false)
                .stream().map(Exercise::getId).toList();
        assert list.size() == 2 && list.containsAll(List.of(exercise1Id, exercise3Id));

        // should have title similar to 'Patinhos sabem nadar'. ex3 should be received
        list = exercisesService.getExercises(0, 10, null, false, null, null, null, null, "Patinhos sabem nadar", null, false)
                .stream().map(Exercise::getId).toList();
        assert list.size() == 1 && list.contains(exercise3Id);

        // should be of type 'OA'. ex1 and ex4 should be received
        list = exercisesService.getExercises(0, 10, null, false, null, null, null, null, null, "OA", false)
                .stream().map(Exercise::getId).toList();
        assert list.size() == 2 && list.containsAll(List.of(exercise1Id, exercise4Id));
    }

    @Test
    public void testCountTags() throws BadInputException {
        Tag tag1 = tagsService.createTag("tag1", "/"),
            tag2 = tagsService.createTag("tag2", "/"),
            tag3 = tagsService.createTag("tag3", "/");
        Exercise exercise1 = createOAExercise(specialistId, courseId),
                exercise2 = createMCExercise(specialist2Id, course2Id),
                exercise3 = createFTBExercise(specialistId, null),
                exercise4 = createOA2Exercise(specialist2Id, course2Id);
        exercise4.setVisibility(Visibility.COURSE);
        String exercise1Id = exercisesService.createExercise(exercise1, null, null, List.of(tag1.getId(), tag2.getId(), tag3.getId()));
        String exercise2Id = exercisesService.createExercise(exercise2, null, null, List.of(tag1.getId(), tag2.getId()));
        String exercise3Id = exercisesService.createExercise(exercise3, null, null, List.of(tag1.getId()));
        String exercise4Id = exercisesService.createExercise(exercise4, null, null, List.of(tag3.getId()));

        var set = exercisesService.countTagsOccurrencesForExercisesList(List.of(exercise1Id, exercise2Id, exercise3Id, exercise4Id));
        System.out.println(set);
        for(var pair : set){
            String tagId = pair.getLeft().getId();
            Long count = pair.getRight();

            if (tagId.equals(tag1.getId()))
                assert count == 3;
            else if (tagId.equals(tag2.getId()))
                assert count == 2;
            else if (tagId.equals(tag3.getId()))
                assert count == 2;
        }
    }
}
