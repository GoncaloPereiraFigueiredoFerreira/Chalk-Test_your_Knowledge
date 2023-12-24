package pt.uminho.di.chalktyk.models.tests;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

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

	@Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb", name = "Groups")
	private Map<Integer, TestResolutionGroup> groups;

	public void updateSum() {
		totalPoints = groups.values().stream()
				.map(TestResolutionGroup::getGroupPoints)
				.filter(Objects::nonNull) // Filter out null values
				.reduce(Float::sum)
				.orElse(0.0f);
	}

	public void verifyProperties() throws BadInputException {
		// check submission nr
		if (submissionNr < 0)
			throw new BadInputException ("Cannot create test resolution: submission nr must be non-negative");

		// check groups
		if (totalPoints != null){
			Float points = 0.0F;
			for (TestResolutionGroup trg: groups.values()){
				points += trg.getGroupPoints();
			}

			if (totalPoints != points)
				throw new BadInputException ("Cannot create test resolution: total points don't correspond with group points");
		}
	}
}