package pt.uminho.di.chalktyk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.models.nonrelational.users.Specialist;
import pt.uminho.di.chalktyk.models.nonrelational.users.Student;
import pt.uminho.di.chalktyk.services.IInstitutionsService;
import pt.uminho.di.chalktyk.services.ISpecialistsService;
import pt.uminho.di.chalktyk.services.IStudentsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;

@SpringBootTest
public class Seed {
    private final IInstitutionsService institutionsService;
    private final IStudentsService studentsService;
    private final ISpecialistsService specialistsService;

    @Autowired
    public Seed(IInstitutionsService institutionsService, IStudentsService studentsService, ISpecialistsService specialistsService){
        this.institutionsService = institutionsService;
        this.studentsService = studentsService;
        this.specialistsService = specialistsService;
    }

    @Test 
    public void seed() throws BadInputException{
        addStudents();
        addSpecialists();
    }

    @Test
    public void addInstitution() throws BadInputException {
        Institution inst = new Institution("UM", "não quero", "some_image.jpg", null);
        institutionsService.createInstitution(inst);
    }

    @Test
    public void addStudents() throws BadInputException {
        Student s1 = new Student("asdasd1", "Student #1", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "no1@gmail.com", 
                "none #1", null);
        Student s2 = new Student("asdasd2", "Student #2", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "no2@gmail.com", 
                "none #2", null);
        Student s3 = new Student("asdasd3", "Student #3", "https://i.kym-cdn.com/photos/images/newsfeed/001/718/713/854.jpg", "no3@gmail.com", 
                "none #3", null);
        studentsService.createStudent(s1);
        studentsService.createStudent(s2);
        studentsService.createStudent(s3);
    }

        @Test
    public void addSpecialists() throws BadInputException {
        // String id, String name, String photoPath, String email, String description
        Specialist s1 = new Specialist("asdasd", "Specialist #1", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg", 
            "spe1@gmail.com", "#1", null);
        Specialist s2 = new Specialist("asdasd", "Specialist #2", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg", 
            "spe2@gmail.com", "#2", null);
        Specialist s3 = new Specialist("asdasd", "Specialist #3", "https://memes.co.in/memes/update/uploads/2021/12/InShot_20211209_222013681.jpg", 
            "spe3@gmail.com", "#3", null);

        specialistsService.createSpecialist(s1);
        specialistsService.createSpecialist(s2);
        specialistsService.createSpecialist(s3);
    }
}
