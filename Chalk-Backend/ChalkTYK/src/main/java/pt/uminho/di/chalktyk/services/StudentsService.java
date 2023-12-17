package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.uminho.di.chalktyk.models.nonrelational.users.Student;
import pt.uminho.di.chalktyk.models.nonrelational.users.User;
import pt.uminho.di.chalktyk.models.relational.StudentSQL;
import pt.uminho.di.chalktyk.repositories.nonrelational.UserDAO;
import pt.uminho.di.chalktyk.repositories.relational.StudentSqlDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service
public class StudentsService implements IStudentsService{
    @PersistenceContext
    private final EntityManager entityManager;
    private final UserDAO userDAO;
    private final StudentSqlDAO studentSqlDAO;

    @Autowired
    public StudentsService(EntityManager entityManager, UserDAO userDAO, StudentSqlDAO studentSqlDAO) {
        this.entityManager = entityManager;
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

        // check for any property format errors
        String inputErrors = student.checkInsertProperties();
        if(inputErrors != null)
            throw new BadInputException(inputErrors);

        // check if email is not owned by another user
        if(userDAO.existsByEmail(student.getEmail()))
            throw new BadInputException("Could not create student: Email is already used by another user.");

        // Save user in nosql database
        student = userDAO.save(student);

        // Create entry in sql database using the id created by the nosql database
        var studentSql = new StudentSQL(student.getId(), null, student.getName(), student.getEmail(), null);
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

    @Override
    public void updateBasicStudentInformation(String studentId, Student newStudent) throws NotFoundException, BadInputException {
        if(newStudent == null)
            throw new BadInputException("Cannot updated student information: Null student was given.");

        Student student = getStudentById(studentId);
        String newEmail = newStudent.getEmail(),
               newName  = newStudent.getName();

        // check for any property format errors
        String insertErrors = newStudent.checkInsertProperties();
        if(insertErrors != null)
            throw new BadInputException("Could not update student information:" + insertErrors);

        // if the email is to be changed, it cannot belong to any other user
        if(!student.getEmail().equals(newEmail) && userDAO.existsByEmail(newEmail))
            throw new BadInputException("Could not update student information: Email is already used by another user.");

        student.setName(newName);
        student.setEmail(newEmail);
        student.setDescription(newStudent.getDescription());
        student.setPhotoPath(newStudent.getPhotoPath());

        userDAO.save(student);
    }
}
