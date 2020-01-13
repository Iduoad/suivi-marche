package ma.ensak.microservicenotification.beans;

import java.util.List;


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
public class Project {

   
    Long id;
    String objective;
    String description;
    String requiredHardware;
    String requiredSoftware;
    String softwareLicences;
    Long duration;
    Double budget;
    String status;
    Long employeeId;
    Long serviceId;
    List<Task> tasks;

}
