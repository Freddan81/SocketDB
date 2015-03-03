package no.connectdb.config;

import java.util.List;

import no.connectdb.model.Json;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JsonRepository extends MongoRepository<Json, Long> {

}
