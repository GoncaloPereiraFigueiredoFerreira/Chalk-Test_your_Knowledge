package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

public interface ISeedService {
    /**
     * Seed database
     *
     * @return 'true' if the database was seeded. 'false' if the database was already seeded.
     */
    boolean seed() throws BadInputException, NotFoundException, InterruptedException;

    String addSpecialistChang() throws BadInputException;

    String addCourse(String specialistId) throws BadInputException;

    String addStudentAnnie() throws BadInputException;

    String addSpecialistWhitman() throws BadInputException;

    String addCourse2(String specialist2Id) throws BadInputException;

    String addStudentGeorge() throws BadInputException;
}
