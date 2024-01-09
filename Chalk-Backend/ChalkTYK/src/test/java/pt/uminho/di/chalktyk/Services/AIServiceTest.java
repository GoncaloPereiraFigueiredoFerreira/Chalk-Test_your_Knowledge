package pt.uminho.di.chalktyk.Services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import pt.uminho.di.chalktyk.Seed;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.ExerciseSolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseStatement;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OAStandard;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerData;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerExercise;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.services.*;
import pt.uminho.di.chalktyk.services.exceptions.ApiConnectionException;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

/**
 * AIServiceTest
 */
@SpringBootTest
public class AIServiceTest {

    private final IAIService aiService;
    private final ICoursesService coursesService;
    private final ISpecialistsService specialistsService;
    private final IExercisesService exercisesService;
    private final IInstitutionsService institutionsService;
    private final IStudentsService studentsService;
    private final ITagsService tagsService;
    private final ISeedService seedService;

    @Autowired
    public AIServiceTest(IAIService aiService, ICoursesService coursesService, ISpecialistsService specialistsService,
                         IExercisesService exercisesService, IInstitutionsService institutionsService,
                         IStudentsService studentsService , ITagsService iTagsService, ISeedService seedService) {
        this.aiService = aiService;
        this.coursesService = coursesService;
        this.specialistsService = specialistsService;
        this.exercisesService = exercisesService;
        this.institutionsService = institutionsService;
        this.studentsService = studentsService;
        this.tagsService = iTagsService;
        this.seedService = seedService;
    }

    private String specialistId,courseId,studentId;

    @BeforeEach
    public void setup() throws BadInputException {
        this.specialistId = seedService.addSpecialistChang();
        this.courseId = seedService.addCourse(specialistId);
        this.studentId = seedService.addStudentAnnie();
    }

    private ExerciseSolution createOASolution(){
        OpenAnswerData openAnswerData = new OpenAnswerData("Ali");
        return new ExerciseSolution(null,openAnswerData);
    }

    private OpenAnswerRubric createOARubric(){
        OAStandard oaStandardMax = new OAStandard("Trató de resolver","Aquí todos pasan la prueba",100.0F);
        OAStandard oaStandardMin = new OAStandard("Ni siquiera intentó resolver","No puedo robar tanto",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandardMin,oaStandardMax);
        return new OpenAnswerRubric(List.of(new OACriterion("Há intentado", 50f, oaStandards),new OACriterion("Há intentado", 50f, oaStandards)));
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

    private ExerciseResolution createOAResolution(){
        OpenAnswerData data = new OpenAnswerData();
        data.setText("ola");

        ExerciseResolution ret = new ExerciseResolution();
        ret.setData(data);

        return ret;
    }

    private String createOAExercise() throws BadInputException {
        ExerciseSolution exerciseSolution = createOASolution();
        ExerciseRubric exerciseRubric = createOARubric();
        Exercise exercise = createOAExercise(specialistId,courseId);
        Tag tag1 = tagsService.createTag("Espanol","/");
        Tag tag2 = tagsService.createTag("NewEspanol","/");
        String exerciseId = exercisesService.createExercise(exercise,exerciseSolution,exerciseRubric, List.of(tag1.getId(), tag2.getId()));

        return exerciseId;
    }

    @Test
    @Transactional
    public void evaluateOpenAnswer() throws BadInputException, NotFoundException, ApiConnectionException{
        Exercise exercise = createOAExercise(specialistId, courseId); 
        ExerciseRubric rubric = createOARubric();
        ExerciseResolution solution = createOAResolution();
        aiService.evaluateOpenAnswer(exercise, rubric, solution);
    }

    @Test
    @Transactional
    public void bypassBackend() throws ApiConnectionException{
        String body = 
            "{\"Text\": \"As grandes bibliotecas – imagino eu as grandes bibliotecas… – atraem-me e apavoram-me, como a montanha magnética dos contos antigos. Convencem-me da minha tremendíssima, acabrunhante e arrasadora ignorância. Eu atrevo-me a murmuraro «Homero» e logo me salta a «questão homérica», os milhares de volumes sobre Homero, em rolos, em códices, alinhados por prateleiras sem fim, vergadas ao peso deles. Eu quero citar Dickens e logo vejo as multidões de comentadores de Dickens, exibindo folhas e folhas de anotações, a rir alvarmente do meu desacerto. Eu tenho umas pobres opiniões sobre a Montanha Mágica, mas hei de calar-me, quando se altaneiram resmas e resmas, alpínicas e ameaçadoras, contendo exegeses sábias sobre Thomas Mann. E o D. Quixote da Mancha? Meu Deus, estou proibido de me pronunciar sobre o D. Quixote. Pode desabar-me em cima toda uma parede de livros, alguns bem grossos e esmagadores, que esmiuçam a obra ao pormenor e não toleram a observação veleira do diletante que diz por dizer, ou por lhe parecer. E Montaigne? Ah, distante Montaigne… E Gogol? Ah, inacessível Gogol…\\nComo é triste e deprimente ser-se tão desconhecedor… Não se trata de retórica, daquele «só sei que nada sei» que foi atirado ao populacho pelo «mais sábio dos homens». Nem tampouco o «eu nem sei se nada sei», triunfal, do nosso Francisco Sanches. Nem sequer dum «eu nem isso sei», aposto num vezo retórico de querer mais, por saber ainda menos. É que eu não sei mesmo absolutamente nada.\\nE, portanto, posso falar sobre o quê? Sobre nada. Que é tarefa muito mais difícil do que falar sobre tudo, porque esta supõe que se sabe tudo sobre tudo e, pelo que se vê em volta, saber tudo sobre tudo é muito mais fácil e generalizado do que nada saber sobre nada.\\nNo Para Sempre de Vergílio Ferreira uma personagem percorre o corredor duma biblioteca. Lá, os autores, desde o cabo da História palram, palram, palram e gargalham. É uma zoada para os ouvidos. A personagem não pode deixar de saber que eles lá estão. Os livros são falantes, discutem, cochicham, incomodam, não dormem, não se calam. De facto, mal eu me chego à Biblioteca Nacional, ou à Torre do Tombo, hei de sentir aquele ruído, aquele zunzum, de gente a querer contar coisas, a querer demonstrar coisas, a exibir, a refutar, a impor-se. E eu sei que não vou conseguir entender-me, ali no meio. Vou ficar confundido. Vou ficar reduzido. Vou-me ver do tamanho daqueles insetos predadores de papel, quase translúcidos, ínfimos e mesquinhos, mas sem possuir sequer as corrosivas mandíbulas que eles têm de defesa.\\nComo é que se pode viver, com esta deficiência, esta inferioridade? É uma boa pergunta, com que me confronto repetidamente. E só posso responder com a confissão da gelada realidade dos factos. A caridade dalguns dos meus concidadãos vale-me e sustenta-me. Graças lhes dou.\",\"Input\": \"Altera-me a opçao errada\"}";

        String endpoint = "/create_mult";

        System.out.println(aiService.bypassBackend(endpoint, body).toString());
    } 
}
