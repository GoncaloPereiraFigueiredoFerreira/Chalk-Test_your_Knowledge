package pt.uminho.di.chalktyk.services;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.models.nonrelational.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.nonrelational.users.Specialist;
import pt.uminho.di.chalktyk.models.nonrelational.users.Student;
import pt.uminho.di.chalktyk.models.nonrelational.users.User;
import pt.uminho.di.chalktyk.repositories.nonrelational.InstitutionDAO;
import pt.uminho.di.chalktyk.repositories.nonrelational.UserDAO;
import pt.uminho.di.chalktyk.repositories.relational.InstitutionManagerSqlDAO;
import pt.uminho.di.chalktyk.repositories.relational.InstitutionSqlDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

@Service("institutionsService")
public class InstitutionsService implements IInstitutionsService {
    private final UserDAO userDAO;
    private final InstitutionDAO idao;
    private final InstitutionSqlDAO isqldao;
    private final ISpecialistsService specialistsService;
    private final IStudentsService studentsService;
    private final InstitutionManagerSqlDAO institutionManagerSqlDAO;
    
    @Autowired
    public InstitutionsService(UserDAO userDAO, InstitutionDAO idao, InstitutionSqlDAO isqldao, ISpecialistsService specialistsService, IStudentsService studentsService, InstitutionManagerSqlDAO institutionManagerSqlDAO){
        this.userDAO = userDAO;
        this.idao = idao;
        this.isqldao = isqldao;
        this.specialistsService = specialistsService;
        this.studentsService = studentsService;
        this.institutionManagerSqlDAO = institutionManagerSqlDAO;
    }

    @Override
    public Page<Institution> getInstitutions(Integer page, Integer itemsPerPage) {
        return this.idao.findAll(PageRequest.of(page, itemsPerPage));
    }

    @Override
    public Institution getInstitutionById(String institutionId) throws NotFoundException {
        Optional<Institution> obj = idao.findById(institutionId);
        if (obj.isPresent()){
            return obj.get();
        }
        else
            throw new NotFoundException("Couldn't get institution with id: " + institutionId);
    }

    /**
     * Checks if an institution exists.
     *
     * @param institutionId identifier of the institution
     * @return 'true' if the institution with the given identifier exists
     */
    @Override
    public boolean existsInstitutionById(String institutionId) {
        return isqldao.existsById(institutionId);
    }

    @Override
    public void updateInstitutionById(String institutionId, Institution body) {
        if (body != null){
            Optional<Institution> obj = idao.findById(institutionId);
            if (obj.isPresent()){
                Institution res = obj.get();
                res.setDescription(body.getDescription());
                res.setLogoPath(body.getLogoPath());
                res.setSubscription(body.getSubscription()); // TODO - a subscricao nao devia ser alterada aqui
                idao.save(res);
            }
            else
                throw new ServiceException("Couldn't update institution with id: " + institutionId);
        }
        else
            throw new ServiceException("Couldn't update institution: institution is null");
    }

    @Override
    public void createInstitution(Institution institution) throws BadInputException {
        if (institution != null){
            if (institution.getName() == null || institution.getName().isEmpty())
                throw new BadInputException("Can't create institution: institution's identifier is null/empty.");
            if (!idao.existsById(institution.getName())){
                // TODO - verificar a parte da subscricao
                idao.save(institution);
                pt.uminho.di.chalktyk.models.relational.Institution sqlInst = new pt.uminho.di.chalktyk.models.relational.Institution(institution.getName());
                isqldao.save(sqlInst);
            }
            else
                throw new BadInputException("Can't create institution: id is already in use");
        }
        else 
            throw new BadInputException("Can't create institution: institution is null!");
    }

    /**
     * Add specialists to institution
     *
     * @param institutionId  identifier of the institution
     * @param specialistsIds list of specialists identifiers
     */
    @Override
    public void addSpecialistsToInstitution(String institutionId, List<String> specialistsIds) throws NotFoundException { //TODO test
        if(!isqldao.existsById(institutionId))
            throw new NotFoundException("Could not add specialists: Institution not found.");
        for(String specialistId : specialistsIds){
            if(specialistsService.existsSpecialistById(specialistId))
                isqldao.addSpecialistToInstitution(specialistId,institutionId);
        }
    }

