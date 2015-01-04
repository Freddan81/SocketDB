package no.connectdb.config;

import no.connectdb.model.Draw;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DrawRepository extends MongoRepository<Draw, Long>{

}
