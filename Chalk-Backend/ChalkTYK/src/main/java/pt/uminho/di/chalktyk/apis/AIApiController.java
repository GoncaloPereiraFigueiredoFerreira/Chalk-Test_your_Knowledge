package pt.uminho.di.chalktyk.apis;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.dtos.ChatExerciseDTO;
import pt.uminho.di.chalktyk.dtos.OpenAnswerDTO;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OAStandard;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.services.IAIService;
import pt.uminho.di.chalktyk.services.exceptions.ApiConnectionException;

/**
 * AIApiController
 */
@RestController
@RequestMapping("/ai")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AIApiController implements AIApi{
    
    private final IAIService aiService;

    @Autowired
    public AIApiController(IAIService aiService) {
        this.aiService = aiService;
    }

    @Override
    public ResponseEntity<String> getNewQuestion(ChatExerciseDTO chatExercise) {

        JsonObjectBuilder request = Json.createObjectBuilder();
        request.add("Topics", Json.createArrayBuilder(chatExercise.getTopics()));
        request.add("Chat", Json.createArrayBuilder(chatExercise.getChat()));
            
        try {
            JsonObject response = aiService.bypassBackend("/get_oral",request.build().toString());
            String ret = response.getString("Question");
            return ResponseEntity.ok(ret);
        } catch (ApiConnectionException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Float> getEvaluationChat(ChatExerciseDTO chatExercise) {

        JsonObjectBuilder request = Json.createObjectBuilder();
        request.add("Topics", Json.createArrayBuilder(chatExercise.getTopics()));
        request.add("Chat", Json.createArrayBuilder(chatExercise.getChat()));
            
        try {
            JsonObject response = aiService.bypassBackend("/eval_oral",request.build().toString());
            Float ret = Float.parseFloat(response.get("Cotation").toString());
            return ResponseEntity.ok(ret);
        } catch (ApiConnectionException e) {
            return new ExceptionResponseEntity<Float>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Float> getEvaluationOpenAnswer(OpenAnswerDTO exercise) {
        Float total = 0.0f;

        JsonObjectBuilder request = Json.createObjectBuilder();
        request.add("Question",exercise.getQuestion());
        request.add("Answer",exercise.getAnswer());
        request.add("Auxiliar",exercise.getAuxiliar());

        OpenAnswerRubric rubric = exercise.getRubric();

        List<OACriterion> criteria = rubric.getCriteria();
        
        try{
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
        } catch (ApiConnectionException e) {
            return new ExceptionResponseEntity<Float>().createRequest(e);
        }


    }

}
