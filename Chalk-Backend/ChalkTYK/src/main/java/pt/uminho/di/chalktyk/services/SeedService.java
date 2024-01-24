package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.ExerciseSolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseStatement;
import pt.uminho.di.chalktyk.models.exercises.chat.ChatExercise;
import pt.uminho.di.chalktyk.models.exercises.chat.ChatExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksData;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksExercise;
import pt.uminho.di.chalktyk.models.exercises.fill_the_blanks.FillTheBlanksRubric;
import pt.uminho.di.chalktyk.models.exercises.items.Item;
import pt.uminho.di.chalktyk.models.exercises.items.StringItem;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.*;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OAStandard;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerExercise;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ConcreteExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.TestExercise;
import pt.uminho.di.chalktyk.models.tests.TestGroup;
import pt.uminho.di.chalktyk.models.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.ServiceException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SeedService implements ISeedService{
    private final IInstitutionsService institutionsService;
    private final IStudentsService studentsService;
    private final ISpecialistsService specialistsService;
    private final ICoursesService coursesService;
    private final ITestsService testsService;
    private final ITagsService tagsService;
    private final IExercisesService exercisesService;

    @Autowired
    public SeedService(IInstitutionsService institutionsService, IStudentsService studentsService, ISpecialistsService specialistsService, ICoursesService coursesService, ITestsService testsService, ITagsService tagsService, IExercisesService exercisesService) {
        this.institutionsService = institutionsService;
        this.studentsService = studentsService;
        this.specialistsService = specialistsService;
        this.coursesService = coursesService;
        this.testsService = testsService;
        this.tagsService = tagsService;
        this.exercisesService = exercisesService;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public boolean seed() throws BadInputException, NotFoundException, InterruptedException {
        // If the tag already exists. Assumes was already done previously.
        if(tagsService.existsTagByNameAndPath("Biologia", "/"))
            return false;

        //List of tags
        Tag tagBiologia = tagsService.createTag("Biologia","/");
        Tag tagPortugues = tagsService.createTag("Português","/");
        Tag tagPoesia = tagsService.createTag("Poesia","/Português/");
        //tags de filosofia
        Tag tagGeologia= tagsService.createTag("Geologia","/");
        Tag tagHistoria = tagsService.createTag("História","/");
        Tag tagHistoriaPortugalReis = tagsService.createTag("Reis de Portugal","/História/");

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
        Specialist ganso = new Specialist(null, "Gonçalo Figueiredo", "https://cdn.pixabay.com/photo/2021/11/05/08/19/goose-6770659_1280.jpg","goncaloff13@gmail.com", "Very important specialist", null);
        Specialist s2 = new Specialist(null, "Professor Ian Duncan", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg", "iduncan@gmail.com", "#2", null);
        Specialist s3 = new Specialist(null, "Professor Whitman", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg","whitman@yahoo.com", "#3", null);
        String gansoId = specialistsService.createSpecialist(ganso);
        String specialist1 = specialistsService.createSpecialist(s1);
        String specialist2 = specialistsService.createSpecialist(s2);
        String specialist3 = specialistsService.createSpecialist(s3);

        // courses
        Course c1 = new Course(null, "Spanish 101", "#1", gansoId, null, null);
        Course c2 = new Course(null, "Anthropology", "#2", gansoId, null, null);
        Course c3 = new Course(null, "Seize the Day", "#3", gansoId, null, null);
        String course1 = coursesService.createCourse(c1);
        String course2 = coursesService.createCourse(c2);
        String course3 = coursesService.createCourse(c3);
        coursesService.addStudentsToCourse(course2, l1);
        coursesService.addStudentsToCourse(course3, l2);

        //Create tests
        createPortugueseExam(ganso,c1, Arrays.asList(tagPortugues,tagPoesia));
        createHistoria(ganso,c1,Arrays.asList(tagHistoria,tagHistoriaPortugalReis));
        //Filosofia

        // test resolutions
        //testsService.startTest(test1, student1);
        //TestResolution tr1 = new TestResolution(null, LocalDateTime.now(), null, 0, null, st1, t1, TestResolutionStatus.ONGOING, List.of());
        //testsService.createTestResolution(test1, tr1);

        return true;
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
        HashMap<String, OpenAnswerRubric> mcRubricMap = new HashMap<>();
        mcRubricMap.put("1",createOARubric());
        mcRubricMap.put("2",createOARubric());
        mcRubricMap.put("3",createOARubric());

        return new MultipleChoiceRubric(0.0F, mcRubricMap);
    }

    private OpenAnswerRubric createOARubric(){
        OAStandard oaStandardMax = new OAStandard("Trató de resolver","Aquí todos pasan la prueba",100.0F);
        OAStandard oaStandardMin = new OAStandard("Ni siquiera intentó resolver","",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandardMin,oaStandardMax);
        return new OpenAnswerRubric(List.of(new OACriterion("Há intentado", 100f, oaStandards)));
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

    // 1_A_1 = Grupo1_ParteA_Questao1
    private Exercise createOAExercise_Portugues_1_A_1(String specialistId, String courseId, List<Tag> tags, String texto) throws BadInputException {
        //Solution
        //OpenAnswerData openAnswerData = new OpenAnswerData("Ali"); //so aqui
        //ExerciseSolution exerciseSolution = new ExerciseSolution(null,openAnswerData);

        //Rubric
        OAStandard oaStandard100 = new OAStandard("5","Compara, com base em dois aspetos distintos, o modo como Marcenda e Baltasar\r\n" + //
                "reagem à impossibilidade de usarem a mão esquerda, abordando, adequadamente, os\r\n" + //
                "dois tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias",100.0F);
        OAStandard oaStandard80 = new OAStandard("4","Compara, com base em dois aspetos distintos, o modo como Marcenda e Baltasar\r\n" + //
                "reagem à impossibilidade de usarem a mão esquerda, abordando, adequadamente, os\r\n" + //
                "dois tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Compara, com base em dois aspetos distintos, o modo como Marcenda e Baltasar reagem\r\n" + //
                "à impossibilidade de usarem a mão esquerda, abordando os dois tópicos de resposta, um\r\n" + //
                "adequadamente e outro com pequenas imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias",80.0F);
        OAStandard oaStandard60 = new OAStandard("3","Compara, com base em dois aspetos distintos, o modo como Marcenda e Baltasar reagem\r\n" + //
                "à impossibilidade de usarem a mão esquerda, abordando os dois tópicos de resposta, um\r\n" + //
                "adequadamente e outro com pequenas imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Compara, com base em dois aspetos distintos, o modo como Marcenda e Baltasar reagem\r\n" + //
                "à impossibilidade de usarem a mão esquerda, abordando os dois tópicos de resposta,\r\n" + //
                "ambos com pequenas imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Compara, com base num aspeto, o modo como Marcenda e Baltasar reagem à\r\n" + //
                "impossibilidade de usarem a mão esquerda, abordando, adequadamente, apenas um\r\n" + //
                "dos tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias",60.0F);
        OAStandard oaStandard40 = new OAStandard("2","Compara, com base em dois aspetos distintos, o modo como Marcenda e Baltasar reagem\r\n" + //
                "à impossibilidade de usarem a mão esquerda, abordando os dois tópicos de resposta,\r\n" + //
                "ambos com pequenas imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Compara, com base num aspeto, o modo como Marcenda e Baltasar reagem à\r\n" + //
                "impossibilidade de usarem a mão esquerda, abordando, adequadamente, apenas um\r\n" + //
                "dos tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.",40.0F);
        OAStandard oaStandard20 = new OAStandard("1","Compara, com base num aspeto, o modo como Marcenda e Baltasar reagem à\r\n" + //
                "impossibilidade de usarem a mão esquerda, abordando, com pequenas imprecisões e/ou\r\n" + //
                "omissões, apenas um dos tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual com eventual ocorrência de falhas que podem\r\n" + //
                "comprometer a progressão e o encadeamento das ideias.",20.0F);
        OAStandard oaStandard0 = new OAStandard("0","Se nenhum critério se aplicar",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandard0,oaStandard20,oaStandard40,oaStandard60,oaStandard80,oaStandard100);
        ExerciseRubric exerciseRubric = new OpenAnswerRubric(List.of(new OACriterion("Nivel de desempenho", 100f, oaStandards)));

        //Exercise
        OpenAnswerExercise exercise = new OpenAnswerExercise();
        exercise.setStatement(new ExerciseStatement(texto+"\n"+"Compare, com base em dois aspetos distintos, o modo como Marcenda e Baltasar reagem à impossibilidade de usarem a mão esquerda.","",""));
        exercise.setTitle("Pergunta Pt 1.1");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,null,exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        OpenAnswerExercise exercise2 = new OpenAnswerExercise();
        exercise2.setStatement(new ExerciseStatement("Compare, com base em dois aspetos distintos, o modo como Marcenda e Baltasar reagem à impossibilidade de usarem a mão esquerda.","",""));
        exercise2.setTitle("Pergunta Pt 1.1");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(null);
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }

    private Exercise createOAExercise_Portugues_1_A_2(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        //OpenAnswerData openAnswerData = new OpenAnswerData("Ali"); //so aqui
        //ExerciseSolution exerciseSolution = new ExerciseSolution(null,openAnswerData);

        //Rubric
        OAStandard oaStandard100 = new OAStandard("5","Apresenta duas evidências que comprovam que pai e filha são clientes habituais do hotel,\r\n" + //
                "abordando, adequadamente, os dois tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias.",100.0F);
        OAStandard oaStandard80 = new OAStandard("4","Apresenta duas evidências que comprovam que pai e filha são clientes habituais do hotel,\r\n" + //
                "abordando, adequadamente, os dois tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Apresenta duas evidências que comprovam que pai e filha são clientes habituais do\r\n" + //
                "hotel, abordando os dois tópicos de resposta, um adequadamente e outro com pequenas\r\n" + //
                "imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias",80.0F);
        OAStandard oaStandard60 = new OAStandard("3","Apresenta duas evidências que comprovam que pai e filha são clientes habituais do\r\n" + //
                "hotel, abordando os dois tópicos de resposta, um adequadamente e outro com pequenas\r\n" + //
                "imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Apresenta duas evidências que comprovam que pai e filha são clientes habituais do hotel,\r\n" + //
                "abordando os dois tópicos de resposta, ambos com pequenas imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Apresenta uma evidência que comprova que pai e filha são clientes habituais do hotel,\r\n" + //
                "abordando, adequadamente, apenas um dos tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias.",60.0F);
        OAStandard oaStandard40 = new OAStandard("2","Apresenta duas evidências que comprovam que pai e filha são clientes habituais do hotel,\r\n" + //
                "abordando os dois tópicos de resposta, ambos com pequenas imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Apresenta uma evidência que comprova que pai e filha são clientes habituais do hotel,\r\n" + //
                "abordando, adequadamente, apenas um dos tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias",40.0F);
        OAStandard oaStandard20 = new OAStandard("1","Apresenta uma evidência que comprova que pai e filha são clientes habituais do hotel,\r\n" + //
                "abordando, com pequenas imprecisões e/ou omissões, apenas um dos tópicos de\r\n" + //
                "resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual com eventual ocorrência de falhas que podem\r\n" + //
                "comprometer a progressão e o encadeamento das ideias.",20.0F);
        OAStandard oaStandard0 = new OAStandard("0","Se nenhum critério se aplicar",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandard0,oaStandard20,oaStandard40,oaStandard60,oaStandard80,oaStandard100);
        ExerciseRubric exerciseRubric = new OpenAnswerRubric(List.of(new OACriterion("Nivel de desempenho", 100f, oaStandards)));

        //Exercise
        OpenAnswerExercise exercise = new OpenAnswerExercise();
        exercise.setStatement(new ExerciseStatement(texto+"\nA forma como pai e filha são tratados no hotel permite concluir que são clientes habituais.\r\n" + //
                "Apresente duas evidências que comprovem esta afirmação.\r","",""));
        exercise.setTitle("Pergunta Pt 1.2");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);
        exercisesService.createExercise(exercise,null,exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        OpenAnswerExercise exercise2 = new OpenAnswerExercise();
        exercise2.setStatement(new ExerciseStatement("A forma como pai e filha são tratados no hotel permite concluir que são clientes habituais.\r\n" + //
                "Apresente duas evidências que comprovem esta afirmação.\r","",""));
        exercise2.setTitle("Pergunta Pt 1.2");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(null);
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }

    private Exercise createOAExercise_Portugues_1_A_3(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        //OpenAnswerData openAnswerData = new OpenAnswerData("Ali"); //so aqui
        //ExerciseSolution exerciseSolution = new ExerciseSolution(null,openAnswerData);

        //Rubric
        OAStandard oaStandard100 = new OAStandard("5","Explica em que medida as expressões transcritas evidenciam a relação que se estabelece\r\n" + //
                "entre Baltasar e Blimunda, abordando, adequadamente, os dois tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias.",100.0F);
        OAStandard oaStandard80 = new OAStandard("4","Explica em que medida as expressões transcritas evidenciam a relação que se estabelece\r\n" + //
                "entre Baltasar e Blimunda, abordando, adequadamente, os dois tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Explica em que medida as expressões transcritas evidenciam a relação que se estabelece\r\n" + //
                "entre Baltasar e Blimunda, abordando os dois tópicos de resposta, um adequadamente e\r\n" + //
                "outro com pequenas imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias",80.0F);
        OAStandard oaStandard60 = new OAStandard("3","Explica em que medida as expressões transcritas evidenciam a relação que se estabelece\r\n" + //
                "entre Baltasar e Blimunda, abordando os dois tópicos de resposta, um adequadamente e\r\n" + //
                "outro com pequenas imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Explica em que medida as expressões transcritas evidenciam a relação que se estabelece\r\n" + //
                "entre Baltasar e Blimunda, abordando os dois tópicos de resposta, ambos com pequenas\r\n" + //
                "imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Explica em que medida as expressões transcritas evidenciam a relação que se estabelece\r\n" + //
                "entre Baltasar e Blimunda, abordando, adequadamente, apenas um dos tópicos de\r\n" + //
                "resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias.",60.0F);
        OAStandard oaStandard40 = new OAStandard("2","Explica em que medida as expressões transcritas evidenciam a relação que se estabelece\r\n" + //
                "entre Baltasar e Blimunda, abordando os dois tópicos de resposta, ambos com pequenas\r\n" + //
                "imprecisões e/ou omissões.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Explica em que medida as expressões transcritas evidenciam a relação que se estabelece\r\n" + //
                "entre Baltasar e Blimunda, abordando, adequadamente, apenas um dos tópicos de\r\n" + //
                "resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.",40.0F);
        OAStandard oaStandard20 = new OAStandard("1","Explica em que medida as expressões transcritas evidenciam a relação que se estabelece\r\n" + //
                "entre Baltasar e Blimunda, abordando, com pequenas imprecisões e/ou omissões, apenas\r\n" + //
                "um dos tópicos de resposta.\r\n" + //
                "Utiliza mecanismos de coesão textual com eventual ocorrência de falhas que podem\r\n" + //
                "comprometer a progressão e o encadeamento das ideias.",20.0F);
        OAStandard oaStandard0 = new OAStandard("0","Se nenhum critério se aplicar",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandard0,oaStandard20,oaStandard40,oaStandard60,oaStandard80,oaStandard100);
        ExerciseRubric exerciseRubric = new OpenAnswerRubric(List.of(new OACriterion("Nivel de desempenho", 100f, oaStandards)));

        //Exercise
        OpenAnswerExercise exercise = new OpenAnswerExercise();
        exercise.setStatement(new ExerciseStatement(texto+"\nExplique em que medida se pode afirmar que as expressões «Tu és Sete-Sóis porque vês às claras, tu\r\n" + //
                "serás Sete-Luas porque vês às escuras» (linhas 40 e 41) e «Lua onde estás, Sol aonde vais» (linha 44)\r\n" + //
                "evidenciam a relação que, no excerto, se estabelece entre Baltasar e Blimunda","",""));
        exercise.setTitle("Pergunta Pt 1.3");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);
        exercisesService.createExercise(exercise,null,exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        OpenAnswerExercise exercise2 = new OpenAnswerExercise();
        exercise2.setStatement(new ExerciseStatement("Explique em que medida se pode afirmar que as expressões «Tu és Sete-Sóis porque vês às claras, tu\r\n" + //
                "serás Sete-Luas porque vês às escuras» (linhas 40 e 41) e «Lua onde estás, Sol aonde vais» (linha 44)\r\n" + //
                "evidenciam a relação que, no excerto, se estabelece entre Baltasar e Blimunda","",""));
        exercise2.setTitle("Pergunta Pt 1.3");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(null);
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }

    private Exercise createOAExercise_Portugues_1_B_4(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        //OpenAnswerData openAnswerData = new OpenAnswerData("Ali"); //so aqui
        //ExerciseSolution exerciseSolution = new ExerciseSolution(null,openAnswerData);

        //Rubric
        OAStandard oaStandard100 = new OAStandard("5","Explicita, adequadamente, com base em dois aspetos significativos, o modo como o\r\n" + //
                "sujeito poético reage à figura feminina, fundamentando a resposta com transcrições\r\n" + //
                "pertinentes em ambos os casos.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias.",100.0F);
        OAStandard oaStandard80 = new OAStandard("4","Explicita, adequadamente, com base em dois aspetos significativos, o modo como o\r\n" + //
                "sujeito poético reage à figura feminina, fundamentando a resposta com transcrições\r\n" + //
                "pertinentes em ambos os casos.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Explicita, com base em dois aspetos significativos, o modo como o sujeito poético reage\r\n" + //
                "à figura feminina, um adequadamente e outro com pequenas imprecisões e/ou omissões,\r\n" + //
                "fundamentando a resposta com transcrições pertinentes em ambos os casos.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias.",80.0F);
        OAStandard oaStandard60 = new OAStandard("3","Explicita, com base em dois aspetos significativos, o modo como o sujeito poético reage\r\n" + //
                "à figura feminina, um adequadamente e outro com pequenas imprecisões e/ou omissões,\r\n" + //
                "fundamentando a resposta com transcrições pertinentes em ambos os casos.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Explicita, com base em dois aspetos significativos, o modo como o sujeito poético reage\r\n" + //
                "à figura feminina, ambos com pequenas imprecisões e/ou omissões, fundamentando a\r\n" + //
                "resposta com transcrições pertinentes em ambos os casos.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Explicita, adequadamente, com base num aspeto significativo, o modo como o sujeito\r\n" + //
                "poético reage à figura feminina, fundamentando a resposta com uma transcrição\r\n" + //
                "pertinente.\r\n" + //
                "Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas,\r\n" + //
                "asseguram a progressão e o encadeamento das ideias",60.0F);
        OAStandard oaStandard40 = new OAStandard("2","Explicita, com base em dois aspetos significativos, o modo como o sujeito poético reage\r\n" + //
                "à figura feminina, ambos com pequenas imprecisões e/ou omissões, fundamentando a\r\n" + //
                "resposta com transcrições pertinentes em ambos os casos.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.\r\n" + //
                "OU\r\n" + //
                "Explicita, adequadamente, com base num aspeto significativo, o modo como o sujeito\r\n" + //
                "poético reage à figura feminina, fundamentando a resposta com uma transcrição\r\n" + //
                "pertinente.\r\n" + //
                "Utiliza mecanismos de coesão textual com falhas que comprometem a progressão e o\r\n" + //
                "encadeamento das ideias.",40.0F);
        OAStandard oaStandard20 = new OAStandard("1","Explicita, com pequenas imprecisões e/ou omissões, com base num aspeto significativo,\r\n" + //
                "o modo como o sujeito poético reage à figura feminina, fundamentando a resposta com\r\n" + //
                "uma transcrição pertinente.\r\n" + //
                "Utiliza mecanismos de coesão textual com eventual ocorrência de falhas que podem\r\n" + //
                "comprometer a progressão e o encadeamento das ideias.",20.0F);
        OAStandard oaStandard0 = new OAStandard("0","Se nenhum critério se aplicar",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandard0,oaStandard20,oaStandard40,oaStandard60,oaStandard80,oaStandard100);
        ExerciseRubric exerciseRubric = new OpenAnswerRubric(List.of(new OACriterion("Nivel de desempenho", 100f, oaStandards)));

        //Exercise
        OpenAnswerExercise exercise = new OpenAnswerExercise();
        exercise.setStatement(new ExerciseStatement(texto+"\nExplicite, com base em dois aspetos significativos, o modo como o sujeito poético reage à figura feminina\r\n" + //
                "evocada no poema. Fundamente a sua resposta com transcrições pertinentes.","",""));
        exercise.setTitle("Pergunta Pt 2.1");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);
        exercisesService.createExercise(exercise,null,exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        OpenAnswerExercise exercise2 = new OpenAnswerExercise();
        exercise2.setStatement(new ExerciseStatement("Explicite, com base em dois aspetos significativos, o modo como o sujeito poético reage à figura feminina\r\n" + //
                "evocada no poema. Fundamente a sua resposta com transcrições pertinentes.","",""));
        exercise2.setTitle("Pergunta Pt 2.1");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);


        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(null);
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }



    private Exercise createMCExercise_Portugues_1_B_5(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option4 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option5 = new MultipleChoiceResolutionItem(0.0F,null,true);
        HashMap<String,MultipleChoiceResolutionItem> itemResolutionsSol = new HashMap<>();
        itemResolutionsSol.put("A",option1);
        itemResolutionsSol.put("B",option2);
        itemResolutionsSol.put("C",option3);
        itemResolutionsSol.put("D",option4);
        itemResolutionsSol.put("E",option5);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutionsSol);
        //Rubric
        //HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        //mcRubricMap.put("1",createOARubric());
        //mcRubricMap.put("2",createOARubric());
        //mcRubricMap.put("3",createOARubric());
        ExerciseRubric exerciseRubric = new MultipleChoiceRubric(0.0F,null);

        //Exercise
        HashMap<String, Item> itemResolutions = new HashMap<>();
        itemResolutions.put("A",new StringItem("O sujeito poético dirige-se à Senhora através de uma apóstrofe."));
        itemResolutions.put("B",new StringItem("A expressão «perder a vista» (verso 3) é usada com sentido metafórico."));
        itemResolutions.put("C",new StringItem("O sujeito poético arrepende-se de desejar algo cujo preço elevado o impede de saldar a dívida."));
        itemResolutions.put("D",new StringItem("O poema ilustra o estilo engenhoso do poeta, nomeadamente no último terceto, quando recorre à\r\n" + //
                "antítese e ao paralelismo alcançado através do jogo de palavras."));
        itemResolutions.put("E",new StringItem("Entre a Senhora e o sujeito poético existe uma relação de igualdade"));

        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise.setStatement(new ExerciseStatement(texto+"\nConsidere as afirmações seguintes sobre o soneto. \r\nIdentifique as duas afirmações falsas","",""));
        exercise.setTitle("Pergunta Pt 2.2");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,new ExerciseSolution(null,multipleChoiceData.clone()),exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        MultipleChoiceExercise exercise2 = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise2.setStatement(new ExerciseStatement("Considere as afirmações seguintes sobre o soneto. \r\nIdentifique as duas afirmações falsas","",""));
        exercise2.setTitle("Pergunta Pt 2.2");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);
        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(new ExerciseSolution(null,multipleChoiceData.clone()));
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }

    private Exercise createMCExercise_Portugues_1_B_6(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option4 = new MultipleChoiceResolutionItem(0.0F,null,false);

        HashMap<String,MultipleChoiceResolutionItem> itemResolutionsSol = new HashMap<>();
        itemResolutionsSol.put("A",option1);
        itemResolutionsSol.put("B",option2);
        itemResolutionsSol.put("C",option3);
        itemResolutionsSol.put("D",option4);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutionsSol);
        //Rubric
        //HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        //mcRubricMap.put("1",createOARubric());
        //mcRubricMap.put("2",createOARubric());
        //mcRubricMap.put("3",createOARubric());
        ExerciseRubric exerciseRubric = new MultipleChoiceRubric(0.0F,null);

        //Exercise
        HashMap<String, Item> itemResolutions = new HashMap<>();
        itemResolutions.put("A",new StringItem("a sua entrega incondicional, a fim de ser merecedor de admirar a beleza singular dos olhos da Senhora."));
        itemResolutions.put("B",new StringItem("o seu descontentamento por ter de pagar o «preço honesto» exigido a quem contempla a Senhora."));
        itemResolutions.put("C",new StringItem("o contraste entre o preço a pagar para contemplar a Senhora e a bem-aventurança que alcança."));
        itemResolutions.put("D",new StringItem("a ideia de que, ao dar a vida e a alma para ser merecedor da beleza da Senhora, se iguala aos outros."));


        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise.setStatement(new ExerciseStatement(texto+"\nSelecione a opção que completa corretamente a frase seguinte.\r\n" + //
                "Na segunda quadra, o sujeito poético pretende enfatizar","",""));
        exercise.setTitle("Pergunta Pt 2.3");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,new ExerciseSolution(null, multipleChoiceData.clone()),exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        MultipleChoiceExercise exercise2 = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise2.setStatement(new ExerciseStatement("Selecione a opção que completa corretamente a frase seguinte.\r\n" + //
                "Na segunda quadra, o sujeito poético pretende enfatizar","",""));
        exercise2.setTitle("Pergunta Pt 2.3");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(new ExerciseSolution(null, multipleChoiceData.clone()));
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }

    private Exercise createOAExercise_Portugues_1_C_7(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        //OpenAnswerData openAnswerData = new OpenAnswerData("Ali"); //so aqui
        //ExerciseSolution exerciseSolution = new ExerciseSolution(null,openAnswerData);

        //Rubric
        OAStandard oaStandard80 = new OAStandard("4","Explicita, adequadamente, um aspeto em que os poemas se aproximam e um aspeto em\r\n" + //
                "que os poemas se distinguem quanto às ideias expressas.",100.0F);
        OAStandard oaStandard60 = new OAStandard("3","Explicita um aspeto em que os poemas se aproximam e um aspeto em que os poemas se\r\n" + //
                "distinguem quanto às ideias expressas, adequadamente num dos casos e com pequenas\r\n" + //
                "imprecisões e/ou omissões no outro caso.",75.0F);
        OAStandard oaStandard40 = new OAStandard("2","Explicita um aspeto em que os poemas se aproximam e um aspeto em que os poemas\r\n" + //
                "se distinguem quanto às ideias expressas, com pequenas imprecisões e/ou omissões em\r\n" + //
                "ambos os casos.\r\n" + //
                "OU\r\n" + //
                "Explicita, adequadamente, apenas um aspeto em que os poemas se aproximam ou\r\n" + //
                "apenas um aspeto em que os poemas se distinguem quanto às ideias expressas.",50.0F);
        OAStandard oaStandard20 = new OAStandard("1","Explicita, com pequenas imprecisões e/ou omissões, apenas um aspeto em que os\r\n" + //
                "poemas se aproximam ou apenas um aspeto em que os poemas se distinguem quanto\r\n" + //
                "às ideias expressas.",25.0F);
        OAStandard oaStandard0 = new OAStandard("0","Se nenhum critério se aplicar",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandard0,oaStandard20,oaStandard40,oaStandard60,oaStandard80);



        OAStandard oaStandardDiscurso_3 = new OAStandard("3","Escreve um texto bem estruturado constituído por três partes (introdução, desenvolvimento\r\n" + //
                "e conclusão) devidamente proporcionadas e utiliza mecanismos de coesão textual que\r\n" + //
                "asseguram a progressão e o encadeamento das ideias",100.0F);
        OAStandard oaStandardDiscurso_2 = new OAStandard("2","Escreve um texto globalmente bem estruturado constituído por três partes (introdução,\r\n" + //
                "desenvolvimento e conclusão) com desequilíbrios de proporção e/ou utiliza mecanismos\r\n" + //
                "de coesão textual com a eventual ocorrência de falhas que não comprometem a\r\n" + //
                "progressão e o encadeamento das ideias.",66.6F);
        OAStandard oaStandardDiscurso_1 = new OAStandard("1","Escreve um texto insuficientemente estruturado e/ou utiliza mecanismos de coesão textual\r\n" + //
                "com falhas que comprometem a progressão e o encadeamento das ideias.",33.3F);
        List<OAStandard> oaStandards2 = Arrays.asList(oaStandardDiscurso_1,oaStandardDiscurso_2,oaStandardDiscurso_3);

        ExerciseRubric exerciseRubric = new OpenAnswerRubric(List.of(new OACriterion("Aspetos de conteúdo", 72.27f, oaStandards),new OACriterion("Descritores de desempenho",27.73f,oaStandards2)));

        //Exercise
        OpenAnswerExercise exercise = new OpenAnswerExercise();
        exercise.setStatement(new ExerciseStatement(texto+"\nEscreva uma breve exposição na qual compare os poemas das partes B e C quanto às ideias expressas.\r\n" + //
                "A sua exposição deve incluir:\r\n" + //
                "•  uma introdução;\r\n" + //
                "•  um desenvolvimento no qual explicite um aspeto em que os poemas se aproximam e um aspeto em que\r\n" + //
                "os poemas se distinguem;\r\n" + //
                "•  uma conclusão adequada ao desenvolvimento do texto.","",""));
        exercise.setTitle("Pergunta Pt 3.1");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);
        exercisesService.createExercise(exercise,null,exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        OpenAnswerExercise exercise2 = new OpenAnswerExercise();
        exercise2.setStatement(new ExerciseStatement("Escreva uma breve exposição na qual compare os poemas das partes B e C quanto às ideias expressas.\r\n" + //
                "A sua exposição deve incluir:\r\n" + //
                "•  uma introdução;\r\n" + //
                "•  um desenvolvimento no qual explicite um aspeto em que os poemas se aproximam e um aspeto em que\r\n" + //
                "os poemas se distinguem;\r\n" + //
                "•  uma conclusão adequada ao desenvolvimento do texto.","",""));
        exercise2.setTitle("Pergunta Pt 3.1");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(null);
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }


    private Exercise createMCExercise_Portugues_2_1(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option4 = new MultipleChoiceResolutionItem(0.0F,null,false);

        HashMap<String,MultipleChoiceResolutionItem> itemResolutionsSol = new HashMap<>();
        itemResolutionsSol.put("A",option1);
        itemResolutionsSol.put("B",option2);
        itemResolutionsSol.put("C",option3);
        itemResolutionsSol.put("D",option4);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutionsSol);

        //Rubric
        //HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        //mcRubricMap.put("1",createOARubric());
        //mcRubricMap.put("2",createOARubric());
        //mcRubricMap.put("3",createOARubric());
        ExerciseRubric exerciseRubric = new MultipleChoiceRubric(0.0F,null);

        //Exercise
        HashMap<String, Item> itemResolutions = new HashMap<>();
        itemResolutions.put("A",new StringItem("que capta uma beleza imensa que, por definição, nem a luz artificial consegue impedir.\r"));
        itemResolutions.put("B",new StringItem("que está dependente do contexto em que se encontra aquele que observa o céu."));
        itemResolutions.put("C",new StringItem("cuja plena concretização se torna impossível, qualquer que seja o local de observação."));
        itemResolutions.put("D",new StringItem("acessível a todos aqueles que se dispõem a olhar o céu das cidades numa noite escura."));


        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise.setStatement(new ExerciseStatement("Segundo o autor do texto, olhar o céu estrelado constitui uma experiência","",""));
        exercise.setTitle("Pergunta Pt 4.1");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,new ExerciseSolution(null, multipleChoiceData.clone()),exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        MultipleChoiceExercise exercise2 = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise2.setStatement(new ExerciseStatement(texto+"\nSegundo o autor do texto, olhar o céu estrelado constitui uma experiência","",""));
        exercise2.setTitle("Pergunta Pt 4.1");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(new ExerciseSolution(null, multipleChoiceData.clone()));
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }

    private Exercise createMCExercise_Portugues_2_2(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option4 = new MultipleChoiceResolutionItem(0.0F,null,false);

        HashMap<String,MultipleChoiceResolutionItem> itemResolutionsSol = new HashMap<>();
        itemResolutionsSol.put("A",option1);
        itemResolutionsSol.put("B",option2);
        itemResolutionsSol.put("C",option3);
        itemResolutionsSol.put("D",option4);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutionsSol);

        //Rubric
        //HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        //mcRubricMap.put("1",createOARubric());
        //mcRubricMap.put("2",createOARubric());
        //mcRubricMap.put("3",createOARubric());
        ExerciseRubric exerciseRubric = new MultipleChoiceRubric(0.0F,null);

        //Exercise
        HashMap<String, Item> itemResolutions = new HashMap<>();
        itemResolutions.put("A",new StringItem("o ser humano detém um conhecimento cada vez mais aprofundado, todavia não olha o céu, no qual\\r\\n" + //
                "\" + //\r\n" + //
                "                    \"se encontram respostas para a sua existência."));
        itemResolutions.put("B",new StringItem("a luz produzida artificialmente é compatível com a observação da Via Láctea, galáxia a que o planeta\r\n" + //
                "Terra pertence, se o ser humano a tal se dispuser. "));
        itemResolutions.put("C",new StringItem("o ser humano constrói um conhecimento cada vez mais amplo sobre as suas origens, o que torna\r\n" + //
                "irrelevante a observação do céu em busca de respostas. \r"));
        itemResolutions.put("D",new StringItem("o uso de aparelhos tecnológicos permite aceder a um vasto conhecimento sobre o Universo, sem\r\n" + //
                "necessidade de erguer os olhos para ver as estrelas."));


        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise.setStatement(new ExerciseStatement("Através da expressão «estranho e paradoxal» (linha 7), depreende-se que","",""));
        exercise.setTitle("Pergunta Pt 4.2");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,new ExerciseSolution(null, multipleChoiceData.clone()),exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        MultipleChoiceExercise exercise2 = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise2.setStatement(new ExerciseStatement("Através da expressão «estranho e paradoxal» (linha 7), depreende-se que","",""));
        exercise2.setTitle("Pergunta Pt 4.2");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(new ExerciseSolution(null, multipleChoiceData.clone()));
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }


    private Exercise createMCExercise_Portugues_2_3(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option4 = new MultipleChoiceResolutionItem(0.0F,null,false);

        HashMap<String,MultipleChoiceResolutionItem> itemResolutionsSol = new HashMap<>();
        itemResolutionsSol.put("A",option1);
        itemResolutionsSol.put("B",option2);
        itemResolutionsSol.put("C",option3);
        itemResolutionsSol.put("D",option4);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutionsSol);

        //Rubric
        //HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        //mcRubricMap.put("1",createOARubric());
        //mcRubricMap.put("2",createOARubric());
        //mcRubricMap.put("3",createOARubric());
        ExerciseRubric exerciseRubric = new MultipleChoiceRubric(0.0F,null);

        //Exercise
        HashMap<String, Item> itemResolutions = new HashMap<>();
        itemResolutions.put("A",new StringItem("trazer a esperança de que os seres humanos possam vir a tornar-se eternos."));
        itemResolutions.put("B",new StringItem("evidenciar um elevado número de dúvidas sobre a importância da nossa origem. "));
        itemResolutions.put("C",new StringItem("mostrar a desproporção entre a imensidão do Universo e a pequenez do ser humano."));
        itemResolutions.put("D",new StringItem("constatar a importância do ser humano no Universo e levar à exacerbação de egos."));


        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise.setStatement(new ExerciseStatement(texto+"\nDe acordo com o autor do texto, os estudos levados a cabo sobre o Universo permitiram","",""));
        exercise.setTitle("Pergunta Pt 4.3");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,new ExerciseSolution(null, multipleChoiceData.clone()),exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());


        MultipleChoiceExercise exercise2 = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise2.setStatement(new ExerciseStatement("De acordo com o autor do texto, os estudos levados a cabo sobre o Universo permitiram","",""));
        exercise2.setTitle("Pergunta Pt 4.3");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(new ExerciseSolution(null, multipleChoiceData.clone()));
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }


    private Exercise createMCExercise_Portugues_2_4(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option4 = new MultipleChoiceResolutionItem(0.0F,null,false);

        HashMap<String,MultipleChoiceResolutionItem> itemResolutionsSol = new HashMap<>();
        itemResolutionsSol.put("A",option1);
        itemResolutionsSol.put("B",option2);
        itemResolutionsSol.put("C",option3);
        itemResolutionsSol.put("D",option4);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutionsSol);

        //Rubric
        //HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        //mcRubricMap.put("1",createOARubric());
        //mcRubricMap.put("2",createOARubric());
        //mcRubricMap.put("3",createOARubric());
        ExerciseRubric exerciseRubric = new MultipleChoiceRubric(0.0F,null);

        //Exercise
        HashMap<String, Item> itemResolutions = new HashMap<>();
        itemResolutions.put("A",new StringItem("uma metáfora, presente em «manto escuro» (linha 2)."));
        itemResolutions.put("B",new StringItem("uma metáfora, presente em «nossa própria casa celeste» (linhas 6 e 7)."));
        itemResolutions.put("C",new StringItem("uma hipérbole, presente em «manto escuro» (linha 2)."));
        itemResolutions.put("D",new StringItem("uma hipérbole, presente em «nossa própria casa celeste» (linhas 6 e 7)."));


        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise.setStatement(new ExerciseStatement(texto+"\nA fim de pôr em destaque a intrínseca e inquebrável relação do homem com o Universo, o autor recorre a\r","",""));
        exercise.setTitle("Pergunta Pt 4.4");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,new ExerciseSolution(null, multipleChoiceData.clone()),exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        MultipleChoiceExercise exercise2 = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise2.setStatement(new ExerciseStatement("A fim de pôr em destaque a intrínseca e inquebrável relação do homem com o Universo, o autor recorre a\r","",""));
        exercise2.setTitle("Pergunta Pt 4.4");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(new ExerciseSolution(null, multipleChoiceData.clone()));
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }

    private Exercise createMCExercise_Portugues_2_5(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option4 = new MultipleChoiceResolutionItem(0.0F,null,true);

        HashMap<String,MultipleChoiceResolutionItem> itemResolutionsSol = new HashMap<>();
        itemResolutionsSol.put("A",option1);
        itemResolutionsSol.put("B",option2);
        itemResolutionsSol.put("C",option3);
        itemResolutionsSol.put("D",option4);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutionsSol);

        //Rubric
        //HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        //mcRubricMap.put("1",createOARubric());
        //mcRubricMap.put("2",createOARubric());
        //mcRubricMap.put("3",createOARubric());
        ExerciseRubric exerciseRubric = new MultipleChoiceRubric(0.0F,null);

        //Exercise
        HashMap<String, Item> itemResolutions = new HashMap<>();
        itemResolutions.put("A",new StringItem("«nos compõe» (linha 11)."));
        itemResolutions.put("B",new StringItem("«nos tornam» (linha 16)."));
        itemResolutions.put("C",new StringItem("«nos manter» (linha 26)."));
        itemResolutions.put("D",new StringItem("«nos dizem» (linha 24)."));


        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise.setStatement(new ExerciseStatement(texto+"\nO pronome pessoal «nos» desempenha a função sintática de complemento direto em todas as expressões\r\n" + //
                "abaixo apresentadas, exceto em","",""));
        exercise.setTitle("Pergunta Pt 4.5");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,new ExerciseSolution(null, multipleChoiceData.clone()),exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        MultipleChoiceExercise exercise2 = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise2.setStatement(new ExerciseStatement("O pronome pessoal «nos» desempenha a função sintática de complemento direto em todas as expressões\r\n" + //
                "abaixo apresentadas, exceto em","",""));
        exercise2.setTitle("Pergunta Pt 4.5");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(new ExerciseSolution(null, multipleChoiceData.clone()));
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }

    private Exercise createMCExercise_Portugues_2_6(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option4 = new MultipleChoiceResolutionItem(0.0F,null,true);

        HashMap<String,MultipleChoiceResolutionItem> itemResolutionsSol = new HashMap<>();
        itemResolutionsSol.put("A",option1);
        itemResolutionsSol.put("B",option2);
        itemResolutionsSol.put("C",option3);
        itemResolutionsSol.put("D",option4);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutionsSol);

        //Rubric
        //HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        //mcRubricMap.put("1",createOARubric());
        //mcRubricMap.put("2",createOARubric());
        //mcRubricMap.put("3",createOARubric());
        ExerciseRubric exerciseRubric = new MultipleChoiceRubric(0.0F,null);

        //Exercise
        HashMap<String, Item> itemResolutions = new HashMap<>();
        itemResolutions.put("A",new StringItem("«de onde vimos» (linha 9)"));
        itemResolutions.put("B",new StringItem("«ainda que faltem milhares de milhões de anos» (linhas 11 e 12)."));
        itemResolutions.put("C",new StringItem("«que operamos com as nossas mãos» (linha 15)."));
        itemResolutions.put("D",new StringItem("«que, afinal, somos mesmo muito importantes» (linha 24)."));


        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise.setStatement(new ExerciseStatement(texto+"\nTal como em «que não somos nem estamos de todo no centro do mundo, do Universo» (linhas 19 e 20), está\r\n" + //
                "presente uma oração subordinada substantiva completiva em","",""));
        exercise.setTitle("Pergunta Pt 4.6");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,new ExerciseSolution(null, multipleChoiceData.clone()),exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        MultipleChoiceExercise exercise2 = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise2.setStatement(new ExerciseStatement("Tal como em «que não somos nem estamos de todo no centro do mundo, do Universo» (linhas 19 e 20), está\r\n" + //
                "presente uma oração subordinada substantiva completiva em","",""));
        exercise2.setTitle("Pergunta Pt 4.6");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(new ExerciseSolution(null, multipleChoiceData.clone()));
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }


    private Exercise createMCExercise_Portugues_2_7(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,null,false);
        MultipleChoiceResolutionItem option4 = new MultipleChoiceResolutionItem(0.0F,null,false);

        HashMap<String,MultipleChoiceResolutionItem> itemResolutionsSol = new HashMap<>();
        itemResolutionsSol.put("A",option1);
        itemResolutionsSol.put("B",option2);
        itemResolutionsSol.put("C",option3);
        itemResolutionsSol.put("D",option4);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutionsSol);

        //Rubric
        //HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        //mcRubricMap.put("1",createOARubric());
        //mcRubricMap.put("2",createOARubric());
        //mcRubricMap.put("3",createOARubric());
        ExerciseRubric exerciseRubric = new MultipleChoiceRubric(0.0F,null);

        //Exercise
        HashMap<String, Item> itemResolutions = new HashMap<>();
        itemResolutions.put("A",new StringItem("«leva-nos hoje à ideia mais nítida de sempre sobre de onde vimos, como chegámos aqui» (linhas 8 e 9)"));
        itemResolutions.put("B",new StringItem("«Olhar o céu é apontar em direção às nossas origens cósmicas» (linhas 12 e 13)."));
        itemResolutions.put("C",new StringItem("«A nossa viagem em busca das nossas origens, olhando ou não o céu, parece ter começado muito,\r\n" + //
                "muito cedo» (linhas 18 e 19)."));
        itemResolutions.put("D",new StringItem("«somos total e completamente insignificantes, espacial e temporalmente» (linhas 20 e 21)."));


        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise.setStatement(new ExerciseStatement(texto+"\nA única expressão em que estão presentes exemplos dos três tipos de dêixis (temporal, espacial e pessoal) é","",""));
        exercise.setTitle("Pergunta Pt 4.7");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,new ExerciseSolution(null, multipleChoiceData.clone()),exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        MultipleChoiceExercise exercise2 = new MultipleChoiceExercise(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION,itemResolutions);
        exercise2.setStatement(new ExerciseStatement("A única expressão em que estão presentes exemplos dos três tipos de dêixis (temporal, espacial e pessoal) é","",""));
        exercise2.setTitle("Pergunta Pt 4.7");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(new ExerciseSolution(null, multipleChoiceData.clone()));
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }

    //private String createOAExercise_Portugues_1_B_4(String specialistId, String courseId, List<Tag> tags) throws BadInputException {
    //    //Solution
    //    //OpenAnswerData openAnswerData = new OpenAnswerData("Ali"); //so aqui
    //    //ExerciseSolution exerciseSolution = new ExerciseSolution(null,openAnswerData);
