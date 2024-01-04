package pt.uminho.di.chalktyk.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.dtos.CreateExerciseDTO;
import pt.uminho.di.chalktyk.dtos.ListPairStudentExerciseResolution;
import pt.uminho.di.chalktyk.dtos.ManualExerciseCorrectionDTO;
import pt.uminho.di.chalktyk.dtos.UpdateExerciseDTO;
import pt.uminho.di.chalktyk.models.exercises.*;
import pt.uminho.di.chalktyk.models.exercises.items.StringItem;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.services.*;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/exercises")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class ExercisesApiController implements ExercisesApi {

    private final ISecurityService securityService;
    private final IExercisesService exercisesService;
    private final IExercisesTestsAuthorization exercisesTestsAuthorization;
    @Autowired
    public ExercisesApiController(ISecurityService securityService, IExercisesService exercisesService, IExercisesTestsAuthorization exercisesTestsAuthorization) {
        this.securityService = securityService;
        this.exercisesService = exercisesService;
        this.exercisesTestsAuthorization = exercisesTestsAuthorization;
    }

    /**
     *
     * @param userId
     * @param userRole
     * @param exerciseId
     * @return 'null' if there is no permission to get the exercise
     * @throws NotFoundException
     */
    private Exercise canGetExercise(String userId, String userRole, String exerciseId) throws NotFoundException {
        Exercise exercise = exercisesService.getExerciseById(exerciseId);
        Visibility vis = exercise.getVisibility();
        String courseId = exercisesService.getExerciseCourse(exerciseId);
        String institutionId = exercisesService.getExerciseInstitution(exerciseId);

        if(userRole.equals("STUDENT")) {
            if (exercisesTestsAuthorization.canStudentGetExercise(userId, vis, courseId, institutionId))
                return exercise;
        }else if (userRole.equals("SPECIALIST")) {
            if (exercisesTestsAuthorization.canSpecialistGetExercise(userId, exercise.getSpecialistId(), vis, courseId, institutionId))
                return exercise;
        }
        return null;
    }

    /*
    @Override
    public ResponseEntity<Exercise> getExerciseById(String jwtToken, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                     role = jwt.getUserRole();

            Exercise exercise = exercisesService.getExerciseById(exerciseId);
            Visibility vis = exercise.getVisibility();
            String courseId = exercisesService.getExerciseCourse(exerciseId);
            String institutionId = exercisesService.getExerciseInstitution(exerciseId);
            boolean perm = false;

            if(role.equals("STUDENT"))
                perm = exercisesTestsAuthorization.canStudentGetExercise(userId, vis, courseId, institutionId);
            else if (role.equals("SPECIALIST")) {
                perm = exercisesTestsAuthorization.canSpecialistGetExercise(userId, exercise.getSpecialistId(), vis, courseId, institutionId);
            }

            if(perm)
                return ResponseEntity.ok(exercise);
            else
                return new ExceptionResponseEntity<Exercise>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to access the exercise.");
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<Exercise>().createRequest(e);
        }
    }
     */

    @Override
    public ResponseEntity<Exercise> getExerciseById(String jwtToken, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                     role = jwt.getUserRole();

            Exercise exercise = canGetExercise(userId, role, exerciseId);

            if(exercise != null)
                return ResponseEntity.ok(exercise);
            else
                return new ExceptionResponseEntity<Exercise>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to access the exercise.");
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<Exercise>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<String> createExercise(String jwtToken, CreateExerciseDTO createExerciseDTO) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                // ensures that the exercise has the correct specialist id
                Exercise exercise = createExerciseDTO.getExercise();
                if(exercise != null)
                    exercise.setSpecialist(new Specialist(userId));

                String exId = exercisesService.createExercise(
                        exercise,
                        new ExerciseSolution(null, createExerciseDTO.getSolution()),
                        createExerciseDTO.getRubric(),
                        createExerciseDTO.getTagsIds());
                return ResponseEntity.ok(exId);
            }
            else return new ExceptionResponseEntity<String>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to create exercises.");

        } catch (UnauthorizedException | BadInputException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteExerciseById(String jwtToken, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.deleteExerciseById(exerciseId);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to delete the exercise.");
        } catch (UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<String> duplicateExerciseById(String jwtToken, String exerciseId, String courseId, String visibility) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            Exercise exercise = exercisesService.getExerciseById(exerciseId);
            Visibility exVisibility = exercise.getVisibility();
            String exCourseId = exercisesService.getExerciseCourse(exerciseId);
            String exInstitutionId = exercisesService.getExerciseInstitution(exerciseId);

            boolean perm = role.equals("SPECIALIST")
                            && exercisesTestsAuthorization.canSpecialistGetExercise(userId, exercise.getSpecialistId(), exVisibility, exCourseId, exInstitutionId);

            if(perm) {
                Visibility vis = visibility != null ? Visibility.fromValue(visibility) : null;
                return ResponseEntity.ok(exercisesService.duplicateExerciseById(userId, exerciseId, courseId, vis));
            }
            else
                return new ExceptionResponseEntity<String>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to duplicate the exercise.");
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateAllOnExercise(String jwtToken, String exerciseId, UpdateExerciseDTO updateExerciseDTO) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.updateAllOnExercise(
                            exerciseId,
                            updateExerciseDTO.getExercise(),
                            updateExerciseDTO.getRubric(),
                            new ExerciseSolution(null, updateExerciseDTO.getSolution()),
                            updateExerciseDTO.getTagsIds(),
                            updateExerciseDTO.getVisibility());
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the exercise.");
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateExerciseBody(String jwtToken, String exerciseId, Exercise newBody) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.updateExerciseBody(exerciseId, newBody);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the exercise's body.");
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateExerciseVisibility(String jwtToken, String exerciseId, Visibility visibility) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.updateExerciseVisibility(exerciseId, visibility);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the exercise's visibility.");
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateExerciseRubric(String jwtToken, String exerciseId, ExerciseRubric rubric) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.updateExerciseRubric(exerciseId, rubric);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the exercise's rubric.");
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateExerciseSolution(String jwtToken, String exerciseId, ExerciseResolutionData solution) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.updateExerciseSolution(exerciseId, new ExerciseSolution(null, solution));
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the exercise's solution.");
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateExerciseCourse(String jwtToken, String exerciseId, String courseId) {
        try {
            if (courseId != null && courseId.startsWith("null"))
                courseId = null;

            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            System.out.println("isCourse null? " + (courseId == null) + "| courseId: " + courseId);

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.updateExerciseCourse(exerciseId, courseId);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the exercise's course.");
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateExerciseTags(String jwtToken, String exerciseId, List<String> tagsIds) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.updateExerciseTags(exerciseId, tagsIds);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the exercise's tags.");
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<ExerciseRubric> getExerciseRubric(String jwtToken, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            Exercise exercise = canGetExercise(userId, role, exerciseId);

            if(exercise != null)
                return ResponseEntity.ok(exercisesService.getExerciseRubric(exerciseId));
            else
                return new ExceptionResponseEntity<ExerciseRubric>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to access the exercise's rubric.");
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<ExerciseRubric>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> createExerciseRubric(String jwtToken, String exerciseId, ExerciseRubric rubric) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.createExerciseRubric(exerciseId, rubric);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to create the exercise's rubric.");
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteExerciseRubric(String jwtToken, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.deleteExerciseRubric(exerciseId);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to delete the exercise's rubric.");
        } catch (UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> issueExerciseResolutionsCorrection(String jwtToken, String exerciseId, String correctionType) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.issueExerciseResolutionsCorrection(exerciseId, correctionType);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to delete the exercise's rubric.");
        } catch (UnauthorizedException | BadInputException | NotFoundException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> issueExerciseResolutionCorrection(String jwtToken, String resolutionId, String correctionType) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            // checks if the user has permission
            boolean perm = false;
            if(role.equals("STUDENT"))
                perm = exercisesTestsAuthorization.canStudentAccessExerciseResolution(userId, resolutionId);
            else if(role.equals("SPECIALIST"))
                perm = exercisesTestsAuthorization.canSpecialistAccessExerciseResolution(userId, resolutionId);

            // if he has permission, execute the request
            if(perm) {
                exercisesService.issueExerciseResolutionCorrection(resolutionId, correctionType);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to issue the correction of the exercise resolution.");
        } catch (UnauthorizedException | BadInputException | NotFoundException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Integer> countExerciseResolutions(String jwtToken, String exerciseId, Boolean total) {
        try {
            total = total != null && total; // default is false, if total is 'null'

            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            Exercise exercise = canGetExercise(userId, role, exerciseId);

            if(exercise != null)
                return ResponseEntity.ok(exercisesService.countExerciseResolutions(exerciseId, total));
            else
                return new ExceptionResponseEntity<Integer>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to count the exercise's resolutions.");
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<Integer>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<ListPairStudentExerciseResolution> getExerciseResolutions(String jwtToken, String exerciseId, int page, int itemsPerPage, Boolean latest) {
        try {
            latest = latest == null || latest; // default value is 'true'

            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    return ResponseEntity.ok(
                            new ListPairStudentExerciseResolution(
                                    exercisesService.getExerciseResolutions(exerciseId, page, itemsPerPage, latest)));
                }
            }

            return new ExceptionResponseEntity<ListPairStudentExerciseResolution>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to list exercise's resolutions.");
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<ListPairStudentExerciseResolution>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> createExerciseResolution(String jwtToken, String exerciseId, ExerciseResolutionData resolutionData) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            Exercise exercise = canGetExercise(userId, role, exerciseId);

            if(exercise != null) {
                exercisesService.createExerciseResolution(userId, exerciseId, resolutionData);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
                return new ExceptionResponseEntity<Void>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to create a resolution for the exercise.");
        } catch (NotFoundException | UnauthorizedException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Integer> countExerciseResolutionsByStudent(String jwtToken, String exerciseId, String studentId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            // checks if the user has permission
            boolean perm = userId.equals(studentId); // user is the student that is the aim of the query
            if(role.equals("SPECIALIST"))
                perm = exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId);

            // if he has permission, execute the request
            if(perm) 
                return ResponseEntity.ok(exercisesService.countExerciseResolutionsByStudent(exerciseId, studentId));

            return new ExceptionResponseEntity<Integer>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to count the number of resolutions made by a student for the given exercise.");
        } catch (UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<Integer>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<List<ExerciseResolution>> getStudentListOfExerciseResolutions(String jwtToken, String exerciseId, String studentId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            // checks if the user has permission
            boolean perm = userId.equals(studentId); // user is the student that is the aim of the query
            if(role.equals("SPECIALIST"))
                perm = exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId);

            // if he has permission, execute the request
            if(perm)
                return ResponseEntity.ok(exercisesService.getStudentListOfExerciseResolutions(exerciseId, studentId));

            return new ExceptionResponseEntity<List<ExerciseResolution>>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to list the resolutions made by a student for the given exercise.");
        } catch (UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<List<ExerciseResolution>>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<ExerciseResolution> getLastExerciseResolutionByStudent(String jwtToken, String exerciseId, String studentId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            // checks if the user has permission
            boolean perm = userId.equals(studentId); // user is the student that is the aim of the query
            if(role.equals("SPECIALIST"))
                perm = exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId);

            // if he has permission, execute the request
            if(perm)
                return ResponseEntity.ok(exercisesService.getLastExerciseResolutionByStudent(exerciseId, studentId));

            return new ExceptionResponseEntity<ExerciseResolution>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to get the last resolution made by a student for the given exercise.");
        } catch (UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<ExerciseResolution>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<List<Exercise>> getExercises(String jwtToken, Integer page, Integer itemsPerPage, List<String> tags, Boolean matchAllTags, String visibility, String courseId, String institutionId, String specialistId, String title, String exerciseType) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            // convert visibility
            Visibility vis = visibility != null ? Visibility.fromValue(visibility) : null;
            // default is false for matchAllTags
            matchAllTags = matchAllTags != null && matchAllTags;

            boolean perm = false;

            // if no visibility is given, only the owner of the exercises can execute the query
            if(visibility == null) {
                if (userId.equals(specialistId))
                    perm = true;
            }
            else{
                if(role.equals("STUDENT"))
                    perm = exercisesTestsAuthorization.canStudentGetExercise(userId, vis, courseId, institutionId);
                else if (role.equals("SPECIALIST")) {
                    perm = exercisesTestsAuthorization.canSpecialistGetExercise(userId, specialistId, vis, courseId, institutionId);
                }
            }

            if(perm)
                return ResponseEntity.ok(
                        exercisesService.getExercises(
                                page, itemsPerPage, tags, matchAllTags,
                                vis, courseId, institutionId,
                                specialistId, title, exerciseType, false));
            else
                return new ExceptionResponseEntity<List<Exercise>>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to list the exercises with the given filters.");
        } catch (NotFoundException | UnauthorizedException | BadInputException e) {
            return new ExceptionResponseEntity<List<Exercise>>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> addCommentToExerciseResolution(String jwtToken, String resolutionId, String comment) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            // checks if the user has permission
            if(role.equals("SPECIALIST") && exercisesTestsAuthorization.canSpecialistAccessExerciseResolution(userId, resolutionId)) {
                Comment c = new Comment(List.of(new StringItem(comment)));
                exercisesService.addCommentToExerciseResolution(resolutionId, c);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            
            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to add a comment to the resolution.");
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> removeCommentFromExerciseResolution(String jwtToken, String resolutionId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            // checks if the user has permission
            if(role.equals("SPECIALIST") && exercisesTestsAuthorization.canSpecialistAccessExerciseResolution(userId, resolutionId)) {
                exercisesService.removeCommentFromExerciseResolution(resolutionId);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to remove a comment from the resolution.");
        } catch (UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<ExerciseResolution> getExerciseResolution(String jwtToken, String resolutionId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            // checks if the user has permission
            boolean perm = false; 
            if(role.equals("STUDENT"))
                perm = exercisesTestsAuthorization.canStudentAccessExerciseResolution(userId, resolutionId);
            else if(role.equals("SPECIALIST"))
                perm = exercisesTestsAuthorization.canSpecialistAccessExerciseResolution(userId, resolutionId);

            // if he has permission, execute the request
            if(perm)
                return ResponseEntity.ok(exercisesService.getExerciseResolution(resolutionId));

            return new ExceptionResponseEntity<ExerciseResolution>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to get the exercise resolution.");
        } catch (UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<ExerciseResolution>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteExerciseResolutionById(String jwtToken, String resolutionId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            // checks if the user has permission
            if(role.equals("STUDENT") && exercisesTestsAuthorization.canStudentAccessExerciseResolution(userId, resolutionId)) {
                exercisesService.deleteExerciseResolutionById(resolutionId);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to delete the exercise resolution.");
        } catch (UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> manuallyCorrectExerciseResolution(String jwtToken, String resolutionId, ManualExerciseCorrectionDTO mecDTO) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(mecDTO == null)
                throw new BadInputException("Bad input.");

            float points = mecDTO.getPoints();
            String commentStr = mecDTO.getComment();
            Comment comment = null;
            if(commentStr != null)
                comment = new Comment(List.of(new StringItem(mecDTO.getComment())));

            // checks if the user has permission
            if(role.equals("SPECIALIST") && exercisesTestsAuthorization.canSpecialistAccessExerciseResolution(userId, resolutionId)) {
                exercisesService.manuallyCorrectExerciseResolution(resolutionId, points, comment);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to manually correct the exercise resolution.");
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<ExerciseSolution> getExerciseSolution(String jwtToken, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            Exercise exercise = canGetExercise(userId, role, exerciseId);

            if(exercise != null)
                return ResponseEntity.ok(exercisesService.getExerciseSolution(exerciseId));
            else
                return new ExceptionResponseEntity<ExerciseSolution>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to access the exercise's solution.");
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<ExerciseSolution>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> createExerciseSolution(String jwtToken, String exerciseId, ExerciseResolutionData solution) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.createExerciseSolution(exerciseId, new ExerciseSolution(null, solution));
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to create the exercise's solution.");
        } catch (UnauthorizedException | NotFoundException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteExerciseSolution(String jwtToken, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessExercise(userId, exerciseId)) {
                    exercisesService.deleteExerciseSolution(exerciseId);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to delete the exercise's solution.");
        } catch (UnauthorizedException | NotFoundException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }
    
    @Override
    public ResponseEntity<String> getExerciseCourseId(String jwtToken, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            Exercise exercise = canGetExercise(userId, role, exerciseId);

            if(exercise != null)
                return ResponseEntity.ok(exercisesService.getExerciseCourse(exerciseId));
            else
                return new ExceptionResponseEntity<String>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to access the exercise's course identifier.");
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<String> getExerciseInstitutionId(String jwtToken, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            Exercise exercise = canGetExercise(userId, role, exerciseId);

            if(exercise != null)
                return ResponseEntity.ok(exercisesService.getExerciseInstitution(exerciseId));
            else
                return new ExceptionResponseEntity<String>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to access the exercise's institution identifier.");
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<String> getExerciseVisibility(String jwtToken, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            Exercise exercise = canGetExercise(userId, role, exerciseId);

            if(exercise != null)
                return ResponseEntity.ok(exercisesService.getExerciseVisibility(exerciseId).toString());
            else
                return new ExceptionResponseEntity<String>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to access the exercise's visibility.");
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Set<Tag>> getExerciseTags(String jwtToken, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT jwt = securityService.validateJWT(jwtToken);
            String userId = jwt.getUserId(),
                    role = jwt.getUserRole();

            Exercise exercise = canGetExercise(userId, role, exerciseId);

            if(exercise != null)
                return ResponseEntity.ok(exercisesService.getExerciseTags(exerciseId));
            else
                return new ExceptionResponseEntity<Set<Tag>>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to access the exercise's tags.");
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<Set<Tag>>().createRequest(e);
        }
    }
}
