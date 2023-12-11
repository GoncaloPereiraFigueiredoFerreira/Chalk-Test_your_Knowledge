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
import pt.uminho.di.chalktyk.services.exceptions.UnauthorizedException;

import java.io.IOException;
import java.util.List;

@RestController
public class CoursesApiController implements CoursesApi {

    public ResponseEntity<Void> coursesCourseIdDelete(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
) {

        throw new RuntimeException("Not implemented");
        //jwt.verify if is specialist
        //throw new UnauthorizedException("Method is only available for specialists");
    }

    public ResponseEntity<CourseWithoutId> coursesCourseIdGet(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> coursesCourseIdPut(@Parameter(in = ParameterIn.PATH, description = "Course identifier", required=true, schema=@Schema()) @PathVariable("courseId") String courseId
,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CoursesCourseIdBody body
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<List<Course>> coursesGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "page", required = true) Integer page
,@NotNull @Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "" ,required=true,schema=@Schema(allowableValues={ "1", "50" }, minimum="1", maximum="50"
)) @Valid @RequestParam(value = "itemsPerPage", required = true) Integer itemsPerPage
,@Parameter(in = ParameterIn.QUERY, description = "Find the courses this student belongs to. " ,schema=@Schema()) @Valid @RequestParam(value = "studentId", required = false) String studentId
,@Parameter(in = ParameterIn.QUERY, description = "Find the courses this specialist belongs to. " ,schema=@Schema()) @Valid @RequestParam(value = "specialistId", required = false) String specialistId
,@Parameter(in = ParameterIn.QUERY, description = "Find the courses of this institution. " ,schema=@Schema()) @Valid @RequestParam(value = "institutionId", required = false) String institutionId
) {
        throw new RuntimeException("Not implemented");
    }

    public ResponseEntity<Void> coursesPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CoursesBody body
) {
        throw new RuntimeException("Not implemented");
    }

}
