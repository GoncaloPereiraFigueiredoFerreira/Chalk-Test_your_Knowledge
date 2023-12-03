package pt.uminho.di.chalktyk.repositories.relational;

import org.hibernate.mapping.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.stereotype.Repository;
import pt.uminho.di.chalktyk.models.relational.Institution;

@Repository
public interface InstitutionSqlDAO extends JpaRepository<Institution, String> {
    @Modifying
    @Query(value = "UPDATE Specialist s SET s.institution.id = :institutionId WHERE s.id = :specialistId")
    void addSpecialistToInstitution(@Param("specialistId") String specialistId, @Param("institutionId") String institutionId);

    @Modifying
    @Query(value = "UPDATE Student s SET s.institution.id = :institutionId WHERE s.id = :studentId")
    void addStudentToInstitution(@Param("studentId") String studentId, @Param("institutionId") String institutionId);

    @Modifying
    @Query(value = "UPDATE Specialist s SET s.institution.id = null WHERE s.id = :specialistId and s.institution.id =:institutionId")
    void removeSpecialistFromInstitution(@Param("specialistId") String specialistId, @Param("institutionId") String institutionId);

    @Modifying
    @Query(value = "UPDATE Student s SET s.institution.id = null WHERE s.id = :studentId and s.institution.id =:institutionId")
    void removeStudentFromInstitution(@Param("studentId") String studentId, @Param("institutionId") String institutionId);

    @Query(value = "SELECT s.institution FROM Specialist s WHERE s.id = :specialistId")
    Institution getInstitutionBySpecialistId(@Param("specialistId") String specialistId);

    @Query(value = "SELECT s.institution FROM Student s WHERE s.id = :studentId")
    Institution getInstitutionByStudentId(@Param("studentId") String studentId);

    @Query(value = "SELECT im.institution FROM InstitutionManager im WHERE im.id = :institutionManagerId")
    Institution getInstitutionByInstitutionManagerId(@Param("institutionManagerId") String institutionManagerId);
}
