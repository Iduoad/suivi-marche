package ma.ensak.microservicenotification.beans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;




import java.util.Date;

@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {


    Long id;
    Date createdAt;
    String content;
    Long employeeId;
    String state;
    Task task;

    
}
