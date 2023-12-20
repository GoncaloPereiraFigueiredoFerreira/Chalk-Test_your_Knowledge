package pt.uminho.di.chalktyk.models.tests;

import pt.uminho.di.chalktyk.models.users.Student;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.type.descriptor.jdbc.JsonJdbcType;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TestResolution")
public class TestResolution {
	@Column(name="ID")
	@Id	
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(name="StartDate")
	private LocalDateTime startDate;

	@Column(name="SubmissionDate")
	private LocalDateTime submissionDate;

	@Column(name="SubmissionNr")
	private int submissionNr;

	@Column(name="TotalPoints")
	private Float totalPoints;

	@ManyToOne(targetEntity= Student.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="StudentID", referencedColumnName="ID", nullable=false) })
	private Student student;

	@ManyToOne(targetEntity= Test.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="TestID", referencedColumnName="ID", nullable=false) })
	private Test test;

	@Column(name="Status")
	private TestResolutionStatus status;

	@Type(JsonJdbcType.class)
    @Column(columnDefinition = "Groups")
	private List<TestResolutionGroup> groups;
}