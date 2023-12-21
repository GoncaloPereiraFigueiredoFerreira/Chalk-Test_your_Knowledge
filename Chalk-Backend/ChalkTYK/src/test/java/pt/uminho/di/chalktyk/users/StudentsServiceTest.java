package pt.uminho.di.chalktyk.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.repositories.StudentDAO;
import pt.uminho.di.chalktyk.repositories.UserDAO;
import pt.uminho.di.chalktyk.services.IStudentsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@SpringBootTest
public class StudentsServiceTest {
    private final IStudentsService studentsService;
    private final UserDAO userDAO;
    private final StudentDAO studentDAO;

    @Autowired
    public StudentsServiceTest(IStudentsService studentsService, UserDAO userDAO, StudentDAO studentDAO) {
        this.studentsService = studentsService;
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
    }

    String createStudentAlex() throws BadInputException {
        String name = "Alex",
                photoPath = "google.com/some/path/to/image.png",
                email = "alex@gmail.com",
                description = "Descricao.";
        Student alex = new Student(null, name, photoPath, email, description);
        return studentsService.createStudent(alex);
    }

    @Test
    @Transactional
    void createStudentSuccess() throws BadInputException {
        String alex_id = createStudentAlex();

        // assert user exists and is instance of student
        User alexUser = userDAO.findById(alex_id).orElse(null);
        assert alexUser instanceof Student;

        // asserts that the information was stored correctly
        Student alexStudent = (Student) alexUser;
        assert alexStudent.getName().equals("Alex")
                && alexStudent.getDescription().equals("Descricao.")
                && alexStudent.getPhotoPath().equals("google.com/some/path/to/image.png")
                && alexStudent.getEmail().equals("alex@gmail.com");
    }

    @Test
    @Transactional
    void createStudentBadEmail(){
        // bad email format
        Student luis = new Student(null, "Luis", "google.com/some/path/to/image.png", "l-gmail.com", "description.");
        try{
            studentsService.createStudent(luis);
            assert false;
        }catch (BadInputException ignored){}
        assert !userDAO.existsByEmail("l-gmail.com");

        // email already used
        luis.setEmail("l@gmail.com");
        try{ studentsService.createStudent(luis);
        }catch (BadInputException bie){assert false;}
        Student luis2 = new Student(null, "Luis2", "google.com/some/path/to/image2.png", "l@gmail.com", "description2.");
        try{
            studentsService.createStudent(luis2);
            assert false;
        }catch (BadInputException ignored){
            assert true;
        }
    }

    @Test
    @Transactional
    void getStudent() throws BadInputException, NotFoundException {
        String id = createStudentAlex();
        Student s = studentsService.getStudentById(id);
        assert s != null;
    }

    @Test
    @Transactional
    void existsStudent() throws BadInputException {
        String id = createStudentAlex();
        assert studentsService.existsStudentById(id);
    }

    @Test
    @Transactional
    void updateStudent() throws BadInputException, NotFoundException {
        String id = createStudentAlex();
        Student s = studentsService.getStudentById(id);
        String name = "Alex Mart",
                photoPath = "somewhere.com/another/PhotoPath/img.jpg",
                email = "newmail@gmail.com",
                description = "new description";
        // assert that the student does not have the values that will be updated
        assert s != null && !s.getName().equals(name) && !s.getEmail().equals(email) && !s.getPhotoPath().equals(photoPath) && !s.getDescription().equals(description);

        Student newStudent = new Student(null, name, photoPath, email, description);
        studentsService.updateBasicStudentInformation(id, newStudent);
        s = studentsService.getStudentById(id);

        // checks that the fields were updated
        assert s != null && !s.getName().equals(name) && !s.getEmail().equals(email) && !s.getPhotoPath().equals(photoPath) && !s.getDescription().equals(description);
    }
}
