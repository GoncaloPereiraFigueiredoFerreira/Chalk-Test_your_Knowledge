package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.nonrelational.users.Student;
import pt.uminho.di.chalktyk.models.nonrelational.users.User;
import pt.uminho.di.chalktyk.repositories.nonrelational.UserDAO;
import pt.uminho.di.chalktyk.repositories.relational.StudentSqlDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service
public class StudentsService implements IStudentsService{
    private final UserDAO userDAO;
    private final StudentSqlDAO studentSqlDAO;

    @Autowired
    public StudentsService(UserDAO userDAO, StudentSqlDAO studentSqlDAO) {
        this.userDAO = userDAO;
        this.studentSqlDAO = studentSqlDAO;
    }

    /**
     * Creates a student.
     *
     * @param student student properties
     * @return identifier of the new student
     * @throws BadInputException if any property of the student is not valid.
     */
    @Override
    @Transactional(rollbackFor = {BadInputException.class})
    public String createStudent(Student student) throws BadInputException {
        if (student == null)
            throw new BadInputException("Cannot create a student with a 'null' body.");

        //set the identifier to null to avoid overwrite attacks
        student.setId(null);

        String inputErrors = student.checkProperties();
        if(inputErrors != null)
            throw new BadInputException(inputErrors);

        // Save user in nosql database
        student = userDAO.save(student);

        // Create entry in sql database using the id created by the nosql database
        var studentSql = new pt.uminho.di.chalktyk.models.relational.Student(student.getId(), null, student.getName(), student.getEmail(), null);
        studentSqlDAO.save(studentSql);

        return student.getId();
    }

    @Override
    public Student getStudentById(String studentId) throws NotFoundException {
        User u = userDAO.findById(studentId).orElse(null);
        if (u instanceof Student s)
            return s;
        else
            throw new NotFoundException("No student with the given id was found.");
    }

    /**
     * Checks if a student exists with the given id.
     *
     * @param studentId identifier of the student
     * @return 'true' if a student exists with the given id
     */
    @Override
    public boolean existsStudentById(String studentId) {
        return studentSqlDAO.existsById(studentId);
    }
}
