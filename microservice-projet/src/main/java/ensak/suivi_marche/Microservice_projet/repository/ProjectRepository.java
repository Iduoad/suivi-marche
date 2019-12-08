package ensak.suivi_marche.Microservice_projet.repository;

import ensak.suivi_marche.Microservice_projet.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import ensak.suivi_marche.Microservice_projet.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
