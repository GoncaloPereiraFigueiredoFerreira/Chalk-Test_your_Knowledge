package pt.uminho.di.chalktyk.apis;

import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.Course;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.CourseWithoutId;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.CoursesBody;
import pt.uminho.di.chalktyk.apis.to_be_removed_models_folder.CoursesCourseIdBody;
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
public class CoursesApiController implements CoursesApi {

    private static final Logger log = LoggerFactory.getLogger(CoursesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public CoursesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> coursesCourseIdDelete(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<CourseWithoutId> coursesCourseIdGet(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<CourseWithoutId>(objectMapper.readValue("{\n  \"description\" : \"description\"\n}", CourseWithoutId.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<CourseWithoutId>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<CourseWithoutId>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> coursesCourseIdPut(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CoursesCourseIdBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Course>> coursesGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Find the courses this student belongs to. " ,schema=@Schema()) @Valid @RequestParam(value = "studentId", required = false) String studentId
,@Parameter(in = ParameterIn.QUERY, description = "Find the courses this specialist belongs to. " ,schema=@Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId
,@Parameter(in = ParameterIn.QUERY, description = "Find the courses of this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Course>>(objectMapper.readValue("[ \"\", \"\" ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Course>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Course>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> coursesPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CoursesBody body
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
