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
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.exercises.ExerciseStatement;
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
    
    private final String apiUrl = "http://localhost:5000";

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

        JsonObject ret = Json.createReader(new StringReader(response.body())).readObject();

        return ret;
    }

    public JsonObject bypassBackend(String endpoint,String bodyS) throws ApiConnectionException{
        JsonObject body = Json.createReader(new StringReader(bodyS)).readObject();
        
        JsonObject response = sendRequest(endpoint, body);

        return response;
    }

    public Float evaluateOpenAnswer(Exercise exerciseI, ExerciseRubric rubricI, ExerciseResolution solution) throws NotFoundException, ApiConnectionException, BadInputException{
        
        JsonObjectBuilder request = Json.createObjectBuilder();

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
            }
        }else{
            throw new BadInputException("Exercise Rubric given is not of the type OpenAnswerRubric");
        }

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
                    .add("id_user", "1")//TODO dar fix a isto 
                    .add("Answer",data.getText()))
            );
        }else{
            throw new BadInputException("Solution given does not contain a OpenAnswerData");
        }

        JsonObject response = sendRequest("/open_answer", request.build());

        Object[] evaluations =  response.getJsonArray("Evaluation").toArray();

        Float cotation = new Float( ((JsonObject)evaluations[0]).get("Cotation").toString());

        return cotation;
    }
    
}
