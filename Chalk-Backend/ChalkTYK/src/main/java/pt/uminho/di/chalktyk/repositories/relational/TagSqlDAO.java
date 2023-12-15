package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.relational.TagSQL;

import java.util.List;

@Repository
public interface TagSqlDAO extends JpaRepository<TagSQL, String> {
    TagSQL findByNameAndPath(String name, String path);
    boolean existsByNameAndPath(String name, String path);
    @Query(nativeQuery = true, value = "SELECT * FROM tag as t WHERE t.path ~ :pathRegex")
    List<TagSQL> findByPathRegex(@Param("pathRegex") String pathRegex);
}
