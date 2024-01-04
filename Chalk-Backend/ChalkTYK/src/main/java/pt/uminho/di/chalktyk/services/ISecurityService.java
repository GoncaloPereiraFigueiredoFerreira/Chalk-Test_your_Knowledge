package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.models.users.User;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

public interface ISecurityService {
    /**
     * Checks if the token is valid, and updates the token associated with login
     * if the token currently associated with the login expired.
     * @param jwtTokenString JWT token as string
     * @return jwt token
     * @throws UnauthorizedException if the token is invalid, expired or is blacklisted.
     */
    JWT validateJWT(String jwtTokenString) throws UnauthorizedException;

    /**
     * Logs in the user.
     * @param jwtToken JWT Token
     * @return returns the user basic properties
     * @throws UnauthorizedException if the token is invalid, expired or is blacklisted.
     */
    User login(String jwtToken) throws UnauthorizedException, NotFoundException;

    /**
     * Logs out the user.
     * @param jwtToken JWT Token
     * @throws UnauthorizedException if the user is not logged in, of if the token is invalid, expired or is blacklisted.
     */
    void logout(String jwtToken) throws UnauthorizedException;


    /**
     * @param jwtToken
     * @return
     * @throws NotFoundException
     * @throws BadInputException
     */
    public Boolean userIsSpecialist(String jwtToken) throws NotFoundException, BadInputException;

    /**
     * @param jwtToken
     * @return
     * @throws NotFoundException
     * @throws BadInputException
     */
    public Boolean userIsStudent(String jwtToken) throws NotFoundException, BadInputException;

    /**
     * @param jwtToken
     * @return
     * @throws NotFoundException
     * @throws BadInputException
     */
    public Boolean userExists(String jwtToken) throws NotFoundException, BadInputException;

    /**
     * @param specialistId
     * @param exerciseId
     * @return
     * @throws NotFoundException
     * @throws UnauthorizedException
     */
    public Boolean specialistOwnesExercise(String specialistId, String exerciseId) throws NotFoundException, UnauthorizedException;

    /**
     * @param studentId
     * @param exerciseId
     * @return
     * @throws NotFoundException
     * @throws UnauthorizedException
     */
    public Boolean studentCanSeeExercise(String studentId, String exerciseId) throws NotFoundException, UnauthorizedException;

    /**
     * @param specialistId
     * @param exerciseId
     * @return
     * @throws NotFoundException
     * @throws UnauthorizedException
     */
    public Boolean specialistCanSeeExercise(String specialistId, String exerciseId) throws NotFoundException, UnauthorizedException;
}
