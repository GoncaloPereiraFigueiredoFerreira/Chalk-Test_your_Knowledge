package pt.uminho.di.chalktyk.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Visibility;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.*;
import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;
import pt.uminho.di.chalktyk.models.relational.*;
import pt.uminho.di.chalktyk.repositories.nonrelational.ExerciseDAO;
import pt.uminho.di.chalktyk.repositories.nonrelational.ExerciseResolutionDAO;
import pt.uminho.di.chalktyk.repositories.nonrelational.ExerciseRubricDAO;
import pt.uminho.di.chalktyk.repositories.nonrelational.ExerciseSolutionDAO;
import pt.uminho.di.chalktyk.repositories.relational.ExerciseCopySqlDAO;
import pt.uminho.di.chalktyk.repositories.relational.ExerciseResolutionSqlDAO;
import pt.uminho.di.chalktyk.repositories.relational.ExerciseSqlDAO;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.*;

@Service("exercisesService")
public class ExercisesService implements IExercisesService{
    private final ISpecialistsService specialistsService;
    private final ExerciseDAO exerciseDAO;
    private final ExerciseSqlDAO exerciseSqlDAO;
    private final ICoursesService coursesService;
    private final IInstitutionsService institutionsService;
    private final ITagsService iTagsService;
    private final ExerciseSolutionDAO exerciseSolutionDAO;
    private final ExerciseRubricDAO exerciseRubricDAO;
    @PersistenceContext
    private final EntityManager entityManager;
    private final ExerciseResolutionSqlDAO exerciseResolutionSqlDAO;
    private final ExerciseResolutionDAO exerciseResolutionDAO;
    private final ExerciseCopySqlDAO exerciseCopySqlDAO;

    public ExercisesService(ISpecialistsService specialistsService, ExerciseDAO exerciseDAO, ExerciseSqlDAO exerciseSqlDAO, ICoursesService coursesService, IInstitutionsService institutionsService, ITagsService iTagsService, ExerciseSolutionDAO exerciseSolutionDAO, ExerciseRubricDAO exerciseRubricDAO, EntityManager entityManager,
                            ExerciseResolutionSqlDAO exerciseResolutionSqlDAO, ExerciseResolutionDAO exerciseResolutionDAO, ExerciseCopySqlDAO exerciseCopySqlDAO) {
        this.specialistsService = specialistsService;
        this.exerciseDAO = exerciseDAO;
        this.exerciseSqlDAO = exerciseSqlDAO;
        this.coursesService = coursesService;
        this.institutionsService = institutionsService;
        this.iTagsService = iTagsService;
        this.exerciseSolutionDAO = exerciseSolutionDAO;
        this.exerciseRubricDAO = exerciseRubricDAO;
        this.entityManager = entityManager;
        this.exerciseResolutionSqlDAO = exerciseResolutionSqlDAO;
        this.exerciseResolutionDAO = exerciseResolutionDAO;
        this.exerciseCopySqlDAO = exerciseCopySqlDAO;
    }

    /**
     * Get Exercise by ID
     *
     * @param exerciseId of the exercise
     * @return exercise from the given ID. Can return shallow exercises.
     **/
    private Exercise _getExerciseById(String exerciseId) throws NotFoundException {
        Optional<Exercise> obj = exerciseDAO.findById(exerciseId);
        if (obj.isPresent()){
            return obj.get();
        }
        else
            throw new NotFoundException("Couldn't get exercise with id: " + exerciseId);
    }

    /**
     * Get Exercise by ID
     *
     * @param exerciseId of the exercise
     * @return concrete exercise with the given ID
     **/
    @Override
    public Exercise getExerciseById(String exerciseId) throws NotFoundException {
        // gets a concrete instance of the exercise (because it might be a shallow exercise)
        ConcreteExercise concreteExercise = getExerciseConcreteInstance(exerciseId);

        // gets tags
        ExerciseSQL exerciseSQL = exerciseSqlDAO.findById(exerciseId).orElse(null);
        assert exerciseSQL != null;
        concreteExercise.setTags(exerciseSQL.getTags());

        return concreteExercise;
    }

    /**
     * Get Exercise by ID
     *
     * @param exerciseId of the exercise
     * @return true if exercise exists, false otherwise
     **/
    @Override
    public boolean exerciseExists(String exerciseId) {
        return exerciseSqlDAO.existsById(exerciseId);
    }

    /**
     * Verify is exercise is shallow
     *
     * @param exerciseId of the exercise
     * @return true if exercise is shallow, false otherwise
     **/
    @Override
    public boolean exerciseIsShallow(String exerciseId) throws NotFoundException {
        return _getExerciseById(exerciseId) instanceof ShallowExercise;
    }

    /**
     * Creates an exercise.
     *
     * @param exercise     body of the exercise to be created. Regarding the metadata should
     *                     contain, at least, the specialist identifier but may contain the institutionId and courseId
     * @param rubric
     * @param solution
     * @param tagsIds
     * @param visibility
     * @return new exercise identifier
     * @throws BadInputException if the exercise is not formed correctly
     */

    @Override
    @Transactional
    public String createExercise(ConcreteExercise exercise, ExerciseRubric rubric, ExerciseSolution solution, List<String> tagsIds, VisibilitySQL visibility) throws BadInputException {
        if(exercise == null)
            throw new BadInputException("Cannot create exercise: Exercise is null");

        // Check if tags are valid
        for (String id:tagsIds)
            if(iTagsService.getTagById(id)==null)
                throw new BadInputException("Cannot create exercise: There is not tag with id \"" + id + "\".");
        // Check if visibility is valid
        if(visibility == null)
            throw new BadInputException("Cannot create exercise: Visibility cant be null");

        // Checks if specialist exists and gets the institution where it belongs
        Institution institution;
        try { institution = institutionsService.getSpecialistInstitution(exercise.getSpecialistId()); }
        catch (NotFoundException nfe){ throw new BadInputException("Cannot create exercise: Specialist does not exist."); }

        // sets identifier of the institution
        if(institution != null) exercise.setInstitutionId(institution.getName());

        // If the specialist that owns the exercise does not have an institution,
        // then the exercise's visibility cannot be set to institution.
        if (visibility == VisibilitySQL.INSTITUTION && institution == null)
            throw new BadInputException("Cannot create exercise: cannot set visibility to INSTITUTION");

        // Check if course is valid
        String courseId = exercise.getCourseId();
        try {
            if (courseId != null && !coursesService.checkSpecialistInCourse(courseId, exercise.getSpecialistId()))
                throw new BadInputException("Cannot create exercise: course not found.");
        } catch (NotFoundException nfe){
            throw new BadInputException("Cannot create exercise: Specialist does not belong to the given course.");
        }

        // Cannot set visibility to COURSE without a course associated
        if (courseId == null && visibility == VisibilitySQL.COURSE)
            throw new BadInputException("Cannot create exercise: cannot set visibility to COURSE without a course associated.");

        // Prevent overrides
        exercise.setId(null);
        exercise.setRubricId(null);
        exercise.setSolutionId(null);

        // Check exercise properties
        exercise.verifyProperties();

        // Check solution
        if (solution != null) {
            solution.verifyInsertProperties();
            exercise.verifyResolutionProperties(solution.getData());
            solution = exerciseSolutionDAO.save(solution);
            exercise.setSolutionId(solution.getId());
        }

        // Check rubric
        if (rubric != null) {
            exercise.verifyRubricProperties(rubric);
            rubric = exerciseRubricDAO.save(rubric);
            exercise.setRubricId(rubric.getId());
        }

        // persists the exercise in nosql database
        exercise = exerciseDAO.save(exercise);

        InstitutionSQL institutionSql = exercise.getInstitutionId() != null ? entityManager.getReference(InstitutionSQL.class, exercise.getInstitutionId()) : null;
        SpecialistSQL specialistSql = exercise.getSpecialistId() != null ? entityManager.getReference(SpecialistSQL.class, exercise.getSpecialistId()) : null;
        CourseSQL courseSql = exercise.getCourseId() != null ? entityManager.getReference(CourseSQL.class, exercise.getCourseId()) : null;
        Set<TagSQL> tagsSet = new HashSet<>();
        for(String tag : tagsIds){
            tagsSet.add(entityManager.getReference(TagSQL.class, tag));
        }

        ExerciseSQL relExercise =
                new ExerciseSQL(
                        exercise.getId(),
                        institutionSql,
                        specialistSql,
                        courseSql,
                        exercise.getTitle(),
                        exercise.getExerciseType(),
                        visibility,
                        0,
                        tagsSet);
        exerciseSqlDAO.save(relExercise);
        return exercise.getId();
    }

