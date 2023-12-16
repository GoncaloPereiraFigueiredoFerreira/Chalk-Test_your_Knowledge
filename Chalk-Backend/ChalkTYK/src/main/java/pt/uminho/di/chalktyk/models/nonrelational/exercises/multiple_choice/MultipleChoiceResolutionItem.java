package pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MultipleChoiceResolutionItem {
	private Float points;
	private String justification;
	private Boolean value;

	/*
	mctype syntax:
		-1X -> multiple choice
		-2X -> true or false

		-X0 -> no justification
		-X1 -> justify all items
		-X2 -> justify false/unmarked items
		-X3 -> justify true/marked items
	*/
	public void verifyProperties(Mctype mcType) throws BadInputException {
		if(mcType.getCode()%10==0 || mcType.getCode()%10==2 && value || mcType.getCode()%10==3 && !value){
			if (justification!=null) //These are the cases where justification is not null, and it's not needed
				throw new BadInputException("Multiple choice resolution was required to be null but it isnt");
		} else if (justification==null) { //These are the cases where justification is null, but it is required
			throw new BadInputException("Multiple choice resolution was required but it was null");
		}
	}

	public void verifyInsertProperties() throws BadInputException {
		if(points != 0.0F)
			throw new BadInputException("Multiple choice resolution item points must be 0.");
	}

	public MultipleChoiceResolutionItem clone(){
		return new MultipleChoiceResolutionItem(points,justification,value);
	}

	public boolean equals(MultipleChoiceResolutionItem multipleChoiceResolutionItem){
        return Objects.equals(multipleChoiceResolutionItem.getJustification(), justification) &&
                Objects.equals(multipleChoiceResolutionItem.getPoints(), points) &&
                multipleChoiceResolutionItem.getValue() == value;
	}

}