//
    //    //Rubric
    //    OAStandard oaStandard100 = new OAStandard("5","",100.0F);
    //    OAStandard oaStandard80 = new OAStandard("4","",80.0F);
    //    OAStandard oaStandard60 = new OAStandard("3","",60.0F);
    //    OAStandard oaStandard40 = new OAStandard("2","",40.0F);
    //    OAStandard oaStandard20 = new OAStandard("1","",20.0F);
    //    OAStandard oaStandard0 = new OAStandard("0","Se nenhum critério se aplicar",0.0F);
    //    List<OAStandard> oaStandards = Arrays.asList(oaStandard0,oaStandard20,oaStandard40,oaStandard60,oaStandard80,oaStandard100);
    //    ExerciseRubric exerciseRubric = new OpenAnswerRubric(List.of(new OACriterion("Nivel de desempenho", 100f, oaStandards)));
//
    //    //Exercise
    //    OpenAnswerExercise exercise = new OpenAnswerExercise();
    //    exercise.setStatement(new ExerciseStatement("Explicite, com base em dois aspetos significativos, o modo como o sujeito poético reage à figura feminina\r\n" + //
    //            "evocada no poema. Fundamente a sua resposta com transcrições pertinentes.","",""));
    //    exercise.setTitle("Pergunta 4");
    //    exercise.setSpecialist(new Specialist(specialistId));
    //    exercise.setCourse(new Course(courseId));
    //    exercise.setVisibility(Visibility.PUBLIC);
    //    return exercisesService.createExercise(exercise,null,exerciseRubric, tags.stream().map(Tag::getId).toList());
    //}

    private Exercise createOAExercise_Portugues_3_1(String specialistId, String courseId, List<Tag> tags,String texto) throws BadInputException {
        //Solution
        //OpenAnswerData openAnswerData = new OpenAnswerData("Ali"); //so aqui
        //ExerciseSolution exerciseSolution = new ExerciseSolution(null,openAnswerData);
        //Rubric

        OAStandard oaFormato_textual10 = new OAStandard("4","Escreve um texto de acordo com o género/formato solicitado (texto de opinião):\r\n" + //
                "• explicita o seu ponto de vista;\r\n" + //
                "• fundamenta a perspetiva adotada em, pelo menos, dois argumentos distintos;\r\n" + //
                "• ilustra cada um dos argumentos com, pelo menos, um exemplo;\r\n" + //
                "• formula uma conclusão adequada à argumentação desenvolvida;\r\n" + //
                "• produz um discurso valorativo (desenvolvendo um juízo de valor explícito ou implícito)",100.0F);
        OAStandard oaFormato_textual8 = new OAStandard("3","Escreve um texto de acordo com o género/formato solicitado (texto de opinião) e\r\n" + //
                "fundamenta a perspetiva adotada em, pelo menos, dois argumentos distintos, mas ilustra\r\n" + //
                "apenas um deles com um exemplo, assegurando os restantes aspetos em avaliação\r\n" + //
                "neste parâmetro.\r\n" + //
                "OU\r\n" + //
                "Escreve um texto de acordo com o género/formato solicitado (texto de opinião) e\r\n" + //
                "fundamenta a perspetiva adotada em, pelo menos, dois argumentos distintos, cada um\r\n" + //
                "deles ilustrado com, pelo menos, um exemplo, mas apresenta falhas em um ou dois dos\r\n" + //
                "restantes aspetos em avaliação neste parâmetro.",80.0F);
        OAStandard oaFormato_textual5 = new OAStandard("2","Escreve um texto de acordo com o género/formato solicitado (texto de opinião), mas\r\n" + //
                "fundamenta a perspetiva adotada em apenas um argumento, ilustrado com um único\r\n" + //
                "exemplo, assegurando os restantes aspetos em avaliação neste parâmetro.\r\n" + //
                "OU\r\n" + //
                "Escreve um texto de acordo com o género/formato solicitado (texto de opinião) e\r\n" + //
                "fundamenta a perspetiva adotada em, pelo menos, dois argumentos distintos, mas ilustra\r\n" + //
                "apenas um deles com um exemplo e apresenta falhas em um ou dois dos restantes\r\n" + //
                "aspetos em avaliação neste parâmetro.",50.0F);
        OAStandard oaFormato_textual3 = new OAStandard("1","Escreve um texto de acordo com o género/formato solicitado (texto de opinião), mas\r\n" + //
                "apresenta falhas no conjunto dos aspetos em avaliação neste parâmetro.\r\n" + //
                "OU\r\n" + //
                "Escreve um texto em que as marcas do género/formato solicitado se misturam, sem\r\n" + //
                "critério nem intencionalidade, com as de outros géneros/formatos.",30.0F);

        List<OAStandard> oaStandards = Arrays.asList(oaFormato_textual3,oaFormato_textual5,oaFormato_textual8,oaFormato_textual10);


        OAStandard oaTema10 = new OAStandard("4","Trata o tema proposto sem desvios e escreve um texto com eficácia argumentativa,\r\n" + //
                "assegurando:\r\n" + //
                "•  a mobilização de argumentos e de exemplos diversificados e pertinentes;\r\n" + //
                "• a progressão da informação de forma coerente;\r\n" + //
                "•  o recurso a um repertório lexical e a um registo de língua globalmente adequados\r\n" + //
                "ao desenvolvimento do tema, ainda que possam existir esporádicos afastamentos,\r\n" + //
                "justificados pela intencionalidade comunicativa.",100.0F);
        OAStandard oaTema8 = new OAStandard("3","Trata o tema proposto sem desvios, mas escreve um texto com falhas pontuais nos\r\n" + //
                "aspetos relativos à eficácia argumentativa.\r\n" + //
                "OU\r\n" + //
                "Trata o tema proposto com desvios pouco significativos, mas escreve um texto com\r\n" + //
                "eficácia argumentativa (tendo em conta a forma como o tema foi desenvolvido)",80.0F);
        OAStandard oaTema5 = new OAStandard("2","Trata o tema proposto com desvios pouco significativos e escreve um texto com falhas\r\n" + //
                "pontuais nos aspetos relativos à eficácia argumentativa.\r\n" + //
                "OU\r\n" + //
                "Trata o tema proposto sem desvios, mas escreve um texto com falhas significativas nos\r\n" + //
                "aspetos relativos à eficácia argumentativa.",50.0F);
        OAStandard oaTema3 = new OAStandard("1","Trata o tema proposto com desvios significativos e escreve um texto com reduzida\r\n" + //
                "eficácia argumentativa, mobilizando muito pouca informação pertinente.",30.0F);

        List<OAStandard> oaStandards2 = Arrays.asList(oaTema3,oaTema5,oaTema8,oaTema10);


        OAStandard oaOrganizacao10 = new OAStandard("4","Escreve um texto bem organizado, evidenciando um bom domínio dos mecanismos de\r\n" + //
                "coesão textual:\r\n" + //
                "•  apresenta um texto constituído por diferentes partes, devidamente proporcionadas e\r\n" + //
                "articuladas entre si de modo consistente;\r\n" + //
                "• marca, corretamente, os parágrafos;\r\n" + //
                "•  utiliza, adequadamente, mecanismos de articulação interfrásica;\r\n" + //
                "•  mantém, de forma sistemática, cadeias de referência através de substituições\r\n" + //
                "nominais e pronominais adequadas;\r\n" + //
                "•  estabelece conexões adequadas entre coordenadas de enunciação (pessoa, tempo,\r\n" + //
                "espaço) ao longo do texto",100.0F);
        OAStandard oaOrganizacao8 = new OAStandard("3","Escreve um texto globalmente bem organizado, em que evidencia domínio dos\r\n" + //
                "mecanismos de coesão textual, mas em que apresenta falhas pontuais em um ou dois\r\n" + //
                "dos aspetos em avaliação neste parâmetro.",80.0F);
        OAStandard oaOrganizacao5 = new OAStandard("2","Escreve um texto satisfatoriamente organizado, em que evidencia um domínio suficiente\r\n" + //
                "dos mecanismos de coesão textual, apresentando falhas pontuais em três ou mais dos\r\n" + //
                "aspetos em avaliação neste parâmetro, ou falhas significativas em um ou dois desses\r\n" + //
                "aspetos.",50.0F);
        OAStandard oaOrganizacao3 = new OAStandard("1","Escreve um texto com uma organização pouco satisfatória, recorrendo a insuficientes\r\n" + //
                "mecanismos de coesão ou mobilizando-os de forma inadequada.",30.0F);

        List<OAStandard> oaStandards3 = Arrays.asList(oaOrganizacao3,oaOrganizacao5,oaOrganizacao8,oaOrganizacao10);

        ExerciseRubric exerciseRubric = new OpenAnswerRubric(List.of(new OACriterion("Género/Formato Textual", 100.0f/3, oaStandards),new OACriterion("Nivel de desempenho", 100.0f/3, oaStandards2),new OACriterion("Nivel de desempenho", 100.0f/3, oaStandards3)));
        //Exercise
        OpenAnswerExercise exercise = new OpenAnswerExercise();
        exercise.setStatement(new ExerciseStatement(texto+"\nNum texto de opinião bem estruturado, com um mínimo de duzentas e um máximo de trezentas e cinquenta\r\n" + //
                "palavras, defenda uma perspetiva pessoal sobre a posição assumida por David Sobral quanto ao impacto da\r\n" + //
                "tecnologia nas relações humanas.\r\n" + //
                "No seu texto:\r\n" + //
                "− explicite, de forma clara e pertinente, o seu ponto de vista, fundamentando-o em dois argumentos, cada\r\n" + //
                "um deles ilustrado com um exemplo significativo;\r\n" + //
                "− formule uma conclusão adequada à argumentação desenvolvida;\r\n" + //
                "− utilize um discurso valorativo (juízo de valor explícito ou implícito).","",""));
        exercise.setTitle("Pergunta Pt 5.1");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);
        exercisesService.createExercise(exercise,null,exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        OpenAnswerExercise exercise2 = new OpenAnswerExercise();
        exercise2.setStatement(new ExerciseStatement("Num texto de opinião bem estruturado, com um mínimo de duzentas e um máximo de trezentas e cinquenta\r\n" + //
                "palavras, defenda uma perspetiva pessoal sobre a posição assumida por David Sobral quanto ao impacto da\r\n" + //
                "tecnologia nas relações humanas.\r\n" + //
                "No seu texto:\r\n" + //
                "− explicite, de forma clara e pertinente, o seu ponto de vista, fundamentando-o em dois argumentos, cada\r\n" + //
                "um deles ilustrado com um exemplo significativo;\r\n" + //
                "− formule uma conclusão adequada à argumentação desenvolvida;\r\n" + //
                "− utilize um discurso valorativo (juízo de valor explícito ou implícito).","",""));
        exercise2.setTitle("Pergunta Pt 5.1");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(null);
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }



    private String createPortugueseExam(Specialist specialist, Course course, List<Tag> tags) throws BadInputException, NotFoundException {
        //Exercises
        //String exercise1 = createMCExercise0(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)));
        String texto1 = "Leia os textos e as notas.\n" +
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
                "estrelas giravam devagar no céu, Lua onde estás, Sol aonde vais.";

        // Exame Portugues_nºgrupo_parte_nºquestao
        Exercise exercise1 = createOAExercise_Portugues_1_A_1(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto1);
        Exercise exercise2 = createOAExercise_Portugues_1_A_2(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto1);
        Exercise exercise3 = createOAExercise_Portugues_1_A_3(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto1);

        TestExercise tex1 = new ConcreteExercise(13.0F,exercise1);
        TestExercise tex2 = new ConcreteExercise(13.0F,exercise2);
        TestExercise tex3 = new ConcreteExercise(13.0F,exercise3);


        TestGroup tg1 = new TestGroup(texto1, Float.valueOf(39), List.of(tex1,tex2,tex3));

        String texto2 = "parte B\r\n" + //
                "Leia o poema e as notas.\r\n" + //
                "5\r\n" + //
                "10\r\n" + //
                " Quem vê, Senhora, claro e manifesto1\r\n" + //
                "o lindo ser de vossos olhos belos,\r\n" + //
                "se não perder a vista só em vê-los,\r\n" + //
                "já não paga o que deve a vosso gesto2.\r\n" + //
                " Este me parecia preço honesto;\r\n" + //
                "mas eu, por de vantagem merecê-los,\r\n" + //
                "dei mais a vida e alma por querê-los,\r\n" + //
                "donde já me não fica mais de resto.\r\n" + //
                " Assi que a vida e alma e esperança\r\n" + //
                "e tudo quanto tenho, tudo é vosso,\r\n" + //
                "e o proveito disso eu só o levo.\r\n" + //
                " Porque é tamanha bem-aventurança3\r\n" + //
                "o dar-vos quanto tenho e quanto posso\r\n" + //
                "que, quanto mais vos pago, mais vos devo.";

        Exercise exercise4 = createOAExercise_Portugues_1_B_4(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto2);
        Exercise exercise5 = createMCExercise_Portugues_1_B_5(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto2);
        Exercise exercise6 = createMCExercise_Portugues_1_B_6(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto2);

        TestExercise tex4 = new ConcreteExercise(13.0F,exercise4);
        TestExercise tex5 = new ConcreteExercise(13.0F,exercise5);
        TestExercise tex6 = new ConcreteExercise(13.0F,exercise6);

        TestGroup tg2 = new TestGroup(texto2, Float.valueOf(39), List.of(tex4,tex5,tex6));


        String texto3 = "parte C\r\n" + //
                "Leia a cantiga de amor a seguir transcrita, tendo em vista o estabelecimento de uma comparação com o\r\n" + //
                "soneto camoniano apresentado na Parte B desta prova.\r\n" + //
                "5\r\n" + //
                "10\r\n" + //
                "A dona que eu am’e tenho por senhor\r\n" + //
                "amostrade-mi-a, Deus, se vos en prazer for1,\r\n" + //
                " senom dade-mi2 a morte.\r\n" + //
                "A que tenh’eu por lume3 destes olhos meus\r\n" + //
                "e por que choram sempr’, amostrade-mi-a, Deus,\r\n" + //
                " senom dade-mi a morte.\r\n" + //
                "Essa que vós fezestes melhor parecer\r\n" + //
                "de quantas sei, ai, Deus!, fazede-mi-a veer4,\r\n" + //
                " senom dade-mi a morte.\r\n" + //
                "Ai Deus! que mi a fezestes mais ca mim amar5,\r\n" + //
                "mostrade-mi-a, u6 possa com ela falar,\r\n" + //
                " senom dade-mi a morte.";

        Exercise exercise7 = createOAExercise_Portugues_1_C_7(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto3);

        TestExercise tex7 = new ConcreteExercise(13.0F,exercise7);

        TestGroup tg3 = new TestGroup(texto3,Float.valueOf(13), List.of(tex7));

        String texto4 = "GRUPO II\r\n" + //
                "Leia o texto.\r\n" + //
                "5\r\n" + //
                "10\r\n" + //
                "15\r\n" + //
                "20\r\n" + //
                "25\r\n" + //
                "30\r\n" + //
                "Olhar o céu numa noite escura, longe de cidades e regiões densamente povoadas, revela\r\n" + //
                "um manto escuro densamente estrelado ao qual é difícil ficar indiferente (até um robô deve ficar\r\n" + //
                "fascinado). Um espetáculo de uma simplicidade profunda, mas que cada vez menos pessoas\r\n" + //
                "têm a oportunidade de ver, pelos mais variados motivos. Sobretudo em Portugal, um dos países\r\n" + //
                "do mundo com maior poluição luminosa, que se tem vindo a acentuar cada vez mais.\r\n" + //
                "Nos centros urbanos, e nos subúrbios, é hoje praticamente impossível vermos a nossa\r\n" + //
                "própria casa celeste, a Via Láctea. Isso leva a algo estranho e paradoxal. O progresso por\r\n" + //
                "vezes frenético da ciência e do conhecimento em geral leva-nos hoje à ideia mais nítida de\r\n" + //
                "sempre sobre de onde vimos, como chegámos aqui, e do nosso lugar no Universo. No entanto,\r\n" + //
                "nunca tantas e tantos de nós estiveram tão distantes de conseguir olhar e ver o céu na sua\r\n" + //
                "plenitude. O céu, de onde vimos, para onde tudo o que nos compõe acabará por voltar, ainda\r\n" + //
                "que faltem milhares de milhões de anos. Olhar o céu é apontar em direção às nossas origens\r\n" + //
                "cósmicas, mas nunca tantos de nós irão viver sem ver o céu plenamente estrelado durante\r\n" + //
                "tantas noites ao longo das suas vidas. Nunca tantos de nós dirigiram o olhar maioritariamente\r\n" + //
                "para baixo. Um olhar focado em pequenos ecrãs que operamos com as nossas mãos e que\r\n" + //
                "nos tornam por vezes cada vez mais isolados. Num mundo que é cada vez mais global, mas\r\n" + //
                "por vezes tão conectadamente desconectado.\r\n" + //
                "A nossa viagem em busca das nossas origens, olhando ou não o céu, parece ter começado\r\n" + //
                "muito, muito cedo. Desde então, descobrimos que não somos nem estamos de todo no centro\r\n" + //
                "do mundo, do Universo. O Universo é de tal forma imenso que, em comparação, somos\r\n" + //
                "total e completamente insignificantes, espacial e temporalmente. Somos total e brutalmente\r\n" + //
                "insignificantes.\r\n" + //
                "Surpreendentemente, ainda nos socorremos de argumentos falsos mas convenientes.\r\n" + //
                "Coisas que nos dizem que, afinal, somos mesmo muito importantes. Potencialmente eternos,\r\n" + //
                "especiais. O céu, na sua beleza e grandiosidade, mas sobretudo na sua capacidade para\r\n" + //
                "nos manter humildes e individualmente irrelevantes, é ainda a melhor ferramenta para nos\r\n" + //
                "apercebermos do quão ligados estamos. Estamos ligados uns aos outros, ao nosso planeta,\r\n" + //
                "ao sistema solar, à nossa galáxia. Paradoxalmente, olhar o céu e estudar o Universo é uma\r\n" + //
                "das formas mais profundas e eficazes de nos valorizarmos humanamente no contexto da vida\r\n" + //
                "na Terra. Um planeta único, belo, frágil. Tudo, sem inflamar demasiado o ego e sem termos a\r\n" + //
                "mania de que somos demasiado bons.";

        Exercise exercise2_1 = createMCExercise_Portugues_2_1(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto4);
        Exercise exercise2_2 = createMCExercise_Portugues_2_2(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto4);
        Exercise exercise2_3 = createMCExercise_Portugues_2_3(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto4);
        Exercise exercise2_4 = createMCExercise_Portugues_2_4(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto4);
        Exercise exercise2_5 = createMCExercise_Portugues_2_5(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto4);
        Exercise exercise2_6 = createMCExercise_Portugues_2_6(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto4);
        Exercise exercise2_7 = createMCExercise_Portugues_2_7(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto4);

        TestExercise tex2_1 = new ConcreteExercise(13.0F,exercise2_1);
        TestExercise tex2_2 = new ConcreteExercise(13.0F,exercise2_2);
        TestExercise tex2_3 = new ConcreteExercise(13.0F,exercise2_3);
        TestExercise tex2_4 = new ConcreteExercise(13.0F,exercise2_4);
        TestExercise tex2_5 = new ConcreteExercise(13.0F,exercise2_5);
        TestExercise tex2_6 = new ConcreteExercise(13.0F,exercise2_6);
        TestExercise tex2_7 = new ConcreteExercise(13.0F,exercise2_7);

        TestGroup tg4 = new TestGroup(texto4,Float.valueOf(91), List.of(tex2_1,tex2_2,tex2_3,tex2_4,tex2_5,tex2_6,tex2_7));


        String texto5 = "GRUPO III\r\n" + //
                "«Nunca tantos de nós dirigiram o olhar maioritariamente para baixo. Um olhar focado em pequenos\r\n" + //
                "ecrãs que operamos com as nossas mãos e que nos tornam por vezes cada vez mais isolados. Num\r\n" + //
                "mundo que é cada vez mais global, mas por vezes tão conectadamente desconectado.»";

        Exercise exercise3_1 = createOAExercise_Portugues_3_1(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)),texto5);

        TestExercise tex3_1 = new ConcreteExercise(18.0F,exercise3_1);

        TestGroup tg5 = new TestGroup(texto5,Float.valueOf(18), List.of(tex3_1));


        String title = "Exame Final Nacional de Português";
        String instrucoes = "A prova inclui 10 itens, devidamente identificados no enunciado, cujas respostas contribuem\r\n" + //
                "obrigatoriamente para a classificação final. Dos restantes 5 itens da prova, apenas contribuem para a\r\n" + //
                "classificação final os 3 itens cujas respostas obtenham melhor pontuação.\r";

        pt.uminho.di.chalktyk.models.tests.Test t1 = new pt.uminho.di.chalktyk.models.tests.Test(null, title, instrucoes,
                200.0F, "FIM", LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), specialist, Visibility.PUBLIC, course, null, List.of(tg1,tg2,tg3,tg4,tg5));
        return testsService.createTest(t1);
    }


    private Exercise createVFJustifyExercise_History_1_1(String specialistId, String courseId, List<Tag> tags) throws BadInputException {
        //Solution
        MultipleChoiceResolutionItem option1 = new MultipleChoiceResolutionItem(0.0F,null,true);
        MultipleChoiceResolutionItem option2 = new MultipleChoiceResolutionItem(0.0F,"Tratado de Zamora",false);
        MultipleChoiceResolutionItem option3 = new MultipleChoiceResolutionItem(0.0F,"Quatro dinastias",false);
        MultipleChoiceResolutionItem option4 = new MultipleChoiceResolutionItem(0.0F,null,true);

        HashMap<String,MultipleChoiceResolutionItem> itemResolutionsSol = new HashMap<>();
        itemResolutionsSol.put("A",option1);
        itemResolutionsSol.put("B",option2);
        itemResolutionsSol.put("C",option3);
        itemResolutionsSol.put("D",option4);
        MultipleChoiceData multipleChoiceData = new MultipleChoiceData(itemResolutionsSol);

        //Rubric
        HashMap<String,OpenAnswerRubric> mcRubricMap = new HashMap<>();
        mcRubricMap.put("A",createOARubric());
        mcRubricMap.put("B",createOARubric());
        mcRubricMap.put("C",createOARubric());
        mcRubricMap.put("D",createOARubric());
        ExerciseRubric exerciseRubric = new MultipleChoiceRubric(25.0F,mcRubricMap);

        //Exercise
        HashMap<String, Item> itemResolutions = new HashMap<>();
        itemResolutions.put("A",new StringItem("Em 1143, Portugal tornou-se um país independente, o seu 1.º Rei foi D. Afonso Henriques."));
        itemResolutions.put("B",new StringItem("O Rei de Leão reconheceu a independência do Condado, com a assinatura do Tratado de Ceuta."));
        itemResolutions.put("C",new StringItem("Em Portugal, durante a monarquia, houve cinco dinastias."));
        itemResolutions.put("D",new StringItem("A 1.ª Dinastia de Portugal terminou com a morte de D.Fernando."));


        MultipleChoiceExercise exercise = new MultipleChoiceExercise(Mctype.TRUE_FALSE__JUSTIFY_FALSE_UNMARKED,itemResolutions);
        exercise.setStatement(new ExerciseStatement("Responda ás seguintes questões de verdadeiro ou falso, justificando as falsas","",""));
        exercise.setTitle("Pergunta Hist 1.1");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,new ExerciseSolution(null, multipleChoiceData.clone()),exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        MultipleChoiceExercise exercise2 = new MultipleChoiceExercise(Mctype.TRUE_FALSE__JUSTIFY_FALSE_UNMARKED,itemResolutions);
        exercise2.setStatement(new ExerciseStatement("Responda ás seguintes questões de verdadeiro ou falso, justificando as falsas","",""));
        exercise2.setTitle("Pergunta Hist 1.1");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);

        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setSolution(new ExerciseSolution(null, multipleChoiceData.clone()));
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }

    private Exercise createChatExercise_History_1_2(String specialistId, String courseId, List<Tag> tags) throws BadInputException {
        //Rubric
        OAStandard oaStandard100 = new OAStandard("5","Acertou em todas as perguntas.",100.0F);
        OAStandard oaStandard80 = new OAStandard("4","Acertou em quatro perguntas.",80.0F);
        OAStandard oaStandard60 = new OAStandard("3","Acertou em três perguntas.",60.0F);
        OAStandard oaStandard40 = new OAStandard("2","Acertou em duas perguntas.",40.0F);
        OAStandard oaStandard20 = new OAStandard("1","Acertou em uma pergunta.",20.0F);
        OAStandard oaStandard0 = new OAStandard("0","Se nenhum critério se aplicar",0.0F);
        List<OAStandard> oaStandards = Arrays.asList(oaStandard0,oaStandard20,oaStandard40,oaStandard60,oaStandard80,oaStandard100);

        ExerciseRubric exerciseRubric = new ChatExerciseRubric(null,List.of(new OACriterion("Nivel de desempenho", 100f, oaStandards)));
        List<String> tagsAI = new ArrayList<>(tags.stream().map(Tag::getName).toList());
        tagsAI.add("Dinastia Portuguesa");
        tagsAI.add("D.Afonso Henriques");

        ChatExercise exercise = new ChatExercise(tagsAI);
        exercise.setStatement(new ExerciseStatement("D.Sebastião I de Portugal, morreu em qual batalha?","",""));
        exercise.setTitle("Pergunta Hist 1.2");
        exercise.setSpecialist(new Specialist(specialistId));
        exercise.setCourse(new Course(courseId));
        exercise.setVisibility(Visibility.PUBLIC);

        exercisesService.createExercise(exercise,null,exerciseRubric.clone(), tags.stream().map(Tag::getId).toList());

        ChatExercise exercise2 = new ChatExercise(tagsAI);
        exercise2.setStatement(new ExerciseStatement("D.Sebastião I de Portugal, morreu em qual batalha?","",""));
        exercise2.setTitle("Pergunta Hist 1.2");
        exercise2.setSpecialist(new Specialist(specialistId));
        exercise2.setCourse(new Course(courseId));
        exercise2.setVisibility(Visibility.PUBLIC);
        exercise2.setRubric(exerciseRubric.clone());
        exercise2.setTags(new HashSet<>(tags));
        return exercise2;
    }

    private String createHistoria(Specialist specialist, Course course, List<Tag> tags) throws BadInputException, NotFoundException {
        //Exercises
        //String exercise1 = createMCExercise0(specialist.getId(),course.getId(),Arrays.asList(tags.get(0)));
        String texto1 = "Responda ás seguintes perguntas sobre História";

        // Exame Portugues_nºgrupo_parte_nºquestao
        Exercise exercise1 = createVFJustifyExercise_History_1_1(specialist.getId(),course.getId(),tags);
        Exercise exercise2 = createChatExercise_History_1_2(specialist.getId(),course.getId(),tags);

        TestExercise tex1 = new ConcreteExercise(100.0F,exercise1);
        TestExercise tex2 = new ConcreteExercise(100.0F,exercise2);


        TestGroup tg1 = new TestGroup(texto1, 200F, List.of(tex1,tex2));

        String title = "Teste Exemplo de História";
        String instrucoes = "A prova inclui 2 itens, devidamente identificados no enunciado, cujas respostas contribuem\r\n" + //
                "obrigatoriamente para a classificação final.\r";

        pt.uminho.di.chalktyk.models.tests.Test t1 = new pt.uminho.di.chalktyk.models.tests.Test(null, title, instrucoes,
                200.0F, "FIM", LocalDateTime.now(), LocalDateTime.now().plusSeconds(1), specialist, Visibility.PUBLIC, course, null, List.of(tg1));
        return testsService.createTest(t1);
    }
}
