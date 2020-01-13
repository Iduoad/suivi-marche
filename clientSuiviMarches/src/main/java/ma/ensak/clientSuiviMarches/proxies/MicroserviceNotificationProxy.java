package ma.ensak.clientSuiviMarches.proxies;

import java.io.IOException;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ma.ensak.clientSuiviMarches.beans.Notification;



@FeignClient(name = "microservice-notification", url = "localhost:9993")
public interface MicroserviceNotificationProxy {

	
	
	
	@GetMapping(value = "/notifications/{id}")
	public Notification getNotificationById(@PathVariable long id);
	
	@GetMapping(value = "/ReadableNotifications/{id}")
	public void markNotifAsRead(@PathVariable long id) ;
	
	@PostMapping("/notifications")
    public void addNotification( @RequestBody Notification notification);
	
	@GetMapping(value = "/NotReadableNotifications")
	public List<Notification> notReadableNotifications() ;
}