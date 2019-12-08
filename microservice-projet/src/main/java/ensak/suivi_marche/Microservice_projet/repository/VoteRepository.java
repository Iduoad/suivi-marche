package ensak.suivi_marche.Microservice_projet.repository;

import ensak.suivi_marche.Microservice_projet.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ensak.suivi_marche.Microservice_projet.model.Vote;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Page<Vote> findByTaskId(Long taskId, Pageable pageable);
    Optional<Vote> findByIdAndTaskId(Long id, Long taskId);

}
