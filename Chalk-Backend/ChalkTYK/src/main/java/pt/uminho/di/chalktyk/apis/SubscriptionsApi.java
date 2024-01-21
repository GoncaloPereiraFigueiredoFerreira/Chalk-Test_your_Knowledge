
package pt.uminho.di.chalktyk.apis;











import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import pt.uminho.di.chalktyk.models.subscriptions.Subscription;
import pt.uminho.di.chalktyk.models.subscriptions.SubscriptionPlan;

import java.util.List;

@Validated
public interface SubscriptionsApi {

    @Operation(summary = "", description = "Returns all the subscription plans.", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SubscriptionPlan.class)))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/subscriptions",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<SubscriptionPlan>> subscriptionsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
, @NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
, @Parameter(in = ParameterIn.QUERY, description = "Finds the specialist subscription. " ,schema=@Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId
, @Parameter(in = ParameterIn.QUERY, description = "Finds the student subscription. " ,schema=@Schema()) @Valid @RequestParam(value = "studentId", required = false) String studentId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Delete subscription from institution", description = "", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid institution id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Institution not found") })
    @RequestMapping(value = "/subscriptions/institutions/{institutionId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> subscriptionsInstitutionsInstitutionIdDelete(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get subscription by institution id.", description = "", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid institution id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Institution not found") })
    @RequestMapping(value = "/subscriptions/institutions/{institutionId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Subscription> subscriptionsInstitutionsInstitutionIdGet(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Update institution subscription", description = "Update an existent subscription in the store", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid institution id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Institution not found"),
        
        @ApiResponse(responseCode = "405", description = "This method is only for update, use the post method.") })
    @RequestMapping(value = "/subscriptions/institutions/{institutionId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> subscriptionsInstitutionsInstitutionIdPut(@Parameter(in = ParameterIn.PATH, description = "Institution identifier", required=true, schema=@Schema()) @PathVariable("institutionId") String institutionId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Subscription body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Create subscription for a institution", description = "This method is used to create a new subsctiption for a institution", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/subscriptions/institutions",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> subscriptionsInstitutionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Subscription body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Create subscription plan", description = "This method is used to create a new subsctiption plan", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/subscriptions",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> subscriptionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Subscription body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Create subscription for a specialist", description = "This method is used to create a new subsctiption for a specialist", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/subscriptions/specialists",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> subscriptionsSpecialistsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Subscription body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Delete subscription from specialist", description = "", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid specialist id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Specialist not found") })
    @RequestMapping(value = "/subscriptions/specialists/{specialistId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> subscriptionsSpecialistsSpecialistIdDelete(@Parameter(in = ParameterIn.PATH, description = "Specialist identifier", required=true, schema=@Schema()) @PathVariable("specialistId") String specialistId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get subscription by specialist id.", description = "", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid specialist id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Specialist not found") })
    @RequestMapping(value = "/subscriptions/specialists/{specialistId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Subscription> subscriptionsSpecialistsSpecialistIdGet(@Parameter(in = ParameterIn.PATH, description = "Specialist identifier", required=true, schema=@Schema()) @PathVariable("specialistId") String specialistId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Update specialist subscription", description = "Update an existent subscription in the store", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid specialist id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Specialist not found"),
        
        @ApiResponse(responseCode = "405", description = "This method is only for update, use the post method.") })
    @RequestMapping(value = "/subscriptions/specialists/{specialistId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> subscriptionsSpecialistsSpecialistIdPut(@Parameter(in = ParameterIn.PATH, description = "Specialist identifier", required=true, schema=@Schema()) @PathVariable("specialistId") String specialistId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Subscription body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Create subscription for a student", description = "This method is used to create a new subsctiption for a student", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation") })
    @RequestMapping(value = "/subscriptions/students",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> subscriptionsStudentsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Subscription body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Delete subscription", description = "", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid student id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Student not found") })
    @RequestMapping(value = "/subscriptions/students/{studentId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> subscriptionsStudentsStudentIdDelete(@Parameter(in = ParameterIn.PATH, description = "Student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get subscription by student id.", description = "", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Subscription.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid student id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Student not found") })
    @RequestMapping(value = "/subscriptions/students/{studentId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Subscription> subscriptionsStudentsStudentIdGet(@Parameter(in = ParameterIn.PATH, description = "Student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Update student subscription", description = "Update an existent subscription in the store", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid student id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Student not found"),
        
        @ApiResponse(responseCode = "405", description = "This method is only for update, use the post method.") })
    @RequestMapping(value = "/subscriptions/students/{studentId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> subscriptionsStudentsStudentIdPut(@Parameter(in = ParameterIn.PATH, description = "Student identifier", required=true, schema=@Schema()) @PathVariable("studentId") String studentId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Subscription body
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Delete subscription plan", description = "", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid subscription plan identifier supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Subscription plan not found"),
        
        @ApiResponse(responseCode = "405", description = "The subsctiption plan cannot be deleted because its being used.") })
    @RequestMapping(value = "/subscriptions/{subscriptionPlanId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> subscriptionsSubscriptionPlanIdDelete(@Parameter(in = ParameterIn.PATH, description = "Subscription Plan identifier", required=true, schema=@Schema()) @PathVariable("subscriptionPlanId") String subscriptionPlanId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Get subscription plan by subscription plan id", description = "", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubscriptionPlan.class))),
        
        @ApiResponse(responseCode = "400", description = "Invalid user id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "User not found") })
    @RequestMapping(value = "/subscriptions/{subscriptionPlanId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<SubscriptionPlan> subscriptionsSubscriptionPlanIdGet(@Parameter(in = ParameterIn.PATH, description = "Subscription Plan identifier", required=true, schema=@Schema()) @PathVariable("subscriptionPlanId") String subscriptionPlanId
, @CookieValue("chalkauthtoken") String jwt);


    @Operation(summary = "Update subscription plan", description = "Update an existent subscription plan in the store", tags={ "subscription" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        
        @ApiResponse(responseCode = "400", description = "Invalid subscription plan id supplied"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized operation"),
        
        @ApiResponse(responseCode = "404", description = "Subscription plan not found") })
    @RequestMapping(value = "/subscriptions/{subscriptionPlanId}",
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<Void> subscriptionsSubscriptionPlanIdPut(@Parameter(in = ParameterIn.PATH, description = "Subscription Plan identifier", required=true, schema=@Schema()) @PathVariable("subscriptionPlanId") String subscriptionPlanId
, @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody SubscriptionPlan body
, @CookieValue("chalkauthtoken") String jwt);

}