    /**
     * Delete exercise by id. If the exercise has copies, it won't be deleted,
     * but instead, its visibility will be set to "deleted",
     * the specialist, course and institution will be set to null.
     * Calling method should be transactional, to rollback changes in case of
     * an exception.
     *
     * @param exerciseId identifier of the exercise
     * @throws NotFoundException     if the exercise was not found
     */
    @Override
    @Transactional
    public void deleteExerciseById(String exerciseId) throws NotFoundException {
        // Checks if exercise exists
        ExerciseSQL exerciseSQL = exerciseSqlDAO.findById(exerciseId).orElse(null);
        if(exerciseSQL == null)
            throw new NotFoundException("Could not delete exercise: Exercise does not exist.");
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        assert exercise != null;

        // Checks existence of resolutions
        //boolean cannotBeDeleted = exerciseResolutionSqlDAO.countExerciseResolutionSQLByExercise_Id(exerciseId) > 0;
        deleteExerciseResolutions(exerciseId);

        // Checks existence of copies
        // If the exercise has copies it cannot be deleted
        // until all copies seize to exist.
        boolean cannotBeDeleted = exerciseSqlDAO.countExerciseCopies(exerciseId) > 0;

        // When the exercise cannot not be deleted:
        if(cannotBeDeleted) {
            //  visibility is set to "DELETED"
            exerciseSQL.setVisibility(VisibilitySQL.DELETED);

            // owner is removed
            exerciseSQL.setSpecialist(null);
            exercise.setSpecialistId(null);

            // course is removed
            exerciseSQL.setCourse(null);
            exercise.setCourseId(null);

            // institution is removed
            exerciseSQL.setInstitution(null);
            exercise.setInstitutionId(null);

            //save modifications in both databases
            exerciseSqlDAO.save(exerciseSQL);
            exerciseDAO.save(exercise);
        }
        else{
            if(exercise instanceof ConcreteExercise ce){
                // Delete rubric if exists
                if(ce.getSolutionId() != null)
                    exerciseSolutionDAO.deleteById(ce.getSolutionId());

                // Delete solution if exists
                if(ce.getRubricId() != null)
                    exerciseRubricDAO.deleteById(ce.getRubricId());
            }

            // Delete from exercise copy table in sql database
            removeExerciseFromCopiesTable(exerciseId);

            // Delete from exercise table in sql database
            exerciseSqlDAO.deleteById(exerciseId);

            // Delete exercise document in nosql database
            exerciseDAO.deleteById(exerciseId);
        }
    }

    /**
     * Duplicates the exercise that contains the given identifier.
     * The id of the specialist, and if existent, the institution identifier
     * is added to the new exercise metadata. The visibility of the new exercise is
     * set to private, and is not associated with any course.
     *
     * @param specialistId identifier of the specialist that wants to own the exercise
     * @param exerciseId   exercise identifier
     * @return new exercise identifier
     * @throws NotFoundException     if the exercise was not found
     */
    @Override
    @Transactional
    public String duplicateExerciseById(String specialistId, String exerciseId) throws NotFoundException {
        // checks if the specialist exists
        if(!specialistsService.existsSpecialistById(specialistId))
            throw new NotFoundException("Could not duplicate exercise: specialist does not exist.");

        // gets specialists institution
        String institutionId = null;
        Institution institution =
                institutionsService.getSpecialistInstitution(specialistId);
        if(institution != null)
            institutionId = institution.getName();

        // finds the original exercise since the source exercise
        // might be shallow, i.e., might not be the original exercise
        String originalId = exerciseId;
        Exercise original = _getExerciseById(exerciseId);

        // if the exercise is shallow, then gets the original exercise id
        if(original instanceof ShallowExercise originalShallow)
            originalId = originalShallow.getOriginalExerciseId();

        // Creates a shallow exercise, because a copy has the same contents as the original
        ShallowExercise copy = new ShallowExercise(originalId, specialistId, institutionId, null, 0.0f);

        // persists nosql copy
        copy = exerciseDAO.save(copy);

        // get source sql (some sql information might differ from the original exercise sql,
        //  and since the exercise being duplicated is the source exercise, the source sql
        //  info is the one that should be duplicated)
        ExerciseSQL sourceSQL = exerciseSqlDAO.findById(exerciseId).orElse(null);
        assert sourceSQL != null;

        // create sql entry
        InstitutionSQL institutionSQL = institutionId != null ? entityManager.getReference(InstitutionSQL.class, institutionId) : null;
        SpecialistSQL specialistSQL = entityManager.getReference(SpecialistSQL.class, specialistId);
        ExerciseSQL copySQL = new ExerciseSQL(copy.getId(), institutionSQL, specialistSQL, null, sourceSQL.getTitle(), sourceSQL.getExerciseType(), VisibilitySQL.PRIVATE, 0, sourceSQL.getTags());
        copySQL = exerciseSqlDAO.save(copySQL);

        // create copy entry
        addExerciseToCopiesTable(copySQL.getId(), originalId);

        return copySQL.getId();
    }

    /**
     * Updates an exercise. If an object is 'null' than it is considered that it should remain the same.
     * To delete it, a specific delete method should be invoked.
     *
     * @param exerciseId   identifier of the exercise to be updated
     * @param exercise     new exercise body
     * @param rubric       new exercise rubric
     * @param solution     new exercise solution
     * @param tagsIds      new list of tags
     * @param visibility   new visibility
     * @throws NotFoundException     if the exercise was not found
     */
    @Override
    @Transactional
    public void updateAllOnExercise(String exerciseId, Exercise exercise, ExerciseRubric rubric, ExerciseSolution solution, List<String> tagsIds, VisibilitySQL visibility) throws NotFoundException, BadInputException {
        if(!exerciseExists(exerciseId))
            throw new NotFoundException("ExerciseId does not correspond to any exercise");
        if(exercise!=null) {
            if(exercise.getId()==null)
                exercise.setId(exerciseId);
            if (!Objects.equals(exerciseId, exercise.getId()))
                throw new BadInputException("ExerciseId must be the same as the one given in the exercise");
            updateExerciseBody(exercise, rubric == null, solution == null); //if rubric or solution are not null, then they will verify exercise
        }
        if(rubric!=null)
            updateExerciseRubric(exerciseId,rubric);
        if(solution!=null)
            updateExerciseSolution(exerciseId,solution);
        if(tagsIds!=null)
            updateExerciseTags(exerciseId,tagsIds);
        if(visibility!=null)
            updateExerciseVisibility(exerciseId,visibility);
    }

