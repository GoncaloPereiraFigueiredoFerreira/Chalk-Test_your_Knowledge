package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.repositories.SpecialistDAO;
import pt.uminho.di.chalktyk.repositories.UserDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service
public class SpecialistsService implements ISpecialistsService{
    private final UserDAO userDAO;
    private final SpecialistDAO specialistDAO;

    @Autowired
    public SpecialistsService(UserDAO userDAO, SpecialistDAO specialistDAO) {
        this.userDAO = userDAO;
        this.specialistDAO = specialistDAO;
    }

    /**
     * Creates a specialist.
     *
     * @param specialist specialist properties
     * @return identifier of the new specialist
     * @throws BadInputException if any property of the specialist is not valid.
     */
    @Override
    @Transactional(rollbackFor = {BadInputException.class})
    public String createSpecialist(Specialist specialist) throws BadInputException {
        if (specialist == null)
            throw new BadInputException("Cannot create a specialist with a 'null' body.");

        //set the identifier to null to avoid overwrite attacks
        specialist.setId(null);
        specialist.setInstitution(null);

        // check for any property format errors
        String inputErrors = specialist.checkInsertProperties();
        if(inputErrors != null)
            throw new BadInputException(inputErrors);

        // check if email is not owned by another user
        if(userDAO.existsByEmail(specialist.getEmail()))
            throw new BadInputException("Could not create specialist: Email is already used by another user.");

        // Save user in database
        specialist = userDAO.save(specialist);

        return specialist.getId();
    }

    @Override
    public Specialist getSpecialistById(String specialistId) throws NotFoundException {
        User u = userDAO.findById(specialistId).orElse(null);
        if (u instanceof Specialist s)
            return s;
        else
            throw new NotFoundException("No specialist with the given id was found.");
    }

    /**
     * Checks if a specialist exists with the given id.
     *
     * @param specialistId identifier of the specialist
     * @return 'true' if a specialist exists with the given id
     */
    @Override
    public boolean existsSpecialistById(String specialistId) {
        return specialistDAO.existsById(specialistId);
    }
}
