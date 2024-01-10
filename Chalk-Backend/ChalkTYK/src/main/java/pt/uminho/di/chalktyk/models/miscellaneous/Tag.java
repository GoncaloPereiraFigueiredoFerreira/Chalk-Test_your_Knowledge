package pt.uminho.di.chalktyk.models.miscellaneous;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Tag")
public class Tag {
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

	public Tag clone(){
		return new Tag(id, name, path);
	}
}