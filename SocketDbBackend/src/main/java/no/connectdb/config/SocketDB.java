package no.connectdb.config;
import no.connectdb.model.Draw;
import no.connectdb.model.Json;
import no.socketdb.listener.handlers.ListenerHandler;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketDB {
	
	
	@Autowired
	private SimpMessagingTemplate template;
	
	private static ListenerHandler listener;
	
	@Autowired
	private CommandRepository handler;
	
    @MessageMapping("/connect")
    @SendTo("/topic/connected")
    public String connect(){
    	if (listener == null) {
	    	listener = new ListenerHandler();
	    	listener.on("insert").listen((change)->{
	    		template.convertAndSend("/topic/insert/" + change.getType(), change.getObject().toString());
	    	});
	      	listener.on("insert_old").listen((change)->{
	    		template.convertAndSend("/topic/insert/old", change.getObject().toString());
	    	});
	      	listener.on("insert_stop").listen((change)->{
	    		template.convertAndSend("/topic/insert/stop", "");
	    	});
    	}
    	handler.allInserted();
    	return "";
    }
    
    @MessageMapping("/save")
    public void save(String save){
    	JSONObject object = new JSONObject(save);
    	handler.save(new Json(object.getString("type"), object));
    }
}