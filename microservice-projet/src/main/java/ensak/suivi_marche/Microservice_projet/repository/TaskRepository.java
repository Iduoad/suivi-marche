package ensak.suivi_marche.Microservice_projet.repository;

import ensak.suivi_marche.Microservice_projet.model.Comment;
import ensak.suivi_marche.Microservice_projet.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import ensak.suivi_marche.Microservice_projet.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    Optional<Task> findByIdAndProjectId(Long id, Long projectId);
}