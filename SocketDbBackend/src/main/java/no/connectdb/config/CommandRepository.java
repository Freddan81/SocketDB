package no.connectdb.config;

import java.util.List;

import no.connectdb.model.Json;
import no.socketdb.listener.handlers.ChangeHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommandRepository {

	@Autowired
	private JsonRepository jsonRepository;	
	
	public void save(Json save) {
		long count = jsonRepository.count();
		save.setId(count + 1);
		Json saved = jsonRepository.save(save);
		ChangeHandler.get().invoke("insert", saved);
	}
	
	public void allInserted() {
		List<Json> all = jsonRepository.findAll();
		for (Json a : all) {
			ChangeHandler.get().invoke("insert_old", a);
		}
		ChangeHandler.get().invoke("insert_stop", null);
	}
}
