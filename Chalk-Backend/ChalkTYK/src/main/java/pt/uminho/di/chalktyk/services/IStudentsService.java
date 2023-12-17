package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.models.nonrelational.users.Student;
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

    /**
     * Update the basic information of a student.
     * The fields that can be updated are name, email, photoPath and description.
     * All this fields should be sent, even the ones that were not updated.
     * For example, if photoPath is null, then the field will be updated as null.
     *
     * @param studentId  identifier of a student
     * @param newStudent body of the basic properties of a student
     * @throws NotFoundException if the student does not exist
     * @throws BadInputException if any of the fields is not valid
     */
    void updateBasicStudentInformation(String studentId, Student newStudent) throws NotFoundException, BadInputException;
}
