package pt.uminho.di.chalktyk.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.stereotype.Service;

import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.ExerciseStatement;
import pt.uminho.di.chalktyk.models.exercises.chat.ChatExercise;
import pt.uminho.di.chalktyk.models.exercises.chat.ChatExerciseData;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OACriterion;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OAStandard;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerData;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerExercise;
import pt.uminho.di.chalktyk.models.exercises.open_answer.OpenAnswerRubric;
import pt.uminho.di.chalktyk.services.exceptions.ApiConnectionException;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

/**
 * AIService
 */
@Service
public class AIService implements IAIService {

    private final IExercisesService exercisesService;
    
    private final String apiUrl = "http://api:5000";

    public AIService(IExercisesService exercisesService) {
        this.exercisesService = exercisesService;
    }

    private  JsonObject sendRequest(String endpoint,JsonObject body) throws ApiConnectionException{
        HttpRequest evalRequest; 
        URI uri = null;

        try {
            uri = new URI(apiUrl + endpoint);
        } catch (URISyntaxException e) {
            // Never should happen since end points are hard code
            e.printStackTrace();
        }

        evalRequest = HttpRequest.newBuilder()
            .uri(uri)
            .method("GET",BodyPublishers.ofString(body.toString()))
            .header("Content-type","application/json")
            .build();

        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(evalRequest,BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new ApiConnectionException("Error stabilishing connection to OpenAI API");
        }
        

        if(response.statusCode() != 200){
            throw new ApiConnectionException("Error in api response");
        }

        System.out.println(response.body().toString());

        JsonObject ret = Json.createReader(new StringReader(response.body())).readObject();

        return ret;
    }

    public JsonObject bypassBackend(String endpoint,String bodyS) throws ApiConnectionException{
        JsonObject body = Json.createReader(new StringReader(bodyS)).readObject();
        
        JsonObject response = sendRequest(endpoint, body);

        return response;
    }

    public String newQuestionChatExercise(Exercise exerciseI, ExerciseResolution solution, String answer) throws ApiConnectionException, BadInputException{
        JsonObjectBuilder request = Json.createObjectBuilder();

        //parse topics and Ex_id
        if(exerciseI instanceof ChatExercise exercise){
            JsonArrayBuilder jTopics = Json.createArrayBuilder();

            for (String topic: exercise.getTopics()){
                    jTopics.add(topic);
            }

            request.add("Ex_id",exercise.getId());
            request.add("Topics", jTopics);
        }else{
            throw new BadInputException("Exercise given is not of the type ChatExercise");
        }

        //parse chat and student id
        if(solution.getData() instanceof ChatExerciseData data){
            JsonArrayBuilder jChat = Json.createArrayBuilder(data.getChat());

            request.add("Chat",jChat);
            request.add("Student_id",solution.getId());
        }else{
            throw new BadInputException("Exercise given is not of the type ChatExercise");
        }

        request.add("Answer",answer);
        
        JsonObject response = sendRequest("/get_oral", request.build());

        return response.getString("Question");

    }

    public Float evaluateChatExercise(Exercise exerciseI, ExerciseRubric rubricI, ExerciseResolution solution) throws ApiConnectionException, BadInputException{
        JsonObjectBuilder request = Json.createObjectBuilder();

        //parse topics nad exercise id
        if(exerciseI instanceof ChatExercise exercise){
            JsonArrayBuilder jTopics = Json.createArrayBuilder();

            for (String topic: exercise.getTopics()){
                    jTopics.add(topic);
            }

            request.add("Ex_id",exercise.getId());
            request.add("Topics", jTopics);
        }else{
            throw new BadInputException("Exercise given is not of the type ChatExercise");
        }

        //parse chat and student id
        if(solution.getData() instanceof ChatExerciseData data){
            JsonArrayBuilder jChat = Json.createArrayBuilder(data.getChat());

            request.add("Chat",jChat);
            request.add("Student_id",solution.getId());
        }else{
            throw new BadInputException("Exercise given is not of the type ChatExercise");
        }

        JsonObject response = sendRequest("/eval_oral", request.build());
        
        Float ret = Float.parseFloat(response.get("Cotation").toString());

        return ret; 

    }

    public Float evaluateOpenAnswer(Exercise exerciseI, ExerciseRubric rubricI, ExerciseResolution solution) throws NotFoundException, ApiConnectionException, BadInputException{
        Float total = 0.0f;
        JsonObjectBuilder request = Json.createObjectBuilder();

        // parse Question to json
        if (exerciseI instanceof OpenAnswerExercise exercise) {
            ExerciseStatement question = exercise.getStatement();
            request.add("Question",question.getText());
        }else{
            throw new BadInputException("Exercise given is not of the type OpenAnswerExercise");
        }

        // parse Answer to json 
        if(solution.getData() instanceof OpenAnswerData data){
            request.add("Answers", Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                    .add("id_user", solution.getId()) 
                    .add("Answer",data.getText()))
            );
        }else{
            throw new BadInputException("Solution given does not contain a OpenAnswerData");
        }

        // parse Rubric to json 
        if(rubricI instanceof OpenAnswerRubric rubric){
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

                JsonObject response = sendRequest("/open_answer", requestJ);
                request = Json.createObjectBuilder(requestJ);

                Float percentage = Float.parseFloat(response.get("Evaluation").toString());

                total = total + percentage * i.getPoints();
            }
        }else{
            throw new BadInputException("Exercise Rubric given is not of the type OpenAnswerRubric");
        }

        return total;
    }
    
}
