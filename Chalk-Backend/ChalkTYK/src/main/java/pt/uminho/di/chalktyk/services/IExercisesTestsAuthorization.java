package pt.uminho.di.chalktyk.services;

import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

public interface IExercisesTestsAuthorization {
    boolean canStudentGetExercise(String studentId, Visibility vis, String courseId, String institutionId);

    boolean canStudentListTest(String studentId, Visibility vis, String courseId, String institutionId);

    boolean canStudentGetExercise(String studentId, String exerciseId) throws NotFoundException;

    boolean canStudentListExercise(String studentId, Visibility vis, String courseId, String institutionId);

    boolean canStudentGetTest(String studentId, Visibility vis, String courseId, String institutionId);

    boolean canStudentGetTest(String userId, String testId) throws NotFoundException;
    boolean canStudentAccessExerciseResolution(String studentId, String resolutionId) throws NotFoundException;
    boolean canStudentAccessTestResolution(String studentId, String resolutionId) throws NotFoundException;

    boolean canSpecialistGetExercise(String specialistId, String ownerId, Visibility vis, String courseId, String institutionId);
    boolean canSpecialistGetExercise(String specialistId, String exerciseId) throws NotFoundException;

    boolean canSpecialistListExercise(String specialistId, String ownerId, Visibility vis, String courseId, String institutionId);

    boolean canSpecialistGetTest(String specialistId, String ownerId, Visibility vis, String courseId, String institutionId);

    boolean canSpecialistListTest(String specialistId, String ownerId, Visibility vis, String courseId, String institutionId);

    boolean canSpecialistAccessExercise(String specialistId, String ownerId, Visibility vis, String courseId, String institutionId);
    boolean canSpecialistAccessExercise(String specialistId, String exerciseId) throws NotFoundException;
    boolean canSpecialistAccessTest(String userId, String testId) throws NotFoundException;

    boolean canSpecialistAccessExerciseResolution(String specialistId, String resolutionId) throws NotFoundException;

    boolean canSpecialistAccessTestResolution(String userId, String resolutionId) throws NotFoundException;

    boolean specialistBelongsToCourse(String userId, String courseId) throws NotFoundException;
}
