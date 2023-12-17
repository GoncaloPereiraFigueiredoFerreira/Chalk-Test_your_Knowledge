package pt.uminho.di.chalktyk.models.nonrelational.exercises.fill_the_blanks;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.List;

@Document(collection = "exercises")
@JsonTypeName("FTB")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FillTheBlanksExercise extends ConcreteExercise {
	private List<String> textSegments;
	@Override
	public void verifyResolutionProperties(ExerciseResolutionData exerciseResolutionData) throws BadInputException {
		if(!(exerciseResolutionData instanceof FillTheBlanksData fillTheBlanksData))
			throw new BadInputException("Exercise resolution does not match exercise type (fill the blanks).");
		fillTheBlanksData.verifyInsertProperties();
		if(numberOfAnswers()!=fillTheBlanksData.getFillings().size())
			throw new BadInputException("Exercise resolution fillings do not match exercise text segments (fillings size = (text segments size -1)).");
	}
	@Override
	public void verifyRubricProperties(ExerciseRubric rubric) throws BadInputException {
		if(!(rubric instanceof FillTheBlanksRubric fillTheBlanksRubric))
			throw new BadInputException("Exercise rubric does not match exercise type (fill the blanks).");

		if(numberOfAnswers()*fillTheBlanksRubric.getFillingPoints()!=super.getPoints())
			throw new BadInputException("Exercise rubric maximum points (fillingPoints * numberOfAnswers) must match the exercise points.");
		fillTheBlanksRubric.verifyProperties();
	}

	@Override
	public String getExerciseType() {
		return "FTB";
	}

	/**
     * Evaluates the resolution of an exercise.
     *
     * @param resolution resolution data that will be evaluated
     * @param solution   solution of the exercise
     * @param rubric     rubric of the exercise
     * @return points to be attributed to the resolution
     * @throws UnauthorizedException if the resolution cannot be evaluated automatically.
     */
	@Override
	public ExerciseResolution automaticEvaluation(ExerciseResolution resolution, ExerciseSolution solution, ExerciseRubric rubric) throws UnauthorizedException {
		// TODO - future work
		throw new UnauthorizedException("Automatic evaluation for fill the blanks exercises is not implemented yet.");
	}


	@Override
	public void verifyProperties() throws BadInputException {
		for (String textSegment:textSegments){
			if(textSegment==null)
				throw new BadInputException("The text segments cannot be null.");
		}
		super.verifyInsertProperties();
	}

	private int numberOfAnswers(){
		return textSegments.size()-1;
	}

}