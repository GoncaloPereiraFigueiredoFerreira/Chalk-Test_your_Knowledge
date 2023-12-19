package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.uminho.di.chalktyk.models.relational.TestTagsPkSQL;
import pt.uminho.di.chalktyk.models.relational.TestTagsSQL;

@Repository
public interface TestTagsSqlDAO extends JpaRepository<TestTagsSQL, TestTagsPkSQL> {
    
}
