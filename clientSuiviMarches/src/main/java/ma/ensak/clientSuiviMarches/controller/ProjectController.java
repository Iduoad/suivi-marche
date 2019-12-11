package ma.ensak.clientSuiviMarches.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ma.ensak.clientSuiviMarches.beans.Project;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceProjetProxy;

@Controller
public class ProjectController {

	@Autowired
	private MicroserviceProjetProxy microSProjetProxy;

	@RequestMapping(value = "addOrUpdateProjectRedirect")
	public String addOrUpdateProject(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "") String message, @RequestParam(defaultValue = "") String idd,
			Project project) {

		model.addAttribute("message", "".equals(message) ? "" : message);
		model.addAttribute("Sectiontitle", "Projets");
		model.addAttribute("project", "".equals(idd) ? project == null ? new Project() : project
				: microSProjetProxy.getProject(Long.parseLong(idd)));
		return "project/addOrUpdateProject";
	}
	
	@RequestMapping(value = "getDetailsProject")
	public String getDetailsProject(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "") String message, @RequestParam(defaultValue = "") String idd) {

		model.addAttribute("message", "".equals(message) ? "" : message);
		model.addAttribute("Sectiontitle", "Projets");
		model.addAttribute("project", microSProjetProxy.getProject(Long.parseLong(idd)));
		return "project/getDetailsProject";
	}

	@RequestMapping(value = "addOrUpdateProject", method = RequestMethod.POST)
	public ModelAndView addOrUpdateProject(HttpServletRequest request, RedirectAttributes redirectAttributes,
			Project project) {

		Long id = project.getId();
		ModelAndView modelAndView = null;
		if (id != null) {
			microSProjetProxy.updateProject(id, project);
			modelAndView = new ModelAndView("redirect:/getProjects", "message", "Projet modifié avec succès");
		}

		else {
			microSProjetProxy.createProject(project);
			modelAndView = new ModelAndView("redirect:/getProjects", "message", "Projet ajouté avec succès");

		}

		return modelAndView;
	}

	@RequestMapping(value = "deleteProject")
	public ModelAndView deleteProject(HttpServletRequest request, @RequestParam(defaultValue = "") String idd) {
		
		microSProjetProxy.deleteProject(Long.parseLong(idd));
		return new ModelAndView("redirect:/getProjects", "message", "Suppréssion réussie");
	}

	@RequestMapping(value = "getProjects")
	public String getProjects(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "") String message) {
		
		model.addAttribute("Sectiontitle", "Projets");
		model.addAttribute("message", "".equals(message) ? "" : message);
		model.addAttribute("projects", microSProjetProxy.getAllProjects());
		return "project/getProjects";
	}

}
