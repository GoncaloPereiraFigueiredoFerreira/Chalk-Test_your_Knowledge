package pt.uminho.di.chalktyk.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.uminho.di.chalktyk.apis.dtos.CreateExerciseDTO;

@RestController
@RequestMapping("/v1/exercises")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class OLD_ExercisesAPI {

    @PostMapping
    public ResponseEntity<String> createExercise(@RequestBody CreateExerciseDTO cedto) throws Exception {
        throw new Exception("Not implemented yet.");
    }

}