    /**
     * Add students to institution.
     *
     * @param institutionId identifier of the institution
     * @param studentsIds   list of students identifiers
     */
    @Override
    public void addStudentsToInstitution(String institutionId, List<String> studentsIds) throws NotFoundException { //TODO test
        if(!isqldao.existsById(institutionId))
            throw new NotFoundException("Could not add students: Institution not found.");
        for(String studentId : studentsIds){
            if(studentsService.existsStudentById(studentId))
                isqldao.addStudentToInstitution(studentId,institutionId);
        }
    }

    /**
     * Remove specialists from institution
     *
     * @param institutionId  identifier of the institution
     * @param specialistsIds list of specialists identifiers
     */
    @Override
    public void removeSpecialistsFromInstitution(String institutionId, List<String> specialistsIds) throws NotFoundException { //TODO test
        if(!isqldao.existsById(institutionId))
            throw new NotFoundException("Institution not found.");
        for(String specialistId : specialistsIds){
            if(specialistsService.existsSpecialistById(specialistId))
                isqldao.removeSpecialistFromInstitution(specialistId,institutionId);
        }
    }

    /**
     * Remove students from institution.
     *
     * @param institutionId identifier of the institution
     * @param studentsIds   list of students identifiers
     */
    @Override
    public void removeStudentsFromInstitution(String institutionId, List<String> studentsIds) throws NotFoundException {  //TODO test
        if(!isqldao.existsById(institutionId))
            throw new NotFoundException("Institution not found.");
        for(String studentId : studentsIds){
            if(studentsService.existsStudentById(studentId))
                isqldao.removeStudentFromInstitution(studentId,institutionId);
        }
    }

    /**
     * Checks if a student is associated with a specific institution.
     *
     * @param studentId     identifier of the student
     * @param institutionId identifier of the institution
     * @return 'true' if the student is associated with the institution
     */
    @Override
    public boolean isStudentOfInstitution(String studentId, String institutionId) throws NotFoundException {  //TODO test
        if(!isqldao.existsById(institutionId))
            throw new NotFoundException("Institution not found.");

        pt.uminho.di.chalktyk.models.relational.Institution institution = isqldao.getInstitutionByStudentId(studentId);

        if(institution==null)
            return false;
        else
            return institution.getId().equals(institutionId);
    }

    /**
     * Checks if a specialist is associated with a specific institution.
     *
     * @param specialistId  identifier of the specialist
     * @param institutionId identifier of the institution
     * @return 'true' if the specialist is associated with the institution
     */
    @Override
    public boolean isSpecialistOfInstitution(String specialistId, String institutionId) throws NotFoundException {  //TODO test
        if(!isqldao.existsById(institutionId))
            throw new NotFoundException("Institution not found.");

        pt.uminho.di.chalktyk.models.relational.Institution institution = isqldao.getInstitutionBySpecialistId(specialistId);

        if(institution==null)
            return false;
        else
            return institution.getId().equals(institutionId);
    }

    /**
     * Checks if a manager is associated with a specific institution.
     *
     * @param managerId     identifier of the manager
     * @param institutionId identifier of the institution
     * @return 'true' if the manager is associated with the institution
     */
    @Override
    public boolean isManagerOfInstitution(String managerId, String institutionId) throws NotFoundException {  //TODO test
        if(!isqldao.existsById(institutionId))
            throw new NotFoundException("Institution not found.");

        pt.uminho.di.chalktyk.models.relational.Institution institution = isqldao.getInstitutionByInstitutionManagerId(managerId);

        if(institution==null)
            return false;
        else
            return institution.getId().equals(institutionId);
    }

    /**
     * Get list of students that are associated with a specific institution.
     *
     * @param institutionId institution identifier
     * @param page          index of the page
     * @param itemsPerPage  number of items each page has
     * @return list of students that are associated with a specific institution.
     */

