package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.Test;
import pt.uminho.di.chalktyk.models.tests.TestResolution;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service
public class ExercisesTestsAuthorization implements IExercisesTestsAuthorization{
    private final IExercisesService exercisesService;
    private final IInstitutionsService institutionsService;
    private final ICoursesService coursesService;
    private final ITestsService testsService;

    @Autowired
    public ExercisesTestsAuthorization(IExercisesService exercisesService, IInstitutionsService institutionsService, ICoursesService coursesService, ITestsService testsService) {
        this.exercisesService = exercisesService;
        this.institutionsService = institutionsService;
        this.coursesService = coursesService;
        this.testsService = testsService;
    }

    public boolean canStudentGetExercise(String studentId, Visibility vis, String courseId, String institutionId) {
        switch (vis){
            case PRIVATE, TEST, DELETED:
                return false;
            case INSTITUTION:
                try { return institutionsService.isStudentOfInstitution(studentId, institutionId); }
                catch (NotFoundException e) { return false; }
            case COURSE:
                try { return coursesService.checkStudentInCourse(courseId, studentId); }
                catch (NotFoundException e) { return false; }
            default:
                return true;
        }
    }

    @Override
    public boolean canStudentGetTest(String studentId, Visibility vis, String courseId, String institutionId) {
        return canStudentGetExercise(studentId,vis,courseId,institutionId);
    }

    @Override
    public boolean canStudentGetTest(String userId, String testId) throws NotFoundException {
        Test test = testsService.getTestById(testId);
        return canStudentGetTest(userId,test.getVisibility(),test.getCourseId(),test.getInstitutionId());
    }

    @Override
    public boolean canStudentGetExercise(String studentId, String exerciseId) throws NotFoundException {
        Exercise exercise = exercisesService.getExerciseById(exerciseId);
        return canStudentGetExercise(
                studentId,
                exercise.getVisibility(),
                exercise.getCourseId(),
                exercise.getInstitutionId());
    }

    @Override
    public boolean canStudentAccessExerciseResolution(String studentId, String resolutionId) throws NotFoundException {
        ExerciseResolution resolution = exercisesService.getExerciseResolution(resolutionId);
        return studentId.equals(resolution.getStudentId());
    }

    @Override
    public boolean canStudentAccessTestResolution(String studentId, String resolutionId) throws NotFoundException {
        TestResolution resolution = testsService.getTestResolutionById(resolutionId);
        return studentId.equals(resolution.getStudentId());
    }


    public boolean canSpecialistGetExercise(String specialistId, String ownerId, Visibility vis, String courseId, String institutionId) {
        // specialist is the owner of the exercise
        if(specialistId.equals(ownerId))
            return true;

        switch (vis){
            case TEST, PRIVATE, DELETED:
                return false;
            case INSTITUTION:
                try { return institutionsService.isSpecialistOfInstitution(specialistId, institutionId); }
                catch (NotFoundException e) { return false; }
            case COURSE:
                try { return coursesService.checkSpecialistInCourse(courseId, specialistId); }
                catch (NotFoundException e) { return false; }
            default:
                return true;
        }
    }

    @Override
    public boolean canSpecialistGetExercise(String specialistId, String exerciseId) throws NotFoundException {
        Exercise exercise = exercisesService.getExerciseById(exerciseId);
        return canSpecialistGetExercise(
                specialistId,
                exercise.getSpecialistId(),
                exercise.getVisibility(),
                exercise.getCourseId(),
                exercise.getInstitutionId());
    }

    @Override
    public boolean canSpecialistGetTest(String specialistId, String ownerId, Visibility vis, String courseId, String institutionId) {
        return canSpecialistGetExercise(
                specialistId,
                ownerId,
                vis,
                courseId,
                institutionId);
    }

    @Override
    public boolean canSpecialistAccessExercise(String specialistId, String ownerId, Visibility vis, String courseId, String institutionId) {
        // specialist is the owner of the exercise
        if(specialistId.equals(ownerId))
            return true;

        // Aside from the exercise owner, only specialists from the same course/institution can update the exercise, if the visibility is set to COURSE or INSTITUTION respectively.
        switch (vis){
            case INSTITUTION:
                try { return institutionsService.isSpecialistOfInstitution(specialistId, institutionId); }
                catch (NotFoundException e) { return false; }
            case COURSE:
                try { return coursesService.checkSpecialistInCourse(courseId, specialistId); }
                catch (NotFoundException e) { return false; }
            default:
                return false;
        }
    }

    @Override
    public boolean canSpecialistAccessExercise(String specialistId, String exerciseId) throws NotFoundException {
        Exercise exercise = exercisesService.getExerciseById(exerciseId);
        return canSpecialistAccessExercise(
                specialistId,
                exercise.getSpecialistId(),
                exercise.getVisibility(),
                exercise.getCourseId(),
                exercise.getInstitutionId());
    }

    @Override
    public boolean canSpecialistAccessTest(String userId, String testId) throws NotFoundException {
        Test test = testsService.getTestById(testId);
        return canSpecialistAccessExercise(
                userId,
                test.getSpecialistId(),
                test.getVisibility(),
                test.getCourseId(),
                test.getInstitutionId());
    }

    @Override
    public boolean canSpecialistAccessExerciseResolution(String specialistId, String resolutionId) throws NotFoundException {
        ExerciseResolution resolution = exercisesService.getExerciseResolution(resolutionId);
        return canSpecialistAccessExercise(specialistId, resolution.getExerciseId());
    }

    @Override
    public boolean canSpecialistAccessTestResolution(String userId, String resolutionId) throws NotFoundException {
        TestResolution resolution = testsService.getTestResolutionById(resolutionId);
        return canSpecialistAccessTest(userId, resolution.getTestId());
    }
}