    /**
     * Updates an exercise body.
     *
     * @param exercise     new exercise body
     * @param hasNewSolution if true then method verifies if current solution corresponds to the exercise
     * @param hasNewRubric if true then method verifies if current rubric corresponds to the exercise
     * @throws BadInputException solution, rubric, institution or specialist ids where changed,
     * course was not found,
     * the rubric or solution don't belong to the new exercise body
     */
    @Transactional
    public void updateExerciseBody(Exercise exercise, Boolean hasNewRubric, Boolean hasNewSolution) throws BadInputException {
        Exercise origExercise = exerciseDAO.findById(exercise.getId()).orElse(null);
        assert origExercise != null;


        String courseId = exercise.getCourseId();
        try {
            if (courseId != null && !coursesService.checkSpecialistInCourse(courseId, exercise.getSpecialistId()))
                throw new BadInputException("Cannot create exercise: course not found.");
        } catch (NotFoundException nfe){
            throw new BadInputException("Cannot create exercise: Specialist does not belong to the given course.");
        }

        //Exercise specialist and institutions cannot be changed
        if(!Objects.equals(exercise.getSpecialistId(), origExercise.getSpecialistId()))
            throw new BadInputException("Cannot change exercise specialist");
        if(!Objects.equals(exercise.getInstitutionId(), origExercise.getInstitutionId()))
            throw new BadInputException("Cannot change exercise institution");


        if(exercise instanceof ShallowExercise shallowExercise){
            if(!(origExercise instanceof ShallowExercise origSE))
                throw new BadInputException("Cannot convert concrete exercise to shallow exercise");
            if(!Objects.equals(shallowExercise.getOriginalExerciseId(), origSE.getOriginalExerciseId()))
                throw new BadInputException("Cannot change shallow exercise original exercise");
        }
        else if (exercise instanceof ConcreteExercise concreteExercise) {
            //If new exercise is concrete and original was shallow then we need to convert it to concrete
            if(origExercise instanceof ShallowExercise origSE){
                convertShallowToConcreteAndUpdate(origSE,false,null,false,null);
                origExercise = exerciseDAO.findById(exercise.getId()).orElse(null);
            }
            if(origExercise instanceof ConcreteExercise origCE) {
                //Ids from rubric and solution cannot be changed
                if(concreteExercise.getRubricId()==null)
                    concreteExercise.setRubricId(origCE.getRubricId());
                if(!Objects.equals(concreteExercise.getRubricId(), origCE.getRubricId()))
                    throw new BadInputException("Cannot change exercise rubric id");
                if(concreteExercise.getSolutionId()==null)
                    concreteExercise.setSolutionId(origCE.getSolutionId());
                if(!Objects.equals(concreteExercise.getSolutionId(), origCE.getSolutionId()))
                    throw new BadInputException("Cannot change exercise solution id");
            } else throw new AssertionError("Exercise is not concrete after conversion");
        }

        // Check exercise properties
        exercise.verifyInsertProperties();

        //Verify that the exercise rubric and solution and still correct after exercise changes
        if(exercise instanceof ConcreteExercise concreteExercise){
            if(hasNewRubric) {
                String rubricId = concreteExercise.getRubricId();
                if (rubricId != null) {
                    ExerciseRubric exerciseRubric = exerciseRubricDAO.findById(rubricId).orElse(null);
                    assert exerciseRubric != null;
                    concreteExercise.verifyRubricProperties(exerciseRubric);
                }
            }

            if(hasNewSolution) {
                String solutionId = concreteExercise.getSolutionId();
                if (solutionId != null) {
                    ExerciseSolution exerciseSolution = exerciseSolutionDAO.findById(solutionId).orElse(null);
                    assert exerciseSolution != null;
                    concreteExercise.verifyResolutionProperties(exerciseSolution.getData());
                }
            }

            //If type changed then modify it on sql
            if(!Objects.equals(((ConcreteExercise) origExercise).getExerciseType(), concreteExercise.getExerciseType())||
                    !Objects.equals(((ConcreteExercise) origExercise).getTitle(), concreteExercise.getTitle())){
                ExerciseSQL exerciseSQL = exerciseSqlDAO.findById(concreteExercise.getId()).orElse(null);
                assert exerciseSQL != null;
                exerciseSQL.setTitle(concreteExercise.getTitle());
                exerciseSQL.setExerciseType(concreteExercise.getExerciseType());
                if(!Objects.equals(exercise.getCourseId(), origExercise.getCourseId())){
                    if(exercise.getCourseId()==null){
                        exerciseSQL.setCourse(null);
                    }
                    else exerciseSQL.setCourse(entityManager.getReference(CourseSQL.class,exercise.getCourseId()));
                }
                exerciseSqlDAO.save(exerciseSQL);
            }
        } else if(!Objects.equals(exercise.getCourseId(), origExercise.getCourseId())){ //If new exercise is shallow verify if course needs to be updated
                ExerciseSQL exerciseSQL = exerciseSqlDAO.findById(exercise.getId()).orElse(null);
                assert exerciseSQL != null;
                if(exercise.getCourseId()==null){
                    exerciseSQL.setCourse(null);
                }
                else exerciseSQL.setCourse(entityManager.getReference(CourseSQL.class,exercise.getCourseId()));
                exerciseSqlDAO.save(exerciseSQL);
        }


        exerciseDAO.save(exercise);
    }

    /**
     * Updates an exercise tags. Assumes exercise exists
     *
     * @param exerciseId   identifier of the exercise to be updated
     * @param tagsIds      new list of tags
     */
    @Transactional
    public void updateExerciseTags(String exerciseId, List<String> tagsIds) throws BadInputException, NotFoundException {
        for (String id:tagsIds)
            if(iTagsService.getTagById(id)==null)
                throw new BadInputException("Cannot create exercise: There is not tag with id \"" + id + "\".");
        ExerciseSQL exerciseSQL = exerciseSqlDAO.findById(exerciseId).orElse(null);
        assert exerciseSQL!=null;

        Set<String> setTagsIds = new HashSet<>(tagsIds);
        Set<TagSQL> tags = new HashSet<>();
        for (String tagId:setTagsIds){
            TagSQL tag = iTagsService.getTagById(tagId);
            if(tag==null)
                throw new NotFoundException("Tag with id "+tagId+" was not found");
            tags.add(tag);
        }
        exerciseSQL.setTags(tags);
        exerciseSqlDAO.save(exerciseSQL);
    }

    /**
     * Updates and exercise visibility, Assumes exercise exists
     *
     * @param exerciseId   identifier of the exercise to be updated
     * @param visibility   new visibility
     */
    private void updateExerciseVisibility(String exerciseId,VisibilitySQL visibility) throws BadInputException {
        ExerciseSQL exerciseSQL = exerciseSqlDAO.findById(exerciseId).orElse(null);
        assert exerciseSQL!=null;

        // Checks if specialist exists and gets the institution where it belongs
        pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution institution;
        try { institution = institutionsService.getSpecialistInstitution(exerciseSQL.getSpecialist().getId()); }
        catch (NotFoundException nfe){ throw new BadInputException("Cannot create exercise: Specialist does not exist."); }

        // If the specialist that owns the exercise does not have an institution,
        // then the exercise's visibility cannot be set to institution.
        if (visibility == VisibilitySQL.INSTITUTION && institution == null)
            throw new BadInputException("Cannot set visibility to INSTITUTION");

        // Check if course is valid
        String courseId = exerciseSQL.getCourse().getId();
        try {
            if (courseId != null && !coursesService.checkSpecialistInCourse(courseId, exerciseSQL.getSpecialist().getId()))
                throw new BadInputException("Cannot create exercise: course not found.");
        } catch (NotFoundException nfe){
            throw new BadInputException("Cannot create exercise: Specialist does not belong to the given course.");
        }

        // Cannot set visibility to COURSE without a course associated
        if (courseId == null && visibility == VisibilitySQL.COURSE)
            throw new BadInputException("Cannot create exercise: cannot set visibility to COURSE without a course associated.");
        exerciseSQL.setVisibility(visibility);
        exerciseSqlDAO.save(exerciseSQL);
    }


