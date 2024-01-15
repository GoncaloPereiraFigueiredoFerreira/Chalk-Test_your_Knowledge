package pt.uminho.di.chalktyk.services;

import javax.json.JsonObject;

import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.services.exceptions.ApiConnectionException;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

/**
 * IAIService
 */
public interface IAIService {
    public String newQuestionChatExercise(Exercise exerciseI, ExerciseResolution solution, String answer) throws ApiConnectionException, BadInputException;

    public Float evaluateChatExercise(Exercise exerciseI, ExerciseRubric rubricI, ExerciseResolution solution) throws ApiConnectionException, BadInputException;

    public Float evaluateOpenAnswer(Exercise exerciseI, ExerciseRubric rubricI, ExerciseResolution solution) throws NotFoundException, ApiConnectionException, BadInputException;

    public JsonObject bypassBackend(String endpoint,String bodyS) throws ApiConnectionException;
}
