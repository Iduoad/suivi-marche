package ma.ensak.clientSuiviMarches.beans;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {

	
	Long id;
	String message;
	Date createdAt;
	boolean isRead;
	Long employeeId;
	Long projectId;
	Long taskId;
	public Notification(String message, Date createdAt, boolean isRead, Long employeeId, Long projectId, Long taskId) {
		super();
		this.message = message;
		this.createdAt = createdAt;
		this.isRead = isRead;
		this.employeeId = employeeId;
		this.projectId = projectId;
		this.taskId = taskId;
	}
	
	
}
