package pt.uminho.di.chalktyk.models.users;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.courses.Course;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Student")
public class Student extends User {
	@Column(name="ID")
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@ManyToMany(targetEntity= Course.class, fetch = FetchType.LAZY)
	@JoinTable(name="Student_Course", joinColumns={ @JoinColumn(name="StudentID") }, inverseJoinColumns={ @JoinColumn(name="CourseID") })
	private ArrayList<Course> courses = new ArrayList<Course>();

	@ManyToOne(targetEntity= Institution.class, fetch=FetchType.LAZY)
	@JoinColumns(value={ @JoinColumn(name="InstitutionID", referencedColumnName="ID") })
	private Institution institution;

    @Override
    public String checkSubscription() {
        //TODO - checkSubscription
        return null;
    }

    @Override
    public String toString() {
        return "Student{" +
                    super.toString()
                + "}";
    }
}