    /**
     * Retrieves the rubric of an exercise.
     * @param exerciseId exercise identifier
     * @return rubric of the exercise or null if it doesn't exist.
     * @throws NotFoundException if the exercise was not found
     */
    @Override
    public ExerciseRubric getExerciseRubric(String exerciseId) throws NotFoundException {
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if(exercise == null)
            throw new NotFoundException("Cannot get exercise rubric: exercise does not exist.");

        // if exercise is shallow, the rubric needs to be retrieved from the original
        if(exercise instanceof ShallowExercise se){
            assert se.getOriginalExerciseId() != null;
            exercise = exerciseDAO.findById(se.getOriginalExerciseId()).orElse(null);
        }

        // Retrieves rubric from the exercise
        if(exercise instanceof ConcreteExercise ce){
            if(ce.getRubricId() == null)
                return null;
            else
                return exerciseRubricDAO.findById(ce.getRubricId()).orElse(null);
        }else {
            throw new AssertionError("Exercise is not a concrete exercise.");
        }
    }

    /**
     * Create an exercise rubric
     *
     * @param exerciseId exercise identifier
     * @param rubric     new rubric
     * @throws NotFoundException if the exercise was not found
     * @throws BadInputException if the rubric is not correctly formulated
     */
    @Override
    @Transactional
    public void createExerciseRubric(String exerciseId, ExerciseRubric rubric) throws NotFoundException, BadInputException {
        // finds exercise
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if(exercise == null)
            throw new NotFoundException("Cannot create exercise rubric: exercise does not exist.");

        // checks if rubric is not null
        if(rubric == null)
            throw new BadInputException("Cannot create exercise rubric: rubric is null.");
        rubric.setId(null); // prevent overwrite attack

        // if exercise is shallow, the exercise needs to be converted to a concrete exercise.
        if(exercise instanceof ShallowExercise se){
            convertShallowToConcreteAndUpdate(se, true, rubric, false, null);
        }else{
            assert exercise instanceof ConcreteExercise;
            ConcreteExercise ce = (ConcreteExercise) exercise;

            // checks if rubric is valid
            ce.verifyRubricProperties(rubric);

            // if exercise already has rubric, then the rubric document should be overwritten, conserving the identifier.
            if(ce.getRubricId() != null) {
                ExerciseRubric oldRubric = exerciseRubricDAO.findById(ce.getRubricId()).orElse(null);
                assert oldRubric != null;
                rubric.setId(oldRubric.getId());
            }

            // persists rubric
            rubric = exerciseRubricDAO.save(rubric);
            ce.setRubricId(rubric.getId());

            // updates the exercise document
            exerciseDAO.save(ce);
        }
    }

    @Override
    @Transactional
    public void deleteExerciseRubric(String exerciseId) throws BadInputException, NotFoundException {
        // finds exercise
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if(exercise == null)
            throw new NotFoundException("Cannot create exercise solution: exercise does not exist.");

        // if exercise is shallow convert it to concrete exercise and delete the rubric
        if(exercise instanceof ShallowExercise se){
            // the rubric is deleted by setting the 'updateRubric' argument to true,
            // and defining the rubric as null
            convertShallowToConcreteAndUpdate(se, true, null, false, null);
        }else{
            // if the exercise is a concrete exercise
            assert exercise instanceof ConcreteExercise;
            ConcreteExercise ce = (ConcreteExercise) exercise;

            // remove the rubric identifier
            String rubricId = ce.getRubricId();
            ce.setRubricId(null);

            // delete the rubric document
            exerciseRubricDAO.deleteById(rubricId);

            // saves the exercise
            exerciseDAO.save(ce);
        }
    }

    @Override
    @Transactional
    public void updateExerciseRubric(String exerciseId, ExerciseRubric rubric) throws BadInputException, NotFoundException {
        createExerciseRubric(exerciseId, rubric);
    }

    @Override
    @Transactional
    public void createExerciseSolution(String exerciseId, ExerciseSolution solution) throws NotFoundException, BadInputException {
        // finds exercise
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if(exercise == null)
            throw new NotFoundException("Cannot create exercise solution: exercise does not exist.");

        // checks if solution is not null
        if(solution == null)
            throw new BadInputException("Cannot create exercise solution: solution is null.");
        solution.setId(null); // prevent overwrite attack

        // if exercise is shallow, the exercise needs to be converted to a concrete exercise.
        // To do this:
        //  - the exercise is removed from the original exercise copies
        //  - the rubric of the original exercise should be duplicated.
        if(exercise instanceof ShallowExercise se){
            convertShallowToConcreteAndUpdate(se, false, null, true, solution);
        }else{
            assert exercise instanceof ConcreteExercise;
            ConcreteExercise ce = (ConcreteExercise) exercise;

            // checks if solution is valid
            ce.verifyResolutionProperties(solution.getData());

            // if exercise already has solution, then the solution document should be overwritten, conserving the identifier.
            if(ce.getSolutionId() != null) {
                ExerciseSolution oldSolution = exerciseSolutionDAO.findById(ce.getSolutionId()).orElse(null);
                assert oldSolution != null;
                solution.setId(oldSolution.getId());
            }

            // persists solution
            solution = exerciseSolutionDAO.save(solution);
            ce.setSolutionId(solution.getId());

            // updates the exercise document
            exerciseDAO.save(ce);
        }
    }

    @Override
    public ExerciseSolution getExerciseSolution(String exerciseId) throws NotFoundException {
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if(exercise == null)
            throw new NotFoundException("Cannot get exercise solution: exercise does not exist.");

        // if exercise is shallow, the solution needs to be retrieved from the original
        if(exercise instanceof ShallowExercise se){
            assert se.getOriginalExerciseId() != null;
            exercise = exerciseDAO.findById(se.getOriginalExerciseId()).orElse(null);
        }

        // Retrieves solution from the exercise
        if(exercise instanceof ConcreteExercise ce){
            if(ce.getSolutionId() == null)
                return null;
            else
                return exerciseSolutionDAO.findById(ce.getSolutionId()).orElse(null);
        }else {
            throw new AssertionError("Exercise is not a concrete exercise.");
        }
    }

    @Override
    @Transactional
    public void updateExerciseSolution(String exerciseId, ExerciseSolution exerciseSolution) throws NotFoundException, BadInputException {
        createExerciseSolution(exerciseId, exerciseSolution);
    }

    @Override
    @Transactional
    public void deleteExerciseSolution(String exerciseId) throws NotFoundException, BadInputException {
        // finds exercise
        Exercise exercise = exerciseDAO.findById(exerciseId).orElse(null);
        if(exercise == null)
            throw new NotFoundException("Cannot create exercise solution: exercise does not exist.");

        // if exercise is shallow convert it to concrete exercise and delete the solution
        if(exercise instanceof ShallowExercise se){
            // the solution is deleted by setting the 'updateSolution' argument to true,
            // and defining the solution as null
            convertShallowToConcreteAndUpdate(se, false, null, true, null);
        }else{
            // if the exercise is a concrete exercise
            assert exercise instanceof ConcreteExercise;
            ConcreteExercise ce = (ConcreteExercise) exercise;

            // remove the solution identifier
            String solutionId = ce.getSolutionId();
            ce.setSolutionId(null);

            // delete the solution document
            exerciseSolutionDAO.deleteById(solutionId);

            // saves the exercise
            exerciseDAO.save(ce);
        }
    }

