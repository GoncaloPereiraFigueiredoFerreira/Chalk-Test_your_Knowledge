package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.services.exceptions.*;

public interface IUsersService {

    /**
     * Gets user
     * @param userId identifier of the user
     * @return user
     * @throws NotFoundException if no user was found with the given id
     */
    User getUserById(String userId) throws NotFoundException;

    boolean login(String userId);

    boolean logout(String userId);
}
