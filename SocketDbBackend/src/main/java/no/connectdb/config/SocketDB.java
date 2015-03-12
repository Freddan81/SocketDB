package no.connectdb.config;
import java.io.IOException;
import java.util.Base64;

import no.connectdb.handlers.ListenerHandler;
import no.connectdb.model.Json;

import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

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
    
    @RequestMapping("/upload")
    @ResponseStatus(value = HttpStatus.OK)
    public void upload(@RequestParam(value = "file", required = false) MultipartFile upload){
    	try {
    		System.out.println("Saving file");
    		JSONObject object = new JSONObject();
    		object.append("file", Base64.getEncoder().encodeToString(upload.getBytes()));
        	handler.save(new Json("img", object));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		listener.on("insert").listen((change)->{
			System.out.println("Sending insert message: " + change.getObject());
			template.convertAndSend("/topic/insert/" + change.getType(), change.getObject().append("id", change.getId()).toString());
		});
		listener.on("insert_old").listen((change)->{
			System.out.println("Sending insert old message: " + change.getObject());
			template.convertAndSend("/topic/insert/old/" + change.getType(), change.getObject().append("id", change.getId()).toString());
		});
		listener.on("insert_stop").listen((change)->{
			System.out.println("Sending insert old stop message");
			template.convertAndSend("/topic/insert/stop", "");
		});
		listener.on("update").listen((change)->{
			System.out.println("Sending update message: " + change.getObject());
			template.convertAndSend("/topic/update/" + change.getType(), change.getObject().append("id", change.getId()).toString());
		});		
	}

}