    @Override
    @Transactional
    public String getExerciseCourse(String exerciseId) throws NotFoundException {
        ExerciseSQL exerciseSQL = exerciseSqlDAO.findById(exerciseId).orElse(null);
        if(exerciseSQL==null)
            throw new NotFoundException("There is no exercise for the given id");
        if(exerciseSQL.getCourse()==null)
            return null;
        return exerciseSQL.getCourse().getId();
    }

    @Override
    @Transactional
    public String getExerciseInstitution(String exerciseId) throws NotFoundException {
        ExerciseSQL exerciseSQL = exerciseSqlDAO.findById(exerciseId).orElse(null);
        if(exerciseSQL==null)
            throw new NotFoundException("There is no exercise for the given id");
        if(exerciseSQL.getInstitution()==null)
            return null;
        return exerciseSQL.getInstitution().getId();
    }

    /**
     * Issue the automatic correction of the exercise resolutions.
     * The correction can either be automatic or done by AI.
     * For a given exercise, it may support either, both, or none of the correction types.
     *
     * @param exerciseId     identifier of the exercise
     * @param correctionType type of correction. Can be 'auto' or 'ai'.
     * @throws BadInputException     if the correction type is not valid. It should be 'auto' or 'ai'.
     * @throws NotFoundException     if the exercise does not exist
     * @throws UnauthorizedException if the exercise does not support the requested correction type.
     */
    @Transactional
    @Override
    public void issueExerciseResolutionsCorrection(String exerciseId, String correctionType) throws BadInputException, NotFoundException, UnauthorizedException {
        if(!correctionType.equalsIgnoreCase("auto") && !correctionType.equalsIgnoreCase("ai"))
            throw new BadInputException("Could not correct exercise resolutions: Correction type must be 'auto' or 'ai'.");

        // gets a concrete instance of the exercise and it's rubric
        ConcreteExercise exercise = getExerciseConcreteInstance(exerciseId);

        // checks existence of a rubric, which is required
        // for the automatic correction of the exercise
        ExerciseRubric rubric = getExerciseRubric(exerciseId);

        // checks existence of a solution, which is required
        // for the automatic correction of the exercise
        ExerciseSolution solution = getExerciseSolution(exerciseId);

        if(correctionType.equalsIgnoreCase("auto"))
            automaticExerciseResolutionsCorrection(exercise, rubric, solution);
        else if (correctionType.equalsIgnoreCase("ai")) {
            // TODO - add the AI part
        }
    }

    /**
     * Issue the automatic correction of the exercise resolutions.
     * The correction can either be automatic or done by AI.
     * For a given exercise, it may support either, both, or none of the correction types.
     *
     * @param resolutionId   identifier of the exercise resolution
     * @param correctionType type of correction. Can be 'auto' or 'ai'.
     * @return points attributed to the resolution
     * @throws BadInputException     if the correction type is not valid. It should be 'auto' or 'ai'.
     * @throws NotFoundException     if the resolution, or the exercise, or the rubric of the exercise, or the solution of the exercise does not exist
     * @throws UnauthorizedException if the exercise does not support the requested correction type.
     */
    @Transactional
    @Override
    public float issueExerciseResolutionCorrection(String resolutionId, String correctionType) throws BadInputException, NotFoundException, UnauthorizedException{
        // gets the identifier of the exercise
        ExerciseResolution resolution = exerciseResolutionDAO.findById(resolutionId).orElse(null);
        if(resolution == null)
            throw new NotFoundException("Could not correct exercise: resolution does not exist.");

        // if the exercise is already revised, then returns the points attributed to the resolution
        if(resolution.getStatus() == ExerciseResolutionStatus.REVISED)
            return resolution.getPoints();
        String exerciseId = resolution.getExerciseId();

        // gets a concrete instance of the exercise and it's rubric
        ConcreteExercise exercise = getExerciseConcreteInstance(exerciseId);

        // checks existence of a rubric, which is required
        // for the automatic correction of the exercise
        ExerciseRubric rubric = getExerciseRubric(exerciseId);

        // checks existence of a solution, which is required
        // for the automatic correction of the exercise
        ExerciseSolution solution = getExerciseSolution(exerciseId);

        return automaticExerciseResolutionCorrection(resolution, exercise, rubric, solution);
    }

    /**
     * @param exerciseId identifier of an exercise
     * @param total      The total number of submissions can be obtained by setting the value to 'true'.
     *                   The number of students that submitted can be obtained by setting the value to 'false'
     * @return the total resolution submissions or the number of students
     * that submitted a resolution for a specific exercise depending
     * on the value of the 'total' parameter.
     */
    @Override
    public Integer countExerciseResolutions(String exerciseId, boolean total) {
        if(total)
            return exerciseResolutionSqlDAO.countExerciseResolutionSQLSByExercise_Id(exerciseId);
        else
            return exerciseResolutionSqlDAO.countStudentsWithResolutionForExercise(exerciseId);
    }

    /**
     * @param exerciseId   identifier of the exercise
     * @param page         index of the page
     * @param itemsPerPage number of pairs in each page
     * @param latest if 'true' only the latest resolution of a student is returned.
     *               'false' every resolution can be returned, i.e., can have
     *               multiple resolutions of a student
     * @return list of pairs of a student and its latest exercise resolution for the requested exercise.
     */
    @Transactional
    @Override
    public List<Pair<StudentSQL, ExerciseResolution>> getExerciseResolutions(String exerciseId, Integer page, Integer itemsPerPage, boolean latest) {
        Page<ExerciseResolutionSQL> resolutionsSQL;

        // gets the page of resolutions to return
        if(latest)
            resolutionsSQL = exerciseResolutionSqlDAO.findLatestExerciseResolutionSQLSByExercise_Id(exerciseId, PageRequest.of(page, itemsPerPage));
        else
            resolutionsSQL = exerciseResolutionSqlDAO.findExerciseResolutionSQLSByExercise_Id(exerciseId,PageRequest.of(page, itemsPerPage));

        // gets the basic student info (StudentSQL) from the ExerciseResolutionSQL instances
        // and pairs it with the respective ExerciseResolution (NoSQL) instances
        List<Pair<StudentSQL, ExerciseResolution>> list = new ArrayList<>();
        for (ExerciseResolutionSQL resSQL : resolutionsSQL){
            ExerciseResolution res = exerciseResolutionDAO.findById(resSQL.getId()).orElse(null);
            assert res != null;
            list.add(Pair.of(resSQL.getStudent(), res));
        }
        return list;
    }

