package ma.ensak.clientSuiviMarches.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import ma.ensak.clientSuiviMarches.beans.Form;
import ma.ensak.clientSuiviMarches.beans.Task;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceProjetProxy;
import ma.ensak.clientSuiviMarches.roles.Roles;

@Controller
public class TaskController {

	@Autowired
	private MicroserviceProjetProxy microSProjetProxy;

	@RequestMapping(value = "addOrUpdateTaskRedirect")
	public String addOrUpdateTask(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "") String message, @RequestParam(defaultValue = "") String idd, Task task) {

		if (!Roles.isALL(request))
			return "redirect:/authentification";

		model.addAttribute("message", "".equals(message) ? "" : message);
		model.addAttribute("Sectiontitle", "Projets");
		model.addAttribute("projects", microSProjetProxy.getAllProjects());
		model.addAttribute("task",
				"".equals(idd) ? task == null ? new Task() : task : microSProjetProxy.getTask(Long.parseLong(idd)));
		return "task/addOrUpdateTask";
	}

	@RequestMapping(value = "addOrUpdateTask", method = RequestMethod.POST)
	public ModelAndView addOrUpdateTask(HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam Map<String, String> params) throws ParseException {

		if (!Roles.isALL(request))
			return new ModelAndView("redirect:/authentification");

		Task task = new Task();

		if (!params.get("id").toString().equals(""))
			task.setId(Long.parseLong(params.get("id")));

		task.setLabel(params.get("label"));
		;
		task.setDescription(params.get("description"));
		task.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(params.get("endDate")));
		task.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(params.get("startDate")));
		task.setProject(microSProjetProxy.getProject(Long.parseLong(params.get("project"))).get());

		Long id = task.getId();
		ModelAndView modelAndView = null;
		if (id != null) {
			microSProjetProxy.updateTask(id, task);
			modelAndView = new ModelAndView("redirect:/getTasksRedirect", "message", "Tâche modifié avec succès");
		}

		else {
			microSProjetProxy.createTask(task);
			modelAndView = new ModelAndView("redirect:/getTasksRedirect", "message", "Tâche ajouté avec succès");

		}

		return modelAndView;
	}

	@RequestMapping(value = "deleteTask")
	public ModelAndView deleteTask(HttpServletRequest request, @RequestParam(defaultValue = "") String idd) {
		if (!Roles.isALL(request))
			return new ModelAndView("redirect:/authentification");

		microSProjetProxy.deleteTask(Long.parseLong(idd));
		return new ModelAndView("redirect:/getTasksRedirect", "message", "Suppréssion réussie");
	}

	@RequestMapping(value = "getTasks")
	public String getTasks(HttpServletRequest request, Model model, @RequestParam(defaultValue = "") String message,
			Form form) {

		if (!Roles.isALL(request))
			return "redirect:/authentification";

		List<Task> tasks = new ArrayList<Task>();

		if (form.getProjectId() == -2) {
			tasks=this.SetStatusOfTasks(microSProjetProxy.getAllTasks());
		}else {
			tasks=this.SetStatusOfTasks(microSProjetProxy.getTasksByProjectId(form.getProjectId()));
		}

		model.addAttribute("Sectiontitle", "Tâches");
		model.addAttribute("message", "".equals(message) ? "" : message);
		model.addAttribute("projects", microSProjetProxy.getAllProjects());
		model.addAttribute("tasks", tasks);
		return "task/getTasks";
	}

	@RequestMapping(value = "getTasksRedirect")
	public String getTasksRedirect(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "") String message) {

		if (!Roles.isALL(request))
			return "redirect:/authentification";

		model.addAttribute("projects", microSProjetProxy.getAllProjects());
		model.addAttribute("Sectiontitle", "Tâches");
		model.addAttribute("message", "".equals(message) ? "" : message);
		model.addAttribute("tasks", null);
		model.addAttribute("form", new Form());
		return "task/getTasks";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	private LocalDate convertDateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	List<Task> SetStatusOfTasks(List<Task> tasksParam) {

		List<Task> tasks = tasksParam;
		LocalDate currentDate = this.convertDateToLocalDate(new Date());
		for (Task task : tasks) {
			LocalDate dateD = this.convertDateToLocalDate(task.getStartDate());
			LocalDate dateF = this.convertDateToLocalDate(task.getEndDate());
			if (currentDate.isBefore(dateD)) {
				task.setStatus("enAttente");
			}
			if ((currentDate.isAfter(dateD) || currentDate.equals(dateD)) && currentDate.isBefore(dateF)) {
				task.setStatus("enCours");
			}
			if (currentDate.isAfter(dateF)) {
				task.setStatus("validee");
			}
		}

		return tasks;
	}

}
