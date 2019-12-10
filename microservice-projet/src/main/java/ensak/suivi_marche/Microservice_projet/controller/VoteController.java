package ensak.suivi_marche.Microservice_projet.controller;

import ensak.suivi_marche.Microservice_projet.exception.ResourceNotFoundException;
import ensak.suivi_marche.Microservice_projet.model.Comment;
import ensak.suivi_marche.Microservice_projet.model.Vote;
import ensak.suivi_marche.Microservice_projet.repository.TaskRepository;
import ensak.suivi_marche.Microservice_projet.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ensak.suivi_marche.Microservice_projet.exception.ResourceNotFoundException;
import ensak.suivi_marche.Microservice_projet.model.Comment;
import ensak.suivi_marche.Microservice_projet.model.Vote;
import ensak.suivi_marche.Microservice_projet.repository.TaskRepository;
import ensak.suivi_marche.Microservice_projet.repository.VoteRepository;

import javax.validation.Valid;

@RestController
public class VoteController {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks/{task_id}/votes")
    public Page<Vote> getAllVotesByTaskId(@PathVariable (value = "task_id") Long taskId,
                                                Pageable pageable) {
        return voteRepository.findByTaskId(taskId, pageable);
    }

    @PostMapping("/tasks/{task_id}/votes")
    public Vote createVote(@PathVariable (value = "task_id") Long taskId,
                           @Valid @RequestBody Vote vote) {
        return taskRepository.findById(taskId).map(task -> {
            vote.setTask(task);
            return voteRepository.save(vote);
        }).orElseThrow(() -> new ResourceNotFoundException("TaskId " + taskId + " not found"));
    }
    @PutMapping("/tasks/{task_id}/votes/{vote_id}")
    public Vote updateVote(@PathVariable (value = "task_id") Long taskId,
                                 @PathVariable (value = "vote_id") Long voteId,
                                 @Valid @RequestBody Comment voteRequest) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("TaskId " + taskId + " not found");
        }

        return voteRepository.findById(voteId).map(comment -> {
            comment.setStatus(voteRequest.getStatus());
            return voteRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId "+ voteId + " not Found"));
    }

    @DeleteMapping("/tasks/{taskId}/votes/{vote_id}")
    public ResponseEntity<?> deleteVote(@PathVariable (value = "task_id") Long taskId,
                                           @PathVariable (value = "vote_id") Long voteId) {
        return voteRepository.findByIdAndTaskId(voteId, taskId).map(vote -> {
            voteRepository.delete(vote);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("VoteId "+ voteId + " not found"));
    }
}