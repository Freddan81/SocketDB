package no.connectdb.handlers;

public class ListenerInstance {

	private String event;
	
	public ListenerInstance(String event) {
		this.event = event;
	}

	public void listen(ConnectDbListener listener) {
		ChangeHandler.get().registerListener(event, listener);
	}
}
