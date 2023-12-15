package pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.*;

@Document(collection = "exercises")
@JsonTypeName("MC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MultipleChoiceExercise extends ConcreteExercise {
	private List<Item> items;
	private Mctype mctype;


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
		if(multipleChoiceRubric.getJustificationsRubrics().size() != items.size())
			throw new BadInputException("Exercise rubric justification size must be the same as the number of items");
		if(multipleChoiceRubric.getMaxPointsSum()!=super.getPoints())
			throw new BadInputException("Exercise rubric maximum points (points*number of items) must be equals to exercise points");
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

	public void updateIdsBySolution(List<Integer> ids) throws BadInputException {
		if(items.size()!=ids.size())
			throw new BadInputException("");
		for (int i=0;i<items.size();i++){
			Item item = items.get(i);
			item.setId(ids.get(i));
			items.set(i,item);
		}
	}

	private boolean matchesTrueOrFalse(String string){
		return string.equals("false") || string.equals("true");
	}
}