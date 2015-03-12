package no.connectdb.config;

import no.connectdb.model.Json;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface ESRepository extends ElasticsearchCrudRepository<Json, Long>{

}
