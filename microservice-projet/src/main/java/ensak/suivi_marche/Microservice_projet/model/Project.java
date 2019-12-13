package ensak.suivi_marche.Microservice_projet.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter 
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "objective")
    private String objective;

    @Column(name = "description")
    private String description;

    @Column(name = "required_hardware")
    private String requiredHardware;

    @Column(name = "required_software")
    private String requiredSoftware;

    @Column(name = "software_licences")
    private String softwareLicences;

  
    @Column(name = "duration")
    private Long duration;

    @Column(name = "budget")
    private Double budget;

    
    @Column(name = "status")
    private String status;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "service_id")
    private Long serviceId;

    @OneToMany()
    private List<Task> tasks;
    
    @PrePersist
    void preInsert() {
       if ( this.getStatus() == null ) { this.setStatus("V");; }
    }
}
