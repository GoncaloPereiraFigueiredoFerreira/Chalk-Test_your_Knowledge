package pt.uminho.di.chalktyk.models.tests;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TestTagPK implements Serializable {
	@ManyToOne(targetEntity= Tag.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TagID", referencedColumnName="ID", nullable=false) })
	private Tag tag;
	
	@ManyToOne(targetEntity= Test.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TestID", referencedColumnName="ID", nullable=false) })
	private Test test;
}
