package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.uminho.di.chalktyk.models.relational.Institution;

public interface InstitutionSqlDAO extends JpaRepository<Institution, String> {
    
}
