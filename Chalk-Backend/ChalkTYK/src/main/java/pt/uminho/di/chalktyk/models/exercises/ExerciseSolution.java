package pt.uminho.di.chalktyk.models.exercises;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseSolution {
	@Id
	@Column(name = "ID")
	private String id;

	@Type(JsonBinaryType.class)
	@Column(name = "Data", columnDefinition = "jsonb")
	private ExerciseResolutionData data;
	
	public void verifyInsertProperties() throws BadInputException {
        if(data==null)
            throw new BadInputException("The solution's exercise resolution data cannot be null");
        data.verifyInsertProperties();
    }
}