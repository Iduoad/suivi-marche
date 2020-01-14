package ma.ensak.clientSuiviMarches.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ma.ensak.clientSuiviMarches.beans.Employee;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceEmployeeProxy;





@Controller
public class AuthentificationController {

	@Autowired
	private MicroserviceEmployeeProxy microSEProxy ;
	
	 @RequestMapping("authentificationRedirect")
	 public String authentification( HttpServletRequest request, Model model ,@RequestParam(defaultValue="") String message , Employee employee )
	 {
		 
		 model.addAttribute("employee", employee == null ? new Employee() : employee );
		 model.addAttribute("message",message);
		 return "authentification/authentification";
	 }
	 
	 @RequestMapping(value="authentification" )
	 public ModelAndView authentification (HttpServletRequest request,RedirectAttributes redirectAttributes , Employee employee )
	 {
		 ModelAndView modelAndView = null ;
		 Employee e = microSEProxy.authentification(employee);
		 if( e  == null )
		 {
			 redirectAttributes.addFlashAttribute("user", employee);
			 modelAndView = new ModelAndView("redirect:/authentificationRedirect","message","Veuillez vous connecter");
		 }
		 else
		 {
			 HttpSession session = request.getSession();
			 session.setAttribute("userSession", e);
			 modelAndView = new ModelAndView("redirect:/accueil");
		 }
		 
		 return modelAndView ;
	 }
	 
	 @RequestMapping(value="/logOut")
		public String logOut(   HttpServletRequest request)
		{

			if(request.getSession(false) == null ||   request.getSession(false).getAttribute("userSession") == null)  return "redirect:/authentification";
			HttpSession s = request.getSession(false) ;
			if(s != null)s.invalidate();
			return "redirect:/authentificationRedirect";
		}
	 
	 
}
