package pt.uminho.di.chalktyk.apis;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pt.uminho.di.chalktyk.apis.utility.ExceptionResponseEntity;
import pt.uminho.di.chalktyk.apis.utility.JWT;
import pt.uminho.di.chalktyk.dtos.CreateTestExerciseDTO;
import pt.uminho.di.chalktyk.dtos.DuplicateTestDTO;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.miscellaneous.Visibility;
import pt.uminho.di.chalktyk.models.tests.Test;
import pt.uminho.di.chalktyk.models.tests.TestGroup;
import pt.uminho.di.chalktyk.models.tests.TestResolution;
import pt.uminho.di.chalktyk.models.tests.TestTag;
import pt.uminho.di.chalktyk.models.users.Specialist;
import pt.uminho.di.chalktyk.services.IExercisesTestsAuthorization;
import pt.uminho.di.chalktyk.services.ISecurityService;
import pt.uminho.di.chalktyk.services.ITestsService;
import pt.uminho.di.chalktyk.services.exceptions.BadInputException;
import pt.uminho.di.chalktyk.services.exceptions.NotFoundException;
import pt.uminho.di.chalktyk.services.exceptions.ForbiddenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pt.uminho.di.chalktyk.services.exceptions.ServiceException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tests")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class TestsApiController implements TestsApi {
    private final ITestsService testsService;
    private final ISecurityService securityService;
    private final IExercisesTestsAuthorization exercisesTestsAuthorization;

    @Autowired
    public TestsApiController(ITestsService testsService, ISecurityService securityService, IExercisesTestsAuthorization exercisesTestsAuthorization){
        this.testsService = testsService;
        this.securityService = securityService;
        this.exercisesTestsAuthorization = exercisesTestsAuthorization;
    }

    private Test canGetTest(String userId, String userRole, String testId) throws NotFoundException {
        Test test = testsService.getTestById(testId);
        Visibility vis = test.getVisibility();
        String courseId = test.getCourseId();
        String institutionId = test.getInstitutionId();
        String ownerId = test.getSpecialistId();

        if(userRole.equals("STUDENT")) {
            if (exercisesTestsAuthorization.canStudentGetTest(userId, vis, courseId, institutionId))
                return test;
        }else if (userRole.equals("SPECIALIST")) {
            if (exercisesTestsAuthorization.canSpecialistGetTest(userId, ownerId, vis, courseId, institutionId))
                return test;
        }
        return null;
    }

    public ResponseEntity<List<Test>> getTests(Integer page, Integer itemsPerPage, List<String> tags, Boolean matchAllTags, String visibilityType, 
                                                String title, String specialistId, String courseId, String institutionId, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();


            boolean perm = false;

            Visibility visibility = null;
            if (visibilityType != null) {
                visibility = Visibility.fromValue(visibilityType);
                if (visibility == null)
                    throw new BadInputException("Visibility type not found");
            }

            if(role.equals("STUDENT"))
                perm = exercisesTestsAuthorization.canStudentGetTest(userId, visibility, courseId, institutionId);
            else if (role.equals("SPECIALIST")) {
                perm = exercisesTestsAuthorization.canSpecialistGetTest(userId, specialistId, visibility, courseId, institutionId);
            }

            if(perm)
                return ResponseEntity.ok(
                        testsService.getTests(
                                page, itemsPerPage, tags, matchAllTags,
                                visibility, specialistId, courseId, institutionId,
                                title, false).stream().toList());
            else
                return new ExceptionResponseEntity<List<Test>>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to list the tests with the given filters.");
        } catch (ServiceException e){
            return new ExceptionResponseEntity<List<Test>>().createRequest(e);
        }
    }

    public ResponseEntity<String> createTest(Test body, String jwt) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                body.setSpecialist(new Specialist(userId));
                String testId = testsService.createTest(body);
                return ResponseEntity.ok(testId);
            }
            else return new ExceptionResponseEntity<String>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to create tests.");
        }
        catch (ServiceException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    public ResponseEntity<TestResolution> getTestResolutionById(String resolutionId, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            // checks if the user has permission
            boolean perm = false;
            if(role.equals("STUDENT"))
                perm = exercisesTestsAuthorization.canStudentAccessTestResolution(userId, resolutionId);
            else if(role.equals("SPECIALIST"))
                perm = exercisesTestsAuthorization.canSpecialistAccessTestResolution(userId, resolutionId);

            // if he has permission, execute the request
            if(perm)
                return ResponseEntity.ok(testsService.getTestResolutionById(resolutionId));

            return new ExceptionResponseEntity<TestResolution>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to get the test resolution.");
        }
         catch (ServiceException e) {
             return new ExceptionResponseEntity<TestResolution>().createRequest(e);
        }
    }

    public ResponseEntity<Void> deleteTestById(String testId, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.deleteTestById(testId);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to delete the test.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Test> getTest(String testId, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            Test test = canGetTest(userId, role, testId);

            if(test != null)
                return ResponseEntity.ok(test);
            else
                return new ExceptionResponseEntity<Test>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to access the test.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Test>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<List<TestTag>> getTestTags(String jwt, String testId) {
        try {
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            return ResponseEntity.ok(testsService.getTestTags(testId));
        } catch (ServiceException e){
            return new ExceptionResponseEntity<List<TestTag>>().createRequest(e);
        }
    }

    public ResponseEntity<String> duplicateTestById(String testId, String jwt, DuplicateTestDTO duplicateTestDTO) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            Test test = testsService.getTestById(testId);
            Visibility exVisibility = test.getVisibility();
            String exCourseId = test.getCourseId();
            String exInstitutionId = test.getInstitutionId();

            boolean perm = role.equals("SPECIALIST")
                    && exercisesTestsAuthorization.canSpecialistGetTest(userId, test.getSpecialistId(), exVisibility, exCourseId, exInstitutionId);
            if(perm && duplicateTestDTO.getCourseId()!=null)
                perm = exercisesTestsAuthorization.specialistBelongsToCourse(userId,duplicateTestDTO.getCourseId());
            if(perm)
                return ResponseEntity.ok(testsService.duplicateTestById(userId, testId, Visibility.valueOf(duplicateTestDTO.getVisibility()), duplicateTestDTO.getCourseId()));
            else
                return new ExceptionResponseEntity<String>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to duplicate the test.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }


    /**
     * Updates test basic properties: title, conclusion, globalInstructions, publishDate and visibility.
     * @param body body containing the new basic properties.
     */
    @Override
    public ResponseEntity<Void> updateTestBasicProperties(String testId, Test body, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestBasicProperties(testId, body);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to delete the test.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestExercisePoints( String testId, int groupIndex, String exerciseId, float points, String jwt){
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestExercisePoints(testId, groupIndex, exerciseId, points);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to delete the test.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    public ResponseEntity<Void> automaticCorrection(String testId, String correctionType, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            // checks if the user has permission
            boolean perm = false;
            if(role.equals("STUDENT"))
                perm = exercisesTestsAuthorization.canStudentAccessTestResolution(userId, testId);
            else if(role.equals("SPECIALIST"))
                perm = exercisesTestsAuthorization.canSpecialistAccessTestResolution(userId, testId);

            // if he has permission, execute the request
            if(perm) {
                testsService.automaticCorrection(testId,correctionType);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to issue the correction of the test resolution.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    public ResponseEntity<Integer> countStudentsTestResolutions(String testId, Boolean total, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            // checks if the user has permission
            boolean perm = false;
            if(role.equals("SPECIALIST"))
                perm = exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId);

            // if he has permission, execute the request
            if(perm) {
                return ResponseEntity.ok(testsService.countStudentsTestResolutions(testId,total));
            }

            return new ExceptionResponseEntity<Integer>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to count the test resolutions");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Integer>().createRequest(e);
        }
    }

    public ResponseEntity<List<TestResolution>> getTestResolutions(String testId, Integer page, Integer itemsPerPage, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            // checks if the user has permission
            boolean perm = false;
            if(role.equals("SPECIALIST"))
                perm = exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId);

            // if he has permission, execute the request
            if(perm) {
                return ResponseEntity.ok(testsService.getTestResolutions(testId,page,itemsPerPage));
            }

            return new ExceptionResponseEntity<List<TestResolution>>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to get the test resolutions.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<List<TestResolution>>().createRequest(e);
        }
    }

    /*
    public ResponseEntity<Void> createTestResolution(String testId, TestResolution body, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("STUDENT") && canGetTest(userId, role, testId)!=null){
                testsService.createTestResolution(testId,body);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ExceptionResponseEntity<Void>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to create a resolution for the test.");
            }
        } catch (NotFoundException | UnauthorizedException | BadInputException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }*/

    /*
    public ResponseEntity<Boolean> canStudentSubmitResolution(String testId, String studentId, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if((role.equals("SPECIALIST") || role.equals("STUDENT")) && canGetTest(userId, role, testId)!=null){
                return ResponseEntity.ok(testsService.canStudentSubmitResolution(testId,studentId));
            }
            else {
                return new ExceptionResponseEntity<Boolean>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to verify if student can submit resolution.");
            }
        } catch (NotFoundException | UnauthorizedException e) {
            return new ExceptionResponseEntity<Boolean>().createRequest(e);
        }
    }*/

    public ResponseEntity<Integer> countStudentSubmissionsForTest(String testId, String studentId, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if((role.equals("SPECIALIST") || role.equals("STUDENT")) && canGetTest(userId, role, testId)!=null){
                return ResponseEntity.ok(testsService.countStudentSubmissionsForTest(testId,studentId));
            }
            else {
                return new ExceptionResponseEntity<Integer>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to count student submissions for the test.");
            }
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Integer>().createRequest(e);
        }
    }

    public ResponseEntity<List<String>> getStudentTestResolutionsIds(String testId, String studentId, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if((role.equals("SPECIALIST") || role.equals("STUDENT")) && canGetTest(userId, role, testId)!=null){
                return ResponseEntity.ok(testsService.getStudentTestResolutionsIds(testId,studentId));
            }
            else {
                return new ExceptionResponseEntity<List<String>>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to get student submissions for the test.");
            }
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<List<String>>().createRequest(e);
        }
    }

    public ResponseEntity<List<TestResolution>> getStudentLastResolutions(String testId, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if((role.equals("SPECIALIST") || role.equals("STUDENT")) && canGetTest(userId, role, testId)!=null){
                return ResponseEntity.ok(testsService.getStudentLastResolutions(testId));
            }
            else {
                return new ExceptionResponseEntity<List<TestResolution>>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to get student last submission for the test.");
            }
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<List<TestResolution>>().createRequest(e);
        }
    }

    public ResponseEntity<List<TestResolution>> getStudentLastResolutionsWithEmails(String testId, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if((role.equals("SPECIALIST") || role.equals("STUDENT")) && canGetTest(userId, role, testId)!=null){
                return ResponseEntity.ok(testsService.getStudentLastResolutionsWithEmails(testId));
            }
            else {
                return new ExceptionResponseEntity<List<TestResolution>>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to get student last submission for the test.");
            }
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<List<TestResolution>>().createRequest(e);
        }
    }

    public ResponseEntity<TestResolution> getStudentLastResolution(String testId, String studentId, String jwt) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwt);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if((role.equals("SPECIALIST") || role.equals("STUDENT")) && canGetTest(userId, role, testId)!=null){
                return ResponseEntity.ok(testsService.getStudentLastResolution(testId,studentId));
            }
            else {
                return new ExceptionResponseEntity<TestResolution>().createRequest(
                        HttpStatus.UNAUTHORIZED.value(),
                        "User does not have permission to get student last submission for the test.");
            }
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<TestResolution>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestTitle(String jwtToken, String testId, String title) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestTitle(testId,title);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test title.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestGlobalInstructions(String jwtToken, String testId, String globalInstructions) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestGlobalInstructions(testId,globalInstructions);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test global instructions.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestConclusion(String jwtToken, String testId, String conclusion) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestConclusion(testId,conclusion);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test conclusion.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestPublishDate(String jwtToken, String testId, LocalDateTime publishDate) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestPublishDate(testId, publishDate);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test publish date.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestVisibility(String jwtToken, String testId, Visibility visibility) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestVisibility(testId,visibility);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test visibility.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestCourse(String jwtToken, String testId, String courseId) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestCourse(testId,courseId);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test course.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestGroups(String jwtToken, String testId, List<TestGroup> groups) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestGroups(testId,groups);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test groups.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestGroup(String jwtToken, String testId, Integer groupIndex, TestGroup group) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestGroup(testId, groupIndex, group);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test groups.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestDeliverDate(String jwtToken, String testId, LocalDateTime deliverDate) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestDeliverDate(testId,deliverDate);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test deliver Date.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestStartDate(String jwtToken, String testId, LocalDateTime startDate) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestStartDate(testId,startDate);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test start date.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestDuration(String jwtToken, String testId, long duration) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestDuration(testId,duration);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test duration.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestStartTolerance(String jwtToken, String testId, long startTolerance) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.updateTestStartTolerance(testId,startTolerance);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to update the test start tolerence.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestExerciseResolution(String jwtToken, String testResolutionId, ExerciseResolution exerciseResolution) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("STUDENT")) {
                if(exercisesTestsAuthorization.canStudentAccessTestResolution(userId, testResolutionId)) {
                    testsService.uploadResolution(testResolutionId,exerciseResolution.getExerciseId(),exerciseResolution);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to upload a exercise resolution for this test.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<String> createTestExercise(String jwtToken, String testId, CreateTestExerciseDTO createTestExerciseDTO) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    return ResponseEntity.ok(testsService.createTestExercise(testId,
                            createTestExerciseDTO.getExercise(),
                            createTestExerciseDTO.getGroupIndex(),
                            createTestExerciseDTO.getExeIndex(),
                            createTestExerciseDTO.getGroupInstructions()));
                }
            }

            return new ExceptionResponseEntity<String>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to create a exercise for this test.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteExerciseFromTest(String jwtToken, String testId, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.deleteExerciseFromTest(testId,exerciseId);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to delete a exercise for this test.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> removeExerciseFromTest(String jwtToken, String testId, String exerciseId) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("SPECIALIST")) {
                if(exercisesTestsAuthorization.canSpecialistAccessTest(userId, testId)) {
                    testsService.removeExerciseFromTest(testId,exerciseId);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to delete a remove for this test.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<String> startTest(String jwtToken, String testId) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("STUDENT")) {
                if(exercisesTestsAuthorization.canStudentGetTest(userId, testId)) {
                    return ResponseEntity.ok(testsService.startTest(testId,userId));
                }
            }

            return new ExceptionResponseEntity<String>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission start this test.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<String>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTestResolution(String jwtToken, String testId) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            if(role.equals("STUDENT")) {
                if(exercisesTestsAuthorization.canStudentAccessTestResolution(userId, testId)) {
                    testsService.startTest(testId,userId);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission start this test.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> automaticCorrectionSingleResolution(String jwtToken, String resolutionId, String correctionType) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            // checks if the user has permission
            boolean perm = false;
            if(role.equals("STUDENT"))
                perm = exercisesTestsAuthorization.canStudentAccessTestResolution(userId, resolutionId);
            else if(role.equals("SPECIALIST"))
                perm = exercisesTestsAuthorization.canSpecialistAccessTestResolution(userId, resolutionId);

            // if he has permission, execute the request
            if(perm) {
                testsService.automaticCorrectionSingle(resolutionId,correctionType);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to issue the correction of the test resolution.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }

    @Override
    public ResponseEntity<Void> submitTestResolution(String jwtToken, String resolutionId) {
        try {
            // validate jwt token and get user id and role
            JWT token = securityService.validateJWT(jwtToken);
            String userId = token.getUserId(),
                    role = token.getUserRole();

            // checks if the user has permission
            boolean perm = false;
            if(role.equals("STUDENT"))
                perm = exercisesTestsAuthorization.canStudentAccessTestResolution(userId, resolutionId);

            // if he has permission, execute the request
            if(perm) {
                testsService.submitTestResolution(resolutionId);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ExceptionResponseEntity<Void>().createRequest(
                    HttpStatus.UNAUTHORIZED.value(),
                    "User does not have permission to issue the correction of the test resolution.");
        } catch (ServiceException e) {
            return new ExceptionResponseEntity<Void>().createRequest(e);
        }
    }
}