    /**
     * Create a resolution for a specific exercise.
     *
     * @param studentId      identifier of the creator of the resolution.
     * @param exerciseId     identifier of the exercise
     * @param resolutionData new resolution
     * @return updated version of the resolution
     * @throws NotFoundException if the exercise was not found
     * @throws BadInputException if there is some problem regarding the resolution of the exercise,
     *                           like the type of resolution does not match the type of the exercise
     */
    @Override
    @Transactional
    public ExerciseResolution createExerciseResolution(String studentId, String exerciseId, ExerciseResolutionData resolutionData) throws NotFoundException, BadInputException {
        // checks if exercise exists
        if (!exerciseSqlDAO.existsById(exerciseId))
            throw new NotFoundException("Could not create exercise resolution: exercise does not exist.");

        // checks if resolution is valid
        if(resolutionData == null)
            throw new BadInputException("Could not create exercise resolution: resolution data is null.");


        // prepares resolution
        ExerciseResolution resolution = new ExerciseResolution(null, // prevents overwrite attacks
                studentId,exerciseId,
                null, // new resolution so it should not have a points
                resolutionData,
                ExerciseResolutionStatus.NOT_REVISED, // new resolution so cannot be already revised
                null,
                null);

        // checks the resolution data against the exercise data
        checkResolutionData(resolution);

        // sets resolution number
        ExerciseResolutionSQL lastResolutionSQL = exerciseResolutionSqlDAO.getStudentLastResolution(studentId,exerciseId);
        int submissionNr = lastResolutionSQL != null ? lastResolutionSQL.getSubmissionNr() + 1 : 1;
        resolution.setSubmissionNr(submissionNr);

        // persists document
        resolution = exerciseResolutionDAO.save(resolution);

        // creates sql info and persists it
        StudentSQL studentSQL = entityManager.getReference(StudentSQL.class, studentId);
        ExerciseSQL exerciseSQL = entityManager.getReference(ExerciseSQL.class, exerciseId);
        ExerciseResolutionSQL resolutionSQL = new ExerciseResolutionSQL(resolution.getId(), studentSQL, exerciseSQL, submissionNr);
        exerciseResolutionSqlDAO.save(resolutionSQL);

        return resolution;
    }

    @Override
    @Transactional
    public ExerciseResolution createEmptyExerciseResolution(String studentId, String exerciseId, String testResId) throws NotFoundException, BadInputException {
        // checks if exercise exists
        if (!exerciseSqlDAO.existsById(exerciseId))
            throw new NotFoundException("Could not create exercise resolution: exercise does not exist.");


        // prepares resolution
        ExerciseResolution resolution = new ExerciseResolution(null, // prevents overwrite attacks
                studentId,exerciseId,
                null, // new resolution so it should not have a points
                null,
                ExerciseResolutionStatus.NOT_REVISED, // new resolution so cannot be already revised
                null,
                0);

        // persists document
        resolution = exerciseResolutionDAO.save(resolution);

        // creates sql info and persists it
        StudentSQL studentSQL = entityManager.getReference(StudentSQL.class, studentId);
        ExerciseSQL exerciseSQL = entityManager.getReference(ExerciseSQL.class, exerciseId);
        TestResolutionSQL testResolutionSQL = entityManager.getReference(TestResolutionSQL.class, testResId);
        ExerciseResolutionSQL resolutionSQL = new ExerciseResolutionSQL(resolution.getId(), testResolutionSQL, studentSQL, exerciseSQL, 0);
        exerciseResolutionSqlDAO.save(resolutionSQL);

        return resolution;
    }

    /**
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return the number of resolutions a student has made for a specific exercise.
     * @throws NotFoundException if the exercise does not exist
     */
    @Override
    public Integer countExerciseResolutionsByStudent(String exerciseId, String studentId) {
        return exerciseResolutionSqlDAO.countExerciseResolutionSQLSByExercise_IdAndStudent_Id(exerciseId, studentId);
    }

    /**
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return list of metadata of all the resolutions a student has made for an exercise.
     * @throws NotFoundException if the exercise does not exist
     */
    @Override
    public List<ExerciseResolutionSQL> getStudentListOfExerciseResolutionsMetadataByExercise(String exerciseId, String studentId) throws NotFoundException {
        return exerciseResolutionSqlDAO.findExerciseResolutionSQLSByExercise_IdAndStudent_Id(exerciseId, studentId);
    }

    /**
     * @param exerciseId identifier of the exercise
     * @param studentId  identifier of the student
     * @return last resolution made by the student for a given exercise, or 'null' if it does not exist.
     */
    @Override
    public ExerciseResolution getLastExerciseResolutionByStudent(String exerciseId, String studentId) {
        ExerciseResolutionSQL resolutionSQL = exerciseResolutionSqlDAO.getStudentLastResolution(studentId, exerciseId);
        try {
            return resolutionSQL != null ? getExerciseResolution(resolutionSQL.getId()) : null;
        }catch (NotFoundException nfe){
            throw new AssertionError(nfe.getMessage());
        }
    }

