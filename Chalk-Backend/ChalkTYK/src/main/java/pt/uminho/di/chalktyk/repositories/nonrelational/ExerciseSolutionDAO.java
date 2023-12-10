package pt.uminho.di.chalktyk.repositories.nonrelational;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.nonrelational.exercises.ExerciseSolution;

@Repository
public interface ExerciseSolutionDAO extends MongoRepository<ExerciseSolution,String> {
}
