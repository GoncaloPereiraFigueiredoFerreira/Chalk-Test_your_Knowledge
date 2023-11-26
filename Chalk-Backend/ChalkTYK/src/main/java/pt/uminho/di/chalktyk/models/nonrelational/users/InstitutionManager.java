package pt.uminho.di.chalktyk.models.nonrelational.users;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@JsonTypeName("institution_manager")
public class InstitutionManager extends User {
	private String institutionId;
}