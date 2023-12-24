package pt.uminho.di.chalktyk.services;

import java.time.LocalDateTime;
import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.exercises.*;
import pt.uminho.di.chalktyk.models.exercises.items.Item;
import pt.uminho.di.chalktyk.models.exercises.items.StringItem;
import pt.uminho.di.chalktyk.models.institutions.Institution;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.*;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ConcreteExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.ReferenceExercise;
import pt.uminho.di.chalktyk.models.tests.TestExercise.TestExercise;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.repositories.ExerciseResolutionDAO;
import pt.uminho.di.chalktyk.repositories.TestDAO;
import pt.uminho.di.chalktyk.repositories.TestResolutionDAO;
import pt.uminho.di.chalktyk.repositories.TestTagsDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service("testsService")
public class TestsService implements ITestsService {
    
    @PersistenceContext
    private final EntityManager entityManager;
    private final TestDAO testDAO;
    private final TestResolutionDAO resolutionDAO;
    private final TestTagsDAO testTagsDAO;
    private final ExerciseResolutionDAO exerciseResolutionDAO;
    private final IInstitutionsService institutionsService;
    private final ISpecialistsService specialistsService;
    private final IStudentsService studentsService;
    private final ICoursesService coursesService;
    private final ITagsService tagsService;
    private final IExercisesService exercisesService;

    @Autowired
    public TestsService(EntityManager entityManager, TestDAO testDAO, TestResolutionDAO resolutionDAO, TestTagsDAO testTagsDAO, ExerciseResolutionDAO exerciseResolutionDAO, ISpecialistsService specialistsService, IStudentsService studentsService,
                        IInstitutionsService institutionsService, ICoursesService coursesService, ITagsService tagsService, IExercisesService exercisesService){
        this.entityManager = entityManager;
        this.testDAO = testDAO;
        this.resolutionDAO = resolutionDAO;
        this.testTagsDAO = testTagsDAO;
        this.exerciseResolutionDAO = exerciseResolutionDAO;
        this.specialistsService = specialistsService;
        this.studentsService = studentsService;
        this.institutionsService = institutionsService;
        this.coursesService = coursesService;
        this.tagsService = tagsService;
        this.exercisesService = exercisesService;
    }

    
    @Override
    public Page<Test> getTests(Integer page, Integer itemsPerPage, List<String> tags, Boolean matchAllTags, String visibilityType, String specialistId, String courseId, String institutionId, String title, boolean verifyParams) throws BadInputException, NotFoundException {
        Visibility visibility;
        if (visibilityType != null) {
            visibility = Visibility.fromValue(visibilityType);
            if (visibility == null)
                throw new BadInputException("Visibility type not found");
        }

        if(verifyParams && courseId!=null) {
            if(!coursesService.existsCourseById(courseId))
                throw new NotFoundException("There is no course with the given id");
        }

        if(verifyParams && institutionId!=null) {
            if(!institutionsService.existsInstitutionById(institutionId))
                throw new NotFoundException("There is no institution with the given id");
        }

        if (verifyParams && specialistId != null) {
            if(!specialistsService.existsSpecialistById(specialistId))
                throw new NotFoundException("There is no specialist with the given id");
        }

        //TODO - getTests
        //Page<TestSQL> testSQLS = testSqlDAO.getTests(PageRequest.of(page, itemsPerPage),tags, tags.size(), matchAllTags,visibility,institutionId,courseId,specialistId,title);
        //return exercisesSqlToNoSql(testSQLS);
        return null;
    }

    @Override
    public Test getTestById(String testId) throws NotFoundException {
        Test t = testDAO.findById(testId).orElse(null);
        if (t == null)
            throw new NotFoundException("Could not get test: there is no test with the given identifier.");
        return t;
    }

