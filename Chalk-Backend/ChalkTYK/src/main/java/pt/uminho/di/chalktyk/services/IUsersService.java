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

    /**
     * Gets user
     * @param email email of the user
     * @return user
     * @throws NotFoundException if no user was found with the given email
     */
    User getUserByEmail(String email) throws NotFoundException;

    /**
     * Allows updating the basic information of a user. 'null' fields can be given, but a bad input exception will be thrown if the field does not allow a 'null' value.
     * @param userId user identifier
     * @param name new name
     * @param email new email
     * @param photoPath new photo path
     * @param description new description
     * @throws NotFoundException if the user could not be found
     */
    void updateBasicProperties(String userId, String name, String email, String photoPath, String description) throws NotFoundException, BadInputException;

    boolean login(String userId);

    boolean logout(String userId);
}
