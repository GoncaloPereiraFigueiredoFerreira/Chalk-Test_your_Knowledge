package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

public interface IStudentsService {

    /**
     * Creates a student.
     * @param student student properties
     * @return identifier of the new student
     * @throws BadInputException if any property of the student is not valid.
     */
    String createStudent(Student student) throws BadInputException;

    /**
     * Gets student
     * @param studentId identifier of the student
     * @return student
     * @throws NotFoundException if no student was found with the given id
     */
    Student getStudentById(String studentId) throws NotFoundException;

    /**
     * Checks if a student exists with the given id.
     * @param studentId identifier of the student
     * @return 'true' if a student exists with the given id
     */
    boolean existsStudentById(String studentId);

    String getStudentIdByEmail(String email);
}