    @Override
    @Transactional
    public String createTest(Test body) throws BadInputException, NotFoundException {
        if (body == null)
            throw new BadInputException("Cannot create test: test is null.");

        body.calculatePoints(); // updates global/group points
        body.setCreationDate(LocalDateTime.now()); // set creation date
        body.verifyProperties();
        body.setId(null);                       // to prevent overwrite attacks

        // check owner (specialist) id
        String specialistId = body.getSpecialistId();
        if (specialistId == null)
            throw new BadInputException("Can't create test: A test must have an owner.");
        if (!specialistsService.existsSpecialistById(specialistId))
            throw new BadInputException("Can't create test: A test must have a valid specialist.");

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
        Visibility visibility = body.getVisibility();
        if (visibility == null)
            throw new BadInputException("Can't create test: Visibility can't be null");
        if (body.getInstitutionId() == null && body.getVisibility().equals(Visibility.INSTITUTION))
            throw new BadInputException("Can't create test: can't set visibility to INSTITUTION");
        if (body.getCourseId() == null && body.getVisibility().equals(Visibility.COURSE))
            throw new BadInputException("Can't create test: can't set visibility to COURSE");

        // count tags
        // check and duplicate exercises
        Map<String, Integer> tagsCounter = new HashMap<>();
        List<TestExercise> exercises = body.getGroups().values().stream().flatMap(g -> g.getExercises().values().stream()).toList();
        for(TestExercise te : exercises){
            Exercise exe = null;
            if(te instanceof ReferenceExercise re){
                // Duplicates the exercise
                String dupExerciseId = exercisesService.duplicateExerciseById(specialistId, re.getId());
                exe = exercisesService.getExerciseById(dupExerciseId);
                exe.setVisibility(Visibility.TEST);
                exe.setPoints(re.getPoints());
                exercisesService.updateExerciseBody(exe, false, false);
            }
            else if (te instanceof ConcreteExercise ce){
                exe = ce.getExercise();
            }
            Set<Tag> tags = exe.getTags();
            if (tags != null){
                for (Tag tag : tags){
                    if (tagsService.getTagById(tag.getId()) == null)
                        throw new BadInputException("Can't create test: There isn't a tag with id \"" + tag.getId() + "\".");
                    if (tagsCounter.containsKey(tag.getId()))
                        tagsCounter.put(tag.getId(), tagsCounter.get(tag.getId()) + 1);
                    else
                        tagsCounter.put(tag.getId(), 1);
                }
            }
        }

        // set course
        Course course = courseId != null ? entityManager.getReference(Course.class, courseId) : null;
        body.setCourse(course);

        // set specialist
        Specialist specialist = entityManager.getReference(Specialist.class, specialistId);
        body.setSpecialist(specialist);

        // get owner's institution id
        try {
            Institution inst = institutionsService.getSpecialistInstitution(specialistId);
            body.setInstitution(inst);
        }
        catch (NotFoundException ignored){}

        body = testDAO.save(body);
        createTestTags(tagsCounter, body);

        return body.getId();
    }

    private void createTestTags(Map<String, Integer> tags, Test test){
        for (Map.Entry<String, Integer> entry: tags.entrySet()){
            Tag tag = tagsService.getTagById(entry.getKey());
            TestTagPK testTagPK = new TestTagPK(tag, test);
            TestTag testTag = new TestTag(entry.getValue(), testTagPK);
            testTagsDAO.save(testTag);
        }
    }

    @Override
    public void deleteTestById(String testId) throws NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't delete test \'" + testId + "\': test was not found in the non-relational database");
        Test testSQL = testDAO.findById(testId).orElse(null);
        if (testSQL == null)
            throw new NotFoundException("Couldn't delete test \'" + testId + "\': test was not found in the relational database");

        List<TestResolution> trs = resolutionDAO.getTestResolutions(testId);
        if (trs != null){
            for (TestResolution tr: trs){
                deleteTestResolutionById(tr.getId());
            }
        }

        List<TestTag> tags = testTagsDAO.getTestTags(testId);
        if (tags != null){
            testTagsDAO.deleteAll(tags);
        }

