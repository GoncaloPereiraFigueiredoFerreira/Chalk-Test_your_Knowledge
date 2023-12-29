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
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

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

        return testDAO.getTests(PageRequest.of(page, itemsPerPage), tags, tags.size(), matchAllTags, Visibility.fromValue(visibilityType), institutionId, courseId, specialistId, title);
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

        // Check if specialist is valid
        String specialistId = body.getSpecialistId();
        Specialist specialist;
        try {
            specialist = specialistsService.getSpecialistById(specialistId);
            body.setSpecialist(specialist);
        } catch (NotFoundException nfe) {
            throw new BadInputException("Cannot create test: Specialist does not exist.");
        }

        // Check if visibility is valid
        Visibility visibility = body.getVisibility();
        if (visibility == null)
            throw new BadInputException("Cannot create test: Visibility cant be null");

        // Checks if specialist exists and gets the institution where it belongs
        Institution institution = specialist.getInstitution();
        // the test's institution is the same has its owner
        body.setInstitution(institution);

        // If the specialist that owns the test does not have an institution,
        // then the test's visibility cannot be set to institution.
        if (visibility == Visibility.INSTITUTION && institution == null)
            throw new BadInputException("Cannot create test: cannot set visibility to INSTITUTION");

        // Check if course is valid
        String courseId = body.getCourseId();
        try {
            if (courseId != null) {
                // the owner of the test must be associated with the course
                if (!coursesService.checkSpecialistInCourse(courseId, body.getSpecialistId()))
                    throw new BadInputException("Cannot create test: specialist does not belong to the given course.");
                else {
                    Course course = coursesService.getCourseById(courseId);
                    body.setCourse(course);
                }
            }
            else body.setCourse(null);
        } catch (NotFoundException nfe) {
            throw new BadInputException("Cannot create test: course not found.");
        }

        // Cannot set visibility to COURSE without a course associated
        if (courseId == null && visibility == Visibility.COURSE)
            throw new BadInputException("Cannot create test: cannot set visibility to COURSE without a course associated.");

        // count tags
        // check and duplicate exercises
        Map<String, Integer> tagsCounter = new HashMap<>();
        List<TestGroup> tgs = body.getGroups();
        List<TestGroup> newTGs = new ArrayList<>();

        for (TestGroup tg: tgs){
            List<TestExercise> newExes = new ArrayList<>();
            List<String> newExesIds = new ArrayList<>();
            for (TestExercise exe: tg.getExercises()){
                // duplicate or persist exercise
                String dupExerciseId;

                // if the exercise is a concrete exercise, the exercise must be persisted
                // and a reference, with the id of the persisted exercise, needs to be created.
                // This allows the exercises to be persisted independently of the test.
                if (exe instanceof ConcreteExercise ce){
                    Exercise tmp = ce.getExercise();
                    List<String> tagIds = tmp.getTags().stream().map(t -> t.getId()).toList();
                    dupExerciseId = exercisesService.createExercise(tmp, tmp.getSolution(), tmp.getRubric(), tagIds);
                }
                // Else the exercise is a reference to an existing exercise,
                // therefore the exercise needs to be duplicated and
                // a new reference, containing the id of the duplicate,
                // needs to be created.
                else {
                    dupExerciseId = exercisesService.duplicateExerciseById(specialistId, exe.getId(), null, Visibility.TEST);
                }
                ReferenceExercise newExe = new ReferenceExercise(dupExerciseId, exe.getPoints());
                newExes.add(newExe);
                newExesIds.add(dupExerciseId);
            }

            // count group tags
            Set<Pair<String,Long>> groupTagsCounter = exercisesService.countTagsOccurrencesForExercisesList(newExesIds);
            for (Pair<String,Long> pair : groupTagsCounter){
                String tagId = pair.getLeft();
                Long count = pair.getRight();

                if (tagsCounter.containsKey(tagId))
                    tagsCounter.put(tagId, tagsCounter.get(tagId) + count.intValue());
                else
                    tagsCounter.put(tagId, count.intValue());
            }

            TestGroup newTG = new TestGroup(tg.getGroupInstructions(), tg.getGroupPoints(), newExes);
            newTGs.add(newTG);
        }
        body.setGroups(newTGs);

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
    @Transactional
    public void deleteTestById(String testId) throws NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't delete test \'" + testId + "\': test was not found.");

        // delete test resolutions
        List<TestResolution> trs = resolutionDAO.getTestResolutions(testId);
        if (trs != null){
            for (TestResolution tr: trs){
                deleteTestResolutionById(tr.getId());
            }
        }

        // delete test tags
        List<TestTag> tags = testTagsDAO.getTestTags(testId);
        if (tags != null){
            testTagsDAO.deleteAll(tags);
        }

        // delete test exercises
        List<TestGroup> tgs = test.getGroups();
        for (TestGroup tg: tgs){
            for (TestExercise exe: tg.getExercises()){
                exercisesService.deleteExerciseById(exe.getId());
            }
        }

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
        ogTest.setCreationDate(LocalDateTime.now());
        ogTest.setPublishDate(null);

        // duplicate exercises
        List<TestGroup> tgs = ogTest.getGroups();
        List<TestGroup> newTGs = new ArrayList<>();
        for (TestGroup tg: tgs){
            List<TestExercise> newExes = new ArrayList<>();
            for (TestExercise exe: tg.getExercises()){
                String dupExerciseId = "";
                Float points = 0.0F;
                if (exe instanceof ReferenceExercise re){
                    dupExerciseId = exercisesService.duplicateExerciseById(specialistId, exe.getId());
                    Exercise ref = exercisesService.getExerciseById(dupExerciseId);
                    ref.setVisibility(Visibility.TEST);
                    exercisesService.updateExerciseBody(dupExerciseId, ref);
                    points = re.getPoints();
                }
                else if (exe instanceof ConcreteExercise ce){
                    Exercise tmp = ce.getExercise();
                    List<String> tagIds = tmp.getTags().stream().map(t -> t.getId()).toList();
                    dupExerciseId = exercisesService.createExercise(tmp, tmp.getSolution(), tmp.getRubric(), tagIds);
                    points = ce.getPoints();
                }
                ReferenceExercise newExe = new ReferenceExercise(dupExerciseId, points);
                newExes.add(newExe);
            }
            TestGroup newTG = new TestGroup(tg.getGroupInstructions(), tg.getGroupPoints(), newExes);
            newTGs.add(newTG);
        }
        ogTest.setGroups(newTGs);
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

        List<TestResolution> resolutions = resolutionDAO.getTestResolutions(testId);
        if (resolutions.size() > 0)
            throw new BadInputException("Couldn't update test publish date: some tests were already resolved");

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
    public void updateTestGroups(String testId, List<TestGroup> groups) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id \'" + testId + "\'");

        LocalDateTime publishDate = test.getPublishDate();
        if (publishDate != null && LocalDateTime.now().isAfter(publishDate))
            throw new BadInputException("Couldn't update test: can't change test after it has already been published.");

        // verify test group properties
        for (TestGroup entry: groups){
            entry.verifyProperties();
        }

        // update points
        test.setGroups(groups);
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
    public void automaticCorrection(String testId, String correctionType) throws NotFoundException, BadInputException, UnauthorizedException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't evaluate test: couldn't find test with id \'" + testId + "\'");

        // issue exercise corrections
        for (TestGroup tg: test.getGroups()){
            for (TestExercise exe: tg.getExercises()){
                exercisesService.issueExerciseResolutionsCorrection(exe.getId(), correctionType);
            }
        }

        // calculate points
        // check if everything has been revised
        boolean isRevised = true;
        List<TestResolution> resolutions = resolutionDAO.getTestResolutions(testId);
        for (TestResolution resolution: resolutions){
            for (TestResolutionGroup trg: resolution.getGroups()){
                Float points = 0.0F;
                for (Map.Entry<String, String> entry: trg.getResolutions().entrySet()){
                    if (entry.getValue() != null && !entry.getValue().equals("")){
                        ExerciseResolution exeRes = exercisesService.getExerciseResolution(entry.getValue());
                        if (exeRes.getStatus().equals(ExerciseResolutionStatus.NOT_REVISED))
                            isRevised = false;
                        points += exeRes.getPoints();
                    }
                }
                trg.setGroupPoints(points);
            }
            resolution.updateSum();
            if (isRevised)
                resolution.setStatus(TestResolutionStatus.REVISED);
            else
                resolution.setStatus(TestResolutionStatus.ONGOING);
            resolutionDAO.save(resolution);
        }
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
            throw new BadInputException("Cannot create test resolution: resolution must belong to a test");
        Test test = getTestById(testId);
        if (test == null)
            throw new NotFoundException("Cannot create test resolution: couldn't find test");
        if (resolution == null)
            throw new BadInputException("Cannot create a test resolution with a 'null' body");

        resolution.setTest(test);
        resolution.verifyProperties();

        // check time constraints
        LocalDateTime startDate = resolution.getStartDate();
        LocalDateTime submissionDate = resolution.getSubmissionDate();
        if (startDate != null && submissionDate != null && startDate.isAfter(submissionDate))
            throw new BadInputException("Cannot create a test resolution: submission date occurs before start date");

        LocalDateTime testCreationDate = test.getCreationDate();
        LocalDateTime testPublishDate = test.getPublishDate();
        if (testCreationDate == null || testPublishDate == null)
            throw new BadInputException("Cannot create a test resolution: test hasn't been created or published");
        if (testCreationDate.isAfter(startDate) || testPublishDate.isAfter(startDate))
            throw new BadInputException("Cannot create a test resolution: start date needs to be after test creation or publication");

        if (test instanceof DeliverDateTest ddt){
            LocalDateTime testDeliverDate = ddt.getDeliverDate();
            if (testDeliverDate != null && startDate.isAfter(testDeliverDate))
                throw new BadInputException("Cannot create a test resolution: start date needs to be after test deliver date");
        }
        else if (test instanceof LiveTest lt) {
            LocalDateTime testStartDate = lt.getStartDate();
            if (testStartDate != null && testStartDate.isAfter(startDate))
                throw new BadInputException("Cannot create a test resolution: start date needs to be after test start");
        }

        //set the identifier to null to avoid overwrite attacks
        resolution.setId(null);

        // check student
        Student student = resolution.getStudent();
        if (student == null)
            throw new BadInputException("Cannot create test resolution: resolution must belong to a student");
        if (!studentsService.existsStudentById(student.getId()))
            throw new BadInputException("Cannot create test resolution: resolution must belong to a valid student");
        
        // check visibililty constraints
        if (test.getVisibility().equals(Visibility.COURSE) && !coursesService.checkStudentInCourse(test.getCourseId(), student.getId()))
            throw new BadInputException("Cannot create test resolution: student must belong to course");
        if (test.getVisibility().equals(Visibility.INSTITUTION) && !institutionsService.isStudentOfInstitution(student.getId(), test.getInstitutionId()))
            throw new BadInputException("Cannot create test resolution: student must belong to institution");

        student = entityManager.getReference(Student.class, student.getId());
        resolution.setStudent(student);

        return resolutionDAO.save(resolution);
    }

    @Override
    @Transactional
    public void deleteTestResolutionById(String resolutionId) throws NotFoundException {
        TestResolution resolution = resolutionDAO.findById(resolutionId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't delete resolution: Resolution \'" + resolutionId + "\'was not found");

        // delete exercise resolutions
        for (TestResolutionGroup trg: resolution.getGroups()){
            for (String res: trg.getResolutions().values()){
                if (res != null && !res.isEmpty())
                    exercisesService.deleteExerciseResolutionById(res);
            }
        }
        resolutionDAO.delete(resolution);
    }

    @Override
    public void updateTestResolutionStartDate(String testResId, LocalDateTime startDate) throws NotFoundException, BadInputException {
        TestResolution resolution = resolutionDAO.findById(testResId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't update resolution start date: Resolution \'" + testResId + "\'was not found");

        Test test = getTestById(resolution.getTest().getId());
        if (test == null)
            throw new NotFoundException("Couldn't update resolution start date: couldn't fetch test");

        // check time constraints
        LocalDateTime submissionDate = resolution.getSubmissionDate();
        if (submissionDate != null && startDate.isAfter(submissionDate))
            throw new BadInputException("Couldn't update resolution start date: submission date occurs before start date");

        LocalDateTime testCreationDate = test.getCreationDate();
        LocalDateTime testPublishDate = test.getPublishDate();
        if (testCreationDate.isAfter(startDate) || testPublishDate.isAfter(startDate))
            throw new BadInputException("Couldn't update resolution start date: start date needs to be after test creation or publication");

        if (test instanceof DeliverDateTest ddt){
            LocalDateTime testDeliverDate = ddt.getDeliverDate();
            if (testDeliverDate != null && startDate.isAfter(testDeliverDate))
                throw new BadInputException("Couldn't update resolution start date: start date needs to be before test deliver date");
        }
        else if (test instanceof LiveTest lt) {
            LocalDateTime testStartDate = lt.getStartDate();
            if (testStartDate != null && testStartDate.isAfter(startDate))
                throw new BadInputException("Couldn't update resolution start date: start date needs to be after test start");
            
            LocalDateTime tolerance = testStartDate.plusSeconds(lt.getStartTolerance());
            if (testStartDate != null && startDate.isAfter(tolerance))
                throw new BadInputException("Couldn't update resolution start date: start date occurs after test start tolerance");
        }

        resolution.setStartDate(startDate);
        resolutionDAO.save(resolution);
    }

    @Override
    public void updateTestResolutionSubmissionDate(String testResId, LocalDateTime submissionDate) throws NotFoundException, BadInputException {
        TestResolution resolution = resolutionDAO.findById(testResId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't update resolution submission date: Resolution \'" + testResId + "\'was not found");

        Test test = getTestById(resolution.getTest().getId());
        if (test == null)
            throw new NotFoundException("Couldn't update resolution submission date: couldn't fetch test");

        // check time constraints
        if (resolution.getStartDate().isAfter(submissionDate))
            throw new NotFoundException("Couldn't update resolution submission date: submission date needs to occur after resolution start date");
        if (test instanceof DeliverDateTest ddt){
            LocalDateTime testDeliverDate = ddt.getDeliverDate();
            if (testDeliverDate != null && submissionDate.isAfter(testDeliverDate))
                throw new BadInputException("Couldn't update resolution submission date: submission date needs to be after test deliver date");
        }
        else if (test instanceof LiveTest lt) {
            LocalDateTime resStartDate = resolution.getStartDate();
            LocalDateTime resEndDate = resStartDate.plusSeconds(lt.getDuration());

            if (submissionDate.isAfter(resEndDate))
                throw new BadInputException("Couldn't update resolution submission date: submission date needs to be before the end of the test");
        }
        
        resolution.setSubmissionDate(submissionDate);
        resolutionDAO.save(resolution);
    }

    @Override
    public void updateTestResolutionSubmissionNr(String testResId, int submissionNr) throws NotFoundException, BadInputException {
        TestResolution resolution = resolutionDAO.findById(testResId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't update resolution submission number: Resolution \'" + testResId + "\'was not found");

        if (submissionNr <= 0)
            throw new BadInputException("Couldn't update resolution submission number: submission number needs to be higher than 0");

        resolution.setSubmissionNr(submissionNr);
        resolutionDAO.save(resolution);
    }

    @Override
    public void updateTestResolutionStatus(String testResId, TestResolutionStatus status) throws NotFoundException {
        TestResolution resolution = resolutionDAO.findById(testResId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't update resolution: Resolution \'" + testResId + "\'was not found");

        resolution.setStatus(status);
        resolutionDAO.save(resolution);
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

        // check time constraints
        if (test.getPublishDate().isAfter(LocalDateTime.now()))
            return false;
        if (test instanceof DeliverDateTest ddt){
            if (LocalDateTime.now().isAfter(ddt.getDeliverDate()))
                return false;
        }
        else if (test instanceof LiveTest lt){
            if (lt.getStartDate().isAfter(LocalDateTime.now()))
                return false;
            LocalDateTime endOfTest = lt.getStartDate().plusSeconds(lt.getDuration()).plusSeconds(lt.getStartTolerance());
            if (LocalDateTime.now().isAfter(endOfTest))
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
                throw new NotFoundException("Cannot get student " + studentId + " last resolution for test " + testId + ": error finding resolution in DB.");
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
        List<TestResolutionGroup> updatedGroups = updatedTR.getGroups();

        for(TestResolutionGroup testResolutionGroup: updatedGroups){
            for (String resolutionId: testResolutionGroup.getResolutions().values()){
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
    public void uploadResolution(String testResId, String exeId, ExerciseResolution resolution) throws NotFoundException, BadInputException {
        TestResolution testRes = getTestResolutionById(testResId);

        boolean found = false;
        for (TestResolutionGroup trg: testRes.getGroups()){
            Map<String, String> mapExeRes = trg.getResolutions();
            for (Map.Entry<String, String> entry: mapExeRes.entrySet()){
                if (entry.getKey().equals(exeId)){
                    ExerciseResolution exeRes = exercisesService.createExerciseResolution(testResId, exeId, resolution.getData());
                    mapExeRes.put(entry.getKey(), exeRes.getId());
                    found = true;
                    break;
                }
            }
            if (found)
                trg.setResolutions(mapExeRes);
        }

        if (found)
            resolutionDAO.save(testRes);
        else
            throw new NotFoundException("Cannot upload resolution for exercise with id \'" + exeId + "\'' in test resolution with id \'" + testResId + "\': couldn't find the exercise");
    }

    public void createTestExercise(String testId, TestExercise exercise, Integer groupIndex, Integer exeIndex, String groupInstructions) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't add exercise to test: couldn't find test with id \'" + testId + "\'");

        LocalDateTime publishDate = test.getPublishDate();
        if (publishDate != null && LocalDateTime.now().isAfter(publishDate))
            throw new BadInputException("Couldn't add exercise to test: can't change test after it has already been published.");

        // retrieve and/or duplicate exercise
        String dupExeId = "";
        Float points = 0.0F;
        List<String> tagIds = new ArrayList<>();
        if (exercise instanceof ConcreteExercise ce){
            Exercise exe = ce.getExercise();
            tagIds = exe.getTags().stream().map(t -> t.getId()).toList();
            dupExeId = exercisesService.createExercise(exe, exe.getSolution(), exe.getRubric(), tagIds);
            points = ce.getPoints();
        }
        else if (exercise instanceof ReferenceExercise re){
            Exercise exe = exercisesService.getExerciseById(re.getId());
            if (exe == null)
                throw new NotFoundException("Couldn't add exercise to test: couldn't find exercise with id \'" + re.getId() + "\'");
            
            dupExeId = exercisesService.duplicateExerciseById(exe.getSpecialistId(), exe.getId());
            points = re.getPoints();
            tagIds = exe.getTags().stream().map(t -> t.getId()).toList();
        }

        // insert the exercise into test
        TestExercise insert = new ReferenceExercise(dupExeId, points);
        List<TestGroup> groups = test.getGroups();
        if (groupIndex < groups.size()){
            TestGroup tg = groups.get(groupIndex);
            List<TestExercise> exes = tg.getExercises();
            exes.add(exeIndex, insert);
            tg.setExercises(exes);
            tg.calculateGroupPoints();
            groups.set(groupIndex, tg);
        }
        else {
            List<TestExercise> exes = new ArrayList<>();
            exes.add(insert);
            TestGroup tg = new TestGroup(groupInstructions, null, exes);
            tg.calculateGroupPoints();
            groups.add(tg);
        }

        // update test tags
        for (String tagId: tagIds){
            Tag tag = tagsService.getTagById(tagId);
            TestTagPK testTagPK = new TestTagPK(tag, test);
            TestTag testTag = testTagsDAO.findById(testTagPK).orElse(null);
            if (testTag != null)
                testTag.setNExercises(testTag.getNExercises() + 1);
            else
                testTag = new TestTag(1, testTagPK);
            testTagsDAO.save(testTag);
        }
        
        // save test
        test.setGroups(groups);
        test.calculatePoints(); 
        testDAO.save(test);
    }

    @Override
    public void deleteExerciseFromTest(String testId, String exerciseId) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't delete exercise from test: couldn't find test with id \'" + testId + "\'");

        LocalDateTime publishDate = test.getPublishDate();
        if (publishDate != null && LocalDateTime.now().isAfter(publishDate))
            throw new BadInputException("Couldn't delete exercise from test: can't change test after it has already been published.");

        // searching for exercise
        boolean found = false;
        List<TestGroup> groups = test.getGroups();
        for (TestGroup tg: groups){
            List<TestExercise> exes = tg.getExercises();
            for (TestExercise exe: exes){
                if (exe.getId().equals(exerciseId)){
                    // remove exercise from test
                    exes.remove(exe);
                    found = true;
                    break;
                }
            }
            if (found){
                if (exes.size() == 0)
                    groups.remove(tg);
                tg.calculateGroupPoints();
                break;
            }
        }
        if (found)
            // delete exercise
            exercisesService.deleteExerciseById(exerciseId);
        else
            throw new NotFoundException("Couldn't delete exercise from test: couldn't find exercise with id \'" + exerciseId + "\'");

        // save test
        test.setGroups(groups);
        test.calculatePoints(); 
        testDAO.save(test);
    }

    @Override
    public void removeExerciseFromTest(String testId, String exerciseId) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't remove exercise from test: couldn't find test with id \'" + testId + "\'");

        LocalDateTime publishDate = test.getPublishDate();
        if (publishDate != null && LocalDateTime.now().isAfter(publishDate))
            throw new BadInputException("Couldn't remove exercise from test: can't change test after it has already been published.");

        // searching for exercise
        boolean found = false;
        List<TestGroup> groups = test.getGroups();
        for (TestGroup tg: groups){
            List<TestExercise> exes = tg.getExercises();
            for (TestExercise exe: exes){
                if (exe.getId().equals(exerciseId)){
                    // remove exercise from test
                    exes.remove(exe);
                    found = true;
                    break;
                }
            }
            if (found){
                if (exes.size() == 0)
                    groups.remove(tg);
                tg.calculateGroupPoints();
                break;
            }
        }
        if (found)
            // change visibility to PRIVATE
            exercisesService.updateExerciseVisibility(exerciseId, Visibility.PRIVATE);
        else
            throw new NotFoundException("Couldn't delete exercise from test: couldn't find exercise with id \'" + exerciseId + "\'");

        // save test
        test.setGroups(groups);
        test.calculatePoints(); 
        testDAO.save(test);
    }
}
