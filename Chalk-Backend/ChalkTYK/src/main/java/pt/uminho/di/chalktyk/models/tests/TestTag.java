package pt.uminho.di.chalktyk.models.tests;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="TestTags")
public class TestTag {
	@Column(name="NExercises", nullable=false)
	private int nExercises;

	@EmbeddedId
	private TestTagPK testTagPK;
}