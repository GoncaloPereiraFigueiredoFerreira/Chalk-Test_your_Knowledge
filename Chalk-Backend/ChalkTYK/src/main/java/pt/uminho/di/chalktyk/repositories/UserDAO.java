package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.users.User;

@Repository
public interface UserDAO extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
}
