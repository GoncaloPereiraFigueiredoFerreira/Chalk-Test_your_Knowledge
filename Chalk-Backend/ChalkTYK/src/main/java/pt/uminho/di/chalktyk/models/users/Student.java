package pt.uminho.di.chalktyk.models.users;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
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
@DiscriminatorValue("Student")
public class Student extends User {

    public Student(String id, String name, String photoPath, String email, String description, Set<Course> courses, Institution institution) {
        super(id, name, photoPath, email, description);
        this.courses = courses;
        this.institution = institution;
    }

    public Student(String id, String name, String photoPath, String email, String description) {
        super(id, name, photoPath, email, description);
        this.courses = null;
        this.institution = null;
    }

    @ManyToMany(targetEntity= Course.class, fetch = FetchType.LAZY)
	@JoinTable(name="Student_Course", joinColumns={ @JoinColumn(name="StudentID") }, inverseJoinColumns={ @JoinColumn(name="CourseID") })
	private Set<Course> courses;

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