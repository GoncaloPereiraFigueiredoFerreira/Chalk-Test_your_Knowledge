package pt.uminho.di.chalktyk.services;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.courses.Course;
import pt.uminho.di.chalktyk.models.exercises.*;
import pt.uminho.di.chalktyk.models.exercises.items.Item;
import pt.uminho.di.chalktyk.models.exercises.items.StringItem;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.Mctype;
import pt.uminho.di.chalktyk.models.exercises.multiple_choice.MultipleChoiceExercise;
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
import pt.uminho.di.chalktyk.services.exceptions.ServiceException;
import pt.uminho.di.chalktyk.services.exceptions.ForbiddenException;

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
    private final ExerciseResolutionDAO exeResDAO;


    @Autowired
    public TestsService(EntityManager entityManager, TestDAO testDAO, TestResolutionDAO resolutionDAO, TestTagsDAO testTagsDAO, ExerciseResolutionDAO exerciseResolutionDAO, ISpecialistsService specialistsService, IStudentsService studentsService,
                        IInstitutionsService institutionsService, ICoursesService coursesService, ITagsService tagsService, IExercisesService exercisesService, ExerciseResolutionDAO exeResDAO){
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
        this.exeResDAO = exeResDAO;
    }

    
    @Override
    @Transactional
    public Page<Test> getTests(Integer page, Integer itemsPerPage, List<String> tags, Boolean matchAllTags, Visibility visibility, String specialistId, String courseId, String institutionId, String title, boolean verifyParams) throws NotFoundException {
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
        Page<Test> tests = testDAO.getTests(PageRequest.of(page, itemsPerPage), tags, tags != null ? tags.size() : 0, matchAllTags, visibility, institutionId, courseId, specialistId, title);

        // turning groups to null
        List<Test> tmpTests = new ArrayList<>();
        for (Test t: tests){
            Test tmpTest = new Test(t.getId(), t.getTitle(), t.getGlobalInstructions(), t.getGlobalPoints(), t.getConclusion(), t.getCreationDate(),
                    t.getPublishDate(), t.getSpecialist(), t.getVisibility(), t.getCourse(), t.getInstitution(), null);
            List<Tag> tmpTags = testTagsDAO.getTestTags(t.getId()).stream().map(tt -> tt.getTestTagPK().getTag().clone()).toList();
            tmpTest.setTags(tmpTags);
            tmpTests.add(tmpTest);
        }

        return new PageImpl<>(tmpTests, tests.getPageable(), tests.getTotalElements());
    }

    @Override
    @Transactional
    public Test getTestById(String testId) throws NotFoundException {
        Test t = testDAO.findById(testId).orElse(null);
        if (t == null)
            throw new NotFoundException("Could not get test: there is no test with the given identifier.");

        // getting concrete exercises
        List<TestGroup> newGroups = new ArrayList<>();
        List<TestGroup> groups = t.getGroups();
        if (groups != null){
            for (TestGroup tg: groups){
                TestGroup newGroup = new TestGroup(tg.getGroupInstructions(), tg.getGroupPoints(), new ArrayList<>());
                List<TestExercise> newExes = new ArrayList<>();
                for (TestExercise ref: tg.getExercises()){
                    Exercise exe = exercisesService.getExerciseById(ref.getId());
                    TestExercise newExe = new ConcreteExercise(ref.getPoints(), exe);
                    newExes.add(newExe);
                }
                newGroup.setExercises(newExes);
                newGroups.add(newGroup);
            }
        }

        Test ret = new Test(testId, t.getTitle(), t.getGlobalInstructions(), t.getGlobalPoints(), t.getConclusion(), t.getCreationDate(),
                t.getPublishDate(), t.getSpecialist(), t.getVisibility(), t.getCourse(), t.getInstitution(), newGroups);

        // get tags
        List<Tag> tags = testTagsDAO.getTestTags(t.getId()).stream().map(tt -> tt.getTestTagPK().getTag().clone()).toList();
        ret.setTags(tags);

        return ret;
    }

    private Test _getTestById(String testId) throws NotFoundException {
        Test t = testDAO.findById(testId).orElse(null);
        if (t == null)
            throw new NotFoundException("Could not get test: there is no test with the given identifier.");
        return t;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String createTest(Test body) throws BadInputException, NotFoundException {
        if (body == null)
            throw new BadInputException("Cannot create test: test is null.");

        body.calculatePoints(); // updates global/group points
        body.setCreationDate(LocalDateTime.now()); // set creation date
        body.verifyProperties();
        body.setId(null); // to prevent overwrite attacks

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
            } else body.setCourse(null);
        } catch (NotFoundException nfe) {
            throw new BadInputException("Cannot create test: course not found.");
        }

        // Cannot set visibility to COURSE without a course associated
        if (courseId == null && visibility == Visibility.COURSE)
            throw new BadInputException("Cannot create test: cannot set visibility to COURSE without a course associated.");

        // check and duplicate exercises
        List<TestGroup> tgs = body.getGroups();
        List<TestGroup> newTGs = new ArrayList<>();
        List<String> allNewExesIds = new ArrayList<>();

        if (tgs != null) {
            for (TestGroup tg : tgs) {
                List<TestExercise> newExes = new ArrayList<>();

                for (TestExercise exe : tg.getExercises()) {
                    // duplicate or persist exercise
                    String dupExerciseId;

                    // if the exercise is a concrete exercise, the exercise must be persisted
                    // and a reference, with the id of the persisted exercise, needs to be created.
                    // This allows the exercises to be persisted independently of the test.
                    if (exe instanceof ConcreteExercise ce) {
                        Exercise tmp = ce.getExercise();
                        List<String> tagIds = new ArrayList<>();
                        if (tmp.getTags() != null)
                            tagIds = tmp.getTags().stream().map(Tag::getId).toList();
                        tmp.setSpecialist(specialist);
                        tmp.setVisibility(Visibility.TEST); // set visibility to test
                        dupExerciseId = exercisesService.createExercise(tmp, tmp.getSolution(), tmp.getRubric(), tagIds);
                    }

                    // Else the exercise is a reference to an existing exercise,
                    // therefore the exercise needs to be duplicated and
                    // a new reference, containing the id of the duplicate,
                    // needs to be created.
                    else if (exe instanceof ReferenceExercise re) {
                        dupExerciseId = exercisesService.duplicateExerciseById(specialistId, re.getId(), null, Visibility.TEST);
                    } else {
                        break;
                    }

                    ReferenceExercise newExe = new ReferenceExercise(dupExerciseId, exe.getPoints());
                    newExes.add(newExe);
                    allNewExesIds.add(dupExerciseId);
                }

                TestGroup newTG = new TestGroup(tg.getGroupInstructions(), tg.getGroupPoints(), newExes);
                newTGs.add(newTG);
            }
        }
        body.setGroups(newTGs);

        // persist test
        body = testDAO.save(body);

        // count and create tags
        createTestTags(exercisesService.countTagsOccurrencesForExercisesList(allNewExesIds), body);

        return body.getId();
    }

    private void createTestTags(Set<Pair<Tag,Long>> tags, Test test){
        for (Pair<Tag,Long> pair: tags){
            Tag tag = pair.getLeft();
            TestTagPK testTagPK = new TestTagPK(tag, test);
            TestTag testTag = new TestTag(pair.getRight().intValue(), testTagPK);
            testTagsDAO.save(testTag);
        }
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteTestById(String testId) throws NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't delete test '" + testId + "': test was not found.");

        // delete test resolutions
        List<TestResolution> trs = resolutionDAO.getTestResolutions(testId);
        if (trs != null){
            for (TestResolution tr: trs){
                deleteTestResolutionById(tr.getId());
            }
        }

        deleteTestTags(testId);

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
    @Transactional(rollbackFor = ServiceException.class)
    public String duplicateTestById(String specialistId, String testId, Visibility visibility, String courseId) throws BadInputException, NotFoundException {
        // if test does not exist, a not found exception will be thrown
        // fetch original models
        Test ogTest = _getTestById(testId);

        // Copies the basic information, and sets the visibility
        Test newTest = new Test(null, ogTest.getTitle(), ogTest.getGlobalInstructions(),
                                ogTest.getGlobalPoints(), ogTest.getConclusion(),
                                LocalDateTime.now(), null, null,
                                visibility != null ? visibility : Visibility.PRIVATE,
                                null, null, null);

        // if specialist does not exist, a not found exception will be thrown
        Specialist specialist = specialistsService.getSpecialistById(specialistId);
        newTest.setSpecialist(specialist);

        // Gets the specialist's institution
        Institution institution = specialist.getInstitution();
        newTest.setInstitution(institution);

        // Check if course is valid
        try {
            if (courseId != null) {
                // the owner of the test must be associated with the course
                if (!coursesService.checkSpecialistInCourse(courseId, specialistId))
                    throw new BadInputException("Cannot duplicate test: specialist does not belong to the given course.");
                else {
                    Course course = coursesService.getCourseById(courseId);
                    newTest.setCourse(course);
                }
            }
        } catch (NotFoundException nfe) {
            throw new BadInputException("Cannot duplicate test: course not found.");
        }

        // duplicate exercises
        List<TestGroup> tgs = ogTest.getGroups();
        List<TestGroup> newTGs = new ArrayList<>();
        for (TestGroup tg: tgs){
            List<TestExercise> newExes = new ArrayList<>();
            for (TestExercise exe: tg.getExercises()){
                // Persisted tests can only contain reference exercises.
                // Asserts that the retrieved test does not have a concrete exercise
                assert !(exe instanceof ConcreteExercise);
                String dupExerciseId = exercisesService.duplicateExerciseById(specialistId, exe.getId(), null, Visibility.TEST);
                ReferenceExercise newExe = new ReferenceExercise(dupExerciseId, exe.getPoints());
                newExes.add(newExe);
            }
            TestGroup newTG = new TestGroup(tg.getGroupInstructions(), tg.getGroupPoints(), newExes);
            newTGs.add(newTG);
        }
        newTest.setGroups(newTGs);

        // persists new test
        newTest = testDAO.save(newTest);

        List<TestTag> tags = testTagsDAO.getTestTags(testId);
        for (TestTag tmp: tags){
            Tag tag = tmp.getTestTagPK().getTag();
            TestTagPK testTagPK = new TestTagPK(tag, newTest);
            TestTag testTag = new TestTag(tmp.getNExercises(), testTagPK);
            testTagsDAO.save(testTag);
        }

        // TODO: future work - duplicate LiveTest and DeliverDateTest properties 
        return newTest.getId();
    }

    /**
     * Updates test basic properties: title, conclusion, globalInstructions, publishDate and visibility.
     * @param body body containing the new basic properties.
     */
    @Transactional(rollbackFor = ServiceException.class)
    public void updateTestBasicProperties(String testId, Test body) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id '" + testId + "'");
        
        if(body == null)
            throw new BadInputException("Couldn't update test: new body is null");

        body.setId(testId);
        body.setCreationDate(test.getCreationDate());
        body.copyBasicDataTo(test);
        test.verifyProperties();
        testDAO.save(test);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateTestExercisePoints(String testId, int groupIndex, String exerciseId, float points) throws NotFoundException, BadInputException {
        if(points <= 0.0f)
            throw new BadInputException("Couldn't update test: Points must be positive.");

        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id '" + testId + "'");

        if(exerciseId == null)
            throw new BadInputException("Couldn't update test: exercise is null.");

        List<TestGroup> testGroups = test.getGroups();
        if(testGroups == null || groupIndex >= testGroups.size())
            throw new BadInputException("Couldn't update test: invalid group index.");

        TestGroup group = testGroups.get(groupIndex);
        assert group != null;

        List<TestExercise> groupExercises = group.getExercises();
        if(groupExercises == null)
            throw new BadInputException("Couldn't update test: exercise does not belong to the given group.");

        for(TestExercise te : groupExercises){
            if(te.getId().equals(exerciseId)){
                float pointsDiff = points - te.getPoints();
                te.setPoints(points); // update exercise points
                test.setGlobalPoints(test.getGlobalPoints() + pointsDiff); // update test points
                group.setGroupPoints(group.getGroupPoints() + pointsDiff); // update group points
                break;
            }
        }

        testDAO.save(test);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateTestTitle(String testId, String title) throws NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test title: couldn't find test with id '" + testId + "'");
        test.setTitle(title);
        testDAO.save(test);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateTestGlobalInstructions(String testId, String globalInstructions) throws NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test global instructions: couldn't find test with id '" + testId + "'");
        test.setGlobalInstructions(globalInstructions);
        testDAO.save(test);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateTestConclusion(String testId, String conclusion) throws NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test conclusion: couldn't find test with id '" + testId + "'");
        test.setConclusion(conclusion);
        testDAO.save(test);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateTestPublishDate(String testId, LocalDateTime publishDate) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test publish date: couldn't find test with id '" + testId + "'");

        boolean hasResolutions = resolutionDAO.existsTestResolutions(testId);
        if (hasResolutions)
            throw new BadInputException("Couldn't update test publish date: there are already some test resolutions.");
        
        if (publishDate != null && test.getCreationDate().isAfter(publishDate))
            publishDate = test.getCreationDate();

        test.setPublishDate(publishDate);
        testDAO.save(test);
    }

    private Test _updateTestPublishDate(Test test, LocalDateTime publishDate) throws BadInputException {
        assert test != null;
        boolean hasResolutions = resolutionDAO.existsTestResolutions(test.getId());
        if (hasResolutions)
            throw new BadInputException("Couldn't update test publish date: there are already some test resolutions.");

        if (publishDate != null && test.getCreationDate().isAfter(publishDate))
            publishDate = test.getCreationDate();

        test.setPublishDate(publishDate);
        testDAO.save(test);
        return test;
    }


    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateTestVisibility(String testId, Visibility visibility) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test visibility: couldn't find test with id '" + testId + "'");

        _updateTestVisibility(test, visibility);
    }

    private void _updateTestVisibility(Test test, Visibility visibility) throws BadInputException {
        // check course constraints
        if(visibility.equals(Visibility.COURSE)){
            Course course = test.getCourse();
            if (course == null)
                throw new BadInputException("Couldn't update test visibility: course is null");
        }

        // check institution constraints
        if(visibility.equals(Visibility.INSTITUTION)){
            Institution institution = test.getInstitution();
            if (institution == null)
                throw new BadInputException("Couldn't update test visibility: institution is null");
        }
        
        test.setVisibility(visibility);
        testDAO.save(test);
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void updateTestCourse(String testId, String courseId) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test course: couldn't find test with id '" + testId + "'");

        // retrieve course
        Course course = coursesService.getCourseById(courseId);
        if (course == null)
            throw new NotFoundException("Couldn't update test course: couldn't find course with id '" + courseId + "'");

        // check specialist constraints
        Specialist specialist = test.getSpecialist();
        if (specialist != null && !coursesService.checkSpecialistInCourse(course.getId(), specialist.getId()))
            throw new BadInputException("Couldn't update test course: specialist must belong to course");

        test.setCourse(course);
        testDAO.save(test);
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void updateTestGroups(String testId, List<TestGroup> groups) throws NotFoundException, BadInputException {
        if(groups == null)
            throw new NotFoundException("Couldn't update test: no groups were given.");

        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id '" + testId + "'");

        _updateTestGroups(test, groups);
    }

    private void _updateTestGroups(Test test, List<TestGroup> groups) throws NotFoundException, BadInputException {
        LocalDateTime publishDate = test.getPublishDate();
        if (publishDate != null && LocalDateTime.now().isAfter(publishDate))
            throw new BadInputException("Couldn't update test: can't change test after it has already been published.");

        // verify test group properties
        for (TestGroup g: groups){
            g.verifyProperties();
        }

        String specialistId = test.getSpecialistId();

        // Set of ids of exercises on the current version of the test.
        // This set is need to delete exercises that are no longer being used.
        Set<String> ogExercisesIds =
                test.getGroups().stream()
                        .flatMap(g -> g.getExercises().stream())
                        .map(TestExercise::getId).collect(Collectors.toSet());

        // check and duplicate exercises
        List<TestGroup> newTGs = new ArrayList<>();
        List<String> allNewExesIds = new ArrayList<>();
        boolean exercisesCollectionChanged = false; // if the global collection of exercises changed, then the tags need to be updated.

        for (TestGroup tg: groups){
            List<TestExercise> newExes = new ArrayList<>();

            for (TestExercise exe: tg.getExercises()){
                // duplicate or persist exercise
                String exerciseId;
                if(exe != null) {
                    // if the exercise is a concrete exercise, the exercise must be persisted
                    // and a reference, with the id of the persisted exercise, needs to be created.
                    // This allows the exercises to be persisted independently of the test.
                    if (exe instanceof ConcreteExercise ce) {
                        Exercise tmp = ce.getExercise();
                        List<String> tagIds = tmp.getTags().stream().map(Tag::getId).toList();
                        tmp.setVisibility(Visibility.TEST); // set visibility to test

                        // checks if exercise with the given id already existed in the collection.
                        // if so, the exercise body must be updated.
                        if(ogExercisesIds.contains(tmp.getId())){
                            exerciseId = exe.getId();
                            ogExercisesIds.remove(exerciseId);
                            exercisesService.updateAllOnExercise(exerciseId, tmp, tmp.getRubric(), tmp.getSolution(), tagIds, Visibility.TEST);
                        }else{
                            exerciseId = exercisesService.createExercise(tmp, tmp.getSolution(), tmp.getRubric(), tagIds);
                            exercisesCollectionChanged = true;
                        }
                    }
                    // Else the exercise is a reference to an existing exercise.
                    // First we need to check if it was already an exercise of the
                    // test. If it isn't the exercise needs to be duplicated and
                    // a new reference, containing the id of the duplicate,
                    // needs to be created.
                    else {
                        // checks if exercise belonged to the test already
                        if (ogExercisesIds.contains(exe.getId())) {
                            exerciseId = exe.getId();
                            ogExercisesIds.remove(exe.getId());
                        } else {
                            exerciseId = exercisesService.duplicateExerciseById(specialistId, exe.getId(), null, Visibility.TEST);
                            exercisesCollectionChanged = true;
                        }
                    }
                    ReferenceExercise newExe = new ReferenceExercise(exerciseId, exe.getPoints());
                    newExes.add(newExe);
                    allNewExesIds.add(exerciseId);
                }
            }

            TestGroup newTG = new TestGroup(tg.getGroupInstructions(), tg.getGroupPoints(), newExes);
            newTG.calculateGroupPoints();
            newTGs.add(newTG);
        }
        test.setGroups(newTGs);

        // update points
        test.calculatePoints();

        test = testDAO.save(test);

        // count and create tags if there was an update regarding the exercises
        if(exercisesCollectionChanged) {
            deleteTestTags(test.getId());
            createTestTags(exercisesService.countTagsOccurrencesForExercisesList(allNewExesIds), test);
        }

        // delete all exercises that are no longer used
        for(String exId : ogExercisesIds)
            exercisesService.deleteExerciseById(exId);
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void updateTestGroup(String testId, Integer groupIndex, TestGroup group) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id '" + testId + "'");

        List<TestGroup> tgs = test.getGroups();
        if (groupIndex >= tgs.size())
            throw new BadInputException("Couldn't update test: there's no group with index " + groupIndex);

        // verify new group
        group.verifyProperties();

        String specialistId = test.getSpecialistId();

        // Set of ids of exercises on the current version of the group.
        // This set is need to delete exercises that are no longer being used.
        Set<String> ogExercisesIds = group.getExercises().stream().map(TestExercise::getId).collect(Collectors.toSet());

        // check and duplicate exercises
        List<String> allNewExesIds = new ArrayList<>();
        boolean exercisesCollectionChanged = false; // if the global collection of exercises changed, then the tags need to be updated.

        List<TestExercise> newExes = new ArrayList<>();
        for (TestExercise exe: group.getExercises()){
            // duplicate or persist exercise
            String exerciseId;
            if(exe != null) {
                // if the exercise is a concrete exercise, the exercise must be persisted
                // and a reference, with the id of the persisted exercise, needs to be created.
                // This allows the exercises to be persisted independently of the test.
                if (exe instanceof ConcreteExercise ce) {
                    Exercise tmp = ce.getExercise();
                    List<String> tagIds = tmp.getTags().stream().map(Tag::getId).toList();
                    tmp.setVisibility(Visibility.TEST); // set visibility to test

                    // checks if exercise with the given id already existed in the collection.
                    // if so, the exercise body must be updated.
                    if(ogExercisesIds.contains(tmp.getId())){
                        exerciseId = exe.getId();
                        ogExercisesIds.remove(exerciseId);
                        exercisesService.updateAllOnExercise(exerciseId, tmp, tmp.getRubric(), tmp.getSolution(), tagIds, Visibility.TEST);
                    }else{
                        exerciseId = exercisesService.createExercise(tmp, tmp.getSolution(), tmp.getRubric(), tagIds);
                        exercisesCollectionChanged = true;
                    }
                }
                // Else the exercise is a reference to an existing exercise.
                // First we need to check if it was already an exercise of the
                // test. If it isn't the exercise needs to be duplicated and
                // a new reference, containing the id of the duplicate,
                // needs to be created.
                else {
                    // checks if exercise belonged to the test already
                    if (ogExercisesIds.contains(exe.getId())) {
                        exerciseId = exe.getId();
                        ogExercisesIds.remove(exe.getId());
                    } else {
                        exerciseId = exercisesService.duplicateExerciseById(specialistId, exe.getId(), null, Visibility.TEST);
                        exercisesCollectionChanged = true;
                    }
                }
                ReferenceExercise newExe = new ReferenceExercise(exerciseId, exe.getPoints());
                newExes.add(newExe);
                allNewExesIds.add(exerciseId);
            }
        }

        TestGroup newTG = new TestGroup(group.getGroupInstructions(), group.getGroupPoints(), newExes);
        newTG.calculateGroupPoints();
        tgs.set(groupIndex, newTG);
        test.setGroups(tgs);

        // update points
        test.calculatePoints();

        test = testDAO.save(test);

        // count and create tags if there was an update regarding the exercises
        if(exercisesCollectionChanged) {
            deleteTestTags(test.getId());
            createTestTags(exercisesService.countTagsOccurrencesForExercisesList(allNewExesIds), test);
        }

        // delete all exercises that are no longer used
        for(String exId : ogExercisesIds)
            exercisesService.deleteExerciseById(exId);
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void updateTestDeliverDate(String testId, LocalDateTime deliverDate) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id '" + testId + "'");

        // check if it is a "deliver date" test
        if (!(test instanceof DeliverDateTest lt))
            throw new BadInputException("Couldn't update test deliver date: test with id '" + testId + "' is not a deliver date test");

        if (test.getPublishDate().isAfter(deliverDate))
            throw new BadInputException("Couldn't update test deliver date: deliver date occurs before publish date");

        lt.setDeliverDate(deliverDate);
        testDAO.save(lt);
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void updateTestStartDate(String testId, LocalDateTime startDate) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id '" + testId + "'");

        // check if it is live test
        if (!(test instanceof LiveTest lt))
            throw new BadInputException("Couldn't update test start date: test with id '" + testId + "' is not a live test");

        if (test.getPublishDate().isAfter(startDate))
            throw new BadInputException("Couldn't update test start date: test hasn't been published yet");

        lt.setStartDate(startDate);
        testDAO.save(lt);
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void updateTestDuration(String testId, long duration) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id '" + testId + "'");

        // check if it is live test
        if (!(test instanceof LiveTest lt))
            throw new BadInputException("Couldn't update test duration: test with id '" + testId + "' is not a live test");

        if (duration <= 0)
            throw new BadInputException("Couldn't update test duration: duration must be positive");

        lt.setDuration(duration);
        testDAO.save(lt);
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void updateTestStartTolerance(String testId, long startTolerance) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't update test: couldn't find test with id '" + testId + "'");

        // check if it is live test
        if (!(test instanceof LiveTest lt))
            throw new BadInputException("Couldn't update test start tolerance: test with id '" + testId + "' is not a live test");

        if (startTolerance < 0)
            throw new BadInputException("Couldn't update test start tolerance: start tolerance can't be negative");

        lt.setStartTolerance(startTolerance);
        testDAO.save(lt);
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void automaticCorrection(String testId, String correctionType) throws NotFoundException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't evaluate test: couldn't find test with id '" + testId + "'");

        // get exercise points
        Map<String, Float> mapExePoints = new HashMap<>();
        for (TestGroup tg: test.getGroups()){
            for (TestExercise exe: tg.getExercises())
                mapExePoints.put(exe.getId(), exe.getPoints());
        }

        // automatically correct all tests
        List<TestResolution> resolutions = resolutionDAO.getTestResolutions(testId);
        for (TestResolution testResolution : resolutions)
            _automaticCorrectionSingle(testResolution, correctionType, mapExePoints);
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void automaticCorrectionSingle(String testResolutionId, String correctionType) throws NotFoundException {
        TestResolution resolution = resolutionDAO.findById(testResolutionId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't evaluate test: couldn't find test resolution with id '" + testResolutionId + "'");

        // get exercise points
        Test test = _getTestById(resolution.getTestId());
        Map<String, Float> mapExePoints = new HashMap<>();
        for (TestGroup tg: test.getGroups()){
            for (TestExercise exe: tg.getExercises())
                mapExePoints.put(exe.getId(), exe.getPoints());
        }

        _automaticCorrectionSingle(resolution, correctionType, mapExePoints);
    }

    /**
     * Corrects all exercises, from a single
     * test resolution, using the given correction type.
     * @param resolution test resolution to be automatically corrected
     * @param correctionType correction type
     * @param mapExePoints Maps exercise id to the total points of the exercise.
     */
    private void _automaticCorrectionSingle(TestResolution resolution, String correctionType, Map<String, Float> mapExePoints) throws NotFoundException {
        assert resolution != null && mapExePoints != null;

        boolean isRevised = true;
        List<TestResolutionGroup> groups = resolution.getGroups();
        for (TestResolutionGroup trg : groups) {
            float points = 0.0F;
            Map<String, TestExerciseResolutionBasic> resMap = trg.getResolutions();
            assert resMap != null;

            for (Map.Entry<String, TestExerciseResolutionBasic> entry : resMap.entrySet()) {
                TestExerciseResolutionBasic pair = entry.getValue();

                if (pair != null) {
                    Float exeResPercentage = null;
                    try {
                        exeResPercentage = exercisesService.issueExerciseResolutionCorrection(pair.getResolutionId(), correctionType);
                    } catch (BadInputException | ForbiddenException e) {
                        // If an exercise can't be corrected, using the chosen correctionType,
                        // it just is not corrected.
                        isRevised = false;
                    }

                    float newPoints;
                    if (exeResPercentage != null) {
                        newPoints = mapExePoints.get(entry.getKey()) * exeResPercentage / 100;
                        pair.setPoints(newPoints);
                        points += newPoints; // updates total points of the group
                    }
                }
            }
            trg.setGroupPoints(points);
        }
        resolution.setGroups(groups);
        resolution.updateSum();

        // check if everything has been revised
        if (isRevised)
            resolution.setStatus(TestResolutionStatus.REVISED);
        else
            resolution.setStatus(TestResolutionStatus.NOT_REVISED);
        resolutionDAO.save(resolution);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Integer countStudentsTestResolutions(String testId, Boolean total) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get test resolutions for test " + testId + ": couldn't find test with given id.");
        if (total)
            return resolutionDAO.countTotalSubmissionsForTest(testId);
        else
            return resolutionDAO.countDistinctSubmissionsForTest(testId);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Page<TestResolution> getTestResolutions(String testId, Integer page, Integer itemsPerPage) throws NotFoundException{
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get test resolutions for test " + testId + ": couldn't find test with given id.");
        return resolutionDAO.getTestResolutions(testId, PageRequest.of(page, itemsPerPage));
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public TestResolution getTestResolutionById(String resolutionId) throws NotFoundException{
        TestResolution tr = resolutionDAO.findById(resolutionId).orElse(null);
        if (tr == null)
            throw new NotFoundException("Could not get test resolution: there is no resolution with the given identifier.");
        return tr;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String startTest(String testId, String studentId) throws BadInputException, NotFoundException, ForbiddenException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Can't start test: couldn't find test with id '" + testId + "'");
        Student student = studentsService.getStudentById(studentId);
        if (student == null)
            throw new NotFoundException("Can't start test: couldn't find student with id '" + studentId + "'");
        
        LocalDateTime now = LocalDateTime.now();
        if(test.getPublishDate() == null || test.getPublishDate().isAfter(now))
            throw new ForbiddenException("Could not start test: The test has not been published yet.");
        
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
    @Transactional(rollbackFor = ServiceException.class)
    public TestResolution createTestResolution(String testId, TestResolution resolution) throws BadInputException, NotFoundException {
        // check test
        if (testId == null)
            throw new BadInputException("Cannot create test resolution: resolution must belong to a test");
        Test test = _getTestById(testId);
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
            if (testDeliverDate != null && startDate != null && startDate.isAfter(testDeliverDate))
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

        TestResolution lastRes = resolutionDAO.getStudentLastResolution(student.getId(), testId);
        int submissionNr = lastRes != null ? lastRes.getSubmissionNr() + 1 : 1;
        resolution.setSubmissionNr(submissionNr);

        return resolutionDAO.save(resolution);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteTestResolutionById(String resolutionId) throws NotFoundException {
        TestResolution resolution = resolutionDAO.findById(resolutionId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't delete resolution: Resolution '" + resolutionId + "'was not found");

        // delete exercise resolutions
        for (TestResolutionGroup trg: resolution.getGroups()){
            for (TestExerciseResolutionBasic exeResPair: trg.getResolutions().values()){
                if (exeResPair != null)
                    exercisesService.deleteExerciseResolutionById(exeResPair.getResolutionId());
            }
        }
        resolutionDAO.delete(resolution);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateTestResolution(String testResId, TestResolution body) throws NotFoundException, BadInputException {
        TestResolution resolution = resolutionDAO.findById(testResId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't update resolution: Resolution '" + testResId + "'was not found");

        resolution.setSubmissionNr(body.getSubmissionNr());
        resolution.setStatus(body.getStatus());
        resolutionDAO.save(resolution);
        _updateTestResolutionStartDate(resolution, body.getStartDate());
        _updateTestResolutionSubmissionDate(resolution, body.getSubmissionDate());
    }

    @Transactional(rollbackFor = ServiceException.class)
    @Override
    public void updateTestResolutionStartDate(String testResId, LocalDateTime startDate) throws NotFoundException, BadInputException {
        TestResolution resolution = resolutionDAO.findById(testResId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't update resolution start date: Resolution '" + testResId + "'was not found");
        _updateTestResolutionStartDate(resolution, startDate);
    }

    private void _updateTestResolutionStartDate(TestResolution resolution, LocalDateTime startDate) throws NotFoundException, BadInputException {
        Test test = _getTestById(resolution.getTest().getId());

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
            LocalDateTime currentStartDate = lt.getStartDate();

            if (currentStartDate != null){
                if(currentStartDate.isAfter(startDate))
                    throw new BadInputException("Couldn't update resolution start date: new start date needs to be after the current start date.");

                LocalDateTime tolerance = currentStartDate.plusSeconds(lt.getStartTolerance());
                if (startDate.isAfter(tolerance))
                    throw new BadInputException("Couldn't update resolution start date: start date occurs after test start tolerance");
            }
        }

        resolution.setStartDate(startDate);
        resolutionDAO.save(resolution);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateTestResolutionSubmissionDate(String testResId, LocalDateTime submissionDate) throws NotFoundException, BadInputException {
        TestResolution resolution = resolutionDAO.findById(testResId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't update resolution submission date: Resolution '" + testResId + "'was not found");
        _updateTestResolutionSubmissionDate(resolution, submissionDate);
    }

    private void _updateTestResolutionSubmissionDate(TestResolution resolution, LocalDateTime submissionDate) throws NotFoundException, BadInputException {
        Test test = _getTestById(resolution.getTest().getId());

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
    @Transactional(rollbackFor = ServiceException.class)
    public void updateTestResolutionSubmissionNr(String testResId, int submissionNr) throws NotFoundException, BadInputException {
        TestResolution resolution = resolutionDAO.findById(testResId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't update resolution submission number: Resolution '" + testResId + "'was not found");

        if (submissionNr <= 0)
            throw new BadInputException("Couldn't update resolution submission number: submission number needs to be higher than 0");

        resolution.setSubmissionNr(submissionNr);
        resolutionDAO.save(resolution);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void updateTestResolutionStatus(String testResId, TestResolutionStatus status) throws NotFoundException {
        TestResolution resolution = resolutionDAO.findById(testResId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Couldn't update resolution: Resolution '" + testResId + "'was not found");

        resolution.setStatus(status);
        resolutionDAO.save(resolution);
    }


    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public boolean canStudentSubmitResolution(String testId, String studentId) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Can't check if student '" + studentId + "' can make a submission for test '" + testId + "'': couldn't find test with given id.");
        if (!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Can't check if student '" + studentId + "' can make a submission for test '" + testId + "'': couldn't find student with given id.");
        Test test = _getTestById(testId);
        return _canStudentSubmitTestResolution(test, studentId);
    }

    private boolean _canStudentSubmitTestResolution(Test test, String studentId) throws NotFoundException {
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
            return !LocalDateTime.now().isAfter(ddt.getDeliverDate());
        }
        else if (test instanceof LiveTest lt){
            if (lt.getStartDate().isAfter(LocalDateTime.now()))
                return false;
            LocalDateTime endOfTest = lt.getStartDate().plusSeconds(lt.getDuration()).plusSeconds(lt.getStartTolerance());
            return !LocalDateTime.now().isAfter(endOfTest);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Integer countStudentSubmissionsForTest(String testId, String studentId) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot count " + studentId + " submissions for test " + testId + ": couldn't find test with given id.");
        if (!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Cannot count " + studentId + " submissions for test " + testId + ": couldn't find student with given id.");
        return resolutionDAO.countStudentSubmissionsForTest(studentId, testId);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public List<String> getStudentTestResolutionsIds(String testId, String studentId) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get student " + studentId + " resolutions for test " + testId + ": couldn't find test with given id.");
        if (!studentsService.existsStudentById(studentId))
            throw new NotFoundException("Cannot get student " + studentId + " resolutions for test " + testId + ": couldn't find student with given id.");
        return resolutionDAO.getStudentTestResolutionsIds(testId, studentId);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public List<TestResolution> getStudentLastResolutions(String testId) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get last resolutions for test " + testId + ": couldn't find test with given id.");
        List<String> studentIds = resolutionDAO.getDistinctStudentsForTest(testId);

        List<TestResolution> resolutions = new ArrayList<>();
        for (String studentId: studentIds){
            resolutions.add(_getStudentLastResolution(testId, studentId));
        }

        return resolutions;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public List<TestResolution> getStudentLastResolutionsWithEmails(String testId) throws NotFoundException {
        if (!testDAO.existsById(testId))
            throw new NotFoundException("Cannot get last resolutions for test " + testId + ": couldn't find test with given id.");
        List<String> studentIds = resolutionDAO.getDistinctStudentsForTest(testId);

        List<TestResolution> resolutions = new ArrayList<>();
        for (String studentId: studentIds){
            TestResolution tr = _getStudentLastResolution(testId, studentId);
            Student s = studentsService.getStudentById(studentId);
            Student sWithEmail = new Student(); sWithEmail.setId(s.getEmail());
            
            TestResolution trWithEmail = new TestResolution(tr.getId(), tr.getStartDate(), tr.getSubmissionDate(), tr.getSubmissionNr(), tr.getTotalPoints(),
                                                            sWithEmail, tr.getTest(), tr.getStatus(), tr.getGroups());
            
            resolutions.add(trWithEmail);
        }

        return resolutions;
    }

    private TestResolution _getStudentLastResolution(String testId, String studentId) throws NotFoundException {
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
    @Transactional(rollbackFor = ServiceException.class)
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
                    if (tmp != null && res.getSubmissionDate().isBefore(tmp.getSubmissionDate()))
                        res = tmp;
                }
            }
        }

        return res;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
	public void manualCorrectionForExercise(String exeResId, String testResId, Float points, String comment) throws NotFoundException, BadInputException {
        TestResolution updatedTR = getTestResolutionById(testResId);
        ExerciseResolution er = exercisesService.getExerciseResolution(exeResId);
        
        TestExercise ref = getTestExercise(updatedTR.getTestId(), er.getExerciseId());
        float refPoints = ref.getPoints();
        if (refPoints < points)
            throw new BadInputException("Could not correct exercise: points attributed are more than the value of the exercise");

        // updating exercise resolution
        Float oldPoints = er.getPoints();
        if (oldPoints == null)
            oldPoints = 0.0F;
        er.setStatus(ExerciseResolutionStatus.REVISED);
        er.setPoints(points / refPoints * 100); // convert points to a percentage between 0 and 100

        Comment c;
        if (comment != null){
            List<Item> items = new ArrayList<>();
            StringItem si = new StringItem(comment);
            items.add(si);
            c = new Comment(items);
            er.setComment(c);
        }
        exerciseResolutionDAO.save(er);

        // modifying test resolution
        List<TestResolutionGroup> updatedGroups = updatedTR.getGroups();
        boolean found = false;

        for (TestResolutionGroup testResolutionGroup : updatedGroups) {
            Map<String, TestExerciseResolutionBasic> resMap = testResolutionGroup.getResolutions();

            for (Map.Entry<String, TestExerciseResolutionBasic> entry : resMap.entrySet()) {
                TestExerciseResolutionBasic exeResPair = entry.getValue();

                if (exeResPair != null && exeResPair.getResolutionId().equals(exeResId)) {
                    if (testResolutionGroup.getGroupPoints() != null)
                        testResolutionGroup.setGroupPoints(testResolutionGroup.getGroupPoints() + points - oldPoints);
                    else
                        testResolutionGroup.setGroupPoints(points);

                    exeResPair.setPoints(points);
                    found = true;
                    break;
                }
            }
        }

        if (!found)
            throw new NotFoundException("Couldn't manually correct exercise: couldn't find exercise in the test");

        updatedTR.updateSum();
        if (isTestResolutionRevised(testResId))
            updatedTR.setStatus(TestResolutionStatus.REVISED);
        resolutionDAO.save(updatedTR);
	}

    private TestExercise getTestExercise(String testId, String exeId) throws NotFoundException {
        Test test = _getTestById(testId);
        TestExercise res = null;
        for (TestGroup tg: test.getGroups()){
            for (TestExercise exe: tg.getExercises()){
                if (exe.getId().equals(exeId)){
                    res = exe;
                    break;
                }
            }
        }

        return res;
    }

    // assumes that the resolution exists
    private boolean isTestResolutionRevised(String testResId) throws NotFoundException {
        List<ExerciseResolution> exes = getExerciseResolutionsForTestResolution(testResId);

        boolean revised = true;
        for (ExerciseResolution er: exes){
            if (er.getSubmissionNr() != -1){
                if (er.getStatus().equals(ExerciseResolutionStatus.NOT_REVISED)){
                    revised = false;
                    break;
                }
            }
        }

        return revised;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public String uploadResolution(String testResId, String exeId, ExerciseResolution resolution) throws NotFoundException, BadInputException, ForbiddenException {
        TestResolution testRes = getTestResolutionById(testResId);

        if(!testRes.getStatus().equals(TestResolutionStatus.ONGOING))
            throw new ForbiddenException("Could not upload exercise resolution: Test was already submitted.");

        boolean found = false;
        String res = null;
        for (TestResolutionGroup trg: testRes.getGroups()){
            Map<String, TestExerciseResolutionBasic> mapExeRes = trg.getResolutions();
            for (Map.Entry<String, TestExerciseResolutionBasic> entry: mapExeRes.entrySet()){
                //finds the exercise in the test resolution
                if (entry.getKey().equals(exeId)){
                    TestExerciseResolutionBasic resInfo = entry.getValue();

                    // if associated with exercise id there is no info about a resolution,
                    // then an exercise resolution is created
                    if(resInfo == null){
                        resolution = exercisesService.createExerciseResolution(testRes.getStudentId(), exeId, resolution.getData(), testRes.getSubmissionNr());
                        resolution = associateExerciseResToTestRes(resolution, testRes);
                        resInfo = new TestExerciseResolutionBasic(resolution.getId(), 0.0f); // TODO - acho que deveria ser null, mas como o Ray meteu a 0.0f noutros sitios, nao quero mudar a logica
                        mapExeRes.put(exeId, resInfo); // updates info about the resolution in the map
                    }else{
                        // else, updates the current resolution
                        resolution = exercisesService.updateExerciseResolution(resInfo.getResolutionId(), resolution.getData());
                        resInfo.setPoints(0.0f); // TODO - acho que deveria ser null, mas como o Ray meteu a 0.0f noutros sitios, nao quero mudar a logica
                    }

                    res = resolution.getId();
                    found = true;
                    break;
                }
            }
        }

        if (found){
            resolutionDAO.save(testRes);
            return res;
        }
        else
            throw new NotFoundException("Cannot upload resolution for exercise with id '" + exeId + "'' in test resolution with id '" + testResId + "': couldn't find the exercise");
    }

    private ExerciseResolution associateExerciseResToTestRes(ExerciseResolution resolution, TestResolution testRes) {
        assert resolution != null && testRes != null;
        resolution.setTestResolution(testRes);
        return exeResDAO.save(resolution);
    }

    public String createTestExercise(String testId, TestExercise exercise, Integer groupIndex, Integer exeIndex, String groupInstructions) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't add exercise to test: couldn't find test with id '" + testId + "'");

        LocalDateTime publishDate = test.getPublishDate();
        if (publishDate != null && LocalDateTime.now().isAfter(publishDate))
            throw new BadInputException("Couldn't add exercise to test: can't change test after it has already been published.");

        // retrieve and/or duplicate exercise
        String exeFinalId = null;

        String dupExeId = "";
        float points = 0.0F;
        List<String> tagIds = new ArrayList<>();
        if (exercise instanceof ConcreteExercise ce){
            Exercise exe = ce.getExercise();
            exe.setVisibility(Visibility.TEST);
            if (exe.getTags() != null)
                tagIds = exe.getTags().stream().map(Tag::getId).toList();
            dupExeId = exercisesService.createExercise(exe, exe.getSolution(), exe.getRubric(), tagIds);
            exeFinalId = dupExeId;
            points = ce.getPoints();
        }
        else if (exercise instanceof ReferenceExercise re){
            Exercise exe = exercisesService.getExerciseById(re.getId());
            if (exe == null)
                throw new NotFoundException("Couldn't add exercise to test: couldn't find exercise with id '" + re.getId() + "'");
            
            dupExeId = exercisesService.duplicateExerciseById(exe.getSpecialistId(), exe.getId(), null, Visibility.TEST);
            exeFinalId = dupExeId;
            points = re.getPoints();
            tagIds = exe.getTags().stream().map(Tag::getId).toList();
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
        return exeFinalId;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteExerciseFromTest(String testId, String exerciseId) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't delete exercise from test: couldn't find test with id '" + testId + "'");

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
                if (exes.isEmpty())
                    groups.remove(tg);
                tg.calculateGroupPoints();
                break;
            }
        }
        if (found)
            // delete exercise
            exercisesService.deleteExerciseById(exerciseId);
        else
            throw new NotFoundException("Couldn't delete exercise from test: couldn't find exercise with id '" + exerciseId + "'");

        // save test
        test.setGroups(groups);
        test.calculatePoints(); 
        testDAO.save(test);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void removeExerciseFromTest(String testId, String exerciseId) throws NotFoundException, BadInputException {
        Test test = testDAO.findById(testId).orElse(null);
        if (test == null)
            throw new NotFoundException("Couldn't remove exercise from test: couldn't find test with id '" + testId + "'");

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
                if (exes.isEmpty())
                    groups.remove(tg);
                tg.calculateGroupPoints();
                break;
            }
        }
        if (found)
            // change visibility to PRIVATE
            exercisesService.updateExerciseVisibility(exerciseId, Visibility.PRIVATE);
        else
            throw new NotFoundException("Couldn't delete exercise from test: couldn't find exercise with id '" + exerciseId + "'");

        // save test
        test.setGroups(groups);
        test.calculatePoints(); 
        testDAO.save(test);
    }

    private void deleteTestTags(String testId){
        List<TestTag> tags = testTagsDAO.getTestTags(testId);
        if (tags != null){
            testTagsDAO.deleteAll(tags);
        }
    }

    public List<ExerciseResolution> getExerciseResolutionsForTestResolution(String testResId) throws NotFoundException {
        TestResolution testResolution = resolutionDAO.findById(testResId).orElse(null);
        if (testResolution == null)
            throw new NotFoundException("Could not get exercise resolution: there is no test resolution with the given identifier");
        
        List<ExerciseResolution> res = new ArrayList<>();
        for (TestResolutionGroup trg: testResolution.getGroups()){
            for (Map.Entry<String, TestExerciseResolutionBasic> entry: trg.getResolutions().entrySet()){
                TestExerciseResolutionBasic erb = entry.getValue();
                ExerciseResolution exeRes;
                if (erb == null || erb.getResolutionId().isEmpty()){
                    exeRes = new ExerciseResolution();
                    exeRes.setSubmissionNr(-1);
                }
                else {
                    exeRes = exercisesService.getExerciseResolution(erb.getResolutionId());
                }
                res.add(exeRes);
            }
        }

        return res;
    }

    public ExerciseResolution getExerciseResolutionForTestResolutionFromExerciseId(String testResId, String exeId) throws NotFoundException {
        TestResolution testResolution = resolutionDAO.findById(testResId).orElse(null);
        if (testResolution == null)
            throw new NotFoundException("Could not get exercise resolution: there is no test resolution with the given identifier");
        
        for (TestResolutionGroup trg: testResolution.getGroups()){
            for (Map.Entry<String, TestExerciseResolutionBasic> entry: trg.getResolutions().entrySet()){
                if (exeId.equals(entry.getKey())){
                    String exeResId = entry.getValue().getResolutionId();
                    if (exeResId.isEmpty()){
                        ExerciseResolution exeRes = new ExerciseResolution();
                        exeRes.setSubmissionNr(-1);
                        return exeRes;
                    }
                    else {
                        return exercisesService.getExerciseResolution(exeResId);
                    }
                }
            }
        }

        throw new NotFoundException("Could not get exercise resolution: there is no exercise in the test with the given identifier");
    }

    public void submitTestResolution(String testResId) throws NotFoundException, ForbiddenException {
        TestResolution resolution = resolutionDAO.findById(testResId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Could not submit test: couldn't find test resolution");

        if(!resolution.getStatus().equals(TestResolutionStatus.ONGOING))
            throw new ForbiddenException("Could not submit test: Test was already submitted.");

        resolution.setSubmissionDate(LocalDateTime.now());
        resolutionDAO.save(resolution);
        automaticCorrectionSingle(testResId, "auto");
    }

    @Override
    public List<TestTag> getTestTags(String testId) throws NotFoundException {
        return testTagsDAO.getTestTags(testId);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public Test createAutoEvaluationTest(String studentId, List<String> tagsIds, int nrExercises) throws NotFoundException, BadInputException {
        Student student = studentsService.getStudentById(studentId);

        if(nrExercises <= 0 || nrExercises > 10)
            throw new BadInputException("Cannot create auto evaluation test: The number of exercises must be between 0 and 10.");

        if(tagsIds == null)
            throw new BadInputException("Cannot create auto evaluation test: list of tag identifiers was not provided.");

        List<Exercise> exercises = testDAO.getExercisesForAutoEvalTest(tagsIds,PageRequest.of(0, nrExercises)).toList();
        List<TestExercise> testExercises = new ArrayList<>();
        float exercisesPoints = 10f;

        // Checks if any exercises were found
        if(exercises.isEmpty())
            throw new NotFoundException("Cannot create auto evaluation test: Could not find any exercises with the given tags.");

        for(Exercise exercise: exercises){
            System.out.println(exercise.getId());
            String dupId = exercisesService.duplicateExerciseByIdNoSpecialist(exercise.getId(), Visibility.TEST);
            exercise = exercisesService.getExerciseById(dupId);
            // Any multiple choice exercise that requires any justification
            // is converted to an exercise that does not require justification
            if(exercise instanceof MultipleChoiceExercise mce
                    && !mce.getMctype().equals(Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION)
                    && !mce.getMctype().equals(Mctype.TRUE_FALSE_NO_JUSTIFICATION)){
                // to check if the exercise is a multiple choice or true or false
                int MCorTF = mce.getMctype().getCode() / 10;
                mce.setMctype(MCorTF == 1 ? Mctype.MULTIPLE_CHOICE_NO_JUSTIFICATION : Mctype.TRUE_FALSE_NO_JUSTIFICATION);
                entityManager.detach(mce);
                exercisesService.updateExerciseBody(dupId, exercise);
            }
            testExercises.add(new ReferenceExercise(dupId, exercisesPoints));
        }

        TestGroup testGroup = new TestGroup("", exercises.size() * exercisesPoints, testExercises);
        LocalDateTime dateTime = LocalDateTime.now();
        AutoEvaluationTest test = new AutoEvaluationTest(
                null, "Teste de autoavaliação", "",
                exercises.size() * exercisesPoints, "",
                dateTime, dateTime, student,
                Visibility.NOT_LISTED, List.of(testGroup)
        );

        test = testDAO.save(test);

        // count and create tags
        createTestTags(
                exercisesService.countTagsOccurrencesForExercisesList(
                        testExercises.stream()
                                .map(TestExercise::getId)
                                .toList())
                ,test
        );

        // get tags
        List<Tag> tags = testTagsDAO.getTestTags(test.getId())
                                    .stream()
                                    .map(tt -> tt.getTestTagPK().getTag().clone())
                                    .toList();
        test.setTags(tags);

        return test;
    }

    @Override
    public Page<Test> getAutoEvaluationTestsFromStudent(String studentId, int page, int itemsPerPage){
        return testDAO.getAutoEvaluationTestsFromStudent(studentId, PageRequest.of(page, itemsPerPage));
    }
}
