package pt.uminho.di.chalktyk.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolution;
import pt.uminho.di.chalktyk.models.relational.StudentSQL;
import pt.uminho.di.chalktyk.models.relational.TestResolutionSQL;
import pt.uminho.di.chalktyk.models.relational.TestSQL;
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
    public Page<TestResolution> getTestResolutions(String testId, Integer page, Integer itemsPerPage) {
        // TODO: not tested
        // TODO: convert to non relational test resolution
        return null; //resolutionSqlDAO.getTestResolutions(testId, PageRequest.of(page, itemsPerPage));
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
        TestSQL test = testId != null ? entityManager.getReference(TestSQL.class, testId) : null;
        StudentSQL student = studentId != null ? entityManager.getReference(StudentSQL.class, studentId) : null;
        var resolutionSql = new TestResolutionSQL(resolution.getId(), test, student);
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

    @Override
    public TestResolution getStudentLastResolution(String testId, String studentId) throws NotFoundException {
        List<String> ids = getStudentTestResolutionsIds(testId, studentId);
        TestResolution res = null;

        if (ids.size() == 0)
            throw new NotFoundException("Cannot get student " + studentId + " last resolution for test " + testId + ": couldn't find any resolution.");
        else {
            int k;
            for (k = 0; res == null && k < ids.size(); k++)
                res = resolutionDAO.findById(ids.get(k)).orElse(null);

            if (res == null)
                throw new NotFoundException("Cannot get student " + studentId + " last resolution for test " + testId + ": error finding resolution in non-relational DB.");
            else {
                for (int i = k; i < ids.size(); i++){
                    TestResolution tmp = resolutionDAO.findById(ids.get(i)).orElse(null);
                    if (tmp != null && res.getSubmissionNr() < tmp.getSubmissionNr())
                        res = tmp;
                }
            }
        }
        return res;
    }
}
