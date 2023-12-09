package pt.uminho.di.chalktyk.repositories.nonrelational;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pt.uminho.di.chalktyk.models.nonrelational.tests.TestResolution;

@Repository
public interface TestResolutionDAO extends MongoRepository<TestResolution, String> {
}
