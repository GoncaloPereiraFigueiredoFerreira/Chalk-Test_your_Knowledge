package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.uminho.di.chalktyk.models.nonrelational.users.User;
import pt.uminho.di.chalktyk.repositories.nonrelational.UserDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service
public class UsersService implements IUsersService{
    private final UserDAO userDAO;

    @Autowired
    public UsersService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getUserById(String userId) throws NotFoundException {
        User u = userDAO.findById(userId).orElse(null);
        if (u != null)
            return u;
        else
            throw new NotFoundException("No user with the given id was found.");
    }

    /**
     * Update the basic information of a user
     *
     * @param userId identifier of a user
     * @param user   body of the basic properties of a user
     */
    @Override
    public void updateBasicUserInformation(String userId, User user) {

    }

    @Override
    public boolean login(String userId) {
        return false;
    }

    @Override
    public boolean logout(String userId) {
        return false;
    }
}
