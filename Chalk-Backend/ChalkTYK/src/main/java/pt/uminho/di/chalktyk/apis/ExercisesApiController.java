package pt.uminho.di.chalktyk.apis;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Comment;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.ExerciseIdRubricBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.ExercisesBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.ExercisesExerciseIdBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InlineResponse200;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Rubric;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.servlet.http.HttpServletRequest;
import pt.uminho.di.chalktyk.dtos.CreateExerciseDTO;
import pt.uminho.di.chalktyk.dtos.UpdateExerciseDTO;
import pt.uminho.di.chalktyk.models.exercises.*;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.users.Student;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/exercises")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class ExercisesApiController implements ExercisesApi {


    @Override
    public ResponseEntity<Exercise> getExerciseById(String jwtToken, String exerciseId) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> exerciseExists(String jwtToken, String exerciseId) {
        return null;
    }

    @Override
    public ResponseEntity<String> createExercise(String jwtToken, CreateExerciseDTO createExerciseDTO) {
        return null;
    }

    @Override
    public void deleteExerciseById(String jwtToken, String exerciseId) {

    }

    @Override
    public ResponseEntity<String> duplicateExerciseById(String jwtToken, String exerciseId, String courseId, String visibility) {
        return null;
    }

    @Override
    public void updateAllOnExercise(String jwtToken, String exerciseId, UpdateExerciseDTO updateExerciseDTO) {

    }

    @Override
    public void updateExerciseBody(String jwtToken, String exerciseId, Exercise newBody) {

    }

    @Override
    public void updateExerciseVisibility(String jwtToken, String exerciseId, Visibility visibility) {

    }

    @Override
    public void updateExerciseRubric(String jwtToken, String exerciseId, ExerciseRubric rubric) {

    }

    @Override
    public void updateExerciseSolution(String jwtToken, String exerciseId, ExerciseResolutionData solution) {

    }

    @Override
    public void updateExerciseCourse(String jwtToken, String exerciseId, String courseId) {

    }

    @Override
    public void updateExerciseTags(String jwtToken, String exerciseId, List<String> tagsIds) {

    }

    @Override
    public ResponseEntity<ExerciseRubric> getExerciseRubric(String jwtToken, String exerciseId) {
        return null;
    }

    @Override
    public void createExerciseRubric(String jwtToken, String exerciseId, ExerciseRubric rubric) {

    }

    @Override
    public void deleteExerciseRubric(String jwtToken, String exerciseId) {

    }

    @Override
    public void issueExerciseResolutionsCorrection(String jwtToken, String exerciseId, String correctionType) {

    }

    @Override
    public void issueExerciseResolutionCorrection(String jwtToken, String resolutionId, String correctionType) {

    }

    @Override
    public ResponseEntity<Integer> countExerciseResolutions(String jwtToken, String exerciseId, Boolean total) {
        return null;
    }

    @Override
    public ResponseEntity<List<Pair<Student, ExerciseResolution>>> getExerciseResolutions(String jwtToken, String exerciseId, Integer itemsPerPage, Integer Page, boolean latest) {
        return null;
    }

    @Override
    public void createExerciseResolution(String jwtToken, String exerciseId, ExerciseResolutionData resolutionData) {

    }

    @Override
    public ResponseEntity<Integer> countExerciseResolutionsByStudent(String jwtToken, String exerciseId, String studentId) {
        return null;
    }

    @Override
    public ResponseEntity<List<ExerciseResolution>> getStudentListOfExerciseResolutions(String jwtToken, String exerciseId, String studentId) {
        return null;
    }

    @Override
    public ResponseEntity<ExerciseResolution> getLastExerciseResolutionByStudent(String jwtToken, String exerciseId, String studentId) {
        return null;
    }

    @Override
    public ResponseEntity<Page<Exercise>> getExercises(String jwtToken, Integer page, Integer itemsPerPage, List<String> tags, Boolean matchAllTags, Visibility visibility, String courseId, String institutionId, String specialistId, String title, String exerciseType) {
        return null;
    }

    @Override
    public void addCommentToExerciseResolution(String jwtToken, String resolutionId, String comment) {

    }

    @Override
    public void removeCommentFromExerciseResolution(String jwtToken, String resolutionId) {

    }

    @Override
    public ResponseEntity<ExerciseResolution> getExerciseResolution(String jwtToken, String resolutionId) {
        return null;
    }

    @Override
    public void deleteExerciseResolutionById(String jwtToken, String resolutionId) {

    }

    @Override
    public void manuallyCorrectExerciseResolution(String resolutionId, float points) throws NotFoundException, BadInputException {

    }

    @Override
    public ResponseEntity<Void> manuallyCorrectExerciseResolution(String jwtToken, String resolutionId, float points) {
        return null;
    }

    @Override
    public ResponseEntity<ExerciseSolution> getExerciseSolution(String jwtToken, String exerciseId) {
        return null;
    }

    @Override
    public void createExerciseSolution(String jwtToken, String exerciseId, ExerciseResolutionData solution) {

    }

    @Override
    public void deleteExerciseSolution(String jwtToken, String exerciseId) {

    }

    @Override
    public ResponseEntity<String> getExerciseCourseId(String jwtToken, String exerciseId) {
        return null;
    }

    @Override
    public ResponseEntity<String> getExerciseInstitutionId(String jwtToken, String exerciseId) {
        return null;
    }

    @Override
    public ResponseEntity<String> getExerciseVisibility(String jwtToken, String exerciseId) {
        return null;
    }

    @Override
    public ResponseEntity<Set<Tag>> getExerciseTags(String jwtToken, String exerciseId) {
        return null;
    }
}
