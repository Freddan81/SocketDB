package no.connectdb.config;

import java.util.List;

import no.connectdb.handlers.ChangeHandler;
import no.connectdb.model.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommandRepository {

	@Autowired
	private JsonRepository jsonRepository;	
//	private ESRepository jsonRepository;
	
	public void save(Json save) {
		long count = jsonRepository.count();
		save.setId(count + 1);
		Json saved = jsonRepository.save(save);
		ChangeHandler.get().invoke("insert", saved);
	}
	
	public void update(Json update) {
		Json old = jsonRepository.findOne(update.getId());
		old.setObject(update.getObject());
		jsonRepository.save(old);
		ChangeHandler.get().invoke("update", old);
	}
	
	public void allInserted() {
		Iterable<Json> all = jsonRepository.findAll();
//		System.out.println("Found " + all.size() + " elements");
		for (Json a : all) {
			ChangeHandler.get().invoke("insert_old", a);
		}
		ChangeHandler.get().invoke("insert_stop", null);
	}
}
