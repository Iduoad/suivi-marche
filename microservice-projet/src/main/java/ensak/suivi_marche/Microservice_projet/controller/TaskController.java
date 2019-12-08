package ensak.suivi_marche.Microservice_projet.controller;

import ensak.suivi_marche.Microservice_projet.model.Task;
import ensak.suivi_marche.Microservice_projet.exception.ResourceNotFoundException;
import ensak.suivi_marche.Microservice_projet.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ensak.suivi_marche.Microservice_projet.exception.ResourceNotFoundException;
import ensak.suivi_marche.Microservice_projet.model.Task;
import ensak.suivi_marche.Microservice_projet.repository.TaskRepository;

import javax.validation.Valid;

@RestController
public class    TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public Page<Task> getAlltasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PutMapping("/tasks/{task_id}")
    public Task updateTask(@PathVariable (value = "task_id") Long taskId, @Valid @RequestBody Task taskRequest) {
        return taskRepository.findById(taskId).map(task -> {
            task.setFinalDate(taskRequest.getFinalDate());
            task.setDuration(taskRequest.getDuration());
            task.setState(taskRequest.getState());
            task.setProjectId(taskRequest.getProjectId());
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
