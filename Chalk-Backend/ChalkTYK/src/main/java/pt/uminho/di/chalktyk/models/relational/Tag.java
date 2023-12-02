package pt.uminho.di.chalktyk.models.relational;

import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Tag")
public class Tag implements Serializable {
	public Tag() {
	}
	
	@Column(name="ID")
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Path")
	private String path;

	@Override
	public String toString() {
		return "Tag{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", path='" + path + '\'' +
				'}';
	}
}
