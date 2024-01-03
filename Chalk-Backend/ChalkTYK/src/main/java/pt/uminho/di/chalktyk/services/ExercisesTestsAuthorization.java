package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

@Service
public class ExercisesTestsAuthorization implements IExercisesTestsAuthorization{
    private final IExercisesService exercisesService;
    private final IInstitutionsService institutionsService;
    private final ICoursesService coursesService;

    @Autowired
    public ExercisesTestsAuthorization(IExercisesService exercisesService, IInstitutionsService institutionsService, ICoursesService coursesService) {
        this.exercisesService = exercisesService;
        this.institutionsService = institutionsService;
        this.coursesService = coursesService;
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
    public boolean canSpecialistAccessExerciseResolution(String specialistId, String resolutionId) throws NotFoundException {
        ExerciseResolution resolution = exercisesService.getExerciseResolution(resolutionId);
        return canSpecialistAccessExercise(specialistId, resolution.getExerciseId());
    }
}
