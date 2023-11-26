package pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Item;

import java.util.*;

@Document(collection = "exercises")
@JsonTypeName("MC")
public class MultipleChoiceExercise extends ConcreteExercise {

	private List<Item> items;
	private int mctype;

}