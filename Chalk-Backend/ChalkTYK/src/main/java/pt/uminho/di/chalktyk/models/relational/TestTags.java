package pt.uminho.di.chalktyk.models.relational;

import java.util.Set;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="TestTags")
//@IdClass(TestTagsPK.class)
public class TestTags implements Serializable {
	public TestTags() {
	}
	
	@Column(name="NExercises", nullable=false)
	private int nExercises;

	@EmbeddedId
	private TestTagsPK testTagsPK;

	//@PrimaryKeyJoinColumn
	//@ManyToOne(targetEntity=pt.uminho.di.chalktyk.models.relational.Tag.class, fetch=FetchType.LAZY)
	//@JoinColumns(value={ @JoinColumn(name="TagID", referencedColumnName="ID", nullable=false) })
	//private pt.uminho.di.chalktyk.models.relational.Tag tag;
	
	@Column(name="TagID", nullable=false, insertable=false, updatable=false)	
	//@Id
	private String tagId;
	
	//@PrimaryKeyJoinColumn
	//@ManyToOne(targetEntity=pt.uminho.di.chalktyk.models.relational.Test.class, fetch=FetchType.LAZY)
	//@JoinColumns(value={ @JoinColumn(name="TestID", referencedColumnName="ID", nullable=false) })
	//private pt.uminho.di.chalktyk.models.relational.Test test;
	
	@Column(name="TestID", nullable=false, insertable=false, updatable=false)	
	//@Id
	private String testId;
}
