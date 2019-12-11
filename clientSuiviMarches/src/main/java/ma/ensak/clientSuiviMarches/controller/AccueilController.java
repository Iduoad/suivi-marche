package ma.ensak.clientSuiviMarches.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ma.ensak.clientSuiviMarches.proxies.MicroserviceEmployeeProxy;

@Controller
public class AccueilController {

	@Autowired
	private MicroserviceEmployeeProxy microSEP;
	
	@GetMapping(value = "accueil")
	public String accueil(Model model) {
		//model.addAttribute("employees", microSEP.getAllEmployees());
		return "accueil/accueil";
		//return "project/addOrUpdateProject";
		//return "task/addOrUpdateTask";
		//return "project/getProjects";
		//return "project/getDetailsProject";
	}
}
