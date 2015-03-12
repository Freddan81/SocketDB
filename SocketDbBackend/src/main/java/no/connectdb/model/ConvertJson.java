package no.connectdb.model;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.util.StdConverter;

public class ConvertJson extends StdConverter<JSONObject, String>{

	@Override
	public String convert(JSONObject value) {
		return value.toString();
	}

}
