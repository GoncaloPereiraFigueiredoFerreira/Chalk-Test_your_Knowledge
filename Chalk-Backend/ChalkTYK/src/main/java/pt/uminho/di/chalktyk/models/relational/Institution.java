package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Institution")
public class Institution implements Serializable {
	public Institution() {
	}
	
	@Column(name="ID")
	@Id	
	private String id;
}