    @Override
    @Transactional
    public Page<Student> getInstitutionStudents(String institutionId, int page, int itemsPerPage) throws NotFoundException { // TODO test
        Pageable pageable = PageRequest.of(page, itemsPerPage);

        Page<String> ids = isqldao.getInstitutionStudentsIds(institutionId,pageable);
        List<Student> studentList= new ArrayList<>();
        for(String id:ids){
            studentList.add(studentsService.getStudentById(id));
        }
        return new PageImpl<>(studentList, pageable, studentList.size());
    }


    /**
     * Get list of institution managers that are associated with a specific institution.
     *
     * @param institutionId institution identifier
     * @param page          index of the page
     * @param itemsPerPage  number of items each page has
     * @return list of institution managers that are associated with a specific institution.
     */

    @Override
    @Transactional
    public Page<InstitutionManager> getInstitutionManagersFromInstitution(String institutionId, int page, int itemsPerPage) throws NotFoundException { // TODO test
        Pageable pageable = PageRequest.of(page, itemsPerPage);

        Page<String> ids = isqldao.getInstitutionStudentsIds(institutionId,pageable);
        List<InstitutionManager> institutionManagerList= new ArrayList<>();
        for(String id:ids){
            institutionManagerList.add(this.getInstitutionManagerById(id));
        }
        return new PageImpl<>(institutionManagerList, pageable, institutionManagerList.size());
    }

    /**
     * Get list of specialists that are associated with a specific institution.
     *
     * @param institutionId institution identifier
     * @param page          index of the page
     * @param itemsPerPage  number of items each page has
     * @return list of specialists that are associated with a specific institution.
     */
    @Override
    public Page<Specialist> getInstitutionSpecialists(String institutionId, int page, int itemsPerPage) throws NotFoundException { //TODO test
        Pageable pageable = PageRequest.of(page, itemsPerPage);

        Page<String> ids = isqldao.getInstitutionSpecialistsIds(institutionId,pageable);
        List<Specialist> specialistList= new ArrayList<>();
        for(String id:ids){
            specialistList.add(specialistsService.getSpecialistById(id));
        }
        return new PageImpl<>(specialistList, pageable, specialistList.size());
    }

