package pt.uminho.di.chalktyk.apis;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.dtos.ChatExerciseDTO;
import pt.uminho.di.chalktyk.dtos.GenerateQuestionAIDTO;
import pt.uminho.di.chalktyk.dtos.MultipleChoiceAIDTO;
import pt.uminho.di.chalktyk.dtos.OpenAnswerAIDTO;
import pt.uminho.di.chalktyk.dtos.TrueOrFalseAIDTO;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.exercises.chat.ChatExercise;
import pt.uminho.di.chalktyk.models.exercises.chat.ChatExerciseData;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OAStandard;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerData;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerExercise;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.services.IAIService;
import pt.uminho.di.chalktyk.services.IExercisesService;
import pt.uminho.di.chalktyk.services.IExercisesTestsAuthorization;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.exceptions.ApiConnectionException;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.ServiceException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

/**
 * AIApiController
 */
@RestController
@RequestMapping("/ai")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AIApiController implements AIApi{

    private final IAIService aiService;
    private final ISecurityService securityService;
    private final IExercisesTestsAuthorization exercisesTestsAuthorization;
    private final IExercisesService exercisesService;

    @Autowired
    public AIApiController(IAIService aiService, ISecurityService securityService, IExercisesTestsAuthorization exercisesTestsAuthorization, IExercisesService exercisesService) {
        this.aiService = aiService;
        this.securityService = securityService;
        this.exercisesTestsAuthorization = exercisesTestsAuthorization;
        this.exercisesService = exercisesService;
    }

    private Boolean canGetExercise(String userId, String userRole, String exerciseId, String resolutionId) throws NotFoundException {
        Boolean ret = false;

        Exercise exercise = exercisesService.getExerciseById(exerciseId);
        Visibility vis = exercise.getVisibility();
        String courseId = exercisesService.getExerciseCourse(exerciseId);
        String institutionId = exercisesService.getExerciseInstitution(exerciseId);

        if(userRole.equals("STUDENT")) {
            ret = exercisesTestsAuthorization.canStudentAccessExerciseResolution(userId, resolutionId);
            ret = ret && exercisesTestsAuthorization.canStudentGetExercise(userId, vis, courseId, institutionId);
        }else if (userRole.equals("SPECIALIST")) {
            ret = exercisesTestsAuthorization.canSpecialistAccessExerciseResolution(userId, resolutionId);
            ret = ret && exercisesTestsAuthorization.canSpecialistGetExercise(userId, exercise.getSpecialistId(), vis, courseId, institutionId);
        }

        return ret;
    }

    @Override
    public ResponseEntity<String> getNewQuestion(ChatExerciseDTO chatExercise, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);

            JsonObjectBuilder request = Json.createObjectBuilder();
            request.add("Topics", Json.createArrayBuilder(chatExercise.getTopics()));
            request.add("Chat", Json.createArrayBuilder(chatExercise.getChat()));
            request.add("StudentID",token.getUserId());

            JsonObject response = aiService.bypassBackend("/get_oral",request.build().toString());
            String ret = response.getString("Question");
            return ResponseEntity.ok(ret);
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Float> getEvaluationChat(String resolutionId, String exerciseId, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                   role = token.getUserRole();


            // checks if the user has permission
            Boolean flag = canGetExercise(userId, role, exerciseId, resolutionId);
            if (!flag){
                throw new UnauthorizedException("User can not see either exercise or resolution");
            }

            Exercise exercise = exercisesService.getExerciseById(exerciseId);
            ExerciseResolution resolution = exercisesService.getExerciseResolution(resolutionId);

            if(!(exercise instanceof ChatExercise chatExercise)){
                throw new BadInputException("Exercise given not of type ChatExercise");
            }

            if(!(resolution.getData() instanceof ChatExerciseData chatData)){
                throw new BadInputException("Resolution given not of type ChatExercise");
            }

            JsonObjectBuilder request = Json.createObjectBuilder();
            request.add("Topics", Json.createArrayBuilder(chatExercise.getTopics()));
            request.add("Chat", Json.createArrayBuilder(chatData.getChat()));

            JsonObject response = aiService.bypassBackend("/eval_oral",request.build().toString());
            Float ret = Float.parseFloat(response.get("Cotation").toString());
            return ResponseEntity.ok(ret);
        } catch (ApiConnectionException | BadInputException | UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<Float>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Float> getEvaluationOpenAnswer(String resolutionId, String exerciseId, String jwt) {
        try{
            Float total = 0.0f;

            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                   role = token.getUserRole();


            // checks if the user has permission
            Boolean flag = canGetExercise(userId, role, exerciseId, resolutionId);
            if (!flag){
                throw new UnauthorizedException("User can not see either exercise or resolution");
            }

            Exercise exercise = exercisesService.getExerciseById(exerciseId);
            ExerciseResolution resolution = exercisesService.getExerciseResolution(resolutionId);

            if(!(exercise instanceof OpenAnswerExercise answerExercise)){
                throw new BadInputException("Exercise given not of the type Open answer exercise");
            }

            if(!(resolution.getData() instanceof OpenAnswerData answerData)){
                throw new BadInputException("Resolution given not of the type Open answer exercise");
            }

            JsonObjectBuilder request = Json.createObjectBuilder();
            request.add("Question",answerExercise.getStatement().getText());
            request.add("Answer",answerData.getText());
            //request.add("Auxiliar",answerExercise.getAuxiliar());

            if(!(answerExercise.getRubric() instanceof OpenAnswerRubric rubric)){
                throw new BadInputException("Exercise rubric not of the type Open answer rubric");
            }

            List<OACriterion> criteria = rubric.getCriteria();

            for (OACriterion i : criteria) {
                List<OAStandard> standart = i.getStandards();

                JsonArrayBuilder jRubric = Json.createArrayBuilder();
                standart.forEach(s ->{
                    jRubric.add(
                            Json.createObjectBuilder()
                            .add("Description",s.getDescription())
                            .add("Cotation",s.getPercentage())
                            );
                });

                request.add("Rubric",jRubric);
                JsonObject requestJ = request.build();

                JsonObject response = aiService.bypassBackend("/open_answer", requestJ.toString());
                request = Json.createObjectBuilder(requestJ);

                Float percentage = Float.parseFloat(response.get("Evaluation").toString());

                total = total + percentage * i.getPoints();
            }

            return ResponseEntity.ok(total);
        } catch (ApiConnectionException | NotFoundException | UnauthorizedException | BadInputException e) {
            return new ExceptionResponseEntity<Float>().createRequest(e);
        }


    }

    @Override
    public ResponseEntity<MultipleChoiceAIDTO> getNewMultiple(GenerateQuestionAIDTO inputQuestion, String jwt) {
        try{
            String text = inputQuestion.getText();
            String input = inputQuestion.getInput();

            JsonObjectBuilder request = Json.createObjectBuilder();
            request.add("Text",text);
            request.add("Input",input);

            JsonObject response = aiService.bypassBackend("/create_mult", request.build().toString());

            int correct = response.getInt("Correct_answer");
            String question = response.getString("Question");
            JsonArray janswers = response.getJsonArray("Answers");

            String[] answers = new String[janswers.size()];

            for (int i = 0; i < janswers.size();i++) {
                answers[i] = janswers.getString(i);
            }

            return ResponseEntity.ok(new MultipleChoiceAIDTO(answers, correct, question));
        }catch(ApiConnectionException e){
            return new ExceptionResponseEntity<MultipleChoiceAIDTO>().createRequest(e); 
        }
    }

    @Override
    public ResponseEntity<OpenAnswerAIDTO> getNewOpenAnswer(GenerateQuestionAIDTO inputQuestion, String jwt) {
        try{
            String text = inputQuestion.getText();
            String input = inputQuestion.getInput();

            JsonObjectBuilder request = Json.createObjectBuilder();
            request.add("Text",text);
            request.add("Input",input);

            JsonObject response = aiService.bypassBackend("/create_open", request.build().toString());

            String question = response.getString("Question");
            JsonArray jtopics = response.getJsonArray("Topics");

            String[] topics = new String[jtopics.size()];

            for (int i = 0; i < jtopics.size();i++) {
                topics[i] = jtopics.getString(i);
            }

            return ResponseEntity.ok(new OpenAnswerAIDTO(question, topics));
        }catch(ApiConnectionException e){
            return new ExceptionResponseEntity<OpenAnswerAIDTO>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<TrueOrFalseAIDTO> getNewTrueOrFalse(GenerateQuestionAIDTO inputQuestion, String jwt) {
        try{
            String text = inputQuestion.getText();
            String input = inputQuestion.getInput();

            JsonObjectBuilder request = Json.createObjectBuilder();
            request.add("Text",text);
            request.add("Input",input);

            JsonObject response = aiService.bypassBackend("/create_open", request.build().toString());

            String question = response.getString("Question");
            Boolean correct = response.getBoolean("True");

            return ResponseEntity.ok(new TrueOrFalseAIDTO(question, correct));
        }catch(ApiConnectionException e){
            return new ExceptionResponseEntity<TrueOrFalseAIDTO>().createRequest(e);
        }
    }

}
