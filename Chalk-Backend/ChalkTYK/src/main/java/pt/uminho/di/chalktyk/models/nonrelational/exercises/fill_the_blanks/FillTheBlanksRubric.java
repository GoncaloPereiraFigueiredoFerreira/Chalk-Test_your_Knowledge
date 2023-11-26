package pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;

@Document(collection = "exercises_rubrics")
@JsonTypeName("FTB")
public class FillTheBlanksRubric extends ExerciseRubric {
	private Float fillingCotation;
	private Float penalty;
}