    /**
     * @param userId           identifier of the user that made the request. Necessary to check authorization.
     * @param page             index of the page
     * @param itemsPerPage     number of items per page
     * @param tags             list of tags to filter exercises
     * @param matchAllTags     if 'false' an exercise will match if at least one tag of the exercise matches one of the given list.
     *                         if 'true' the exercise must have all the tags present in the list
     * @param visibilityType   type of visibility
     * @param courseId         to search for an exercise from a specific course
     * @param institutionId    to search for and exercise from a specific institution
     * @param specialistId     to search for the exercises created by a specific specialist
     * @param title            to search for an exercise title
     * @param exerciseType     to search for an exercise of a certain type
     * @param verifyParams     if 'true' then verify if parameters exist in the database (example: verify if specialist exists),
     *                         'false' does not verify database logic
     * @return list of exercises that match the given filters
     */
    @Override
    public List<Exercise> getExercises(String userId, Integer page, Integer itemsPerPage, List<String> tags, boolean matchAllTags, String visibilityType, String courseId, String institutionId, String specialistId, String title, String exerciseType, boolean verifyParams) throws BadInputException, NotFoundException {
        Visibility visibility= null;
        if (visibilityType != null) {
            visibility = Visibility.fromValue(visibilityType);
            if (visibility == null)
                throw new BadInputException("Visibility type not found");
        }

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
        } //TODO verificação das tags
        Page<ExerciseSQL> exerciseSQLS = exerciseSqlDAO.getExercises(PageRequest.of(page, itemsPerPage),tags,matchAllTags,visibility,institutionId,courseId,specialistId,title,exerciseType);
        return exercisesSqlToNoSql(exerciseSQLS);
    }

    /**
     * Gets the exercise resolution identified by the given identifier.
     * @param resolutionId identifier of the resolution
     * @return the exercise resolution identified by the given identifier.
     * @throws NotFoundException if the resolution does not exist
     */
    @Override
    public ExerciseResolution getExerciseResolution(String resolutionId) throws NotFoundException {
        ExerciseResolution resolution = exerciseResolutionDAO.findById(resolutionId).orElse(null);
        if(resolution == null)
            throw new NotFoundException("Could not get exercise resolution: No resolution with the given id exists.");
        return resolution;
    }

    @Override
    public ExerciseResolution getExerciseResolution(String exerciseId, String testResId) throws NotFoundException {
        List<String> ids = exerciseResolutionSqlDAO.getResolutionIdFromExeAndTest(exerciseId, testResId);
        if (ids.size() > 0 && ids.get(0) != null){
            ExerciseResolution resolution = exerciseResolutionDAO.findById(ids.get(0)).orElse(null);
            return resolution;
        }
        else
            throw new NotFoundException("Could not get exercise resolution: No resolution was found.");
    }


    /**
     * Adds a comment to an exercise resolution.
     * If the resolution already has a
     * comment associated, it will be overwritten.
     * @param resolutionId identifier of the resolution
     * @param comment body of the comment
     * @throws NotFoundException if the resolution does not exist
     * @throws BadInputException if the comment is malformed or is null.
     */
    @Override
    @Transactional
    public void addCommentToExerciseResolution(String resolutionId, Comment comment) throws NotFoundException, BadInputException {
        // checks that the comment is valid
        if(comment == null)
            throw new BadInputException("Could not add comment to exercise resolution: Comment is null.");
        String commentError = comment.verifyComment();
        if (commentError != null)
            throw new BadInputException("Could not add comment to exercise resolution: " + commentError);

        // adds the comment to the resolution
        ExerciseResolution resolution = getExerciseResolution(resolutionId);
        resolution.setComment(comment);

        // updates the document
        exerciseResolutionDAO.save(resolution);
    }

    /**
     * Deletes a comment made to an exercise resolution.
     *
     * @param resolutionId identifier of the resolution
     * @throws NotFoundException if the resolution does not exist
     */
    @Override
    @Transactional
    public void removeCommentFromExerciseResolution(String resolutionId) throws NotFoundException {
        ExerciseResolution resolution = getExerciseResolution(resolutionId);
        resolution.setComment(null);
        exerciseResolutionDAO.save(resolution);
    }

    /**
     * Used to set the points of an exercise resolution.
     * @param resolutionId identifier of the resolution
     * @param points points to set
     * @throws NotFoundException if the resolution does not exist
     * @throws BadInputException if the points exceed the max points for the exercise.
     */
    @Override
    @Transactional
    public void setExerciseResolutionPoints(String resolutionId, float points) throws NotFoundException, BadInputException {
        // checks if the resolution exists
        ExerciseResolution resolution = exerciseResolutionDAO.findById(resolutionId).orElse(null);
        if (resolution == null)
            throw new NotFoundException("Could not set resolution points: Resolution does not exist.");

        // finds the max points for the exercise
        Exercise exercise = exerciseDAO.findById(resolution.getExerciseId()).orElse(null);
        assert exercise != null;
        float maxPoints = exercise.getPoints();

        // points cannot be higher than the defined points for the question
        if (points > maxPoints)
            throw new BadInputException("Could not set resolution points: Points exceed the max points of the exercise");

        // sets the points and persists the document
        resolution.setPoints(points);
        exerciseResolutionDAO.save(resolution);
    }

    /**
     * @param exerciseId identifier of the exercise
     * @return visibility of an exercise
     * @throws NotFoundException if the exercise does not exist
     */
    @Override
    public String getExerciseVisibility(String exerciseId) throws NotFoundException {
        Optional<ExerciseSQL> exerciseSQL = exerciseSqlDAO.findById(exerciseId);
        if(exerciseSQL.isPresent())
            return exerciseSQL.get().getVisibility().toString();
        else
            throw new NotFoundException("Exercise does not exist.");
    }


    /* **** Auxiliary methods **** */

    /**
     * Checks if a specialist is the owner of an exercise.
     * @param exerciseId identifier of the exercise
     * @param specialistId identifier of the specialist
     * @return 'true' if a specialist is the owner of the exercise
     */
    private boolean isExerciseOwner(String exerciseId, String specialistId){
        if(specialistId == null) return false;
        return specialistId.equals(exerciseSqlDAO.getExerciseSpecialistId(exerciseId));
    }

    private SpecialistSQL verifyOwnershipAuthorization(String specialistId, String exerciseId) throws NotFoundException, UnauthorizedException {
        if(!exerciseSqlDAO.existsById(exerciseId))
            throw new NotFoundException("Exercise not found");
        if(!specialistsService.existsSpecialistById(specialistId))
            throw new NotFoundException("Specialist not found");

        ExerciseSQL relExerciseToBeCopied = exerciseSqlDAO.getReferenceById(exerciseId);
        SpecialistSQL specialist = relExerciseToBeCopied.getSpecialist();
        if(specialist==null || !specialist.getId().equals(specialistId)) //TODO maybe mudar isto
            throw new UnauthorizedException("The specialist is not the owner of the exercise");
        return specialist;
    }

    /**
     * Delete all resolutions associated with an exercise
     * @param exerciseId identifier of the exercise
     */
    private void deleteExerciseResolutions(String exerciseId){
        // get all resolutions related to the given exercise
        List<ExerciseResolutionSQL> resolutionSQLS =
                exerciseResolutionSqlDAO.findExerciseResolutionSQLSByExercise_Id(exerciseId);
        // Delete resolutions entries in sql database
        exerciseResolutionSqlDAO.deleteExerciseResolutionSQLSByExercise_Id(exerciseId);
        // Delete resolutions documents in nosql database
        resolutionSQLS.stream().map(ExerciseResolutionSQL::getId).forEach(exerciseResolutionDAO::deleteById);
    }

    /**
     * Removes exercise (which will stop being a copy) from the copies table. Assumes the exercise exists.
     * If the original exercise gets to zero copies and has as visibility "deleted",
     * the original exercise is deleted.
     * @param exerciseId identifier of the exercise. Expects the identifier to not be null.
     */
    private void removeExerciseFromCopiesTable(String exerciseId) {
        assert exerciseId != null;

        //find entry in copies table
        ExerciseCopySQL exerciseCopySQL = exerciseCopySqlDAO.findById(exerciseId).orElse(null);
        assert exerciseCopySQL != null;

        // get the id of the original exercise, to update the number of copies
        String originalId = exerciseCopySQL.getOriginalId();

        // deletes entry from copies table
        exerciseCopySqlDAO.deleteById(exerciseId);

        // Gets original exercise and decrements number of copies by one
        ExerciseSQL originalExerciseSql = exerciseSqlDAO.findById(originalId).orElse(null);
        assert originalExerciseSql != null && originalExerciseSql.getNrCopies() > 0;
        originalExerciseSql.decreaseNrCopies();

        // Deletes original exercise if it does not have any copies and the visibility is set no deleted
        if (originalExerciseSql.getNrCopies() == 0 && originalExerciseSql.getVisibility() == VisibilitySQL.DELETED) {
            try {
                deleteExerciseById(originalId);
            } catch (NotFoundException nfe) {
                throw new AssertionError(nfe.getMessage());
            }
        } else {
            // update exercise
            exerciseSqlDAO.save(originalExerciseSql);
        }
    }

    /**
     * Adds a copy entry of an exercise, and updates
     * the original exercise number of copies.
     * Assumes that both exercises exist,
     * both the sql and non sql version, and that the original exercise is
     * a concrete exercise.
     * @param exerciseId identifier of the exercise that is a copy.
     * @param originalId identifier of the original exercise.
     */
    private void addExerciseToCopiesTable(String exerciseId, String originalId){
        // get sql references to the exercises
        ExerciseSQL exerciseSQL = exerciseSqlDAO.getReferenceById(exerciseId),
                    originalSQL = exerciseSqlDAO.findById(originalId).orElse(null);

        assert originalSQL != null;

        // creates copy entry
        ExerciseCopySQL exerciseCopySQL = new ExerciseCopySQL(exerciseSQL, exerciseId, originalSQL, originalId);
        exerciseCopySqlDAO.save(exerciseCopySQL);

        // increments originalSQL number of copies
        originalSQL.increaseNrCopies();
        exerciseSqlDAO.save(originalSQL);
    }

    /**
     * Copies exercise metadata from ex1 to ex2
     * @param from exercise that has the wanted metadata. Assumed not null.
     * @param to exercise that will have its metadata overwritten. Assumed not null.
     */
    private void copyExerciseMetadata(Exercise from, Exercise to){
        to.setId(from.getId());
        to.setSpecialistId(from.getSpecialistId());
        to.setInstitutionId(from.getInstitutionId());
        to.setPoints(from.getPoints());
        to.setCourseId(from.getCourseId());
    }

    /**
     * Convert a shallow exercise to a concrete exercise.
     * @param se the shallow exercise that should be converted to a concrete exercise.
     * @param updateRubric if 'true' the rubric should be updated with the given rubric.
     *                     if 'false' the rubric from the original exercise is duplicated.
     * @param rubric new rubric, or 'null' if the rubric should be deleted
     * @param updateSolution if 'true' the solution should be updated with the given solution.
     *                       if 'false' the solution from the original exercise is duplicated.
     * @param solution new solution, or 'null' if the solution should be deleted
     * @throws BadInputException if the new solution or rubric are not valid
     */
    private void convertShallowToConcreteAndUpdate(ShallowExercise se, boolean updateRubric, ExerciseRubric rubric, boolean updateSolution, ExerciseSolution solution) throws BadInputException {
        // Retrieves original exercise
        Exercise origExercise = exerciseDAO.findById(se.getOriginalExerciseId()).orElse(null);

        if(origExercise instanceof ConcreteExercise origCE) {
            // if the rubric should be updated, check if the rubric is valid and then persist it
            if(updateRubric) {
                if(rubric != null) {
                    origCE.verifyRubricProperties(rubric);
                    rubric.setId(null); //prevent overwrite attacks
                    rubric = exerciseRubricDAO.save(rubric);
                    origCE.setRubricId(rubric.getId());
                }else origCE.setRubricId(null);
            }
            // else, copy the original exercise rubric
            else {
                // Retrieves original exercise rubric and duplicates it
                String rubricId = origCE.getRubricId();
                if(rubricId != null) {
                    ExerciseRubric exerciseRubric = exerciseRubricDAO.findById(rubricId).orElse(null);
                    assert exerciseRubric != null;
                    exerciseRubric.setId(null);
                    exerciseRubric = exerciseRubricDAO.save(exerciseRubric);
                    origCE.setRubricId(exerciseRubric.getId());
                }
            }

            // if the solution should be updated, check if the rubric is valid and then persist it
            if(updateSolution) {
                if(solution != null) {
                    origCE.verifyResolutionProperties(solution.getData());
                    solution.setId(null); //prevent overwrite attacks
                    solution = exerciseSolutionDAO.save(solution);
                    origCE.setSolutionId(solution.getId());
                } else origCE.setSolutionId(null);
            }
            // else, copy the original exercise solution
            else {
                // Retrieves original exercise solution and duplicates it
                String solutionId = origCE.getSolutionId();
                if(solutionId != null) {
                    ExerciseSolution exerciseSolution = exerciseSolutionDAO.findById(solutionId).orElse(null);
                    assert exerciseSolution != null;
                    exerciseSolution.setId(null);
                    exerciseSolution = exerciseSolutionDAO.save(exerciseSolution);
                    origCE.setSolutionId(exerciseSolution.getId());
                }
            }

            // copies metadata to the original exercise object, since this object is an
            // instance of the object we want the exercise to become
            copyExerciseMetadata(se, origCE);

            // updates the exercise document
            exerciseDAO.save(origCE);

            // Removes exercise from the original exercise copies
            removeExerciseFromCopiesTable(origCE.getId());
        } else throw new AssertionError("Original exercise is not concrete");
    }

    /**
     * If an exercise is shallow, creates a concrete instance of the exercise. Only an instance is created, the databases are not updated.
     * @param exerciseId Identifier of the exercise
     * @return a concrete exercise
     * @throws NotFoundException if the original exercise does not exist
     */
    private ConcreteExercise getExerciseConcreteInstance(String exerciseId) throws NotFoundException {
        Exercise exercise = _getExerciseById(exerciseId);
        ConcreteExercise concreteExercise;

        // if the exercise is shallow, converts it to a concrete instance
        if(exercise instanceof ShallowExercise se) {
            // gets original concrete exercise
            Exercise original = _getExerciseById(se.getOriginalExerciseId());
            assert original instanceof ConcreteExercise;
            concreteExercise = (ConcreteExercise) original;

            // copies the shallow exercise metadata to the concrete exercise
            copyExerciseMetadata(se, concreteExercise);
        }
        else concreteExercise = (ConcreteExercise) exercise;

        return concreteExercise;
    }

    /**
     * Checks the resolution data against the exercise data.
     * @param exerciseResolution resolution
     * @throws NotFoundException if the exercise does not exist
     * @throws BadInputException if the resolution data is malformed
     */
    private void checkResolutionData(ExerciseResolution exerciseResolution) throws NotFoundException, BadInputException {
        assert exerciseResolution != null;

        // gets concrete exercise
        ConcreteExercise exercise = getExerciseConcreteInstance(exerciseResolution.getExerciseId());

        // checks the resolution data against the exercise data.
        exercise.verifyResolutionProperties(exerciseResolution.getData());
    }

    /**
     * Converts a page of ExerciseSQL to a list of Exercise(NoSQL)s
     * @param exerciseSQLPage page of exercises
     * @return a list of Exercise(NoSQL)s
     * @throws NotFoundException if one of the exercises, on the page, was not found
     */
    private List<Exercise> exercisesSqlToNoSql(Page<ExerciseSQL> exerciseSQLPage) throws NotFoundException {
        List<Exercise> exerciseList = new ArrayList<>();
        for(var exercise : exerciseSQLPage)
            exerciseList.add(this.getExerciseById(exercise.getId()));
        return exerciseList;
    }

    // TODO - meter a rubrica e a solucao como transient no concrete exercise?
    /**
     * Automatically corrects the not revised resolutions of an exercise.
     * @param exercise concrete exercise
     * @param rubric rubric of the exercise
     * @param solution solution of the exercise
     * @throws NotFoundException if the exercise, or its rubric, or its solution were not found
     * @throws UnauthorizedException if the resolutions cannot be corrected automatically
     */
    private void automaticExerciseResolutionsCorrection(ConcreteExercise exercise, ExerciseRubric rubric, ExerciseSolution solution) throws NotFoundException, UnauthorizedException {
        String exerciseId = exercise.getId();

        // Get number of resolutions not revised
        long resolutionsCount = exerciseResolutionDAO.countByExerciseIdAndStatus(exerciseId, ExerciseResolutionStatus.NOT_REVISED);

        // Iterates over all resolutions that are not revised, and corrects them
        // Corrects a portion at a time, to avoid a great memory consumption
        for(int pageIndex = 0, i = 0; i < resolutionsCount; pageIndex++, i += 5){
            // gets page
            Page<ExerciseResolution> page =
                    exerciseResolutionDAO.findByExerciseIdAndStatus(exerciseId,
                                                                    ExerciseResolutionStatus.NOT_REVISED,
                                                                    PageRequest.of(pageIndex, 5));

            // corrects each exercise of the page
            for (ExerciseResolution res : page)
                automaticExerciseResolutionCorrection(res, exercise, rubric, solution);
        }
    }

    /**
     * Automatically corrects a specific resolution of an exercise.
     * @param resolution resolution to be corrected
     * @param exercise concrete exercise
     * @param rubric rubric of the exercise
     * @param solution solution of the exercise
     * @return points attributed to the resolution
     * @throws UnauthorizedException if the resolution cannot be corrected automatically
     */
    private float automaticExerciseResolutionCorrection(ExerciseResolution resolution, ConcreteExercise exercise, ExerciseRubric rubric, ExerciseSolution solution) throws UnauthorizedException {
        assert resolution != null && exercise != null && rubric != null && solution != null;
        resolution = exercise.automaticEvaluation(resolution, solution, rubric);
        exerciseResolutionDAO.save(resolution);
        return resolution.getPoints();
    }
}
