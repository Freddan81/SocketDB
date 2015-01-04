package no.socketdb.listener.handlers;

public class ListenerHandler {

	public ListenerInstance on(String event) {
		return new ListenerInstance(event);
	}
}
