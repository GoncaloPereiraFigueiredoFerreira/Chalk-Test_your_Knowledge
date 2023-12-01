package pt.uminho.di.chalktyk.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.uminho.di.chalktyk.models.nonrelational.users.Student;
import pt.uminho.di.chalktyk.services.IStudentsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@SpringBootTest
public class TestStudents {
    IStudentsService studentsService;

    @Autowired
    public TestStudents(IStudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @Test
    void createStudent() throws BadInputException {
        // No error creating
        Student alex = new Student(null, "Alex", "google.com/some/path/to/image.png", "alex@gmail.com", "Descricao.", null);
        String alex_id = studentsService.createStudent(alex);
        System.out.println("id alex: " + alex_id);
        assert true;

        // Bad name
        try{
            Student noname = new Student(null, "", "google.com/some/path/to/image2.png", "noname@gmail.com", "Descricao2.", null);
            studentsService.createStudent(noname);
            assert false;
        }catch (BadInputException bie){
            assert true;
            System.out.println(bie.getMessage());
        }

        // Bad email
        try{
            Student bademail = new Student(null, "Luis", "google.com/some/path/to/image3.png", "luis.gmail.com", "Descricao3.", null);
            studentsService.createStudent(bademail);
            assert false;
        }catch (BadInputException bie){
            assert true;
            System.out.println(bie.getMessage());
        }
    }

    @Test
    void getStudent() throws NotFoundException{
        Student alex = studentsService.getStudentById("656a1fddea74d266176115d8");
        System.out.println(alex);
        assert alex != null;
    }

}
