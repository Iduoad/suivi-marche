package ensak.suivi_marche.Microservice_projet.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter @Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;


    @Column(name = "start_date")
    private Date startDate;

    
    @Column(name = "end_date")
    private Date endDate;

    
    @Column(name = "duration")
    private Long duration;

    
    //@Column(name = "status",columnDefinition = "varchar(255) default 'A'")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

    @OneToMany()
    private List<Comment> comments;

    @OneToMany()
    private List<Vote> votes;
    
    @PrePersist
    void preInsert() {
       if ( this.getStatus() == null ) { this.setStatus("A");; }
    }
}
