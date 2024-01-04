package pt.uminho.di.chalktyk.models.exercises.fill_the_blanks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import pt.uminho.di.chalktyk.models.exercises.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("FTB")
@JsonTypeName("FTB")
public class FillTheBlanksExercise extends Exercise {
	// between each text segment a blank is supposed to be filled.
	@Type(JsonBinaryType.class)
	@Column(name = "TextSegments", columnDefinition = "jsonb")
	private List<String> textSegments;

	@Override
	public void verifyResolutionProperties(ExerciseResolutionData exerciseResolutionData) throws BadInputException {
		if(exerciseResolutionData == null || !exerciseResolutionData.getType().equals(this.getExerciseType()))
			throw new BadInputException("Exercise solution/resolution does not match exercise type (fill the blanks).");
		FillTheBlanksData fillTheBlanksData = (FillTheBlanksData) exerciseResolutionData;
		fillTheBlanksData.verifyInsertProperties();
		if(getNumberOfBlanks() != fillTheBlanksData.getFillings().size())
			throw new BadInputException("Exercise solution/resolution does not have enough fillings. The number of fillings should equal the number of text segments minus 1.");
	}
	@Override
	public void verifyRubricProperties(ExerciseRubric rubric) throws BadInputException {
		if(rubric == null || !rubric.getType().equals(this.getExerciseType()))
			throw new BadInputException("Exercise rubric does not match exercise type (fill the blanks).");
		rubric.verifyProperties();
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
	public void verifyInsertProperties() throws BadInputException {
		for (String textSegment:textSegments){
			if(textSegment==null)
				throw new BadInputException("The text segments cannot be null.");
		}
		super.verifyInsertProperties();
	}

	@JsonIgnore
	private int getNumberOfBlanks(){
		return textSegments.size() - 1;
	}

	@JsonIgnore
	private float getPointsPerBlank(){
		return 100.0f / getNumberOfBlanks();
	}

	@Override
	public Exercise cloneExerciseDataOnly() {
		var exercise = new FillTheBlanksExercise();
		try { copyExerciseDataOnlyTo(exercise); }
		catch (BadInputException ignored){}
		return exercise;
	}

	@Override
	public void copyExerciseDataOnlyTo(Exercise exercise) throws BadInputException {
		if(exercise == null || !exercise.getExerciseType().equals(this.getExerciseType()))
			throw new BadInputException("Exercise is not of the same type.");
		FillTheBlanksExercise ftbe = (FillTheBlanksExercise) exercise;
		_copyExerciseDataOnlyTo(ftbe);
		ftbe.setTextSegments(new ArrayList<>(textSegments));
	}

	@Override
	public boolean equalsDataOnly(Object o){
		if (!super.equalsDataOnly(o)) return false;
		FillTheBlanksExercise that = (FillTheBlanksExercise) o;
		return Objects.equals(textSegments, that.textSegments);
	}

	@Override
	public boolean equalsWithoutAssociations(Object o) {
		if (!super.equalsWithoutAssociations(o)) return false;
		FillTheBlanksExercise that = (FillTheBlanksExercise) o;
		return Objects.equals(textSegments, that.textSegments);
	}
}