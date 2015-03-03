package no.connectdb.config;
import no.connectdb.model.Json;
import no.socketdb.listener.handlers.ListenerHandler;

import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketDB implements InitializingBean {
	
	
	@Autowired
	private SimpMessagingTemplate template;
	
	private static ListenerHandler listener = new ListenerHandler();
	
	@Autowired
	private CommandRepository handler;
	
    @MessageMapping("/connect")
    @SendTo("/topic/connected")
    public String connect(){
		handler.allInserted();
    	return "";
    }
    
    @MessageMapping("/save")
    public void save(String save){
    	System.out.println("SAVE:" + save);
    	JSONObject object = new JSONObject(save);
    	handler.save(new Json(object.getString("type"), object));
    }
    
    @MessageMapping("/update")
    public void update(String update){
    	JSONObject object = new JSONObject(update);
    	System.out.println("UPDATE:" + update + " ID: " + object.getLong("id"));
    	Json json = new Json(object.getString("type"), object);
    	json.setId(object.getLong("id"));
    	object.remove("id");
		handler.update(json);
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		listener.on("insert").listen((change)->{
			System.out.println("Sending insert message: " + change);
			template.convertAndSend("/topic/insert/" + change.getType(), change.getObject().append("id", change.getId()).toString());
		});
		listener.on("insert_old").listen((change)->{
			System.out.println("Sending insert old message: " + change);
			template.convertAndSend("/topic/insert/old/" + change.getType(), change.getObject().append("id", change.getId()).toString());
		});
		listener.on("insert_stop").listen((change)->{
			System.out.println("Sending insert old stop message");
			template.convertAndSend("/topic/insert/stop", "");
		});
		listener.on("update").listen((change)->{
			System.out.println("Sending update message: " + change);
			template.convertAndSend("/topic/update/" + change.getType(), change.getObject().append("id", change.getId()).toString());
		});		
	}

}