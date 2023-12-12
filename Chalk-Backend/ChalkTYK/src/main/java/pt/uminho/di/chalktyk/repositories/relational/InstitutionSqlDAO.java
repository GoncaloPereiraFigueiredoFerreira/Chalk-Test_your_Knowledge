package pt.uminho.di.chalktyk.repositories.relational;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.uminho.di.chalktyk.models.relational.InstitutionSQL;

@Repository
public interface InstitutionSqlDAO extends JpaRepository<InstitutionSQL, String> {
    @Modifying
    @Query(value = "UPDATE SpecialistSQL s SET s.institution.id = :institutionId WHERE s.id = :specialistId")
    void addSpecialistToInstitution(@Param("specialistId") String specialistId, @Param("institutionId") String institutionId);

    @Modifying
    @Query(value = "UPDATE StudentSQL s SET s.institution.id = :institutionId WHERE s.id = :studentId")
    void addStudentToInstitution(@Param("studentId") String studentId, @Param("institutionId") String institutionId);

    @Modifying
    @Query(value = "UPDATE SpecialistSQL s SET s.institution.id = null WHERE s.id = :specialistId and s.institution.id =:institutionId")
    void removeSpecialistFromInstitution(@Param("specialistId") String specialistId, @Param("institutionId") String institutionId);

    @Modifying
    @Query(value = "UPDATE StudentSQL s SET s.institution.id = null WHERE s.id = :studentId and s.institution.id =:institutionId")
    void removeStudentFromInstitution(@Param("studentId") String studentId, @Param("institutionId") String institutionId);

    @Query(value = "SELECT s.institution FROM SpecialistSQL s WHERE s.id = :specialistId")
    InstitutionSQL getInstitutionBySpecialistId(@Param("specialistId") String specialistId);

    @Query(value = "SELECT s.institution FROM StudentSQL s WHERE s.id = :studentId")
    InstitutionSQL getInstitutionByStudentId(@Param("studentId") String studentId);

    @Query(value = "SELECT im.institution FROM InstitutionManagerSQL im WHERE im.id = :institutionManagerId")
    InstitutionSQL getInstitutionByInstitutionManagerId(@Param("institutionManagerId") String institutionManagerId);

    @Query(value = "SELECT s.id FROM SpecialistSQL s where s.institution.id = :institutionId")
    Page<String> getInstitutionSpecialistsIds(@Param("institutionId") String institutionId, Pageable pageable);

    @Query(value = "SELECT s.id FROM StudentSQL s where s.institution.id = :institutionId")
    Page<String> getInstitutionStudentsIds(@Param("institutionId") String institutionId, Pageable pageable);

    @Query(value = "SELECT im.id FROM InstitutionManagerSQL im where im.institution.id = :institutionId")
    Page<String> getInstitutionManagersIds(@Param("institutionId") String institutionId, Pageable pageable);

    @Query(value = "SELECT COUNT (s) FROM SpecialistSQL s where s.institution.id = :institutionId")
    int countInstitutionSpecialists(@Param("institutionId") String institutionId);

    @Query(value = "SELECT COUNT (s) FROM StudentSQL s where s.institution.id = :institutionId")
    int countInstitutionStudents(@Param("institutionId") String institutionId);

    @Query(value = "SELECT COUNT (im) FROM InstitutionManagerSQL im where im.institution.id = :institutionId")
    int countInstitutionManagersFromInstitution(@Param("institutionId") String institutionId);
}
