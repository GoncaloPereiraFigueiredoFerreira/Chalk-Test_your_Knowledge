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
import pt.uminho.di.chalktyk.models.relational.Course;
import pt.uminho.di.chalktyk.models.relational.Specialist;
import pt.uminho.di.chalktyk.models.relational.Visibility;
import pt.uminho.di.chalktyk.repositories.nonrelational.TestDAO;
import pt.uminho.di.chalktyk.repositories.relational.TestSqlDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service("testsService")
public class TestsService implements ITestsService {
    
    @PersistenceContext
    private final EntityManager entityManager;
    private final TestDAO testDAO;
    private final TestSqlDAO testSqlDAO;
    private final ISpecialistsService specialistsService;
    //private final IInstitutionsService institutionsService;
    private final ICoursesService coursesService;
    private final ITestResolutionsService resolutionsService;

    @Autowired
    public TestsService(EntityManager entityManager, TestDAO testDAO, TestSqlDAO testSqlDAO, ISpecialistsService specialistsService, 
            /*IInstitutionsService institutionsService,*/ ICoursesService coursesService, ITestResolutionsService resolutionsService){
        this.entityManager = entityManager;
        this.testDAO = testDAO;
        this.testSqlDAO = testSqlDAO;
        this.specialistsService = specialistsService;
        //this.institutionsService = institutionsService;
        this.coursesService = coursesService;
        this.resolutionsService = resolutionsService;
    }

    @Override
    public Page<Test> getTests(Integer page, Integer itemsPerPage, List<Integer> tags, Boolean matchAllTags,
            String visibilityType, String visibilityTarget, String specialistId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTests'");
    }

    @Override
    public String createTest(Visibility visibility, Test body) throws BadInputException {
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
        Course course = courseId != null ? entityManager.getReference(Course.class, courseId) : null;
        Specialist specialist = specialistId != null ? entityManager.getReference(Specialist.class, specialistId) : null;
        // TODO: add institution later
        var bodySQL = new pt.uminho.di.chalktyk.models.relational.Test(body.getId(), null, course, visibility, specialist, body.getTitle(), body.getPublishDate());
        testSqlDAO.save(bodySQL);

        return body.getId();
    }

    @Override
    public TestResolution getTestResolutionById(String resolutionId) throws NotFoundException{
        return resolutionsService.getTestResolutionById(resolutionId);
    }

    @Override
    public void deleteTestById(String testId) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteTestById'");
    }

    @Override
    public String duplicateTestById(String testId) {
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
    public Page<TestResolution> getTestResolutions(String testId, Integer page, Integer itemsPerPage) {
        throw new UnsupportedOperationException("Unimplemented method 'getTestResolutions'");
    }

    @Override
    public void createTestResolution(String testId, TestResolution body) throws BadInputException {
        // check test
        if (testId == null)
            throw new BadInputException("Cannot create test resolution: resolution must belong to a test.");
        if (!testDAO.existsById(testId))
            throw new BadInputException("Cannot create test resolution: resolution must belong to a valid test.");
        body.setTestId(testId);

        // TODO: check visibility
        // TODO: check if student belongs to course

        resolutionsService.createTestResolution(body);
    }

    @Override
    public Boolean canStudentSubmitResolution(String testId, String studentId) {
        throw new UnsupportedOperationException("Unimplemented method 'canStudentSubmitResolution'");
    }

    @Override
    public Integer countStudentSubmissionsForTest(String testId, String studentId) {
        return resolutionsService.countStudentSubmissionsForTest(testId, studentId);
    }

    @Override
    public List<String> getStudentTestResolutionsIds(String testId, String studentId) {
        return resolutionsService.getStudentTestResolutionsIds(testId, studentId);
    }

    @Override
    public TestResolution getStudentLastResolution(String testId, String studentId) {
        throw new UnsupportedOperationException("Unimplemented method 'getStudentLastResolution'");
    }    
}