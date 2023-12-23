package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

public interface ISpecialistsService {

    /**
     * Creates a specialist.
     * @param specialist specialist properties
     * @return identifier of the new specialist
     * @throws BadInputException if any property of the specialist is not valid.
     */
    String createSpecialist(Specialist specialist) throws BadInputException;

    /**
     * Gets specialist
     * @param specialistId identifier of the specialist
     * @return specialist
     * @throws NotFoundException if no specialist was found with the given id
     */
    Specialist getSpecialistById(String specialistId)  throws NotFoundException;
    
    /**
     * Checks if a specialist exists with the given id.
     * @param specialistId identifier of the specialist
     * @return 'true' if a specialist exists with the given id
     */
    boolean existsSpecialistById(String specialistId);
}