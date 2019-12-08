package ma.ensak.clientSuiviMarches.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {

	@GetMapping(value = "accueil")
	public String accueil() {
		return "accueil/accueil";
	}
}
