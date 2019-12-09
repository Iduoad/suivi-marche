package ma.ensak.clientSuiviMarches.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ma.ensak.clientSuiviMarches.beans.Comment;
import ma.ensak.clientSuiviMarches.beans.Project;
import ma.ensak.clientSuiviMarches.beans.Task;
import ma.ensak.clientSuiviMarches.beans.Vote;

import java.util.Optional;

import javax.validation.Valid;

@FeignClient(name = "microservice-projet", url = "localhost:9991")
public interface MicroserviceProjetProxy {

	//Comment
	
	 /*@GetMapping("/task/{task_id}/comments")
	    public Page<Comment> getAllCommentsByTaskId(@PathVariable (value = "task_id") Long taskId,
	                                                     Pageable pageable);*/

	    @PostMapping("/tasks/{task_id}/comments")
	    public Comment createComment(@PathVariable (value = "task_id") Long taskId,
	                                     @Valid @RequestBody Comment comment);

	    @PutMapping("/tasks/{task_id}/comments/{comment_id}")
	    public Comment updateComment(@PathVariable (value = "task_id") Long taskId,
	                                     @PathVariable (value = "comment_id") Long commentId,
	                                     @Valid @RequestBody Comment commentRequest) ;

	    @DeleteMapping("/tasks/{taskId}/comments/{comment_id}")
	    public ResponseEntity<?> deleteComment(@PathVariable (value = "task_id") Long taskId,
	                                            @PathVariable (value = "comment_id") Long commentId) ;
	
	
	
	//projet
	
	    @GetMapping("/projects/{project_id}")
	    public Optional<Project> getProject(@PathVariable (value = "project_id") Long projectId);

	    @PostMapping("/projects")
	    public Project createProject(@Valid @RequestBody Project project) ;

	    @PutMapping("/projects/{project_id}")
	    public Project updateProject(@PathVariable (value = "project_id") Long projectId,
	                                 @Valid @RequestBody Project projectRequest) ;

	    @DeleteMapping("/projects/{project_id}")
	    public ResponseEntity<?> deleteProject(@PathVariable (value = "project_id") Long projectId) ;
	    
	//Tasks
	    
	   /* @GetMapping("/tasks")
	    public Page<Task> getAlltasks(Pageable pageable);*/

	    @PostMapping("/tasks")
	    public Task createTask(@Valid @RequestBody Task task) ;

	    @PutMapping("/tasks/{task_id}")
	    public Task updateTask(@PathVariable (value = "task_id") Long taskId, @Valid @RequestBody Task taskRequest) ;

	    @DeleteMapping("/tasks/{task_id}")
	    public ResponseEntity<?> deleteTask(@PathVariable (value = "task_id") Long taskId) ;
	    
	//Vote
	    
	   /* @GetMapping("/tasks/{task_id}/votes")
	    public Page<Vote> getAllVotesByTaskId(@PathVariable (value = "task_id") Long taskId,
	                                                Pageable pageable) ;*/

	    @PostMapping("/tasks/{task_id}/votes")
	    public Vote createVote(@PathVariable (value = "task_id") Long taskId,
	                           @Valid @RequestBody Vote vote) ;
	    @PutMapping("/tasks/{task_id}/votes/{vote_id}")
	    public Vote updateVote(@PathVariable (value = "task_id") Long taskId,
	                                 @PathVariable (value = "vote_id") Long voteId,
	                                 @Valid @RequestBody Comment voteRequest);

	    @DeleteMapping("/tasks/{taskId}/votes/{vote_id}")
	    public ResponseEntity<?> deleteVote(@PathVariable (value = "task_id") Long taskId,
	                                           @PathVariable (value = "vote_id") Long voteId) ;

}