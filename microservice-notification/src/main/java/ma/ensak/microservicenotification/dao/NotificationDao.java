package ma.ensak.microservicenotification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.ensak.microservicenotification.models.Notification;

@Repository
public interface NotificationDao extends JpaRepository<Notification, Long> {

	Notification  findByEmployeeId(Long employeeId);;

	@Query("from Notification where employeeId=:employeeId ORDER BY createdAt DESC")
	List<Notification> userNotification(@Param("employeeId") Long employeeId);
	
	@Query("from Notification where isRead=0 ")
	List<Notification> getNotReadNotifs();
	
	@Query("from Notification where isRead=1 ")
	List<Notification> getReadNotifs();
	
	@Query("from Notification where isRead=0 and employeeId=:employeeId ORDER BY createdAt ASC ")
	List<Notification> getNotReadNotifsByEmployee(@Param("employeeId") Long employeeId);

	Notification findByEmployeeIdAndId(Long employeeId,Long id);
}
