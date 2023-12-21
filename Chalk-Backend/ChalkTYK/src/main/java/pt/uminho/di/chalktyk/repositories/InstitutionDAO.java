package pt.uminho.di.chalktyk.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pt.uminho.di.chalktyk.models.institutions.Institution;

@Repository
public interface InstitutionDAO extends JpaRepository<Institution, String> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Specialist s SET s.institution = (SELECT i FROM Institution i WHERE i.name=:institutionId) WHERE s.id = :specialistId")
    void addSpecialistToInstitution(@Param("specialistId") String specialistId, @Param("institutionId") String institutionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Student s SET s.institution = (SELECT i FROM Institution i WHERE i.name=:institutionId) WHERE s.id = :studentId")
    void addStudentToInstitution(@Param("studentId") String studentId, @Param("institutionId") String institutionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Specialist s SET s.institution = null WHERE s.id = :specialistId and s.institution.name =:institutionId")
    void removeSpecialistFromInstitution(@Param("specialistId") String specialistId, @Param("institutionId") String institutionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Student s SET s.institution = null WHERE s.id = :studentId and s.institution.name =:institutionId")
    void removeStudentFromInstitution(@Param("studentId") String studentId, @Param("institutionId") String institutionId);

    @Query(value = "SELECT s.institution FROM Specialist s WHERE s.id = :specialistId")
    Institution getInstitutionBySpecialistId(@Param("specialistId") String specialistId);

    @Query(value = "SELECT s.institution FROM Student s WHERE s.id = :studentId")
    Institution getInstitutionByStudentId(@Param("studentId") String studentId);

    @Query(value = "SELECT im.institution FROM InstitutionManager im WHERE im.id = :institutionManagerId")
    Institution getInstitutionByInstitutionManagerId(@Param("institutionManagerId") String institutionManagerId);

    @Query(value = "SELECT s.id FROM Specialist s where s.institution.name = :institutionId")
    Page<String> getInstitutionSpecialistsIds(@Param("institutionId") String institutionId, Pageable pageable);

    @Query(value = "SELECT s.id FROM Student s where s.institution.name = :institutionId")
    Page<String> getInstitutionStudentsIds(@Param("institutionId") String institutionId, Pageable pageable);

    @Query(value = "SELECT im.id FROM InstitutionManager im where im.institution.name = :institutionId")
    Page<String> getInstitutionManagersIds(@Param("institutionId") String institutionId, Pageable pageable);

    @Query(value = "SELECT COUNT (s) FROM Specialist s where s.institution.name = :institutionId")
    int countInstitutionSpecialists(@Param("institutionId") String institutionId);

    @Query(value = "SELECT COUNT (s) FROM Student s where s.institution.name = :institutionId")
    int countInstitutionStudents(@Param("institutionId") String institutionId);

    @Query(value = "SELECT COUNT (im) FROM InstitutionManager im where im.institution.name = :institutionId")
    int countInstitutionManagersFromInstitution(@Param("institutionId") String institutionId);
}
