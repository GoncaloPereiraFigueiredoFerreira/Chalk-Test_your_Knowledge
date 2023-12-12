package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Tag")
public class TagSQL implements Serializable {
	public TagSQL() {
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
