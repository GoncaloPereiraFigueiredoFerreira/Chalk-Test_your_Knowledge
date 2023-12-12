package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.uminho.di.chalktyk.models.relational.TestSQL;

@Repository
public interface TestSqlDAO extends JpaRepository<TestSQL, String>{
}
