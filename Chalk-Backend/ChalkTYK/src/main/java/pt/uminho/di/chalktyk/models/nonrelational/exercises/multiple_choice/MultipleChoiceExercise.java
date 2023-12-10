package pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseRubric;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Item;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.*;

@Document(collection = "exercises")
@JsonTypeName("MC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MultipleChoiceExercise extends ConcreteExercise {

	private List<Item> items;
	private int mctype;
	/*
	mctype syntax:
		-1X -> multiple choice
		-2X -> true or false

		-X0 -> no justification
		-X1 -> justify all items
		-X2 -> justify false/unmarked items
		-X3 -> justify true/marked items
	*/


	@Override
	public void verifyResolutionProperties(ExerciseResolutionData exerciseResolutionData) throws BadInputException {
		if(!(exerciseResolutionData instanceof MultipleChoiceData multipleChoiceData))
			throw new BadInputException("Exercise resolution does not match exercise type (multiple choice).");
		if(multipleChoiceData.getItemResolutions().size()!=items.size())
			throw new BadInputException("Exercise resolution items size do not match exercise total options.");
		multipleChoiceData.verifyProperties(mctype);
	}

	@Override
	public void verifyRubricProperties(ExerciseRubric rubric) throws BadInputException {
		if(!(rubric instanceof MultipleChoiceRubric multipleChoiceRubric))
			throw new BadInputException("Exercise rubric does not match exercise type (multiple choice).");
		multipleChoiceRubric.verifyProperties();
	}

	@Override
	public String getExerciseType() {
		return "MC";
	}

	@Override
	public void verifyProperties() throws BadInputException {
		for (Item item:items)
			item.VerifyProperties();
		super.verifyInsertProperties();
	}

	private boolean matchesTrueOrFalse(String string){
		return string.equals("false") || string.equals("true");
	}
}