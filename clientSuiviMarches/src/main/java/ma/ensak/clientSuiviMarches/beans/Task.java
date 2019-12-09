package ma.ensak.clientSuiviMarches.beans;

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
public class Task {

 
    private Long id;
    private Date finalDate;
    private Long duration;
    private String state;
    private long projectId;

}
