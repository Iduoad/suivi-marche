package ensak.suivi_marche.Microservice_projet.repository;

import ensak.suivi_marche.Microservice_projet.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ensak.suivi_marche.Microservice_projet.model.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByTaskId(Long taskId, Pageable pageable);
    Optional<Comment> findByIdAndTaskId(Long id, Long taskId);

}
