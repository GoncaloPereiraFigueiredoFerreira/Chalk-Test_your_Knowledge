package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.repositories.UserDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service
public class UsersService implements IUsersService{
    private final UserDAO userDAO;

    @Autowired
    public UsersService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Gets user
     * @param userId identifier of the user
     * @return user
     * @throws NotFoundException if no user was found with the given id
     */
    @Override
    public User getUserById(String userId) throws NotFoundException {
        User u = userDAO.findById(userId).orElse(null);
        if (u != null)
            return u;
        else
            throw new NotFoundException("No user with the given id was found.");
    }

    @Override
    public boolean login(String userId) {
        //TODO - preciso saber como é que o JWT funciona para definir os argumentos e return value
        return false;
    }

    @Override
    public boolean logout(String userId) {
        //TODO - preciso saber como é que o JWT funciona para definir os argumentos e return value
        return false;
    }
}
