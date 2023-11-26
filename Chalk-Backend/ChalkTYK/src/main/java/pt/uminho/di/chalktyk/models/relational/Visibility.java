package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Visibility")
public class Visibility implements Serializable {
	public Visibility() {
	}
	
	@Column(name="ID")
	@Id	
	private String type;
}
