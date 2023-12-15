package pt.uminho.di.chalktyk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.uminho.di.chalktyk.models.nonrelational.courses.Course;
import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.models.nonrelational.users.Specialist;
import pt.uminho.di.chalktyk.services.ICoursesService;
import pt.uminho.di.chalktyk.services.IInstitutionsService;
import pt.uminho.di.chalktyk.services.ISpecialistsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

import java.util.List;

@SpringBootTest
public class TestCourses {

    private final IInstitutionsService institutionsService;
    private final ICoursesService coursesService;
    private final ISpecialistsService specialistsService;

    @Autowired
    public TestCourses(IInstitutionsService institutionsService, ICoursesService coursesService, ISpecialistsService specialistsService) {
        this.institutionsService = institutionsService;
        this.coursesService = coursesService;
        this.specialistsService = specialistsService;
    }

    @Test
    void createCourse() throws BadInputException, NotFoundException {
        Institution institution = new Institution("U. Minho", "Qualquer coisa...", "uminho.pt/images/logo.png", null);
        institutionsService.createInstitution(institution);
        String institutionId = institution.getName();
        Specialist specialist = new Specialist(null, "Ze", "ze.png", "ze@hotmail.com", "Lindo", null);
        specialistsService.createSpecialist(specialist);
        institutionsService.addSpecialistsToInstitution(institutionId, List.of(specialist.getId()));
        Course course = new Course(null, "Basic Japanese", institutionId, "hiragana, katakana and basic sentences.", specialist.getId());
        String courseId = coursesService.createCourse(course);
        System.out.println(courseId);
    }

    @Test
    void getInstitution() throws BadInputException, NotFoundException {
        Specialist specialist = specialistsService.getSpecialistById("656b64414e32db298cccffd5");
        System.out.println(specialist);
    }
}
