package pt.uminho.di.chalktyk.models.institutions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="Institution")
public class Institution {
	@Column(name="ID")
	@Id	
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