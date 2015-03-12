package no.connectdb.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.connectdb.model.Json;

import com.mongodb.DB;

public class ChangeHandler {

	private final static ChangeHandler CHANGE_HANDLER = new ChangeHandler();
	public static ChangeHandler get() {
		return CHANGE_HANDLER;
	}
	
	private Map<String, List<ConnectDbListener>> listeners = new HashMap<>();
	
	private ChangeHandler() {}
	
	public void registerListener(String event, ConnectDbListener listener) {
		List<ConnectDbListener> listenersTypeList = listeners.get(event);
		if (listenersTypeList == null) {
			listenersTypeList = new ArrayList<>();
			listeners.put(event, listenersTypeList);
		}
		listenersTypeList.add(listener);
	}
	
	public void invoke(String type, Json change) {
		List<ConnectDbListener> list = listeners.get(type);
		if (list != null) {
			for (ConnectDbListener connectDbListener : list) {
				connectDbListener.change(change);
			}
		}
	}
	
}
