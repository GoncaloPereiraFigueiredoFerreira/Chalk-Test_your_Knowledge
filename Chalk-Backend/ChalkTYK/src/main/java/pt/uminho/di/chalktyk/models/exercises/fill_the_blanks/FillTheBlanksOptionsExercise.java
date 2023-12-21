package pt.uminho.di.chalktyk.models.exercises.fill_the_blanks;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonTypeName("FTBO")
@DiscriminatorValue("FTBO")
public class FillTheBlanksOptionsExercise extends FillTheBlanksExercise {
	private List<List<String>> options;

	@Override
	public void verifyResolutionProperties(ExerciseResolutionData exerciseResolutionData) throws BadInputException {
		super.verifyResolutionProperties(exerciseResolutionData);
		if(!(exerciseResolutionData instanceof FillTheBlanksData fillTheBlanksData)){
			throw new BadInputException("Exercise resolution does not match exercise type (fill the blanks options).");
		}
		List<String> fillings = fillTheBlanksData.getFillings();
		for (int i =0;i<options.size();i++){
			if(!options.get(i).contains(fillings.get(i)))
				throw new BadInputException("Exercise resolution fillings response number" + i+1 + "is not in the exercise options list");
		}
	}

	@Override
	public void verifyProperties() throws BadInputException {
		if(options.size()!=(super.getTextSegments().size()-1))
			throw new BadInputException("The options size must be equals to the number of segments minus 1.");
		for (List<String> opts:options){
			for (String option:opts) {
				if (option == null)
					throw new BadInputException("The fill the blanks options cannot be null.");
			}
		}
		super.verifyInsertProperties();
	}
}