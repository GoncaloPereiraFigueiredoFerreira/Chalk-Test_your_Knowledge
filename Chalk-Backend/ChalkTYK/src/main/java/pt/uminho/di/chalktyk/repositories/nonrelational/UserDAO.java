package pt.uminho.di.chalktyk.repositories.nonrelational;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.nonrelational.users.User;

@Repository
public interface UserDAO extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
}
