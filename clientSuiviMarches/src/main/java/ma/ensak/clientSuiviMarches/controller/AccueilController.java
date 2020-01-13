package ma.ensak.clientSuiviMarches.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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

import ma.ensak.clientSuiviMarches.beans.EtatAllProject;
import ma.ensak.clientSuiviMarches.beans.EtatProject;
import ma.ensak.clientSuiviMarches.beans.NbreDeProjetsNonValideParMois;
import ma.ensak.clientSuiviMarches.beans.NbreDeProjetsValideParMois;
import ma.ensak.clientSuiviMarches.beans.Project;
import ma.ensak.clientSuiviMarches.beans.Task;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceProjetProxy;
import ma.ensak.clientSuiviMarches.roles.Roles;

@Controller
public class AccueilController {
	
	@Autowired
	MicroserviceProjetProxy microSPProxy;

	@RequestMapping(value="accueilRedirect")
	public String acceuilRedirect( HttpServletRequest request ,Model model )
	{
		if(!Roles.isALL(request))  return "redirect:/authentification";
		model.addAttribute("Sectiontitle", "Accueil");
		model.addAttribute("etatAllProject", this.getEtatOfAllProject());
		model.addAttribute("nbreDeProjetsValideParMois", this.getProjetsValideParMois());
		model.addAttribute("nbreDeProjetsNonValideParMois", this.getProjetsNonValideParMois());
		return "accueil/accueil";
	}
	
