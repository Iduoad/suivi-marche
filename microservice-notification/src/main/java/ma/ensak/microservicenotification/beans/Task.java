package ma.ensak.microservicenotification.beans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;



@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

 
    private Long id;
    private String label;
    private String description;
    private Date startDate;   
    private Date endDate;
    private Long duration;
    private String status;
    private Project project;
    private List<Comment> comments;
    private List<Vote> votes;

}
