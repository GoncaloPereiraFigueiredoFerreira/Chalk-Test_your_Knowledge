package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Institution")
public class InstitutionSQL implements Serializable {
	public InstitutionSQL() {
	}
	
	@Column(name="ID")
	@Id	
	private String id;
}
