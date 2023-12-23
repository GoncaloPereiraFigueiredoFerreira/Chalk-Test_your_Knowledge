package pt.uminho.di.chalktyk.models.exercises.fill_the_blanks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolutionData;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonTypeName("FTB")
public class FillTheBlanksData extends ExerciseResolutionData implements Serializable {
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

	@Override
	public ExerciseResolutionData clone() {
		return new FillTheBlanksData(new ArrayList<>(fillings));
	}
}