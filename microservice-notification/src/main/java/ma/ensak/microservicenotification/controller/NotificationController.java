package ma.ensak.microservicenotification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ma.ensak.microservicenotification.models.Notification;
import ma.ensak.microservicenotification.service.NotificationService;

@RestController
public class NotificationController {

	
	@Autowired
	private NotificationService notificationService;
	
	
	@GetMapping(value = "/notifications/{id}")
	public Notification getNotificationById(@PathVariable long id) {
		return notificationService.getNotificationById(id) ;
	}
	
	@GetMapping(value = "/ReadableNotifications/{id}")
	public void markNotifAsRead(@PathVariable long id) {
		Notification notification=notificationService.getNotificationById(id) ;
		notification.setRead(true);
		notificationService.markNotifAsRead(notification);
	}
	
	@GetMapping(value = "/NotReadableNotifications")
	public List<Notification> notReadableNotifications() {
		return notificationService.getNotReadNotifs();
	}
	
	@PostMapping("/notifications")
    public void addNotification( @RequestBody Notification notification) {
        notificationService.addNotification(notification);;
    }
}
