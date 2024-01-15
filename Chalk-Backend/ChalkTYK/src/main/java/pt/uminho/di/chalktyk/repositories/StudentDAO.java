package pt.uminho.di.chalktyk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.users.Student;

@Repository
public interface StudentDAO extends JpaRepository<Student, String> {
    @Query("SELECT id FROM Student WHERE email = :email")
    String getStudentIdByEmail(@Param("email") String email);
}
