package ensak.suivi_marche.Microservice_projet.controller;

import ensak.suivi_marche.Microservice_projet.model.Task;
import ensak.suivi_marche.Microservice_projet.exception.ResourceNotFoundException;
import ensak.suivi_marche.Microservice_projet.repository.ProjectRepository;
import ensak.suivi_marche.Microservice_projet.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
public class TaskController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public Page<Task> getAllTasks(@RequestParam(required = true, value = "project") Long projectId ,
                                  Pageable pageable) {
        return projectRepository.findById(projectId).map(project -> {
            return taskRepository.findByProjectId(projectId, pageable);
        }).orElseThrow(() -> new ResourceNotFoundException("Project " + projectId + " Not Found" ));
    }

    @GetMapping("/tasks/{task_id}")
    public Optional<Task> getTask(@PathVariable (value = "task_id") Long taskId) {
        return taskRepository.findById(taskId);
    }
    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PutMapping("/tasks/{task_id}")
    public Task updateTask(@PathVariable (value = "task_id") Long taskId, @Valid @RequestBody Task taskRequest) {
        return taskRepository.findById(taskId).map(task -> {
            task.setLabel(taskRequest.getLabel());
            task.setDescription(taskRequest.getDescription());
            task.setStartDate(taskRequest.getStartDate());
            task.setEndDate(taskRequest.getEndDate());
            task.setDuration(taskRequest.getDuration());
            task.setStatus(taskRequest.getStatus());
            return taskRepository.save(task);
        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " Not found"));
    }

    @DeleteMapping("/tasks/{task_id}")
    public ResponseEntity<?> deleteTask(@PathVariable (value = "task_id") Long taskId) {
        return taskRepository.findById(taskId).map(task -> {
            taskRepository.delete(task);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " Not found"));
    }
}
