package pt.uminho.di.chalktyk.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.uminho.di.chalktyk.models.nonrelational.tests.Test;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolution;
import pt.uminho.di.chalktyk.models.relational.CourseSQL;
import pt.uminho.di.chalktyk.models.relational.SpecialistSQL;
import pt.uminho.di.chalktyk.models.relational.StudentSQL;
import pt.uminho.di.chalktyk.models.relational.TestResolutionSQL;
import pt.uminho.di.chalktyk.models.relational.TestSQL;
import pt.uminho.di.chalktyk.models.relational.VisibilitySQL;
import pt.uminho.di.chalktyk.repositories.nonrelational.TestDAO;
import pt.uminho.di.chalktyk.repositories.nonrelational.TestResolutionDAO;
import pt.uminho.di.chalktyk.repositories.relational.TestResolutionSqlDAO;
import pt.uminho.di.chalktyk.repositories.relational.TestSqlDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service("testsService")
public class TestsService implements ITestsService {
    
    @PersistenceContext
    private final EntityManager entityManager;
    private final TestDAO testDAO;
    private final TestSqlDAO testSqlDAO;
    private final TestResolutionDAO resolutionDAO;
    private final TestResolutionSqlDAO resolutionSqlDAO;
    private final ISpecialistsService specialistsService;
    private final IStudentsService studentsService;
    //private final IInstitutionsService institutionsService;
    private final ICoursesService coursesService;

    @Autowired
    public TestsService(EntityManager entityManager, TestDAO testDAO, TestSqlDAO testSqlDAO, TestResolutionDAO resolutionDAO, TestResolutionSqlDAO resolutionSqlDAO,
            ISpecialistsService specialistsService, IStudentsService studentsService, /*IInstitutionsService institutionsService,*/ ICoursesService coursesService){
        this.entityManager = entityManager;
        this.testDAO = testDAO;
        this.testSqlDAO = testSqlDAO;
        this.resolutionDAO = resolutionDAO;
        this.resolutionSqlDAO = resolutionSqlDAO;
        this.specialistsService = specialistsService;
        this.studentsService = studentsService;
        //this.institutionsService = institutionsService;
        this.coursesService = coursesService;
    }

    @Override
    public Page<Test> getTests(Integer page, Integer itemsPerPage, List<Integer> tags, Boolean matchAllTags,
            String visibilityType, String visibilityTarget, String specialistId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTests'");
    }

    @Override
    public String createTest(VisibilitySQL visibility, Test body) throws BadInputException {
        // TODO: live tests + resto das classes

        if (body == null)
            throw new BadInputException("Cannot create test: test is null.");

        body.verifyProperties();
        body.setId(null);                       // to prevent overwrite attacks
        body.setCreationDate(LocalDateTime.now()); // set creation date

        // check owner (specialist) id
        String specialistId = body.getSpecialistId();
        if (specialistId == null)
            throw new BadInputException("Cannot create test: A test must have an owner.");
        if (!specialistsService.existsSpecialistById(specialistId))
            throw new BadInputException("Cannot create test: A test must have a valid specialist.");

        // TODO: add institution later
        // get owner's institution id
        /*
        String institutionId = null;
        try {
            var inst = institutionsService.getSpecialistInstitution(specialistId);
            if (inst != null)
                institutionId = inst.getName();
        }
        catch (NotFoundException ignored){}
        body.setInstituitionId(institutionId);
        */

        // check course
        String courseId = body.getCourseId();
        if (courseId != null){
            if (!coursesService.existsCourseById(courseId))
                throw new BadInputException("Cannot create test: A test must belong to a valid course.");

            // check if specialist belongs to course
            try{ coursesService.checkSpecialistInCourse(courseId, specialistId); }
            catch (NotFoundException nfe){ throw new BadInputException("Cannot create test: The test's owner must belong to the course given."); } 
        }

        // persist the test in nosql database
        body = testDAO.save(body);

        // persists the test in sql database
        // TODO: add institution later
        //Institution inst = institutionId != null ? entityManager.getReference(Institution.class, institutionId) : null;
        // TODO: check if course exists
        CourseSQL course = courseId != null ? entityManager.getReference(CourseSQL.class, courseId) : null;
        SpecialistSQL specialist = specialistId != null ? entityManager.getReference(SpecialistSQL.class, specialistId) : null;
        // TODO: add institution later
        var bodySQL = new TestSQL(body.getId(), null, course, visibility, specialist, body.getTitle(), body.getPublishDate());
        testSqlDAO.save(bodySQL);

        return body.getId();
    }

