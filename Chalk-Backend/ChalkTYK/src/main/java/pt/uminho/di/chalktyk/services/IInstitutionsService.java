package pt.uminho.di.chalktyk.services;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.nonrelational.courses.Course;
import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.models.nonrelational.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.nonrelational.users.Specialist;
import pt.uminho.di.chalktyk.models.nonrelational.users.Student;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

import java.util.List;

public interface IInstitutionsService {

    /**
     * Get list of institutions
     *
     * @param page
     * @param itemsPerPage maximum items in a page
     * @return a list of institutions
     **/
    Page<Institution> getInstitutions(Integer page, Integer itemsPerPage);

    /**
     * Get institution by ID
     *
     * @param institutionId
     * @return institution the given ID
     **/
    Institution getInstitutionById(String institutionId) throws NotFoundException;

    /**
     * Checks if an institution exists.
     * @param institutionId identifier of the institution
     * @return 'true' if the institution with the given identifier exists
     */
    boolean existsInstitutionById(String institutionId);

    /**
    * Update institution by ID
    * @param institutionId
    * @param institution
    **/
    void updateInstitutionById(String institutionId, Institution institution);

    /**
    * Create institution
    * @param institution
    **/
    void createInstitution(Institution institution) throws BadInputException;

    /**
     * Add specialists to institution
     * @param institutionId identifier of the institution
     * @param specialistsIds list of specialists identifiers
     */
    void addSpecialistsToInstitution(String institutionId, List<String> specialistsIds) throws NotFoundException;

    /**
     * Add students to institution.
     * @param institutionId identifier of the institution
     * @param studentsIds list of students identifiers
     */
    void addStudentsToInstitution(String institutionId, List<String> studentsIds) throws NotFoundException;

    /**
     * Remove specialists from institution
     * @param institutionId identifier of the institution
     * @param specialistsIds list of specialists identifiers
     */
    void removeSpecialistsFromInstitution(String institutionId, List<String> specialistsIds) throws NotFoundException;

    /**
     * Remove students from institution.
     * @param institutionId identifier of the institution
     * @param studentsIds list of students identifiers
     */
    void removeStudentsFromInstitution(String institutionId, List<String> studentsIds) throws NotFoundException;

    /**
     * Checks if a student is associated with a specific institution.
     * @param studentId identifier of the student
     * @param institutionId identifier of the institution
     * @return 'true' if the student is associated with the institution
     */
    boolean isStudentOfInstitution(String studentId, String institutionId) throws NotFoundException;

    /**
     * Checks if a specialist is associated with a specific institution.
     * @param specialistId identifier of the specialist
     * @param institutionId identifier of the institution
     * @return 'true' if the specialist is associated with the institution
     */
    boolean isSpecialistOfInstitution(String specialistId, String institutionId) throws NotFoundException;

    /**
     * Checks if a manager is associated with a specific institution.
     * @param managerId identifier of the manager
     * @param institutionId identifier of the institution
     * @return 'true' if the manager is associated with the institution
     */
    boolean isManagerOfInstitution(String managerId, String institutionId) throws NotFoundException;

    /**
     * Get list of students that are associated with a specific institution.
     * @param institutionId institution identifier
     * @param page index of the page
     * @param itemsPerPage number of items each page has
     * @return list of students that are associated with a specific institution.
     */
    Page<Student> getInstitutionStudents(String institutionId, int page, int itemsPerPage) throws NotFoundException;

    /**
     * Get list of institution managers that are associated with a specific institution.
     *
     * @param institutionId institution identifier
     * @param page          index of the page
     * @param itemsPerPage  number of items each page has
     * @return list of institution managers that are associated with a specific institution.
     */
    public Page<InstitutionManager> getInstitutionManagersFromInstitution(String institutionId, int page, int itemsPerPage) throws NotFoundException;

    /**
     * Get list of specialists that are associated with a specific institution.
     * @param institutionId institution identifier
     * @param page index of the page
     * @param itemsPerPage number of items each page has
     * @return list of specialists that are associated with a specific institution.
     */
    Page<Specialist> getInstitutionSpecialists(String institutionId, int page, int itemsPerPage) throws NotFoundException;

    /**
     * Get institution associated with a specific student
     * @param studentId identifier of the student
     * @return institution associated with a specific student
     */
    Institution getStudentInstitution(String studentId) throws NotFoundException;

    /**
     * Get institution associated with a specific specialist
     * @param specialistId identifier of the specialist
     * @return institution associated with a specific specialist
     */
    Institution getSpecialistInstitution(String specialistId) throws NotFoundException;

    /**
     * Get institution associated with a specific manager
     * @param managerId identifier of the manager
     * @return institution associated with a specific manager
     */
    Institution getManagerInstitution(String managerId) throws NotFoundException;

    /**
     * @param institutionId identifier of the institution
     * @return number of students an institution has
     */
    int countInstitutionStudents(String institutionId);

    /**
     * @param institutionId identifier of the institution
     * @return number of specialists an institution has
     */
    int countInstitutionSpecialists(String institutionId);

    /**
     * @param institutionId identifier of the institution
     * @return number of institution managers an institution has
     */
    int countInstitutionManagersFromInstitution(String institutionId);

    /**
     * Creates an institution manager.
     * @param manager properties
     * @param institutionId identifier of the institution this manager belongs to
     * @return identifier of the new manager
     * @throws BadInputException if any property of the manager is not valid.
     */
    String createInstitutionManager(InstitutionManager manager, String institutionId) throws BadInputException, NotFoundException;

    /**
     * Creates an institution manager.
     * @param manager properties
     * @param institution identifier of the institution this manager belongs to
     * @return identifier of the new manager
     * @throws BadInputException if any property of the manager is not valid.
     */
    String createInstitutionManagerAndInstitution(InstitutionManager manager, Institution institution) throws BadInputException, NotFoundException;

    /**
     * Gets an institution manager
     * @param managerId identifier of the manager
     * @return institution manager
     * @throws NotFoundException if no manager was found with the given id
     */
    InstitutionManager getInstitutionManagerById(String managerId) throws NotFoundException;


    /**
     * Checks if an institution manager exists with the given id.
     * @param managerId identifier of the manager
     * @return 'true' if a manager exists with the given id
     */
    boolean existsInstitutionManagerById(String managerId);
}
