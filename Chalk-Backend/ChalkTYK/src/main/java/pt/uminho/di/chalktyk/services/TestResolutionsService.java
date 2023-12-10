package pt.uminho.di.chalktyk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolution;
import pt.uminho.di.chalktyk.models.relational.Student;
import pt.uminho.di.chalktyk.models.relational.Test;
import pt.uminho.di.chalktyk.repositories.nonrelational.TestResolutionDAO;
import pt.uminho.di.chalktyk.repositories.relational.TestResolutionSqlDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service("testResolutionsService")
public class TestResolutionsService implements ITestResolutionsService {
    
    @PersistenceContext
    private final EntityManager entityManager;
    private final TestResolutionDAO resolutionDAO;
    private final TestResolutionSqlDAO resolutionSqlDAO;
    private final IStudentsService studentsService;

    @Autowired
    public TestResolutionsService(EntityManager entityManager, TestResolutionDAO resolutionDAO, 
            TestResolutionSqlDAO resolutionSqlDAO, IStudentsService studentsService){
        this.entityManager = entityManager;
        this.resolutionDAO = resolutionDAO;
        this.resolutionSqlDAO = resolutionSqlDAO;
        this.studentsService = studentsService;
    }

    @Override
    @Transactional
    public String createTestResolution(TestResolution resolution) throws BadInputException {
        if (resolution == null)
            throw new BadInputException("Cannot create a test resolution with a 'null' body.");
        
        //set the identifier to null to avoid overwrite attacks
        resolution.setId(null);

        // TODO: check other resolutions made by the student and update the submission nr
        // check student
        String studentId = resolution.getStudentId();
        if (studentId == null)
            throw new BadInputException("Cannot create test resolution: resolution must belong to a student.");
        if (!studentsService.existsStudentById(studentId))
            throw new BadInputException("Cannot create test resolution: resolution must belong to a valid student.");

        // Save resolution in nosql database
        resolution = resolutionDAO.save(resolution);

        // TODO: check resolution status

        // Persists the test resolution in SQL database
        String testId = resolution.getTestId();
        Test test = testId != null ? entityManager.getReference(Test.class, testId) : null;
        Student student = studentId != null ? entityManager.getReference(Student.class, studentId) : null;
        var resolutionSql = new pt.uminho.di.chalktyk.models.relational.TestResolution(resolution.getId(), test, student);
        resolutionSqlDAO.save(resolutionSql);

        return resolution.getId();
    }

    @Override
    public TestResolution getTestResolutionById(String resolutionId) throws NotFoundException {
        TestResolution tr = resolutionDAO.findById(resolutionId).orElse(null);
        if (tr == null)
            throw new NotFoundException("Could not get test resolution: there is no resolution with the given identifier.");
        return tr;
    }

    @Override
    public boolean existsTestResolutionById(String resolutionId) {
        return resolutionSqlDAO.existsById(resolutionId);
    }

    @Override
    public Integer countStudentSubmissionsForTest(String testId, String studentId) {
        return resolutionSqlDAO.countStudentSubmissionsForTest(studentId, testId);
    }

    @Override
    public List<String> getStudentTestResolutionsIds(String testId, String studentId) {
        return resolutionSqlDAO.getStudentTestResolutionsIds(testId, studentId);
    }
}
