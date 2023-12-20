package pt.uminho.di.chalktyk.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Exercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolutionStatus;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Item;
import jakarta.transaction.Transactional;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.Comment;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ConcreteExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ShallowExercise;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.StringItem;
import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.models.nonrelational.tests.DeliverDateTest;
import pt.uminho.di.chalktyk.models.nonrelational.tests.LiveTest;
import pt.uminho.di.chalktyk.models.nonrelational.tests.Test;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestGroup;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolution;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolutionGroup;
import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolutionStatus;
import pt.uminho.di.chalktyk.models.relational.CourseSQL;
import pt.uminho.di.chalktyk.models.relational.InstitutionSQL;
import pt.uminho.di.chalktyk.models.relational.SpecialistSQL;
import pt.uminho.di.chalktyk.models.relational.StudentSQL;
import pt.uminho.di.chalktyk.models.relational.TagSQL;
import pt.uminho.di.chalktyk.models.relational.TestResolutionSQL;
import pt.uminho.di.chalktyk.models.relational.TestSQL;
import pt.uminho.di.chalktyk.models.relational.TestTagsPkSQL;
import pt.uminho.di.chalktyk.models.relational.TestTagsSQL;
import pt.uminho.di.chalktyk.models.relational.VisibilitySQL;
import pt.uminho.di.chalktyk.repositories.nonrelational.ExerciseResolutionDAO;
import pt.uminho.di.chalktyk.repositories.nonrelational.TestDAO;
import pt.uminho.di.chalktyk.repositories.nonrelational.TestResolutionDAO;
import pt.uminho.di.chalktyk.repositories.relational.TestResolutionSqlDAO;
import pt.uminho.di.chalktyk.repositories.relational.TestSqlDAO;
import pt.uminho.di.chalktyk.repositories.relational.TestTagsSqlDAO;
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
    private final TestTagsSqlDAO tagsSqlDAO;
    private final ExerciseResolutionDAO exerciseResolutionDAO;
    private final IInstitutionsService institutionsService;
    private final ISpecialistsService specialistsService;
    private final IStudentsService studentsService;
    private final ICoursesService coursesService;
    private final ITagsService tagsService;
    private final IExercisesService exercisesService;

    @Autowired
    public TestsService(EntityManager entityManager, TestDAO testDAO, TestSqlDAO testSqlDAO, TestResolutionDAO resolutionDAO, TestResolutionSqlDAO resolutionSqlDAO,
                        TestTagsSqlDAO tagsSqlDAO, ExerciseResolutionDAO exerciseResolutionDAO, ISpecialistsService specialistsService, IStudentsService studentsService, 
                        IInstitutionsService institutionsService, ICoursesService coursesService, ITagsService tagsService, IExercisesService exercisesService){
        this.entityManager = entityManager;
        this.testDAO = testDAO;
        this.testSqlDAO = testSqlDAO;
        this.resolutionDAO = resolutionDAO;
        this.resolutionSqlDAO = resolutionSqlDAO;
        this.tagsSqlDAO = tagsSqlDAO;
        this.exerciseResolutionDAO = exerciseResolutionDAO;
        this.specialistsService = specialistsService;
        this.studentsService = studentsService;
        this.institutionsService = institutionsService;
        this.coursesService = coursesService;
        this.tagsService = tagsService;
        this.exercisesService = exercisesService;
    }


    /**
     * Retrieves tests that match the given filters. Necessary to check authorization.
     *
     * @param page
     * @param itemsPerPage   maximum items in a page
     * @param tags           Array of identifiers from the tags that will be used to filter the tests
     * @param matchAllTags   Value that defines if the exercise must have all the given tags to be retrieved
     * @param visibilityType Describes the type of visibility that the tests must have.  This parameter must be paired with the parameter 'visibilityTarget'  when the value is either 'institution' or 'course'.
     * @param specialistId
     * @param courseId       to search for a test from a specific course
     * @param institutionId  to search for a test from a specific institution
     * @param title          to search for a test title
     * @param verifyParams   if 'true' then verify if parameters exist in the database (example: verify if specialist exists),
     *                       *                         'false' does not verify database logic
     * @return page of tests
     **/
    @Override
    public List<Test> getTests(Integer page, Integer itemsPerPage, List<String> tags, Boolean matchAllTags, String visibilityType, String specialistId, String courseId, String institutionId, String title, boolean verifyParams) throws BadInputException, NotFoundException {
        //Visibility visibility= null;
        //if (visibilityType != null) {
        //    visibility = Visibility.fromValue(visibilityType);
        //    if (visibility == null)
        //        throw new BadInputException("Visibility type not found");
        //}

        if(verifyParams && courseId!=null) {
            if(!coursesService.existsCourseById(courseId))
                throw new NotFoundException("Theres no course with the given id");
        }

        if(verifyParams && institutionId!=null) {
            if(!institutionsService.existsInstitutionById(institutionId))
                throw new NotFoundException("Theres no institution with the given id");
        }

        if (verifyParams && specialistId != null) {
            if(!specialistsService.existsSpecialistById(specialistId))
                throw new NotFoundException("Theres no specialist with the given id");
        }
        //Page<TestSQL> testSQLS = testSqlDAO.getTests(PageRequest.of(page, itemsPerPage),tags, tags.size(), matchAllTags,visibility,institutionId,courseId,specialistId,title);
        //return exercisesSqlToNoSql(testSQLS);
        return null;
    }

    /**
     * Converts a page of TestSQL to a list of Tests(NoSQL)s
     * @param testSQLPage page of testes
     * @return a list of Test(NoSQL)s
     * @throws NotFoundException if one of the testes on the page, was not found
     */
    private List<Test> exercisesSqlToNoSql(Page<TestSQL> testSQLPage) throws NotFoundException {
        List<Test> testList = new ArrayList<>();
        for(var test : testSQLPage)
            testList.add(this.getTestById(test.getId()));
        return testList;
    }

    @Override
    public Test getTestById(String testId) throws NotFoundException{
        Test t = testDAO.findById(testId).orElse(null);
        if (t == null)
            throw new NotFoundException("Could not get test: there is no test with the given identifier.");
        return t;
    }

    @Override
    @Transactional
    public String createTest(VisibilitySQL visibility, Test body) throws BadInputException, NotFoundException {
        if (body == null)
            throw new BadInputException("Cannot create test: test is null.");

        body.verifyProperties();
        body.setId(null);                       // to prevent overwrite attacks
        body.setCreationDate(LocalDateTime.now()); // set creation date

        // check visibility
        if (visibility == null)
            throw new BadInputException("Can't create test: Visibility can't be null");
        if (!visibility.isValid())
            throw new BadInputException("Can't create test: Visibility is invalid");

        // check owner (specialist) id
        String specialistId = body.getSpecialistId();
        if (specialistId == null)
            throw new BadInputException("Can't create test: A test must have an owner.");
        if (!specialistsService.existsSpecialistById(specialistId))
            throw new BadInputException("Can't create test: A test must have a valid specialist.");

        // get owner's institution id
        try {
            Institution inst = institutionsService.getSpecialistInstitution(specialistId);
            // sets identifier of the institution
            if (inst != null)
                body.setInstitutionId(inst.getName());
            else
                body.setInstitutionId(null);
        }
        catch (NotFoundException ignored){}

        // check course
        String courseId = body.getCourseId();
        if (courseId != null){
            if (!coursesService.existsCourseById(courseId))
                throw new BadInputException("Can't create test: A test must belong to a valid course.");

            // check if specialist belongs to course
            try{ coursesService.checkSpecialistInCourse(courseId, specialistId); }
            catch (NotFoundException nfe){ throw new BadInputException("Can't create test: The test's owner must belong to the course specified."); } 
        }

        // check visibility constraints
        if (body.getInstitutionId() == null && visibility.equals(VisibilitySQL.INSTITUTION))
            throw new BadInputException("Can't create test: can't set visibility to INSTITUTION");
        if (body.getCourseId() == null && visibility.equals(VisibilitySQL.COURSE))
            throw new BadInputException("Can't create test: can't set visibility to COURSE");
        
        // check test group
        // TODO: check if any exercise appears more than onceq
        Map<String, Integer> tagsCounter = new HashMap<>();
        List<TestGroup> groups = body.getGroups();
        if (groups != null){
            for (TestGroup tg: groups){
                tg.verifyProperties();
                List<Exercise> exeList = tg.getExercises();
                if (exeList != null){
                    for (Exercise exe: exeList){
                        if (exe instanceof ConcreteExercise ce){
		    	    	    ce.verifyProperties();
                            Set<TagSQL> tags = ce.getTags();
                            if (tags != null){
                                for (TagSQL tag : tags){
                                    if (tagsService.getTagById(tag.getId()) == null)
                                        throw new BadInputException("Can't create test: There is not tag with id \"" + tag.getId() + "\".");
                                    if (tagsCounter.containsKey(tag.getId()))
                                        tagsCounter.put(tag.getId(), tagsCounter.get(tag.getId()) + 1);
                                    else
                                        tagsCounter.put(tag.getId(), 1);
                                }
                            }
                        }
		    	        if (exe instanceof ShallowExercise se){
                            // TODO: create new exercise?

		    	    	    se.verifyInsertProperties();
                            Exercise og = exercisesService.getExerciseById(se.getOriginalExerciseId());
                            while(!(og instanceof ConcreteExercise ce)){
                                og = exercisesService.getExerciseById(se.getOriginalExerciseId());
                            }
                            ConcreteExercise ogExe = (ConcreteExercise) og;
                            ogExe.verifyProperties();
                            Set<TagSQL> tags = ogExe.getTags();
                            if (tags != null){
                                for (TagSQL tag : tags){
                                    if (tagsService.getTagById(tag.getId()) == null)
                                        throw new BadInputException("Can't create test: There is not tag with id \"" + tag.getId() + "\".");
                                    if (tagsCounter.containsKey(tag.getId()))
                                        tagsCounter.put(tag.getId(), tagsCounter.get(tag.getId()) + 1);
                                    else
                                        tagsCounter.put(tag.getId(), 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        // TODO: check points in groups

        // persist the test in nosql database
        body = testDAO.save(body);

        // persists the test in sql database
        InstitutionSQL inst = body.getInstitutionId() != null ? entityManager.getReference(InstitutionSQL.class, body.getInstitutionId()) : null;
        CourseSQL course = courseId != null ? entityManager.getReference(CourseSQL.class, courseId) : null;
        SpecialistSQL specialist = specialistId != null ? entityManager.getReference(SpecialistSQL.class, specialistId) : null;
        TestSQL bodySQL = new TestSQL(body.getId(), inst, course, visibility, specialist, body.getTitle(), body.getPublishDate());
        testSqlDAO.save(bodySQL);
        createTestTags(tagsCounter, bodySQL);

        return body.getId();
    }

    private void createTestTags(Map<String, Integer> tags, TestSQL test){
        for (Map.Entry<String, Integer> entry: tags.entrySet()){
            TagSQL tag = tagsService.getTagById(entry.getKey());
            TestTagsPkSQL testTagPK = new TestTagsPkSQL(tag, test);
            TestTagsSQL testTag = new TestTagsSQL(entry.getValue(), testTagPK, entry.getKey(), test.getId());
            tagsSqlDAO.save(testTag);
        }
    }

    @Override
    public void deleteTestById(String testId) throws NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't delete test \'" + testId + "\': test was not found in the non-relational database");
        TestSQL testSQL = testSqlDAO.findById(testId).orElse(null);
        if (testSQL == null)
            throw new NotFoundException("Couldn't delete test \'" + testId + "\': test was not found in the relational database");

        List<TestResolutionSQL> trs = resolutionSqlDAO.getTestResolutions(testId);
        if (trs != null){
            for (TestResolutionSQL tr: trs){
                deleteTestResolutionById(tr.getId());
            }
        }

        List<TestTagsSQL> tags = tagsSqlDAO.getTestTags(testId);
        if (tags != null){
            for (TestTagsSQL tag: tags){
                tagsSqlDAO.delete(tag);
            }
        }

        // TODO: delete test exercises

        testDAO.delete(test);
        testSqlDAO.delete(testSQL);
    }

    @Override
    @Transactional
    public String duplicateTestById(String specialistId, String testId) throws BadInputException, NotFoundException {
        // check if the specialist exists
        if (!specialistsService.existsSpecialistById(specialistId))
            throw new NotFoundException("Can't duplicate test: couldn't find specialist with id \'" + specialistId + "\' .");
        // check if the test exists
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Can't duplicate test: couldn't find test with id \'" + testId + "\' .");
        
        // gets specialists institution
        String institutionId = null;
        Institution institution = institutionsService.getSpecialistInstitution(specialistId);
        if(institution != null)
            institutionId = institution.getName();

        // fetch original models
        Test og = getTestById(testId);
        if (og == null)
            throw new NotFoundException("Can't duplicate test: couldn't fetch test with id \'" + testId + "\' from non-relational database.");
        TestSQL ogSQL = testId != null ? entityManager.getReference(TestSQL.class, testId) : null;
        if (ogSQL == null)
            throw new NotFoundException("Can't duplicate test: couldn't find test with id \'" + testId + "\' from relational database.");

        // TODO: what to do about publish date and course
        Test newTest = new Test(null, specialistId, institutionId, og.getCourseId(), og.getTitle(), og.getGlobalInstructions(), og.getGlobalPoints(), 
                                og.getConclusion(), LocalDateTime.now(), null, null);

        // persist the test in nosql database
        newTest = testDAO.save(newTest);

        // persists the test in sql database
        InstitutionSQL inst = newTest.getInstitutionId() != null ? entityManager.getReference(InstitutionSQL.class, newTest.getInstitutionId()) : null;
        CourseSQL course = newTest.getCourseId() != null ? entityManager.getReference(CourseSQL.class, newTest.getCourseId()) : null;
        SpecialistSQL specialist = specialistId != null ? entityManager.getReference(SpecialistSQL.class, specialistId) : null;
        // TODO: what to do about visibility
        TestSQL newTestSQL = new TestSQL(newTest.getId(), inst, course, VisibilitySQL.PRIVATE, specialist, newTest.getTitle(), newTest.getPublishDate());
        testSqlDAO.save(newTestSQL);
        
        duplicateExercises(og, newTest);
        List<TestTagsSQL> tags = tagsSqlDAO.getTestTags(testId);
        for (TestTagsSQL tmp: tags){
            TagSQL tag = tagsService.getTagById(tmp.getTagId());
            TestTagsPkSQL testTagPK = new TestTagsPkSQL(tag, newTestSQL);
            TestTagsSQL testTag = new TestTagsSQL(tmp.getNExercises(), testTagPK, tag.getId(), newTest.getId());
            tagsSqlDAO.save(testTag);
        }

        return newTest.getId();
    }

    @Transactional
    private void duplicateExercises(Test oldTest, Test newTest) throws BadInputException, NotFoundException {
        List<TestGroup> groups = oldTest.getGroups();
        List<TestGroup> newGroup = new ArrayList<>();

        if (groups != null){
            for (TestGroup tg: groups){
                tg.verifyProperties();
                List<Exercise> exeList = tg.getExercises();
                List<Exercise> newExeList = new ArrayList<>();

                if (exeList != null){
                    for (Exercise exe: exeList){
                        // duplicate exercise
                        String cloneId = exercisesService.duplicateExerciseById(exe.getSpecialistId(), exe.getId());
                        Exercise cloneExe = exercisesService.getExerciseById(cloneId);
                        if (cloneExe != null)
                            newExeList.add(cloneExe);
                    }
                }

                TestGroup newTg = new TestGroup(newExeList, tg.getGroupInstructions(), tg.getGroupPoints());
                newGroup.add(newTg);
            }
        }

        newTest.setGroups(newGroup);
        testDAO.save(newTest);
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
    public Integer countStudentsTestResolutions(String testId, Boolean total) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get test resolutions for test " + testId + ": couldn't find test with given id.");
        if (total)
            return resolutionSqlDAO.countTotalSubmissionsForTest(testId);
        else
            return resolutionSqlDAO.countDistinctSubmissionsForTest(testId);
    }

    @Override
    public List<TestResolution> getTestResolutions(String testId, Integer page, Integer itemsPerPage) throws NotFoundException{
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get test resolutions for test " + testId + ": couldn't find test with given id.");

        return resolutionsSQLtoNoSQL(resolutionSqlDAO.getTestResolutions(testId, PageRequest.of(page, itemsPerPage)));
    }

    @Override
    public TestResolution getTestResolutionById(String resolutionId) throws NotFoundException{
        TestResolution tr = resolutionDAO.findById(resolutionId).orElse(null);
        if (tr == null)
            throw new NotFoundException("Could not get test resolution: there is no resolution with the given identifier.");
        return tr;
    }

    @Override
    @Transactional
    public String startTest(String testId, String studentId) throws BadInputException, NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Can't start test: couldn't find test with id \'" + testId + "\'");
        
        TestResolution resolution = new TestResolution(null, studentId, testId, TestResolutionStatus.ONGOING, 
                                                        LocalDateTime.now(), null, 0, null, null);
        String resId = createTestResolution(testId, resolution);
        List<TestGroup> tgs = test.getGroups();
        if (tgs != null){
            List<TestResolutionGroup> trgs = new ArrayList<>();
            for(TestGroup tg: tgs){
                List<ExerciseResolution> ers = new ArrayList<>();
                List<Exercise> exes = tg.getExercises();
                if (exes != null){
                    for(Exercise exe: exes){
                        ExerciseResolution er = exercisesService.createEmptyExerciseResolution(studentId, exe.getId(), resId);
                        ers.add(er);
                    }
                }
                trgs.add(new TestResolutionGroup(ers, null));
            }

            resolution.setGroups(trgs);
            resolutionDAO.save(resolution);
        }
        
        return resId;
    }

    @Override
    public String createTestResolution(String testId, TestResolution resolution) throws BadInputException, NotFoundException {
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
        // TODO: check course and visibility
        // TODO: check if student belongs to course
        // TODO: check resolution status
        // TODO: check points in groups

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

        // Persists the test resolution in SQL database
        TestSQL test = testId != null ? entityManager.getReference(TestSQL.class, testId) : null;
        StudentSQL student = studentId != null ? entityManager.getReference(StudentSQL.class, studentId) : null;
        TestResolutionSQL resolutionSql = new TestResolutionSQL(resolution.getId(), test, student);
        resolutionSqlDAO.save(resolutionSql);

        return resolution.getId();
    }

    @Override
    @Transactional
    public void deleteTestResolutionById(String resolutionId) throws NotFoundException {
        TestResolution resolution = resolutionDAO.findById(resolutionId).orElse(null);
        TestResolutionSQL resolutionSQL = resolutionSqlDAO.findById(resolutionId).orElse(null);
        if (resolutionSQL == null)
            throw new NotFoundException("Couldn't delete resolution: Resolution \'" + resolutionId + "\'was not found");

        exercisesService.deleteAllExerciseResolutionByTestResolutionId(resolutionId);
        resolutionDAO.delete(resolution);
        resolutionSqlDAO.delete(resolutionSQL);
    }

    @Override
    public void updateTestResolution(String testResId, TestResolution resolution) throws BadInputException, NotFoundException {
        TestResolution tr = getTestResolutionById(testResId);
        
        if (resolution != null){
            resolutionDAO.save(tr);
        }
    }

    @Override
    @Transactional
    public Boolean canStudentSubmitResolution(String testId, String studentId) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Can't check if student \'" + studentId + "\' can make a submission for test \'" + testId + "\'': couldn't find test with given id.");
        if (!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Can't check if student \'" + studentId + "\' can make a submission for test \'" + testId + "\'': couldn't find student with given id.");

        Test test = getTestById(testId);
        if (test == null)
            throw new NotFoundException("Can't check if student \'" + studentId + "\' can make a submission for test \'" + testId + "\'': couldn't fetch non relational test with given id.");
        TestSQL testSQL = entityManager.getReference(TestSQL.class, testId);
        if (testSQL == null)
            throw new NotFoundException("Can't check if student \'" + studentId + "\' can make a submission for test \'" + testId + "\'': couldn't fetch relational test with given id.");

        VisibilitySQL vis = testSQL.getVisibility();
        if (vis.equals(VisibilitySQL.PRIVATE)){ return false; }
        else if (vis.equals(VisibilitySQL.COURSE)){
            if (!coursesService.checkStudentInCourse(test.getCourseId(), studentId))
                return false;
        }
        else if (vis.equals(VisibilitySQL.INSTITUTION)){
            if (!institutionsService.isStudentOfInstitution(studentId, test.getInstitutionId()))
                return false;
        }
        // TODO: the fuck is "TEST" visibility

        // check time constraints
        if (test.getPublishDate().isAfter(LocalDateTime.now()))
            return false;
        if (test instanceof DeliverDateTest ddt){
            if (LocalDateTime.now().isAfter(ddt.getDeliverDate()))
                return false;
        }
        else if (test instanceof LiveTest lt){
            if (lt.getStartDate().isAfter(LocalDateTime.now()) ||
                LocalDateTime.now().isAfter(lt.getStartDate().plus(lt.getDuration()).plus(lt.getStartTolerance())))
                return false;
        }

        return true;
    }

    @Override
    public Integer countStudentSubmissionsForTest(String testId, String studentId) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot count " + studentId + " submissions for test " + testId + ": couldn't find test with given id.");
        if (!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Cannot count " + studentId + " submissions for test " + testId + ": couldn't find student with given id.");
        return resolutionSqlDAO.countStudentSubmissionsForTest(studentId, testId);
    }

    @Override
    public List<String> getStudentTestResolutionsIds(String testId, String studentId) throws NotFoundException {
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

    @Override
	public void manualCorrectionForExercise(String exeResId, String testResId, Float points, String comment) throws NotFoundException {
        ExerciseResolution er = exercisesService.getExerciseResolution(exeResId);
        er.setStatus(ExerciseResolutionStatus.REVISED);
        er.setPoints(points);
        Comment c = null;
        if (comment != null){
            List<Item> items = new ArrayList<>();
            StringItem si = new StringItem(comment);
            items.add(si);
            c = new Comment(items);
            er.setComment(c);
        }
        exerciseResolutionDAO.save(er);

        TestResolution tr = getTestResolutionById(testResId);
        List<TestResolutionGroup> trgs = tr.getGroups();
        for (TestResolutionGroup trg: trgs){
            for (ExerciseResolution exeRes: trg.getResolutions()){
                if (er.getId().equals(exeRes.getId())){
                    exeRes.setPoints(points);
                    exeRes.setStatus(ExerciseResolutionStatus.REVISED);
                    if (comment != null)
                        er.setComment(c);

                    if (trg.getGroupPoints() != null)
                        trg.setGroupPoints(trg.getGroupPoints() + points);
                    else
                        trg.setGroupPoints(points);

                    break;
                }
            }
        }

        resolutionDAO.save(tr);
	}

    @Override
    public void uploadResolution(String testResId, String exeId, ExerciseResolution resolution)
            throws NotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uploadResolution'");
    }

    @Override
    public String deleteExerciseFromTest(String exerciseId) throws NotFoundException {
        //TODO
        // cant be done if after publish date
        // if there is no publish date, then, resolutions of the exercise must be deleted
        return null;
    }

    @Override
    public String removeExerciseFromTest(String exerciseId) throws NotFoundException{
        //TODO
        // changes visibility of exercise from TEST to private
        // cant be done if after publish date
        return null;
    }

    
    /* **** Auxiliary methods **** */

    private List<TestResolution> resolutionsSQLtoNoSQL(Page<TestResolutionSQL> resPage) throws NotFoundException {
        List<TestResolution> resList = new ArrayList<>();

        for(var res : resPage)
            resList.add(getTestResolutionById(res.getId()));

        return resList;
    }
}