	@RequestMapping(value = "accueil")
	public String accueil(HttpServletRequest request,Model model) {
	
		if(!Roles.isALL(request))  return "redirect:/authentification";
		model.addAttribute("Sectiontitle", "Accueil");
		model.addAttribute("etatAllProject", this.getEtatOfAllProject());
		model.addAttribute("nbreDeProjetsValideParMois", this.getProjetsValideParMois());
		model.addAttribute("nbreDeProjetsNonValideParMois", this.getProjetsNonValideParMois());
		return "accueil/accueil";
	}
	
	
	EtatAllProject getEtatOfAllProject(){
		EtatAllProject etatAllProject=new EtatAllProject();
		List<Project> projects=this.setStatusOfProject();
		for (Project project : projects) {
			if (project.getStatus().equals("enAttente")) {
				etatAllProject.setNbreEnAttente(etatAllProject.getNbreEnAttente()+1);
			}
			if (project.getStatus().equals("enCours")) {
				etatAllProject.setNbreEnCours(etatAllProject.getNbreEnCours()+1);
			}
			if (project.getStatus().equals("valide")) {
				etatAllProject.setNbreValide(etatAllProject.getNbreValide()+1);
			}
			if (project.getStatus().equals("nonValide")) {
				etatAllProject.setNbreNonValide(etatAllProject.getNbreNonValide()+1);
			}
		}
		return etatAllProject;
		
	}
	NbreDeProjetsValideParMois getProjetsValideParMois(){
		NbreDeProjetsValideParMois nbreDeProjetsValideParMois=new NbreDeProjetsValideParMois();
		List<Project> projects=this.setStatusOfProject();
		for (Project project : projects) {
			if (project.getDateValidateOrNot()!=null && project.getStatus().equals("valide")) {
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==1) {
					nbreDeProjetsValideParMois.setJan(nbreDeProjetsValideParMois.getJan()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==2) {
					nbreDeProjetsValideParMois.setFev(nbreDeProjetsValideParMois.getFev()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==3) {
					nbreDeProjetsValideParMois.setMar(nbreDeProjetsValideParMois.getMar()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==4) {
					nbreDeProjetsValideParMois.setAvr(nbreDeProjetsValideParMois.getAvr()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==5) {
					nbreDeProjetsValideParMois.setMai(nbreDeProjetsValideParMois.getMai()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==6) {
					nbreDeProjetsValideParMois.setJun(nbreDeProjetsValideParMois.getJun()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==7) {
					nbreDeProjetsValideParMois.setJul(nbreDeProjetsValideParMois.getJul()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==8) {
					nbreDeProjetsValideParMois.setAou(nbreDeProjetsValideParMois.getAou()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==9) {
					nbreDeProjetsValideParMois.setSep(nbreDeProjetsValideParMois.getSep()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==10) {
					nbreDeProjetsValideParMois.setOct(nbreDeProjetsValideParMois.getOct()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==11) {
					nbreDeProjetsValideParMois.setNov(nbreDeProjetsValideParMois.getNov()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==12) {
					nbreDeProjetsValideParMois.setDec(nbreDeProjetsValideParMois.getDec()+1);
				}
			}
		}
		return nbreDeProjetsValideParMois;
	}
	
	NbreDeProjetsNonValideParMois getProjetsNonValideParMois(){
		NbreDeProjetsNonValideParMois nbreDeProjetsNonValideParMois=new NbreDeProjetsNonValideParMois();
		List<Project> projects=this.setStatusOfProject();
		for (Project project : projects) {
			if (project.getDateValidateOrNot()!=null && project.getStatus().equals("nonValide")) {
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==1) {
					nbreDeProjetsNonValideParMois.setJan(nbreDeProjetsNonValideParMois.getJan()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==2) {
					nbreDeProjetsNonValideParMois.setFev(nbreDeProjetsNonValideParMois.getFev()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==3) {
					nbreDeProjetsNonValideParMois.setMar(nbreDeProjetsNonValideParMois.getMar()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==4) {
					nbreDeProjetsNonValideParMois.setAvr(nbreDeProjetsNonValideParMois.getAvr()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==5) {
					nbreDeProjetsNonValideParMois.setMai(nbreDeProjetsNonValideParMois.getMai()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==6) {
					nbreDeProjetsNonValideParMois.setJun(nbreDeProjetsNonValideParMois.getJun()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==7) {
					nbreDeProjetsNonValideParMois.setJul(nbreDeProjetsNonValideParMois.getJul()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==8) {
					nbreDeProjetsNonValideParMois.setAou(nbreDeProjetsNonValideParMois.getAou()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==9) {
					nbreDeProjetsNonValideParMois.setSep(nbreDeProjetsNonValideParMois.getSep()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==10) {
					nbreDeProjetsNonValideParMois.setOct(nbreDeProjetsNonValideParMois.getOct()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==11) {
					nbreDeProjetsNonValideParMois.setNov(nbreDeProjetsNonValideParMois.getNov()+1);
				}
				if (this.convertDateToLocalDate(project.getDateValidateOrNot()).getMonthValue()==12) {
					nbreDeProjetsNonValideParMois.setDec(nbreDeProjetsNonValideParMois.getDec()+1);
				}
			}
		}
		return nbreDeProjetsNonValideParMois;
	}
	
	List<Project> setStatusOfProject() {
		this.setStatusOfTasks();
		List<Project> projects = microSPProxy.getAllProjects();
		for (Project project : projects) {
			//
			EtatProject etatProject = new EtatProject();
			for (Task task : microSPProxy.getTasksByProjectId(project.getId())) {
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
			if (etatProject.getNbreEnAttente()==microSPProxy.getTasksByProjectId(project.getId()).size() && etatProject.getNbreEnCours()==0) {
				project.setStatus("enAttente");
			}
			if (etatProject.getNbreEnCours()>0) {
				project.setStatus("enCours");
			}
			if (etatProject.getNbreValide()==microSPProxy.getTasksByProjectId(project.getId()).size() && microSPProxy.getTasksByProjectId(project.getId()).size()>0 ) {
				project.setStatus("valide");
			}
			if (etatProject.getNbrenNonValide()>0) {
				project.setStatus("nonValide");
			}
			
			/*System.out.println("projet "+project.getId()+" en attente "+etatProject.getNbreEnAttente());
			System.out.println("projet "+project.getId()+" en cours "+etatProject.getNbreEnCours());
			System.out.println("projet "+project.getId()+" valide "+etatProject.getNbreValide());
			System.out.println("projet "+project.getId()+" non valide "+etatProject.getNbrenNonValide());*/
			
			microSPProxy.createProject(project);
		}
		return projects;
	}
	
	List<Task> setStatusOfTasks(){
	
			
			List<Task> tasks=microSPProxy.getAllTasks();
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
				microSPProxy.createTask(task);
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
