package ensak.suivi_marche.Microservice_projet.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "objective")
    private String objective;

    @Column(name = "required_hardware")
    private String requiredHardware;

    @Column(name = "required_software")
    private String requiredSoftware;

    @Column(name = "software_licences")
    private String softwareLicences;

    @NotNull
    @Column(name = "duration")
    private Long duration;

    @Column(name = "budget")
    private Double budget;

    @NotNull
    @Column(name = "state")
    private String state;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "service_id")
    private Long serviceId;

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getRequiredHardware() {
        return requiredHardware;
    }

    public void setRequiredHardware(String requiredHardware) {
        this.requiredHardware = requiredHardware;
    }

    public String getRequiredSoftware() {
        return requiredSoftware;
    }

    public void setRequiredSoftware(String requiredSoftware) {
        this.requiredSoftware = requiredSoftware;
    }

    public String getSoftwareLicences() {
        return softwareLicences;
    }

    public void setSoftwareLicences(String softwareLicences) {
        this.softwareLicences = softwareLicences;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
