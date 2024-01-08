package pt.uminho.di.chalktyk.services;

import org.springframework.data.domain.Page;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

public interface ISeedService {
    /**
     * Seed database
     **/
    void seed() throws BadInputException, NotFoundException, InterruptedException;

    String addSpecialistChang() throws BadInputException;

    String addCourse(String specialistId) throws BadInputException;

    String addStudentAnnie() throws BadInputException;

    String addSpecialistWhitman() throws BadInputException;

    String addCourse2(String specialist2Id) throws BadInputException;

    String addStudentGeorge() throws BadInputException;
}
