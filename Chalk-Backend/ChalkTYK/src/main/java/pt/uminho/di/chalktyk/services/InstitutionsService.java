package pt.uminho.di.chalktyk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.models.nonrelational.subscriptions.InstitutionSubscription;
import pt.uminho.di.chalktyk.repositories.nonrelational.InstitutionDAO;
import pt.uminho.di.chalktyk.repositories.relational.InstitutionSqlDAO;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInstitutions'");
    }

    @Override
    public void deleteInstitutionByID(String institutionID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteInstitutionByID'");
    }

    @Override
    public Institution getInstitutionByID(String institutionID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInstitutionByID'");
    }

    @Override
    public void updateInstitutionByID(String institutionId, Institution body) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInstitutionByID'");
    }

    @Override
    public void createInstitution(Institution institution) {
        if(institution != null){
            if(institution.getName() != null && !idao.existsById(institution.getName())){
                institution = idao.save(institution);
                pt.uminho.di.chalktyk.models.relational.Institution sqlInst = new pt.uminho.di.chalktyk.models.relational.Institution(institution.getName());
                isqldao.save(sqlInst);
            }
        }        
    }

}
