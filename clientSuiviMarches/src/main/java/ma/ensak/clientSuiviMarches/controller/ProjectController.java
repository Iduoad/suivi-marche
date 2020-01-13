package ma.ensak.clientSuiviMarches.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ma.ensak.clientSuiviMarches.beans.Employee;
import ma.ensak.clientSuiviMarches.beans.EtatProject;
import ma.ensak.clientSuiviMarches.beans.Notification;
import ma.ensak.clientSuiviMarches.beans.Project;
import ma.ensak.clientSuiviMarches.beans.Task;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceNotificationProxy;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceProjetProxy;
import ma.ensak.clientSuiviMarches.roles.Roles;

@Controller
public class ProjectController {

	@Autowired
	private MicroserviceProjetProxy microSProjetProxy;

	@Autowired
	private MicroserviceNotificationProxy microSNProxy;

	@RequestMapping(value = "addOrUpdateProjectRedirect")
	public String addOrUpdateProject(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "") String message, @RequestParam(defaultValue = "") String idd,
			Project project) {

		if(!Roles.isALL(request)) return "redirect:/authentification";
		
		model.addAttribute("message", "".equals(message) ? "" : message);
		model.addAttribute("Sectiontitle", "Projets");
		model.addAttribute("project", "".equals(idd) ? project == null ? new Project() : project
				: microSProjetProxy.getProject(Long.parseLong(idd)));
		return "project/addOrUpdateProject";
	}

	@RequestMapping(value = "getDetailsProject")
	public String getDetailsProject(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "") String message, @RequestParam(defaultValue = "") String idd) {

		if(!Roles.isALL(request)) return "redirect:/authentification";
		
		model.addAttribute("message", "".equals(message) ? "" : message);
		model.addAttribute("Sectiontitle", "Projets");
		model.addAttribute("tasks", microSProjetProxy.getTasksByProjectId(Long.parseLong(idd)));
		model.addAttribute("project", microSProjetProxy.getProject(Long.parseLong(idd)));
		return "project/getDetailsProject";
	}

	@RequestMapping(value = "addOrUpdateProject", method = RequestMethod.POST)
	public ModelAndView addOrUpdateProject(HttpServletRequest request, RedirectAttributes redirectAttributes,
			Project project) {

		ModelAndView modelAndView = null;
		if(!Roles.isALL(request)) return new ModelAndView("redirect:/authentification");
		Long id = project.getId();
		Employee employee = (Employee) request.getSession(false).getAttribute("userSession");
		if (id != null) {

			Notification notification = new Notification(
					employee.getNom() + " " + employee.getPrenom() + " a modfie un projet", new Date(), false,
					employee.getId(), id, null);
			microSNProxy.addNotification(notification);
			microSProjetProxy.updateProject(id, project);
			modelAndView = new ModelAndView("redirect:/getProjects", "message", "Projet modifié avec succès");
		}

		else {
			Notification notification = new Notification(
					employee.getNom() + " " + employee.getPrenom() + " a ajoute un projet", new Date(), false,
					employee.getId(), id, null);
			microSNProxy.addNotification(notification);
			microSProjetProxy.createProject(project);
			modelAndView = new ModelAndView("redirect:/getProjects", "message", "Projet ajouté avec succès");

		}

		return modelAndView;
	}

	@RequestMapping(value = "deleteProject")
	public ModelAndView deleteProject(HttpServletRequest request, @RequestParam(defaultValue = "") String idd) {

		if(!Roles.isALL(request)) return new ModelAndView("redirect:/authentification");
		
		microSProjetProxy.deleteProject(Long.parseLong(idd));
		return new ModelAndView("redirect:/getProjects", "message", "Suppréssion réussie");
	}

	@RequestMapping(value = "getProjects")
	public String getProjects(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "") String message) {

		if (!Roles.isALL(request)) return "redirect:/authentification";

		model.addAttribute("Sectiontitle", "Projets");
		model.addAttribute("message", "".equals(message) ? "" : message);
		model.addAttribute("projects", this.setStatusOfProject());
		return "project/getProjects";
	}

	List<Project> setStatusOfProject() {
		this.setStatusOfTasks();
		List<Project> projects = microSProjetProxy.getAllProjects();
		for (Project project : projects) {
			//
			EtatProject etatProject = new EtatProject();
			for (Task task : microSProjetProxy.getTasksByProjectId(project.getId())) {
				if (task.getStatus().equals("enAttente")) {
					etatProject.setNbreEnAttente(etatProject.getNbreEnAttente()+1);
				}
				if (task.getStatus().equals("enCours")) {
					etatProject.setNbreEnCours(etatProject.getNbreEnCours()+1);
				}
				if (task.getStatus().equals("validee")) {
					etatProject.setNbreValide(etatProject.getNbreValide()+1);
				}
				if (task.getStatus().equals("nonValidee")) {
					etatProject.setNbrenNonValide(etatProject.getNbrenNonValide()+1);
				}
			}
			//
			if (etatProject.getNbreEnAttente()==microSProjetProxy.getTasksByProjectId(project.getId()).size() && etatProject.getNbreEnCours()==0) {
				project.setStatus("enAttente");
			}
			if (etatProject.getNbreEnCours()>0) {
				project.setStatus("enCours");
			}
			if (etatProject.getNbreValide()==microSProjetProxy.getTasksByProjectId(project.getId()).size() && microSProjetProxy.getTasksByProjectId(project.getId()).size()>0 ) {
				project.setStatus("valide");
			}
			if (etatProject.getNbrenNonValide()>0) {
				project.setStatus("nonValide");
			}
			
			/*System.out.println("projet "+project.getId()+" en attente "+etatProject.getNbreEnAttente());
			System.out.println("projet "+project.getId()+" en cours "+etatProject.getNbreEnCours());
			System.out.println("projet "+project.getId()+" valide "+etatProject.getNbreValide());
			System.out.println("projet "+project.getId()+" non valide "+etatProject.getNbrenNonValide());*/
			
			microSProjetProxy.createProject(project);
		}
		return projects;
	}
	
	List<Task> setStatusOfTasks(){
	
			
			List<Task> tasks=microSProjetProxy.getAllTasks();
			LocalDate currentDate=this.convertDateToLocalDate(new Date());
			for (Task task : tasks) {
				LocalDate dateD=this.convertDateToLocalDate(task.getStartDate());
				LocalDate dateF=this.convertDateToLocalDate(task.getEndDate());
				if (currentDate.isBefore(dateD)) {
					task.setStatus("enAttente");
				}
				if ((currentDate.isAfter(dateD) || currentDate.equals(dateD)) && currentDate.isBefore(dateF)) {
					task.setStatus("enCours");
				}
				if (currentDate.isAfter(dateF)) {
					task.setStatus("validee");
				}
				microSProjetProxy.createTask(task);
			}
			
			return tasks;
		
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
        binder.registerCustomEditor(Date.class, editor);
    }
	
	private LocalDate convertDateToLocalDate(Date date)
	{
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

}
