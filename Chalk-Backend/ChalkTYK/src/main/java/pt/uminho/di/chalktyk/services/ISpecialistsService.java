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

    /**
     * Update the basic information of a specialist.
     * The fields that can be updated are name, email, photoPath and description.
     * All this fields should be sent, even the ones that were not updated.
     * For example, if photoPath is null, then the field will be updated as null.
     *
     * @param specialistId identifier of a specialist
     * @param newSpecialist body of the basic properties of a specialist
     * @throws NotFoundException if the student does not exist
     * @throws BadInputException if any of the fields is not valid
     */
    void updateBasicSpecialistInformation(String specialistId, Specialist newSpecialist) throws NotFoundException, BadInputException;
}