package no.connectdb.model;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Document(indexName="json")
public class Json {

	@Id
	private Long id;
	
	private String type;
	
	@JsonSerialize(converter = ConvertJson.class)
	private JSONObject object = new JSONObject();

	public Json() {
	}


	public Json(String type, JSONObject object) {
		this.type = type;
		this.object = object;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JSONObject getObject() {
		return object;
	}

	public void setObject(JSONObject object) {
		this.object = object;
	}

	public String getType() {
		return type;
	}
	
}