    @Override
    public void deleteTestById(String testId) throws NotFoundException {
        throw new UnsupportedOperationException("Unimplemented method 'deleteTestById'");
    }

    @Override
    public String duplicateTestById(String testId) throws NotFoundException{
        throw new UnsupportedOperationException("Unimplemented method 'duplicateTestById'");
    }

    @Override
    public void updateTest(String testId, Test body) {
        throw new UnsupportedOperationException("Unimplemented method 'updateTest'");
    }

    @Override
    public void automaticCorrection(String testId, String correctionType) {
        throw new UnsupportedOperationException("Unimplemented method 'automaticCorrection'");
    }

    @Override
    public Integer countStudentsTestResolutions(String testId, Boolean total) {
        throw new UnsupportedOperationException("Unimplemented method 'countStudentsTestResolutions'");
    }

    @Override
    public Page<TestResolution> getTestResolutions(String testId, Integer page, Integer itemsPerPage) throws NotFoundException{
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get test resolutions for test " + testId + ": couldn't find test with given id.");
        // TODO: not tested and not done
        return null;
    }

    @Override
    public TestResolution getTestResolutionById(String resolutionId) throws NotFoundException{
        TestResolution tr = resolutionDAO.findById(resolutionId).orElse(null);
        if (tr == null)
            throw new NotFoundException("Could not get test resolution: there is no resolution with the given identifier.");
        return tr;
    }

    @Override
    public String createTestResolution(String testId, TestResolution resolution) throws BadInputException, NotFoundException {
        // TODO: é suposto adicionar a resolução de um exercício, não de um teste inteiro

        // check test
        if (testId == null)
            throw new BadInputException("Cannot create test resolution: resolution must belong to a test.");
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot create test resolution: couldn't find test.");

        if (resolution == null)
            throw new BadInputException("Cannot create a test resolution with a 'null' body.");
        resolution.setTestId(testId);

        // TODO: check if test is ongoing
        // TODO: check visibility
        // TODO: check if student belongs to course

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
        TestSQL test = testId != null ? entityManager.getReference(TestSQL.class, testId) : null;
        StudentSQL student = studentId != null ? entityManager.getReference(StudentSQL.class, studentId) : null;
        var resolutionSql = new TestResolutionSQL(resolution.getId(), test, student);
        resolutionSqlDAO.save(resolutionSql);

        return resolution.getId();
    }

    @Override
    public Boolean canStudentSubmitResolution(String testId, String studentId) {
        // check test
        //if (testId == null)
        //    throw new BadInputException("Cannot create test resolution: resolution must belong to a test.");
        //if (!testDAO.existsById(testId))
        //    throw new BadInputException("Cannot create test resolution: resolution must belong to a valid test.");
        throw new UnsupportedOperationException("Unimplemented method 'canStudentSubmitResolution'");
    }

    @Override
    public Integer countStudentSubmissionsForTest(String testId, String studentId) throws NotFoundException{
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot count " + studentId + " submissions for test " + testId + ": couldn't find test with given id.");
        if (!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Cannot count " + studentId + " submissions for test " + testId + ": couldn't find student with given id.");
        return resolutionSqlDAO.countStudentSubmissionsForTest(studentId, testId);
    }

    @Override
    public List<String> getStudentTestResolutionsIds(String testId, String studentId) throws NotFoundException{
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get student " + studentId + " resolutions for test " + testId + ": couldn't find test with given id.");
        if (!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Cannot get student " + studentId + " resolutions for test " + testId + ": couldn't find student with given id.");
        return resolutionSqlDAO.getStudentTestResolutionsIds(testId, studentId);
    }

    @Override
    public TestResolution getStudentLastResolution(String testId, String studentId) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get student " + studentId + " last resolution for test " + testId + ": couldn't find test with given id.");
        if (!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Cannot get student " + studentId + " last resolution for test " + testId + ": couldn't find student with given id.");

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
