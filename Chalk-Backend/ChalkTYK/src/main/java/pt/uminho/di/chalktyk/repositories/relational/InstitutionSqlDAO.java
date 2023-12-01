package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.relational.Institution;

@Repository
public interface InstitutionSqlDAO extends JpaRepository<Institution, String> {
}
