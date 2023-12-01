package pt.uminho.di.chalktyk.services;

import java.util.List;

import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;

public interface IInstitutionsService {

    /**
    * Get list of institutions
    * @param page
    * @param itemsPerPage maximum items in a page
    * @return a list of institutions
    **/
    List<Institution> getInstitutions(Integer page, Integer itemsPerPage);

    /**
    * Delete institution by ID
    * @param institutionID
    **/
    void deleteInstitutionByID(String institutionID);

    /**
    * Get institution by ID
    * @param institutionID
    * @return institution the given ID
    **/
    Institution getInstitutionByID(String institutionID);

    /**
    * Update institution by ID
    * @param institutionID
    * @param institution
    **/
    void updateInstitutionByID(String institutionId, Institution institution);

    /**
    * Create institution
    * @param institution
    **/
    void createInstitution(Institution institution);
}
