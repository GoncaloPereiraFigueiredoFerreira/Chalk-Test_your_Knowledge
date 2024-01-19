package pt.uminho.di.chalktyk.models.tests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.*;
import lombok.*;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.services.exceptions.ForbiddenException;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("autoEval")
@JsonTypeName("autoEval")
public class AutoEvaluationTest extends Test {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="StudentID", referencedColumnName = "ID")
    @JsonIgnore
    private Student student;

    @Column(name = "StudentID", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private String studentId;

    public AutoEvaluationTest(String id, String title, String globalInstructions, Float globalPoints, String conclusion, LocalDateTime creationDate, LocalDateTime publishDate, Student student, Visibility visibility, List<TestGroup> groups) {
        setId(id);
        setTitle(title);
        setGlobalInstructions(globalInstructions);
        setGlobalPoints(globalPoints);
        setConclusion(conclusion);
        setCreationDate(creationDate);
        setPublishDate(publishDate);
        setStudent(student);
        setVisibility(visibility);
        setSpecialist(null);
        setCourse(null);
        setInstitution(null);
        setGroups(groups);
    }

    public void setStudent(Student student) {
        this.student = student;
        this.studentId = student != null ? student.getId() : null;
    }

    @Override
    public void setSpecialist(Specialist specialist) {
        if(specialist != null)
            throw new RuntimeException(new ForbiddenException("Cannot set specialist of an auto evaluation test."));
    }

    @Override
    public void setInstitution(Institution institution) {
        if(institution != null)
            throw new RuntimeException(new ForbiddenException("Cannot set institution of an auto evaluation test."));
    }

    @Override
    public void setCourse(Course course) {
        if(course != null)
            throw new RuntimeException(new ForbiddenException("Cannot set course of an auto evaluation test."));
    }
}
