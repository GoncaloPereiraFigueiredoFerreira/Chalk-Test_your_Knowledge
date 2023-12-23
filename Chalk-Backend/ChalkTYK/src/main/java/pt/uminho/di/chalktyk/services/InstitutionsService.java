package pt.uminho.di.chalktyk.services;

import java.util.ArrayList;
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
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.users.InstitutionManager;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.repositories.InstitutionDAO;
import pt.uminho.di.chalktyk.repositories.InstitutionManagerDAO;
import pt.uminho.di.chalktyk.repositories.UserDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service("institutionsService")
public class InstitutionsService implements IInstitutionsService {
    private final UserDAO userDAO;
    private final InstitutionDAO idao;
    private final ISpecialistsService specialistsService;
    private final IStudentsService studentsService;
    private final InstitutionManagerDAO institutionManagerDAO;
    @PersistenceContext
    private final EntityManager entityManager;
    
    @Autowired
    public InstitutionsService(UserDAO userDAO, InstitutionDAO idao, ISpecialistsService specialistsService, IStudentsService studentsService, InstitutionManagerDAO institutionManagerDAO, EntityManager entityManager){
        this.userDAO = userDAO;
        this.idao = idao;
        this.specialistsService = specialistsService;
        this.studentsService = studentsService;
        this.institutionManagerDAO = institutionManagerDAO;
        this.entityManager = entityManager;
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
        return idao.existsById(institutionId);
    }

    @Override
    public void updateInstitutionById(String institutionId, Institution body) {
        if (body != null){
            Optional<Institution> obj = idao.findById(institutionId);
            if (obj.isPresent()){
                Institution res = obj.get();
                res.setDescription(body.getDescription());
                res.setLogoPath(body.getLogoPath());
                //res.setSubscription(body.getSubscription()); // TODO - a subscricao nao devia ser alterada aqui
                idao.save(res);
            }
            else
                throw new ServiceException("Couldn't update institution with id: " + institutionId);
        }
        else
            throw new ServiceException("Couldn't update institution: institution is null");
    }



