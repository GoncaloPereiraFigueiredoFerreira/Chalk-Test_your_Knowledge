package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.login.Login;

@Repository
public interface LoginDao extends JpaRepository<Login, String> {
}
