package pt.uminho.di.chalktyk.models.nonrelational.courses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("courses")
public class Course {
	private String id;
	private String name;
	private String instituitionId;
	private String description;
	private String ownerId;
}