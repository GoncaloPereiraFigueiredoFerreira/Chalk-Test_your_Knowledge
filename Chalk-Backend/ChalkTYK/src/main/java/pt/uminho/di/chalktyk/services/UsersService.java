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
    public User getUserByEmail(String email) throws NotFoundException {
        User u = userDAO.findByEmail(email).orElse(null);
        if (u != null)
            return u;
        else
            throw new NotFoundException("No user with the given email was found.");
    }

    @Override
    public String getUserIdByEmail(String email) throws NotFoundException {
        return getUserByEmail(email).getId();
    }

    @Override
    public boolean existsUserById(String userId) {
        return userDAO.existsById(userId);
    }

    @Override
    public void updateBasicProperties(String userId, String name, String email, String photoPath, String description) throws NotFoundException, BadInputException {
        User user = getUserById(userId);

        if(!user.getEmail().equals(email) && userDAO.existsByEmail(email))
            throw new BadInputException("Email already used by another user.");

        user.setName(name);
        user.setEmail(email);
        user.setPhotoPath(photoPath);
        user.setDescription(description);
        String insertError = user.checkInsertProperties();
        if(insertError != null)
            throw new BadInputException("Could not update user properties: " + insertError);
        userDAO.save(user);
    }
}
