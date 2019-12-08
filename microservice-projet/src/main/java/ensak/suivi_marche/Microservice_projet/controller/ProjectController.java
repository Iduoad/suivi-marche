package ensak.marche.controller;

import ensak.marche.exception.ResourceNotFoundException;
import ensak.marche.model.Project;
import ensak.marche.repository.ProjectRepository;
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
    public Page<Project> getAllProjects(Pageable pageable) {
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
            project.setRequiredHardware(projectRequest.getRequiredHardware());
            project.setRequiredSoftware(projectRequest.getRequiredSoftware());
            project.setSoftwareLicences(projectRequest.getSoftwareLicences());
            project.setBudget(projectRequest.getBudget());
            project.setDuration(projectRequest.getDuration());
            project.setState(projectRequest.getState());
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
