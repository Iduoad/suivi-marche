package ensak.marche.repository;

import ensak.marche.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Page<Vote> findByTaskId(Long taskId, Pageable pageable);
    Optional<Vote> findByIdAndTaskId(Long id, Long taskId);

}
