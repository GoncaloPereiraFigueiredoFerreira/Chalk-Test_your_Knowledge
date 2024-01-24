package pt.uminho.di.chalktyk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.uminho.di.chalktyk.models.exercises.Exercise;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.Test;
import pt.uminho.di.chalktyk.models.tests.TestResolution;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

import java.util.Objects;

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
        if(vis == null)
            return false;

        System.out.println("studentId: " + studentId);
        System.out.println("vis: " + vis);
        System.out.println("courseId: " + courseId);
        System.out.println("institutionId: " + institutionId);

        // if an institution is provided, the student can only
        // check the exercises if the student belongs
        // to the institution and if the visibility is set to INSTITUTION
        try {
            if (institutionId != null)
                return institutionsService.isStudentOfInstitution(studentId, institutionId)
                        && Visibility.INSTITUTION.equals(vis);
        }catch (NotFoundException e) {return false;}

        // if a course is provided, the student can only
        // check the exercises if the student belongs
        // to the course and if the visibility is set to COURSE
        try {
            if (courseId != null)
                return coursesService.checkStudentInCourse(courseId, studentId)
                        && Visibility.COURSE.equals(vis);
        }catch (NotFoundException e) {return false;}

        return switch (vis) {
            case PUBLIC, NOT_LISTED -> true;
            default -> false;
        };
    }

    @Override
    public boolean canStudentListExercise(String studentId, Visibility vis, String courseId, String institutionId) {
        if(Visibility.NOT_LISTED.equals(vis))
            return false;
        return canStudentGetExercise(studentId, vis, courseId, institutionId);
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
    public boolean canStudentListTest(String studentId, Visibility vis, String courseId, String institutionId) {
        return canStudentListExercise(studentId,vis,courseId,institutionId);
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

        if(vis == null)
            return false;

        // if an institution is provided, the specialist can only
        // check the exercises if the specialist belongs
        // to the institution and if the visibility is set to INSTITUTION
        try {
            if (institutionId != null)
                return institutionsService.isSpecialistOfInstitution(specialistId, institutionId)
                        && Visibility.INSTITUTION.equals(vis);
        }catch (NotFoundException e) {return false;}

        // if a course is provided, the specialist can only
        // check the exercises if the specialist belongs
        // to the course and if the visibility is set to COURSE
        try {
            if (courseId != null)
                return coursesService.checkSpecialistInCourse(courseId, specialistId)
                        && Visibility.COURSE.equals(vis);
        }catch (NotFoundException e) {return false;}

        return switch (vis) {
            case PUBLIC, NOT_LISTED -> true;
            default -> false;
        };
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
    public boolean canSpecialistListExercise(String specialistId, String ownerId, Visibility vis, String courseId, String institutionId) {
        if(Visibility.NOT_LISTED.equals(vis) && !Objects.equals(specialistId, ownerId))
            return false;
        return canSpecialistGetExercise(specialistId, ownerId, vis, courseId, institutionId);
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
    public boolean canSpecialistListTest(String specialistId, String ownerId, Visibility vis, String courseId, String institutionId) {
        return canSpecialistListExercise(specialistId, ownerId, vis, courseId, institutionId);
    }

    @Override
    public boolean canSpecialistAccessExercise(String specialistId, String ownerId, Visibility vis, String courseId, String institutionId) {
        // specialist is the owner of the exercise
        if(specialistId.equals(ownerId))
            return true;

        if(vis == null)
            return false;

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

    @Override
    public boolean specialistBelongsToCourse(String userId, String courseId) throws NotFoundException {
        return coursesService.checkSpecialistInCourse(courseId,userId);
    }
}
