package ensak.suivi_marche.Microservice_projet.repository;

import ensak.suivi_marche.Microservice_projet.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import ensak.suivi_marche.Microservice_projet.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}