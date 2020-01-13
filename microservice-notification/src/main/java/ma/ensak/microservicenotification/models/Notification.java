package ma.ensak.microservicenotification.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
@Entity
public class Notification {

	@Id
	@GeneratedValue
	Long id;
	String message;
	Date createdAt;
	boolean isRead;
	Long employeeId;
	Long projectId;
	Long taskId;
}
