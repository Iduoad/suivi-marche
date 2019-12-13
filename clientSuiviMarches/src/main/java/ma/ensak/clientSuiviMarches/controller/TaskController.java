package ma.ensak.clientSuiviMarches.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
import ma.ensak.clientSuiviMarches.beans.Task;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceProjetProxy;

@Controller
public class TaskController {

	@Autowired
	private MicroserviceProjetProxy microSProjetProxy;

	@RequestMapping(value = "addOrUpdateTaskRedirect")
	public String addOrUpdateTask(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "") String message, @RequestParam(defaultValue = "") String idd,
			Task task) {

		model.addAttribute("message", "".equals(message) ? "" : message);
		model.addAttribute("Sectiontitle", "Projets");
		model.addAttribute("projects", microSProjetProxy.getAllProjects());
		model.addAttribute("task", "".equals(idd) ? task == null ? new Task() : task
				: microSProjetProxy.getTask(Long.parseLong(idd)));
		return "task/addOrUpdateTask";
	}

	@RequestMapping(value = "addOrUpdateTask", method = RequestMethod.POST)
	public ModelAndView addOrUpdateTask(HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam Map<String, String> params) throws ParseException {

		
Task task=new Task();
		
		if(!params.get("id").toString().equals("")) task.setId(Long.parseLong(params.get("id")));
		task.setLabel(params.get("label"));;
		task.setDescription(params.get("description"));
		task.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(params.get("endDate")));
		task.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(params.get("startDate")));
		task.setProject(microSProjetProxy.getProject(Long.parseLong(params.get("project"))).get());
		
		Long id = task.getId();
		ModelAndView modelAndView = null;
		if (id != null) {
			microSProjetProxy.updateTask(id, task);
			modelAndView = new ModelAndView("redirect:/getTasks", "message", "Tâche modifié avec succès");
		}

		else {
			microSProjetProxy.createTask(task);
			modelAndView = new ModelAndView("redirect:/getTasks", "message", "Tâche ajouté avec succès");

		}

		return modelAndView;
	}

	@RequestMapping(value = "deleteTask")
	public ModelAndView deleteTask(HttpServletRequest request, @RequestParam(defaultValue = "") String idd) {
		
		microSProjetProxy.deleteTask(Long.parseLong(idd));
		return new ModelAndView("redirect:/getTasks", "message", "Suppréssion réussie");
	}

	@RequestMapping(value = "getTasks")
	public String getProjects(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "") String message) {
		
		model.addAttribute("Sectiontitle", "Projets");
		model.addAttribute("message", "".equals(message) ? "" : message);
		model.addAttribute("tasks", microSProjetProxy.getAllTasks());
		return "task/getTasks";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
        binder.registerCustomEditor(Date.class, editor);
    }
 
}
