package ensak.suivi_marche.Microservice_projet.repository;

import ensak.suivi_marche.Microservice_projet.model.Project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findByServiceId(Long serviceId);
	List<Project> findByEmployeeId(Long employeeId);
	List<Project> findByServiceIdAndEmployeeId(Long serviceId,Long employeeId);
}
