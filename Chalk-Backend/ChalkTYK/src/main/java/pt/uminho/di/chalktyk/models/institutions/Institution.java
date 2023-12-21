package pt.uminho.di.chalktyk.models.institutions;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Institution")
public class Institution {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name="ID")
	private String name;

	@Column(name="Description")
	private String description;

	@Column(name="LogoPath")
	private String logoPath;

	// TODO: there's no subscription
	@Override
	public String toString() {
		return "Institution{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", logoPath='" + logoPath + '\'' +
				//", subscription=" + subscription +
				'}';
	}
}