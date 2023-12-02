package pt.uminho.di.chalktyk.services;

import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
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
                res.setSubscription(body.getSubscription());
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
            if (institution.getName() == null)
                throw new BadInputException("Can't create institution: institution id is null");
            if (!idao.existsById(institution.getName())){
                idao.save(institution);
                pt.uminho.di.chalktyk.models.relational.Institution sqlInst = new pt.uminho.di.chalktyk.models.relational.Institution(institution.getName());
                isqldao.save(sqlInst);
            }
            else
                throw new BadInputException("Can't create institution: id is alrealy in use");
        }
        else 
            throw new BadInputException("Can't create institution: institution is null!");
    }

}
