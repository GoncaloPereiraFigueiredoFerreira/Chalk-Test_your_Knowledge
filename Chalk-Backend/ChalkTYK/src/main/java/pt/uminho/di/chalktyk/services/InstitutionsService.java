package pt.uminho.di.chalktyk.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.models.relational.Specialist;
import pt.uminho.di.chalktyk.models.relational.Student;
import pt.uminho.di.chalktyk.repositories.nonrelational.InstitutionDAO;
import pt.uminho.di.chalktyk.repositories.relational.InstitutionSqlDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service("institutionsService")
public class InstitutionsService implements IInstitutionsService {

    private final InstitutionDAO idao;
    private final InstitutionSqlDAO isqldao;
    
    @Autowired
    public InstitutionsService(InstitutionDAO idao, InstitutionSqlDAO isqldao){
        this.idao = idao;
        this.isqldao = isqldao;
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
    public void addSpecialistsToInstitution(String institutionId, List<String> specialistsIds) {
        // TODO
    }

    /**
     * Add students to institution.
     *
     * @param institutionId identifier of the institution
     * @param studentsIds   list of students identifiers
     */
    @Override
    public void addStudentsToInstitution(String institutionId, List<String> studentsIds) {
        // TODO
    }

    /**
     * Remove specialists from institution
     *
     * @param institutionId  identifier of the institution
     * @param specialistsIds list of specialists identifiers
     */
    @Override
    public void removeSpecialistsFromInstitution(String institutionId, List<String> specialistsIds) {
        // TODO
    }

    /**
     * Remove students from institution.
     *
     * @param institutionId identifier of the institution
     * @param studentsIds   list of students identifiers
     */
    @Override
    public void removeStudentsFromInstitution(String institutionId, List<String> studentsIds) {
        // TODO
    }

    /**
     * Checks if a student is associated with a specific institution.
     *
     * @param studentId     identifier of the student
     * @param institutionId identifier of the institution
     * @return 'true' if the student is associated with the institution
     */
    @Override
    public boolean isStudentOfInstitution(String studentId, String institutionId) {
        return false; // TODO
    }

    /**
     * Checks if a specialist is associated with a specific institution.
     *
     * @param specialistId  identifier of the specialist
     * @param institutionId identifier of the institution
     * @return 'true' if the specialist is associated with the institution
     */
    @Override
    public boolean isSpecialistOfInstitution(String specialistId, String institutionId) {
        return false; // TODO
    }

    /**
     * Checks if a manager is associated with a specific institution.
     *
     * @param managerId     identifier of the manager
     * @param institutionId identifier of the institution
     * @return 'true' if the manager is associated with the institution
     */
    @Override
    public boolean isManagerOfInstitution(String managerId, String institutionId) {
        return false; // TODO
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
    public Page<Student> getInstitutionStudents(String institutionId, int page, int itemsPerPage) {
        return null; // TODO
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
    public Page<Specialist> getInstitutionSpecialists(String institutionId, int page, int itemsPerPage) {
        return null; // TODO
    }

    /**
     * Get institution associated with a specific student
     *
     * @param studentId identifier of the student
     * @return institution associated with a specific student
     */
    @Override
    public Institution getStudentInstitution(String studentId) {
        return null; // TODO
    }

    /**
     * Get institution associated with a specific specialist
     *
     * @param specialistId identifier of the specialist
     * @return institution associated with a specific specialist
     */
    @Override
    public Institution getSpecialistInstitution(String specialistId) {
        return null; // TODO
    }

    /**
     * Get institution associated with a specific manager
     *
     * @param managerId identifier of the manager
     * @return institution associated with a specific manager
     */
    @Override
    public Institution getManagerInstitution(String managerId) {
        return null; // TODO
    }

    /**
     * @param institutionId identifier of the institution
     * @return number of students an institution has
     */
    @Override
    public int countInstitutionStudents(String institutionId) {
        return 0; // TODO
    }

    /**
     * @param institutionId identifier of the institution
     * @return number of students an institution has
     */
    @Override
    public int countInstitutionSpecialists(String institutionId) {
        return 0; // TODO
    }

}
