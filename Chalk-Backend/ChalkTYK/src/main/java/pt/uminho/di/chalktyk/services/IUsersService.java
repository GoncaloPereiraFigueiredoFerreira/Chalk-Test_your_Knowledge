package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.models.nonrelational.users.User;
import pt.uminho.di.chalktyk.services.exceptions.*;

public interface IUsersService {

    /**
     * Gets user
     * @param userId identifier of the user
     * @return user
     * @throws NotFoundException if no user was found with the given id
     */
    User getUserById(String userId) throws NotFoundException;

    /**
     * Update the basic information of a user
     * @param userId identifier of a user
     * @param user body of the basic properties of a user
     */
    void updateBasicUserInformation(String userId, User user);

    //TODO - preciso saber como é que o JWT funciona para definir os argumentos e return value
    boolean login(String userId);

    //TODO - preciso saber como é que o JWT funciona para definir os argumentos e return value
    boolean logout(String userId);
}
