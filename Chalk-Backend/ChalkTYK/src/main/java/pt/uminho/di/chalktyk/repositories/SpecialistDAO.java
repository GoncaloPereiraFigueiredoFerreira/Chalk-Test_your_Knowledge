package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.users.Specialist;

@Repository
public interface SpecialistDAO extends JpaRepository<Specialist, String> {
}
