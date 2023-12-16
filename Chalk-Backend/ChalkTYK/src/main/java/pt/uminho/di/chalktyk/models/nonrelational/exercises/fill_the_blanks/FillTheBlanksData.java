package pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.open_answer.OpenAnswerData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FillTheBlanksData extends ExerciseResolutionData {

	private List<String> fillings;

	public void verifyInsertProperties() throws BadInputException {
		for (String fill:fillings) {
			if(fill==null)
				throw new BadInputException("Fillings from fill the blank data cannot bet null");
		}
	}

	@Override
	public boolean equals(ExerciseResolutionData exerciseResolutionData) {
		if(!(exerciseResolutionData instanceof FillTheBlanksData fillTheBlanksData))
			return false;
		if(fillTheBlanksData.getFillings().size()!=fillings.size())
			return false;
		for (int i=0;i<fillings.size();i++){
			if(!fillings.get(i).equals(fillTheBlanksData.getFillings().get(i)))
				return false;
		}
		return true;
	}

}