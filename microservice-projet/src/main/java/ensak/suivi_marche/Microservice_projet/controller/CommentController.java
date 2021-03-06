package ensak.suivi_marche.Microservice_projet.controller;


import ensak.suivi_marche.Microservice_projet.model.Comment;
import ensak.suivi_marche.Microservice_projet.exception.ResourceNotFoundException;
import ensak.suivi_marche.Microservice_projet.repository.CommentRepository;
import ensak.suivi_marche.Microservice_projet.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ensak.suivi_marche.Microservice_projet.exception.ResourceNotFoundException;
import ensak.suivi_marche.Microservice_projet.model.Comment;
import ensak.suivi_marche.Microservice_projet.repository.CommentRepository;
import ensak.suivi_marche.Microservice_projet.repository.TaskRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/task/{task_id}/comments")
    public List<Comment> getAllCommentsByTaskId(@PathVariable (value = "task_id") Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }

    @PostMapping("/tasks/{task_id}/comments")
    public Comment createComment(@PathVariable (value = "task_id") Long taskId,
                                     @Valid @RequestBody Comment comment) {
        return taskRepository.findById(taskId).map(task -> {
            comment.setTask(task);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " not found"));
    }

    @PutMapping("/tasks/{task_id}/comments/{comment_id}")
    public Comment updateComment(@PathVariable (value = "task_id") Long taskId,
                                     @PathVariable (value = "comment_id") Long commentId,
                                     @Valid @RequestBody Comment commentRequest) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("TaskId " + taskId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setContent(commentRequest.getContent());
            comment.setStatus(commentRequest.getStatus());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId "+ commentId + " not Found"));
    }

    @DeleteMapping("/tasks/{taskId}/comments/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "task_id") Long taskId,
                                            @PathVariable (value = "comment_id") Long commentId) {
        return commentRepository.findByIdAndTaskId(commentId, taskId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId "+ commentId + " not found"));
    }
}
