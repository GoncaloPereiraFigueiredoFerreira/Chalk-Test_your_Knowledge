package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

public interface ISecurityService {

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
