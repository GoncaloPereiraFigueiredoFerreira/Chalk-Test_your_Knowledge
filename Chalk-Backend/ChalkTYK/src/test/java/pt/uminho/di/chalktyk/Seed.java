package pt.uminho.di.chalktyk;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
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
import pt.uminho.di.chalktyk.models.exercises.open_answer.*;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.DeliverDateTest;
import pt.uminho.di.chalktyk.models.tests.LiveTest;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ReferenceExercise;
import pt.uminho.di.chalktyk.models.tests.TestGroup;
import pt.uminho.di.chalktyk.models.tests.TestResolution;
import pt.uminho.di.chalktyk.models.tests.TestResolutionStatus;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ConcreteExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.TestExercise;
import pt.uminho.di.chalktyk.models.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.services.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

import javax.swing.*;

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
    public void seed() throws BadInputException, NotFoundException, InterruptedException {
        //List of tags
        Tag tagBiologia = tagsService.createTag("Biologia","/");
        Tag tagProgamacao = tagsService.createTag("Programação","/");
        Tag tagGeologia= tagsService.createTag("Geologia","/");


        Institution inst = new Institution("Greendale", null, null);
        InstitutionManager instMan = new InstitutionManager(null, "Dean Pelton", null, "dpelton@gmail.com", "i'm a peanut bar", inst);
        institutionsService.createInstitutionAndManager(inst,instMan);
        
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

        TimeUnit.SECONDS.sleep(1);
        //Create tests
        createPortugueseExam(s1,c1,Arrays.asList(tagProgamacao));
        // test resolutions
        //testsService.startTest(test1, student1);
        //TestResolution tr1 = new TestResolution(null, LocalDateTime.now(), null, 0, null, st1, t1, TestResolutionStatus.ONGOING, List.of());
        //testsService.createTestResolution(test1, tr1);
    }


    @Test
    public void test() throws BadInputException, NotFoundException {

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
        HashMap<String,MultipleChoiceResolutionItem> itemResolutions = new HashMap<>();
        itemResolutions.put("1",option1);
        itemResolutions.put("2",option2);
        itemResolutions.put("3",option3);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutions);
        return new ExerciseSolution(null,multipleChoiceData);
    }

    private ExerciseRubric createMCRubric(){
        HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        mcRubricMap.put("1",createOARubric());
        mcRubricMap.put("2",createOARubric());
        mcRubricMap.put("3",createOARubric());

        return new MultipleChoiceRubric(0.0F, mcRubricMap);
    }

    private OpenAnswerRubric createOARubric(){
        OAStandard oaStandardMax = new OAStandard("Trató de resolver","Aquí todos pasan la prueba",100.0F);
        OAStandard oaStandardMin = new OAStandard("Ni siquiera intentó resolver","No puedo robar tanto",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandardMin,oaStandardMax);
        return new OpenAnswerRubric(List.of(new OACriterion("Há intentado", 100f, oaStandards)));
    }

    private String createOAExercise0_1(String specialistId, String courseId, List<Tag> tags) throws BadInputException {
        //Solution
        OpenAnswerData openAnswerData = new OpenAnswerData("Ali");
        ExerciseSolution exerciseSolution = new ExerciseSolution(null,openAnswerData);

        //Rubric
        OAStandard oaStandardMax = new OAStandard("Trató de resolver","Aquí todos pasan la prueba",100.0F);
        OAStandard oaStandardMin = new OAStandard("Ni siquiera intentó resolver","No puedo robar tanto",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandardMin,oaStandardMax);
        ExerciseRubric exerciseRubric = new OpenAnswerRubric(List.of(new OACriterion("Há intentado", 100f, oaStandards)));

        //Exercise
        OpenAnswerExercise exercise = new OpenAnswerExercise();
        exercise.setStatement(new ExerciseStatement("Compare, com base em dois aspetos distintos, o modo como Marcenda e Baltasar reagem à impossibilidade de usarem a mão esquerda.","",""));
        exercise.setTitle("Pergunta 1");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);
        return exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, tags.stream().map(Tag::getId).toList());
    }

    private String createFTBExercise0(String specialistId, String courseId, List<Tag> tags) throws BadInputException {
        //Solution
        FillTheBlanksData fillTheBlanksData = new FillTheBlanksData(Arrays.asList("patinhos","nadar"));
        ExerciseSolution exerciseSolution = new ExerciseSolution(null,fillTheBlanksData);
        //Rubric
        FillTheBlanksRubric exerciseRubric = new FillTheBlanksRubric(0.0F);

        //Exercise
        FillTheBlanksExercise exercise = new FillTheBlanksExercise(Arrays.asList("Todos os ", " sabem bem ",""));
        exercise.setStatement(new ExerciseStatement("Preenche com a música dos patinhos","",""));
        exercise.setTitle("Patinhos sabem nadar FTB");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);
        return exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, tags.stream().map(Tag::getId).toList());
    }

    private String createMCExercise0(String specialistId, String courseId, List<Tag> tags) throws BadInputException {
        //Solution
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        HashMap<String,MultipleChoiceResolutionItem> itemResolutionsSol = new HashMap<>();
        itemResolutionsSol.put("1",option1);
        itemResolutionsSol.put("2",option2);
        itemResolutionsSol.put("3",option3);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutionsSol);
        ExerciseSolution exerciseSolution = new ExerciseSolution(null,multipleChoiceData);
        //Rubric
        HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        mcRubricMap.put("1",createOARubric());
        mcRubricMap.put("2",createOARubric());
        mcRubricMap.put("3",createOARubric());
        ExerciseRubric exerciseRubric = new MultipleChoiceRubric(0.0F,mcRubricMap);

        //Exercise
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

        return exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, tags.stream().map(Tag::getId).toList());
    }

    private String createPortugueseExam(Specialist specialist, Course course, List<Tag> tags) throws BadInputException, NotFoundException {
        //Exercises
        String exercise0 = createMCExercise0(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)));
        TestExercise tex1 = new ReferenceExercise(exercise0,3.0F);


        TestGroup tg1 = new TestGroup("Leia os textos e as notas.\n" +
                "A rapariga 1 fica de perfil, o homem está de costas, conversam em voz baixa, mas o tom dela\n" +
                "subiu quando disse, Não, meu pai, sinto-me bem, são portanto pai e filha, conjunção pouco\n" +
                "costumada em hotéis, nestas idades. O criado veio servi-los, sóbrio mas familiar de modos,\n" +
                "depois afastou-se, agora a sala está silenciosa, nem as crianças levantam as vozes, estranho\n" +
                "caso, Ricardo Reis não se lembra de as ter ouvido falar, ou são mudas, ou têm os beiços\n" +
                "colados, presos por agrafes invisíveis, absurda lembrança, se estão comendo. A rapariga\n" +
                "magra acabou a sopa, pousa a colher, a sua mão direita vai afagar, como um animalzinho\n" +
                "doméstico, a mão esquerda que descansa no colo. Então Ricardo Reis, surpreendido\n" +
                "pela sua própria descoberta, repara que desde o princípio aquela mão estivera imóvel,\n" +
                "recorda-se de que só a mão direita desdobrara o guardanapo, e agora agarra a esquerda e vai\n" +
                "pousá-la sobre a mesa, com muito cuidado, cristal fragilíssimo, e ali a deixa ficar, ao lado\n" +
                "do prato, assistindo à refeição, os longos dedos estendidos, pálidos, ausentes. Ricardo Reis\n" +
                "sente um arrepio, é ele quem o sente, ninguém por si o está sentindo, por fora e por dentro da\n" +
                "pele se arrepia, e olha fascinado a mão paralisada e cega que não sabe aonde há de ir se a\n" +
                "não levarem, aqui a apanhar sol, aqui a ouvir a conversa, aqui para que te veja aquele senhor\n" +
                "doutor que veio do Brasil, mãozinha duas vezes esquerda, por estar desse lado e ser canhota,\n" +
                "inábil, inerte, mão morta mão morta que não irás bater àquela porta. Ricardo Reis observa que\n" +
                "os pratos da rapariga vêm já arranjados da copa, limpo de espinhas o peixe, cortada a carne,\n" +
                "descascada e aberta a fruta, é patente que filha e pai são hóspedes conhecidos, costumados\n" +
                "na casa, talvez vivam mesmo no hotel.\n" +
                "                                           José Saramago, O Ano da Morte de Ricardo Reis,\n\n" +
                "10.ª ed., Lisboa, Editorial Caminho, 1993, pp. 26-27.\n" +
                "Nem sempre o trabalho corre bem. Não é verdade que a mão esquerda não faça falta 2 .\n" +
                "Se Deus pode viver sem ela, é porque é Deus, um homem precisa das duas mãos, uma\n" +
                "mão lava a outra, as duas lavam o rosto, quantas vezes já teve Blimunda de limpar o sujo\n" +
                "que ficou agarrado às costas da mão e doutro modo não sairia, são os desastres da guerra,\n" +
                "mínimos estes, porque muitos outros soldados houve que ficaram sem os dois braços, ou as\n" +
                "duas pernas, ou as suas partes de homem, e não têm Blimunda para ajudá-los ou por isso\n" +
                "mesmo a deixaram de ter. É excelente o gancho para travar uma lâmina de ferro ou torcer\n" +
                "um vime, é infalível o espigão para abrir olhais 3 no pano de vela, mas as coisas obedecem\n" +
                "mal quando lhes falta a carícia da pele humana, cuidam que se sumiram os homens a quem\n" +
                "se habituaram, é o desconcerto do mundo. Por isso, Blimunda vem ajudar, e, chegando ela,\n" +
                "acaba-se a rebelião, Ainda bem que vieste, diz Baltasar, ou sentem-no as coisas, não se sabe\n" +
                "ao certo.\n"+
                "Uma vez por outra, Blimunda levanta-se mais cedo, antes de comer o pão de todas as\n" +
                "manhãs 4 , e, deslizando ao longo da parede para evitar pôr os olhos em Baltasar, afasta o pano\n" +
                "e vai inspecionar a obra feita 5 , descobrir a fraqueza escondida do entrançado, a bolha de ar\n" +
                "no interior do ferro, e, acabada a vistoria, fica enfim a mastigar o alimento, pouco a pouco se\n" +
                "tornando tão cega como a outra gente que só pode ver o que à vista está. Quando isto fez\n" +
                "pela primeira vez e Baltasar depois disse ao padre Bartolomeu Lourenço, Este ferro não serve,\n" +
                "tem uma racha por dentro, Como é que sabes, Foi Blimunda que viu, o padre virou-se para\n" +
                "ela, sorriu, olhou um e olhou outro, e declarou, Tu és Sete-Sóis porque vês às claras, tu serás\n" +
                "Sete-Luas porque vês às escuras, e, assim, Blimunda, que até aí só se chamava, como sua\n" +
                "mãe, de Jesus, ficou sendo Sete-Luas, e bem batizada estava, que o batismo foi de padre,\n" +
                "não alcunha de qualquer um. Dormiram nessa noite os sóis e as luas abraçados, enquanto as\n" +
                "estrelas giravam devagar no céu, Lua onde estás, Sol aonde vais.", Float.valueOf(10), List.of(tex1));
        TestGroup tg2 = new TestGroup("Geologia", Float.valueOf(10), List.of(tex1));

        pt.uminho.di.chalktyk.models.tests.Test t1 = new pt.uminho.di.chalktyk.models.tests.Test(null, "TEST #1", "instructions 1",
                20.0F, "", LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), specialist, Visibility.PUBLIC, course, null, List.of(tg1));
        return testsService.createTest(t1);
    }
}
