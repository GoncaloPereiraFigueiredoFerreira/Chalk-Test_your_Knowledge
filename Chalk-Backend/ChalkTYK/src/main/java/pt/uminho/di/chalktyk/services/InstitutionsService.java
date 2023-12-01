package pt.uminho.di.chalktyk.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.repositories.nonrelational.InstitutionDAO;
import pt.uminho.di.chalktyk.repositories.relational.InstitutionSqlDAO;

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
    public List<Institution> getInstitutions(Integer page, Integer itemsPerPage) {
        List<Institution> list = this.idao.findAll();
        
        List<Institution> res = new ArrayList<>();
        for(int i = (page - 1) * itemsPerPage; i < page * itemsPerPage && i < list.size(); i ++){
            res.add(list.get(i));
        }

        return res;
    }

    @Override
    public void deleteInstitutionByID(String institutionID) {
        idao.deleteById(institutionID);
    }

    @Override
    public Institution getInstitutionByID(String institutionID) {
        long count = idao.count();

        Optional<Institution> obj = idao.findById(institutionID);
        if (obj.isPresent()){
            return obj.get();
        }
        else
            throw new ServiceException("count: " + count + "Couldn't get institution with id: " + institutionID);
    }

    @Override
    public void updateInstitutionByID(String institutionId, Institution body) {
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
    public void createInstitution(Institution institution) {
        if (institution != null){
            if (institution.getName() == null)
                throw new ServiceException("Can't create institution: institution id is null");
            if (institution.getName() != null && !idao.existsById(institution.getName())){
                idao.save(institution);
                pt.uminho.di.chalktyk.models.relational.Institution sqlInst = new pt.uminho.di.chalktyk.models.relational.Institution(institution.getName());
                isqldao.save(sqlInst);
            }
            else
                throw new ServiceException("Can't create institution: id is alrealy in use");
        }
        else 
            throw new ServiceException("Can't create institution: institution is null!");
    }

}
