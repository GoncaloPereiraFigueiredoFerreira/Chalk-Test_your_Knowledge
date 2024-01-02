package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.login.BlackListedJWT;

@Repository
public interface BlackListedJWTDao extends JpaRepository<BlackListedJWT, String> {
}
