package ma.ensak.clientSuiviMarches.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ma.ensak.clientSuiviMarches.roles.Roles;

@Controller
public class AccueilController {

	@RequestMapping(value="accueilRedirect")
	public String acceuilRedirect( HttpServletRequest request ,Model model )
	{
		if(!Roles.isALL(request))  return "redirect:/authentification";
		model.addAttribute("Sectiontitle", "Accueil");
		return "accueil/accueil";
	}
	
	@RequestMapping(value = "accueil")
	public String accueil(HttpServletRequest request,Model model) {
	
		if(!Roles.isALL(request))  return "redirect:/authentification";
		model.addAttribute("Sectiontitle", "Accueil");
		return "accueil/accueil";
	}

}
