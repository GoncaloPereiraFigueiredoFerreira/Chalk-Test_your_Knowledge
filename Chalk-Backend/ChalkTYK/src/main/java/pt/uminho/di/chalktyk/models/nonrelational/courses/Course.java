package pt.uminho.di.chalktyk.models.nonrelational.courses;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("courses")
public class Course {
	private String id;
	private String name;
	private String instituitionId;
	private String description;

}