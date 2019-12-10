package ensak.suivi_marche.Microservice_projet.controller;

import ensak.suivi_marche.Microservice_projet.exception.ResourceNotFoundException;
import ensak.suivi_marche.Microservice_projet.model.Project;
import ensak.suivi_marche.Microservice_projet.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/projects")
    public Page<Project> getAllProjects(@RequestParam (value = "employee", defaultValue = "0") Long employeeId,
                                        @RequestParam (value = "service", defaultValue = "0") Long serviceId,
                                        Pageable pageable) {
        // find by employee and by service

            return projectRepository.findAll(pageable);

    }

    @GetMapping("/projects/{project_id}")
    public Optional<Project> getProject(@PathVariable (value = "project_id") Long projectId) {
        return projectRepository.findById(projectId);
    }

    @PostMapping("/projects")
    public Project createProject(@Valid @RequestBody Project project) {
        return projectRepository.save(project);
    }

    @PutMapping("/projects/{project_id}")
    public Project updateProject(@PathVariable (value = "project_id") Long projectId,
                                 @Valid @RequestBody Project projectRequest) {
        return projectRepository.findById(projectId).map(project -> {
            project.setObjective(projectRequest.getObjective());
            project.setDescription(projectRequest.getDescription());
            project.setRequiredHardware(projectRequest.getRequiredHardware());
            project.setRequiredSoftware(projectRequest.getRequiredSoftware());
            project.setSoftwareLicences(projectRequest.getSoftwareLicences());
            project.setBudget(projectRequest.getBudget());
            project.setDuration(projectRequest.getDuration());
            project.setStatus(projectRequest.getStatus());
            return projectRepository.save(project);
        }).orElseThrow(()-> new ResourceNotFoundException("Project " + projectId + "Not found"));
    }

    @DeleteMapping("/projects/{project_id}")
    public ResponseEntity<?> deleteProject(@PathVariable (value = "project_id") Long projectId) {
        return projectRepository.findById(projectId).map(project -> {
            projectRepository.delete(project);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Project " + projectId + " Not found"));
    }
}
