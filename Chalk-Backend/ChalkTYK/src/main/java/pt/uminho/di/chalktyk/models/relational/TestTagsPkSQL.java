package pt.uminho.di.chalktyk.models.relational;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class TestTagsPkSQL implements Serializable {
	@ManyToOne(targetEntity= TagSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TagID", referencedColumnName="ID", nullable=false) })
	private TagSQL tag;
	
	@ManyToOne(targetEntity= TestSQL.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TestID", referencedColumnName="ID", nullable=false) })
	private TestSQL test;
}
