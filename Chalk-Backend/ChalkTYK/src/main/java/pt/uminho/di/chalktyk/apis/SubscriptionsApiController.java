package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.InstitutionsInstitutionIdBody1;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.SpecialistsSpecialistIdBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.StudentsStudentIdBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Subscription;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.SubscriptionPlan;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.SubscriptionsBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.SubscriptionsInstitutionsBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.SubscriptionsSpecialistsBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.SubscriptionsStudentsBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.SubscriptionsSubscriptionPlanIdBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class SubscriptionsApiController implements SubscriptionsApi {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public SubscriptionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<SubscriptionPlan>> subscriptionsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Finds the specialist subscription. " ,schema=@Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId
,@Parameter(in = ParameterIn.QUERY, description = "Finds the student subscription. " ,schema=@Schema()) @Valid @RequestParam(value = "studentId", required = false) String studentId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<SubscriptionPlan>>(objectMapper.readValue("[ {\n  \"name\" : \"IDK yet\",\n  \"perks\" : [ \"perk1\" ],\n  \"priceMonth\" : 100000.5,\n  \"targetAudience\" : [ \"audience1\" ]\n}, {\n  \"name\" : \"IDK yet\",\n  \"perks\" : [ \"perk1\" ],\n  \"priceMonth\" : 100000.5,\n  \"targetAudience\" : [ \"audience1\" ]\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<SubscriptionPlan>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<SubscriptionPlan>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsInstitutionsInstitutionIdDelete(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Subscription> subscriptionsInstitutionsInstitutionIdGet(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Subscription>(objectMapper.readValue("{\n  \"nextPayment\" : \"11-09-2024\",\n  \"payment\" : {\n    \"creditCard\" : {\n      \"cvv\" : \"666\",\n      \"expirationDate\" : \"11-09-2050\",\n      \"name\" : \"GONCALO COUTO DOS SANTOS\",\n      \"number\" : \"5555555555554444\"\n    }\n  },\n  \"planId\" : \"planID\"\n}", Subscription.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Subscription>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Subscription>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsInstitutionsInstitutionIdPut(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody InstitutionsInstitutionIdBody1 body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsInstitutionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody SubscriptionsInstitutionsBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody SubscriptionsBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsSpecialistsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody SubscriptionsSpecialistsBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsSpecialistsSpecialistIdDelete(@Parameter(in = ParameterIn.PATH, description = "Specialist identifier", required=true, schema=@Schema()) @PathVariable("specialistId") String specialistId
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Subscription> subscriptionsSpecialistsSpecialistIdGet(@Parameter(in = ParameterIn.PATH, description = "Specialist identifier", required=true, schema=@Schema()) @PathVariable("specialistId") String specialistId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Subscription>(objectMapper.readValue("{\n  \"nextPayment\" : \"11-09-2024\",\n  \"payment\" : {\n    \"creditCard\" : {\n      \"cvv\" : \"666\",\n      \"expirationDate\" : \"11-09-2050\",\n      \"name\" : \"GONCALO COUTO DOS SANTOS\",\n      \"number\" : \"5555555555554444\"\n    }\n  },\n  \"planId\" : \"planID\"\n}", Subscription.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Subscription>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Subscription>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsSpecialistsSpecialistIdPut(@Parameter(in = ParameterIn.PATH, description = "Specialist identifier", required=true, schema=@Schema()) @PathVariable("specialistId") String specialistId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody SpecialistsSpecialistIdBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsStudentsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody SubscriptionsStudentsBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsStudentsStudentIdDelete(@Parameter(in = ParameterIn.PATH, description = "Student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Subscription> subscriptionsStudentsStudentIdGet(@Parameter(in = ParameterIn.PATH, description = "Student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Subscription>(objectMapper.readValue("{\n  \"nextPayment\" : \"11-09-2024\",\n  \"payment\" : {\n    \"creditCard\" : {\n      \"cvv\" : \"666\",\n      \"expirationDate\" : \"11-09-2050\",\n      \"name\" : \"GONCALO COUTO DOS SANTOS\",\n      \"number\" : \"5555555555554444\"\n    }\n  },\n  \"planId\" : \"planID\"\n}", Subscription.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Subscription>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Subscription>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsStudentsStudentIdPut(@Parameter(in = ParameterIn.PATH, description = "Student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody StudentsStudentIdBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsSubscriptionPlanIdDelete(@Parameter(in = ParameterIn.PATH, description = "Subscription Plan identifier", required=true, schema=@Schema()) @PathVariable("subscriptionPlanId") String subscriptionPlanId
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<SubscriptionPlan> subscriptionsSubscriptionPlanIdGet(@Parameter(in = ParameterIn.PATH, description = "Subscription Plan identifier", required=true, schema=@Schema()) @PathVariable("subscriptionPlanId") String subscriptionPlanId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<SubscriptionPlan>(objectMapper.readValue("{\n  \"name\" : \"IDK yet\",\n  \"perks\" : [ \"perk1\" ],\n  \"priceMonth\" : 100000.5,\n  \"targetAudience\" : [ \"audience1\" ]\n}", SubscriptionPlan.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<SubscriptionPlan>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<SubscriptionPlan>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> subscriptionsSubscriptionPlanIdPut(@Parameter(in = ParameterIn.PATH, description = "Subscription Plan identifier", required=true, schema=@Schema()) @PathVariable("subscriptionPlanId") String subscriptionPlanId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody SubscriptionsSubscriptionPlanIdBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
