package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class TestTagsPK implements Serializable {
	@ManyToOne(targetEntity=pt.uminho.di.chalktyk.models.relational.Tag.class, fetch=FetchType.LAZY)	
	@JoinColumns(value={ @JoinColumn(name="TagID", referencedColumnName="ID", nullable=false) })
	private pt.uminho.di.chalktyk.models.relational.Tag tag;
	
	@ManyToOne(targetEntity=pt.uminho.di.chalktyk.models.relational.Test.class, fetch=FetchType.LAZY)	
	@JoinColumns(value={ @JoinColumn(name="TestID", referencedColumnName="ID", nullable=false) })
	private pt.uminho.di.chalktyk.models.relational.Test test;
}
