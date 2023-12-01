package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.nonrelational.users.Specialist;
import pt.uminho.di.chalktyk.models.nonrelational.users.User;
import pt.uminho.di.chalktyk.repositories.nonrelational.UserDAO;
import pt.uminho.di.chalktyk.repositories.relational.SpecialistSqlDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service
public class SpecialistsService implements ISpecialistsService{
    private final UserDAO userDAO;
    private final SpecialistSqlDAO specialistSqlDAO;

    @Autowired
    public SpecialistsService(UserDAO userDAO, SpecialistSqlDAO specialistSqlDAO) {
        this.userDAO = userDAO;
        this.specialistSqlDAO = specialistSqlDAO;
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

        String inputErrors = specialist.checkProperties();
        if(inputErrors != null)
            throw new BadInputException(inputErrors);

        // Save user in nosql database
        specialist = userDAO.save(specialist);

        // Create entry in sql database using the id created by the nosql database
        var specialistSql = new pt.uminho.di.chalktyk.models.relational.Specialist(specialist.getId(), null, specialist.getName(), specialist.getEmail());
        specialistSqlDAO.save(specialistSql);

        return specialist.getId();
    }

    @Override
    public Specialist getSpecialistById(String specialistId) throws NotFoundException {
        //Query query = new Query();
        //query.addCriteria(Criteria.where("_id").is(specialistId))
        //     .addCriteria(Criteria.where("_class").is("specialist"));
        User u = userDAO.findById(specialistId).orElse(null);
        if (u instanceof Specialist s)
            return s;
        else
            throw new NotFoundException("No specialist with the given id was found.");
    }
}