    /**
     * Get institution associated with a specific student
     *
     * @param studentId identifier of the student
     * @return institution associated with a specific student
     */
    @Override
    public Institution getStudentInstitution(String studentId) throws NotFoundException { //TODO test
        if(!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Student not found");
        pt.uminho.di.chalktyk.models.relational.Institution institution = isqldao.getInstitutionByStudentId(studentId);
        return idao.findById(institution.getId()).orElse(null);
    }

    /**
     * Get institution associated with a specific specialist
     *
     * @param specialistId identifier of the specialist
     * @return institution associated with a specific specialist or null if not found
     */
    @Override
    public Institution getSpecialistInstitution(String specialistId) throws NotFoundException { //TODO test
        if(!specialistsService.existsSpecialistById(specialistId))
            throw new NotFoundException("Specialist not found");
        pt.uminho.di.chalktyk.models.relational.Institution institution = isqldao.getInstitutionBySpecialistId(specialistId);
        return institution == null ? null : idao.findById(institution.getId()).orElse(null);
    }

    /**
     * Get institution associated with a specific manager
     *
     * @param managerId identifier of the manager
     * @return institution associated with a specific manager
     */
    @Override
    public Institution getManagerInstitution(String managerId) throws NotFoundException { //TODO test
        if(!this.existsInstitutionManagerById(managerId))
            throw new NotFoundException("Institution manager not found");
        pt.uminho.di.chalktyk.models.relational.Institution institution = isqldao.getInstitutionByInstitutionManagerId(managerId);
        if(institution==null)
            throw new NotFoundException("Institution manager is not in an institution");
        return idao.findById(institution.getId()).orElse(null);
    }

    /**
     * @param institutionId identifier of the institution
     * @return number of students an institution has
     */
    @Override
    public int countInstitutionStudents(String institutionId) { //TODO test
        return isqldao.countInstitutionStudents(institutionId);
    }

    /**
     * @param institutionId identifier of the institution
     * @return number of specialists an institution has
     */
    @Override
    public int countInstitutionSpecialists(String institutionId) {  //TODO test
        return isqldao.countInstitutionSpecialists(institutionId);
    }

    /**
     * @param institutionId identifier of the institution
     * @return number of institution managers an institution has
     */
    @Override
    public int countInstitutionManagersFromInstitution(String institutionId) {  //TODO test
        return isqldao.countInstitutionManagersFromInstitution(institutionId);
    }

    /**
     * Verify the integrity of the institution manager input for creation an institution manager.
     * @param manager properties
     * @throws BadInputException if any property of the manager is not valid.
     */
    private void verifyInstitutionManagerInput(InstitutionManager manager) throws BadInputException { //TODO test
        if (manager == null)
            throw new BadInputException("Could not create institution manager: Cannot create a manager with a 'null' body.");

        //set the identifier to null to avoid overwrite attacks
        manager.setId(null);

        String inputErrors = manager.checkProperties();
        if(inputErrors != null)
            throw new BadInputException(inputErrors);
    }

    /**
     * Create the institution manager on non-relational and create the relation connection to institution
     * @param manager entity after input verification
     * @param institution reference to the sql table
     */
    private void createInstitutionManagerAux(InstitutionManager manager, pt.uminho.di.chalktyk.models.relational.Institution institution) { //TODO test
        manager = userDAO.save(manager);

        var managerSql = new pt.uminho.di.chalktyk.models.relational.InstitutionManager(manager.getId(), manager.getName(), manager.getEmail(),institution);
        institutionManagerSqlDAO.save(managerSql);
    }

    /**
     * Creates an institution manager.
     * @param manager properties
     * @param institutionId identifier of the institution this manager belongs to
     * @return identifier of the new manager
     * @throws BadInputException if any property of the manager is not valid.
     */
    @Override
    @Transactional(rollbackFor = {BadInputException.class})
    public String createInstitutionManager(InstitutionManager manager, String institutionId) throws BadInputException, NotFoundException { //TODO test
        pt.uminho.di.chalktyk.models.relational.Institution institution = isqldao.findById(institutionId).orElse(null);
        
        if(institution == null)
            throw new NotFoundException("Could not create institution manager: The institution was not found");

        verifyInstitutionManagerInput(manager);

        // Create entry in sql database using the id created by the nosql database
        createInstitutionManagerAux(manager,institution);

        return manager.getId();
    }

    /**
     * Creates an institution manager.
     * @param manager properties
     * @param institution identifier of the institution this manager belongs to
     * @return identifier of the new manager
     * @throws BadInputException if any property of the manager is not valid.
     */
    @Override
    @Transactional(rollbackFor = {BadInputException.class})
    public String createInstitutionManagerAndInstitution(InstitutionManager manager, Institution institution) throws BadInputException { //TODO test
        if(!this.existsInstitutionById(institution.getName()))
            throw new BadInputException("Could not create institution manager: Institution name already in use");

        verifyInstitutionManagerInput(manager);

        this.createInstitution(institution);

        // Create entry in sql database using the id created by the nosql database
        createInstitutionManagerAux(manager,isqldao.findById(institution.getName()).orElse(null));

        return manager.getId();
    }

    /**
     * Gets an institution manager
     * @param managerId identifier of the manager
     * @return institution manager
     * @throws NotFoundException if no manager was found with the given id
     */
    @Override
    public InstitutionManager getInstitutionManagerById(String managerId) throws NotFoundException { //TODO test
        User u = userDAO.findById(managerId).orElse(null);
        if (u instanceof InstitutionManager manager)
            return manager;
        else
            throw new NotFoundException("No institution manager with the given id was found.");
    }

    /**
     * Checks if an institution manager exists with the given id.
     * @param managerId identifier of the manager
     * @return 'true' if a manager exists with the given id
     */
    @Override
    public boolean existsInstitutionManagerById(String managerId) {
        return institutionManagerSqlDAO.existsById(managerId);
    }
}
