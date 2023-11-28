package pt.uminho.di.chalktyk.services;

import org.apache.commons.lang3.tuple.Pair;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.*;
import pt.uminho.di.chalktyk.models.relational.Student;
import pt.uminho.di.chalktyk.models.relational.Specialist;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseResolution;

import java.io.IOException;
import java.util.List;

public interface IExercisesService{

    String createExercise(ExercisesBody body);

    void deleteExerciseById(String exerciseId);

    /**
     * Duplicates the exercise that contains the given identifier.
     * The id of the specialist, and if existent, the institution identifier
     * is added to the new exercise metadata. The visibility of the new exercise is
     * set to private, and is not associated with any course.
     * @param exerciseId exercise identifier
     * @return new exercise identifier
     */
    String duplicateExerciseById(String specialistId, String exerciseId);

    void updateExercise(String specialistId, String exerciseId, ExercisesExerciseIdBody body);

    Rubric getExerciseRubric(String exerciseId);

    void createExerciseRubric(String exerciseId, ExerciseIdRubricBody body);


    void issueExerciseResolutionsCorrection(String specialistId, String exerciseId, String correctionType);

    Integer countExerciseResolutions(String exerciseId, boolean total);

    List<Pair<Student, ExerciseResolution>> getExerciseResolutions(String exerciseId, Integer page, Integer itemsPerPage);

    void createExerciseResolution(Integer exerciseId, ExerciseResolution body);

    Integer countExerciseResolutionsByStudent(String exerciseId, String studentId);

    List<String> getStudentListOfExerciseResolutionsIdsByExercise(String exerciseId, String studentId);

    ExerciseResolution getLastExerciseResolutionByStudent(String exerciseId, String studentId);

    List<Exercise> getExercises(Integer page, Integer itemsPerPage,
                                List<String> tags, boolean matchAllTags,
                                String visibilityType, String visibilityTarget,
                                String specialistId);

    Void addCommentToExerciseResolution(String resolutionId, Comment body);

    ExerciseResolution getExerciseResolution(String resolutionId);

    Void exerciseResolutionManualCorrection(String resolutionId, Float cotation);

    Void deleteExerciseRubric(String rubricId);

    Void updateRubric(String rubricId, Rubric body);

}
