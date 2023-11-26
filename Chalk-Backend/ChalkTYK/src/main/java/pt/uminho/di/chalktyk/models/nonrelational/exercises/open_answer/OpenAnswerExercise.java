package pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;

@Document(collection = "exercises")
@JsonTypeName("OA")
public class OpenAnswerExercise extends ConcreteExercise {
}