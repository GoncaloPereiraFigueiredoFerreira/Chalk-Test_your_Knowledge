package pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;

import java.util.List;

@Document(collection = "exercises")
@JsonTypeName("FTB")
public class FillTheBlanksExercise extends ConcreteExercise {
	private List<String> textSegments;
}