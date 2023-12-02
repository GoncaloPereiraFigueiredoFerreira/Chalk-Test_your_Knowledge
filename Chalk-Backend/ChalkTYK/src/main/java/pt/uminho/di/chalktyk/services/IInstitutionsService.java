package pt.uminho.di.chalktyk.services;

import org.springframework.data.domain.Page;
import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

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
}
