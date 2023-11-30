package pt.uminho.di.chalktyk.repositories.nonrelational;

import org.springframework.data.mongodb.repository.MongoRepository;

import pt.uminho.di.chalktyk.models.nonrelational.institutions.Institution;

public interface InstitutionDAO extends MongoRepository<Institution, String> {
}
