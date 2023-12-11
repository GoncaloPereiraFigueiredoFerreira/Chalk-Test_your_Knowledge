package pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.*;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.OpenAnswerExercise;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.*;
import java.util.stream.IntStream;

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
		if(multipleChoiceRubric.getMaxCotationSum()!=super.getCotation())
			throw new BadInputException("Exercise rubric maximum cotation (cotation*number of items) must be equals to exercise cotation");
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

	/**
	 * Updates an exercise. If an object is 'null' than it is considered that it should remain the same.
	 * @param exercise     new exercise body
	 */
	@Override
	public boolean updateExercise(Exercise exercise) throws UnauthorizedException {
		if(!(exercise instanceof MultipleChoiceExercise mce))
			throw new UnauthorizedException("The type of the exercise cannot be changed");
		boolean updated = super.updateExercise(exercise);
		List<Item> mceItems = mce.getItems();
		if(mceItems!=null){
			if(mceItems.size()!=items.size()){
				items= mceItems.stream().map(Item::clone).toList();
				updated=true;
			}
			else {
				for (int i=0;i<items.size();i++){
					if(mceItems.get(i)!=null){
						Item item = items.get(i);
						if(item.updateItem(mceItems.get(i))){
							updated=true;
							items.set(i,item);
						}
					}
				}
			}
		}
		if(mce.getMctype()!=null){
			mctype=mce.getMctype();
			updated=true;
		}
		return updated;
	}

	private boolean matchesTrueOrFalse(String string){
		return string.equals("false") || string.equals("true");
	}
}