    /**
     * Add specialists to institution
     *
     * @param institutionId  identifier of the institution
     * @param specialistsIds list of specialists identifiers
     */
    @Override
    public void addSpecialistsToInstitution(String institutionId, List<String> specialistsIds) throws NotFoundException { //TODO test
        if(!existsInstitutionById(institutionId))
            throw new NotFoundException("Could not add specialists: Institution not found.");
        for(String specialistId : specialistsIds){
            if(specialistsService.existsSpecialistById(specialistId)){
                idao.addSpecialistToInstitution(specialistId,institutionId);
            }
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
        if(!idao.existsById(institutionId))
            throw new NotFoundException("Could not add students: Institution not found.");
        for(String studentId : studentsIds){
            if(studentsService.existsStudentById(studentId))
                idao.addStudentToInstitution(studentId,institutionId);
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
        if(!idao.existsById(institutionId))
            throw new NotFoundException("Institution not found.");
        for(String specialistId : specialistsIds){
            if(specialistsService.existsSpecialistById(specialistId))
                idao.removeSpecialistFromInstitution(specialistId,institutionId);
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
        if(!idao.existsById(institutionId))
            throw new NotFoundException("Institution not found.");
        for(String studentId : studentsIds){
            if(studentsService.existsStudentById(studentId))
                idao.removeStudentFromInstitution(studentId,institutionId);
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
        if(!idao.existsById(institutionId))
            throw new NotFoundException("Institution not found.");

        Institution institution = idao.getInstitutionByStudentId(studentId);

        if(institution==null)
            return false;
        else
            return institution.getName().equals(institutionId);
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
        if(!idao.existsById(institutionId))
            throw new NotFoundException("Institution not found.");

        Institution institution = idao.getInstitutionBySpecialistId(specialistId);

        if(institution==null)
            return false;
        else
            return institution.getName().equals(institutionId);
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
        if(!idao.existsById(institutionId))
            throw new NotFoundException("Institution not found.");

        Institution institution = idao.getInstitutionByInstitutionManagerId(managerId);

        if(institution==null)
            return false;
        else
            return institution.getName().equals(institutionId);
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

        Page<String> ids = idao.getInstitutionStudentsIds(institutionId,pageable);
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

        Page<String> ids = idao.getInstitutionStudentsIds(institutionId,pageable);
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

        Page<String> ids = idao.getInstitutionSpecialistsIds(institutionId,pageable);
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
        Institution institution = idao.getInstitutionByStudentId(studentId);
        return idao.findById(institution.getName()).orElse(null);
    }

    /**
     * Get institution associated with a specific specialist
     *
     * @param specialistId identifier of the specialist
     * @return institution associated with a specific specialist or null if not found
     * @throws NotFoundException if the specialist does not exist
     */
    @Override
    public Institution getSpecialistInstitution(String specialistId) throws NotFoundException { //TODO test
        if(!specialistsService.existsSpecialistById(specialistId))
            throw new NotFoundException("Specialist not found");
        Institution institution = idao.getInstitutionBySpecialistId(specialistId);
        return institution == null ? null : idao.findById(institution.getName()).orElse(null);
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
        Institution institution = idao.getInstitutionByInstitutionManagerId(managerId);
        if(institution==null)
            throw new NotFoundException("Institution manager is not in an institution");
        return idao.findById(institution.getName()).orElse(null);
    }

    /**
     * @param institutionId identifier of the institution
     * @return number of students an institution has
     */
    @Override
    public int countInstitutionStudents(String institutionId) { //TODO test
        return idao.countInstitutionStudents(institutionId);
    }

    /**
     * @param institutionId identifier of the institution
     * @return number of specialists an institution has
     */
    @Override
    public int countInstitutionSpecialists(String institutionId) {  //TODO test
        return idao.countInstitutionSpecialists(institutionId);
    }

    /**
     * @param institutionId identifier of the institution
     * @return number of institution managers an institution has
     */
    @Override
    public int countInstitutionManagersFromInstitution(String institutionId) {  //TODO test
        return idao.countInstitutionManagersFromInstitution(institutionId);
    }

    /**
     * Verify the integrity of the institution manager input for creation an institution manager.
     * @param manager properties
     * @throws BadInputException if any property of the manager is not valid.
     */
    private void verifyInstitutionManagerInput(InstitutionManager manager) throws BadInputException {
        if (manager == null)
            throw new BadInputException("Could not create institution manager: Cannot create a manager with a 'null' body.");

        String inputErrors = manager.checkInsertProperties();
        if(inputErrors != null)
            throw new BadInputException(inputErrors);
    }


    /**
     * Creates an institution manager.
     * @param manager properties (the institution should be null since it will be the one referenced on the id)
     * @param institutionId identifier of the institution this manager belongs to
     * @return identifier of the new manager
     * @throws BadInputException if any property of the manager is not valid.
     */
    @Override
    @Transactional(rollbackFor = {BadInputException.class})
    public String createInstitutionManager(InstitutionManager manager, String institutionId) throws BadInputException, NotFoundException { //TODO test
        // check if manager is null
        if(manager == null)
            throw new BadInputException("Manager cannot be null.");

        // persists institution
        Institution institution = idao.findById(institutionId).orElse(null);
        if(institution==null)
            throw new BadInputException("The institution does not exist");

        // sets the manager institution
        manager.setInstitution(institution);

        // check manager properties
        verifyInstitutionManagerInput(manager);

        //set the identifier to null to avoid overwrite attacks
        manager.setId(null);

        manager = institutionManagerDAO.save(manager);
        return manager.getId();
    }


    /**
     * Creates an institution and its manager.
     *
     * @param institution
     * @param manager
     * @return identifier of the new manager
     * @throws BadInputException if any property of the institution or the manager is not valid.
     */
    @Override
    @Transactional(rollbackFor = {BadInputException.class})
    public String createInstitutionAndManager(Institution institution, InstitutionManager manager) throws BadInputException {
        // check institution
        if(institution == null)
            throw new BadInputException("Institution cannot be null.");
        institution.verifyProperties();

        // check if an institution with the given name already exists
        if(idao.existsById(institution.getName()))
            throw new BadInputException("An institution already exists with the given name.");

        // check if manager is null
        if(manager == null)
            throw new BadInputException("Manager cannot be null.");

        // persists institution
        institution = idao.save(institution);
        // sets the manager institution
        manager.setInstitution(institution);

        // check manager properties
        verifyInstitutionManagerInput(manager);

        //set the identifier to null to avoid overwrite attacks
        manager.setId(null);

        manager = institutionManagerDAO.save(manager);
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
        return institutionManagerDAO.existsById(managerId);
    }
}