        // TODO: delete test exercises
        testDAO.delete(test);
    }

    @Override
    @Transactional
    public String duplicateTestById(String specialistId, String testId) throws BadInputException, NotFoundException {
        // check if the specialist exists
        if (!specialistsService.existsSpecialistById(specialistId))
            throw new NotFoundException("Can't duplicate test: couldn't find specialist with id \'" + specialistId + "\' .");
        Specialist specialist = specialistsService.getSpecialistById(specialistId);
        assert specialist!=null;
        // check if the test exists
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Can't duplicate test: couldn't find test with id \'" + testId + "\' .");

        // fetch original models
        Test ogTest = getTestById(testId);
        if (ogTest == null)
            throw new NotFoundException("Can't duplicate test: couldn't fetch test with id \'" + testId + "\' from database.");
        ogTest.setId(null);
        ogTest.setSpecialist(specialist);
        ogTest.setVisibility(Visibility.PRIVATE);
        ogTest.setInstitution(specialist.getInstitution());
        ogTest.setCourse(null);

        duplicateExercises(specialistId,ogTest);
        Test newTest = testDAO.save(ogTest);

        List<TestTag> tags = testTagsDAO.getTestTags(testId);
        for (TestTag tmp: tags){
            Tag tag = tagsService.getTagById(tmp.getTestTagPK().getTag().getId());
            TestTagPK testTagPK = new TestTagPK(tag, ogTest);
            TestTag testTag = new TestTag(tmp.getNExercises(), testTagPK);
            testTagsDAO.save(testTag);
        }
        return newTest.getId();
    }

    @Transactional
    protected void duplicateExercises(String specialistId, Test oldTest) throws NotFoundException {
        Map<Integer,TestGroup> groups = oldTest.getGroups();
        Map<Integer,TestGroup> newGroups = new HashMap<>();
        if (groups != null){
            for (Map.Entry<Integer,TestGroup> entryGroups: groups.entrySet()){

                TestGroup olgTg = entryGroups.getValue();
                /* 
                Map<Integer, List<String>> exeIdMap = olgTg.getExercises();
                Map<Integer, List<String>> newExeMap = new HashMap<>();

                if (exeIdMap != null){
                    for (Map.Entry<Integer,List<String>> entryIds: exeIdMap.entrySet()){
                        // duplicate exercise
                        List<String> newList = new ArrayList<>();
                        for (String exeId:entryIds.getValue()){
                            String cloneId = exercisesService.duplicateExerciseById(specialistId,exeId);
                            newList.add(cloneId);
                        }
                        newExeMap.put(entryIds.getKey(),newList);
                    }
                }

                TestGroup newTg = new TestGroup(olgTg.getGroupInstructions(), olgTg.getGroupPoints(), newExeMap);
                newGroups.put(entryGroups.getKey(),newTg);
                */
            }
        }
        oldTest.setGroups(newGroups);
    }

    @Override
    public void updateTestTitle(String testId, String title) throws NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test title: couldn't find test with id \'" + testId + "\'");
        test.setTitle(title);
        testDAO.save(test);
    }

    @Override
    public void updateTestGlobalInstructions(String testId, String globalInstructions) throws NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test global instructions: couldn't find test with id \'" + testId + "\'");
        test.setGlobalInstructions(globalInstructions);
        testDAO.save(test);
    }

    @Override
    public void updateTestConclusion(String testId, String conclusion) throws NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test conclusion: couldn't find test with id \'" + testId + "\'");
        test.setConclusion(conclusion);
        testDAO.save(test);
    }

    @Override
    public void updateTestPublishDate(String testId, LocalDateTime publishDate) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test publish date: couldn't find test with id \'" + testId + "\'");

        if (test.getCreationDate().isAfter(publishDate))
            throw new BadInputException("Couldn't update test publish date: publish date occurs before creation date");

        test.setPublishDate(publishDate);
        testDAO.save(test);
    }


    @Override
    public void updateTestVisibility(String testId, Visibility visibility) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test visibility: couldn't find test with id \'" + testId + "\'");

        Specialist specialist = test.getSpecialist();
        // check course constraints
        Course course = test.getCourse();
        if (course == null && visibility.equals(Visibility.COURSE))
            throw new BadInputException("Couldn't update test visibility: course is null");
        if (course != null && specialist != null && visibility.equals(Visibility.COURSE)){
            if (!coursesService.checkSpecialistInCourse(course.getId(), specialist.getId()))
                throw new BadInputException("Couldn't update test visibility: current specialist must belong to course");
        }

        // check institution constraints
        Institution institution = test.getInstitution();
        if (institution == null && visibility.equals(Visibility.INSTITUTION))
            throw new BadInputException("Couldn't update test visibility: institution is null");
        if (institution != null && specialist != null && visibility.equals(Visibility.INSTITUTION)){
            if (!institutionsService.isSpecialistOfInstitution(specialist.getId(), institution.getName()))
                throw new BadInputException("Couldn't update test visibility: current specialist must belong to institution");
        }
        
        test.setVisibility(visibility);
        testDAO.save(test);
    }

    @Override
    public void updateTestSpecialist(String testId, String specialistId) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test specialist: couldn't find test with id \'" + testId + "\'");

        // retrieve specialist
        Specialist specialist = specialistsService.getSpecialistById(specialistId);
        if (specialist == null)
            throw new NotFoundException("Couldn't update test specialist: couldn't find specialist with id \'" + specialistId + "\'");
        
        // check course constraints
        Course course = test.getCourse();
        if (course != null && !coursesService.checkSpecialistInCourse(course.getId(), specialistId))
            throw new BadInputException("Couldn't update test specialist: specialist must belong to course");

        // check institution constraints
        Institution institution = test.getInstitution();
        if (institution != null && !institutionsService.isSpecialistOfInstitution(specialistId, institution.getName()))
            throw new BadInputException("Couldn't update test specialist: specialist must belong to institution");

        test.setSpecialist(specialist);
        testDAO.save(test);
    }

    @Override
    public void updateTestCourse(String testId, String courseId) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test course: couldn't find test with id \'" + testId + "\'");

        // retrieve course
        Course course = coursesService.getCourseById(courseId);
        if (course == null)
            throw new NotFoundException("Couldn't update test course: couldn't find course with id \'" + courseId + "\'");

        // check specialist constraints
        Specialist specialist = test.getSpecialist();
        if (specialist != null && !coursesService.checkSpecialistInCourse(course.getId(), specialist.getId()))
            throw new BadInputException("Couldn't update test course: specialist must belong to course");

        test.setCourse(course);
        testDAO.save(test);
    }

    @Override
    public void updateTestInstitution(String testId, String institutionId) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test institution: couldn't find test with id \'" + testId + "\'");

        // retrieve institution
        Institution institution = institutionsService.getInstitutionById(institutionId);
        if (institution == null)
            throw new NotFoundException("Couldn't update test institution: couldn't find institution with id \'" + institutionId + "\'");

        // check specialist constraints
        Specialist specialist = test.getSpecialist();
        if (specialist != null && institutionsService.isSpecialistOfInstitution(specialist.getId(), institutionId))
            throw new BadInputException("Couldn't update test institution: specialist must belong to institution");

        test.setInstitution(institution);
        testDAO.save(test);
    }

    @Override
    public void updateTestGroups(String testId, Map<Integer, TestGroup> groups) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id \'" + testId + "\'");

        // verify test group properties
        for (Map.Entry<Integer, TestGroup> entry: groups.entrySet()){
            entry.getValue().verifyProperties();
        }

        test.setGroups(groups);
        // update points
        test.calculatePoints();
        
        testDAO.save(test);
    }

    @Override
    public void updateTestDeliverDate(String testId, LocalDateTime deliverDate) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id \'" + testId + "\'");

        // check if it is deliver date test
        if (!(test instanceof DeliverDateTest))
            throw new BadInputException("Couldn't update test deliver date: test with id \'" + testId + "\' is not a deliver date test");

        if (test.getPublishDate().isAfter(deliverDate))
            throw new BadInputException("Couldn't update test deliver date: deliver date occurs before publish date");

        DeliverDateTest lt = (DeliverDateTest) test;
        lt.setDeliverDate(deliverDate);
        testDAO.save(lt);
    }

    @Override
    public void updateTestStartDate(String testId, LocalDateTime startDate) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id \'" + testId + "\'");

        // check if it is live test
        if (!(test instanceof LiveTest))
            throw new BadInputException("Couldn't update test start date: test with id \'" + testId + "\' is not a live test");

        if (test.getPublishDate().isAfter(startDate))
            throw new BadInputException("Couldn't update test start date: test hasn't been published yet");

        LiveTest lt = (LiveTest) test;
        lt.setStartDate(startDate);
        testDAO.save(lt);
    }

    @Override
    public void updateTestDuration(String testId, long duration) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id \'" + testId + "\'");

        // check if it is live test
        if (!(test instanceof LiveTest))
            throw new BadInputException("Couldn't update test duration: test with id \'" + testId + "\' is not a live test");

        if (duration <= 0)
            throw new BadInputException("Couldn't update test duration: duration must be positive");

        LiveTest lt = (LiveTest) test;
        lt.setDuration(duration);
        testDAO.save(lt);
    }

    @Override
    public void updateTestStartTolerance(String testId, long startTolerance) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id \'" + testId + "\'");

        // check if it is live test
        if (!(test instanceof LiveTest))
            throw new BadInputException("Couldn't update test start tolerance: test with id \'" + testId + "\' is not a live test");

        if (startTolerance < 0)
            throw new BadInputException("Couldn't update test start tolerance: start tolerance can't be negative");

        LiveTest lt = (LiveTest) test;
        lt.setStartTolerance(startTolerance);
        testDAO.save(lt);
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
            return resolutionDAO.countTotalSubmissionsForTest(testId);
        else
            return resolutionDAO.countDistinctSubmissionsForTest(testId);
    }

    @Override
    public List<TestResolution> getTestResolutions(String testId, Integer page, Integer itemsPerPage) throws NotFoundException{
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get test resolutions for test " + testId + ": couldn't find test with given id.");

        return resolutionDAO.getTestResolutions(testId, PageRequest.of(page, itemsPerPage)).stream().toList();
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
        Student student = studentsService.getStudentById(studentId);
        if (student == null)
            throw new NotFoundException("Can't start test: couldn't find student with id \'" + studentId + "\'");

        TestResolution resolution = new TestResolution(null,
                LocalDateTime.now(),
                null,
                0,
                null,
                student,
                test,
                TestResolutionStatus.ONGOING,
                test.createEmptyResolutionGroups());
        return createTestResolution(testId, resolution).getId();
    }

    @Override
    public TestResolution createTestResolution(String testId, TestResolution resolution) throws BadInputException, NotFoundException {
        // check test
        if (testId == null)
            throw new BadInputException("Cannot create test resolution: resolution must belong to a test.");
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot create test resolution: couldn't find test.");

        if (resolution == null)
            throw new BadInputException("Cannot create a test resolution with a 'null' body.");

        Test test = testDAO.getReferenceById(testId);
        resolution.setTest(test);

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
        String studentId = resolution.getStudent().getId();
        if (studentId == null)
            throw new BadInputException("Cannot create test resolution: resolution must belong to a student.");
        if (!studentsService.existsStudentById(studentId))
            throw new BadInputException("Cannot create test resolution: resolution must belong to a valid student.");
        Student student = entityManager.getReference(Student.class, studentId);
        resolution.setStudent(student);

        return resolutionDAO.save(resolution);
    }

    @Override
    @Transactional
    public void deleteTestResolutionById(String resolutionId) throws NotFoundException {
        TestResolution resolution = resolutionDAO.findById(resolutionId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't delete resolution: Resolution \'" + resolutionId + "\'was not found");

        //exercisesService.deleteAllExerciseResolutionByTestResolutionId(resolutionId);
        resolutionDAO.delete(resolution);
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

        Visibility vis = test.getVisibility();
        if (vis.equals(Visibility.PRIVATE)){ return false; }
        else if (vis.equals(Visibility.COURSE)){
            if (!coursesService.checkStudentInCourse(test.getCourseId(), studentId))
                return false;
        }
        else if (vis.equals(Visibility.INSTITUTION)){
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
            if (lt.getStartDate().isAfter(LocalDateTime.now()))
                // TODO: convert to duration  
                //LocalDateTime.now().isAfter(lt.getStartDate().plus(lt.getDuration()).plus(lt.getStartTolerance())))
                return false;
        }

        return true;
    }

    @Override
    @Transactional
    public Integer countStudentSubmissionsForTest(String testId, String studentId) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot count " + studentId + " submissions for test " + testId + ": couldn't find test with given id.");
        if (!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Cannot count " + studentId + " submissions for test " + testId + ": couldn't find student with given id.");
        return resolutionDAO.countStudentSubmissionsForTest(studentId, testId);
    }

    @Override
    public List<String> getStudentTestResolutionsIds(String testId, String studentId) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get student " + studentId + " resolutions for test " + testId + ": couldn't find test with given id.");
        if (!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Cannot get student " + studentId + " resolutions for test " + testId + ": couldn't find student with given id.");
        return resolutionDAO.getStudentTestResolutionsIds(testId, studentId);
    }

    @Override
    public TestResolution getStudentLastResolution(String testId, String studentId) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get student " + studentId + " last resolution for test " + testId + ": couldn't find test with given id.");
        if (!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Cannot get student " + studentId + " last resolution for test " + testId + ": couldn't find student with given id.");

        List<String> ids = getStudentTestResolutionsIds(testId, studentId);
        TestResolution res = null;
    
        if (ids.isEmpty())
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
        Float oldPoints = er.getPoints();
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

        TestResolution updatedTR = getTestResolutionById(testResId);
        Map<Integer, TestResolutionGroup> updatedGroups = updatedTR.getGroups();

        for(TestResolutionGroup testResolutionGroup:updatedGroups.values()){
            for (String resolutionId: testResolutionGroup.getResolutions().values().stream().map(Pair::getRight).toList()){
                if(Objects.equals(resolutionId, exeResId)){
                    //work the difference in points
                    testResolutionGroup.setGroupPoints(testResolutionGroup.getGroupPoints()+points-oldPoints);
                }
            }
        }
        updatedTR.setGroups(updatedGroups);
        updatedTR.updateSum();
        resolutionDAO.save(updatedTR);
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


    @Override
    public ExerciseResolution getExerciseResolution(String exerciseId, String testResId) throws NotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExerciseResolution'");
    }


    @Override
    public void deleteAllExerciseResolutionByTestResolutionId(String testResId) throws NotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllExerciseResolutionByTestResolutionId'");
    }
}
