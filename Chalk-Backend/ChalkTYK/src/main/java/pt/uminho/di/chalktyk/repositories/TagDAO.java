package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.miscellaneous.Tag;

import java.util.List;

@Repository
public interface TagDAO extends JpaRepository<Tag, String> {
    Tag findByNameAndPath(String name, String path);
    boolean existsByNameAndPath(String name, String path);
    @Query(nativeQuery = true, value = "SELECT * FROM tag as t WHERE t.path ~ :pathRegex")
    List<Tag> findByPathRegex(@Param("pathRegex") String pathRegex);
}
