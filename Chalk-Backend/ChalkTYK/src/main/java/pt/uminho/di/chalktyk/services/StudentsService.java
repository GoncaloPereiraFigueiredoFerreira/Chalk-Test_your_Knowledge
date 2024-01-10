package pt.uminho.di.chalktyk.services;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.repositories.StudentDAO;
import pt.uminho.di.chalktyk.repositories.UserDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service
public class StudentsService implements IStudentsService{
    private final UserDAO userDAO;
    private final StudentDAO studentDAO;

    @Autowired
    public StudentsService(UserDAO userDAO, StudentDAO studentDAO) {
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
    }

    /**
     * Creates a student.
     *
     * @param student student properties
     * @return identifier of the new student
     * @throws BadInputException if any property of the student is not valid.
     */
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String createStudent(Student student) throws BadInputException {
        if (student == null)
            throw new BadInputException("Cannot create a student with a 'null' body.");

        student.setId(null); //set the identifier to null to avoid overwrite attacks
        student.setCourses(null); //set courses to null since a student when created should not have courses associated
        student.setInstitution(null); //set institution to null since a student when created should not have an institution associated

        // check for any property format errors
        String inputErrors = student.checkInsertProperties();
        if(inputErrors != null)
            throw new BadInputException(inputErrors);

        // check if email is not owned by another user
        if(userDAO.existsByEmail(student.getEmail()))
            throw new BadInputException("Could not create student: Email is already used by another user.");

        // Save user in database
        student = studentDAO.save(student);

        return student.getId();
    }

    @Override
    public Student getStudentById(String studentId) throws NotFoundException {
        Student s = studentDAO.findById(studentId).orElse(null);
        if (s != null)
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
        return studentDAO.existsById(studentId);
    }

    @Override
    public String getStudentIdByEmail(String email) {
        return studentDAO.getStudentIdByEmail(email);
    }
}
