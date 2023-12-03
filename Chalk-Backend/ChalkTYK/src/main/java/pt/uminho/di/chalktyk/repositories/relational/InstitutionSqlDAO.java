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
    void addSpecialistToInstitution(@Param("specialistId") String specialistId, @Param("institutionId") String institution);


    @Query(value = "SELECT s.institution FROM Specialist s WHERE s.id = :specialistId")
    Institution getInstitutionBySpecialistId(@Param("specialistId") String specialistId);
}
