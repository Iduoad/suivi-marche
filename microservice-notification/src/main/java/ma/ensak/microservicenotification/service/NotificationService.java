package ma.ensak.microservicenotification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.ensak.microservicenotification.dao.NotificationDao;
import ma.ensak.microservicenotification.models.Notification;

@Service
public class NotificationService {
	
	@Autowired 
	private NotificationDao notificationDao;
	
	public Notification getNotificationById(Long id) {
		return notificationDao.findById(id).get();
		
	}
	
	public void markNotifAsRead(Notification notification) {
		notificationDao.save(notification);
		
	}
	
	public void addNotification(Notification notification) {
		notificationDao.save(notification);
		
	}
	
	public List<Notification> getNotifications(){
		return notificationDao.findAll();
	}

	
	public List<Notification> getNotReadNotifs(){
		return notificationDao.getNotReadNotifs();
	}
	
	
	public List<Notification> getReadNotifs(){
		return notificationDao.getReadNotifs();
	}
	

	public List<Notification> getNotReadNotifsByEmployee(Long employeeId){
		return notificationDao.getNotReadNotifsByEmployee(employeeId);
	}
}
