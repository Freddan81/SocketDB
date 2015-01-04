package no.socketdb.listener.handlers;

import no.connectdb.model.Json;

public interface ConnectDbListener {

	void change(Json value);
}
