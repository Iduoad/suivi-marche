package ma.ensak.clientSuiviMarches.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import ma.ensak.clientSuiviMarches.beans.Employee;
import ma.ensak.clientSuiviMarches.beans.Service;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceEmployeeProxy;
import ma.ensak.clientSuiviMarches.roles.Roles;



@Controller
public class EmployeeController {



	@Autowired
	private MicroserviceEmployeeProxy microSEProxy;
	
	@RequestMapping(value="addOrUpdateEmployeeRedirect")
	public String addOrUpdateEmployee(HttpServletRequest request , Model model ,@RequestParam(defaultValue="")String message ,@RequestParam(defaultValue="") String idd  , Employee employee)
	{
		if(!Roles.isALL(request) && Roles.isFonctionnaire(request))  return "redirect:/authentification";
		
		model.addAttribute("message", "".equals(message) ? "" : message );
		model.addAttribute("Sectiontitle", "Employees");
		model.addAttribute("services", microSEProxy.getAllServices());
		model.addAttribute("employee", "".equals(idd) ? employee == null ? new Employee() : employee : microSEProxy.getEmployeeById(Long.parseLong(idd)));
		return "employee/addOrUpdateEmployee";
	}
	
	@RequestMapping(value="addOrUpdateEmployee", method=RequestMethod.POST)
	public ModelAndView addOrUpdateService( HttpServletRequest request  ,RedirectAttributes redirectAttributes, @RequestParam Map<String, String> params)
	{
		ModelAndView modelAndView = null ; 
		
		if(!Roles.isALL(request) && Roles.isFonctionnaire(request))  {
			modelAndView = new ModelAndView("redirect:/authentification");
			return modelAndView;
		}
		
		Employee employee=new Employee();
		
		if(!params.get("id").toString().equals("")) employee.setId(Long.parseLong(params.get("id")));
		employee.setNom(params.get("nom"));
		employee.setPrenom(params.get("prenom"));
		employee.setEmail(params.get("email"));
		employee.setJob(params.get("job"));
		employee.setPassword(params.get("password"));
		if(params.get("service") != null) employee.setService(microSEProxy.getServiceById(Long.parseLong(params.get("service"))).get());

		Long id = employee.getId();
		
		if(id ==null )
		{
			microSEProxy.createEmployee(employee);
			modelAndView = new ModelAndView("redirect:/getEmployees" , "message" ,  "Employee ajouté avec succès" );
		}else
		{
			microSEProxy.updateEmployee(employee, employee.getId());
			modelAndView = new ModelAndView("redirect:/getEmployees" , "message" ,"Employee modifié avec succès"  );
		}
		return modelAndView;
	}
	
	@RequestMapping(value="deleteEmployee")
	public ModelAndView deleteEmployee( HttpServletRequest request , @RequestParam(defaultValue="")String idd)
	{
		if(!Roles.isALL(request) && Roles.isFonctionnaire(request))  {
			return new ModelAndView("redirect:/authentification");
			 
		}
		
		microSEProxy.deleteEmployee(Long.parseLong(idd));;;
		return new ModelAndView("redirect:/getEmployees","message","Suppréssion réussie");
	}
	
	@RequestMapping(value="getEmployees")
	public String getServices( HttpServletRequest request , Model model,@RequestParam(defaultValue="") String message)
	{
		
		if(!Roles.isALL(request) && Roles.isFonctionnaire(request))  return "redirect:/authentification";
		
		model.addAttribute("message", "".equals(message) ? "" : message );
		model.addAttribute("employees", microSEProxy.getAllEmployees());
		model.addAttribute("Sectiontitle", "Employees");
		return "employee/getEmployees";
	}
	
	@RequestMapping(value="ProfileRedirect" , method=RequestMethod.GET)
	public String profileRedirect( HttpServletRequest request, Model model )
	{
		if(!Roles.isALL(request)) return "redirect:/authentification";
		
		
		Employee userSession = (Employee) request.getSession(false).getAttribute("userSession");
			
		userSession.setPassword(null);
		model.addAttribute("Sectiontitle", "Profile");
		model.addAttribute("employee", userSession);
		return "employee/profile";
	}
	
	@RequestMapping(value="UpdateProfile" , method=RequestMethod.POST)
	public ModelAndView updateProfile( HttpServletRequest request, Model model , @RequestParam Map<String, String> params , RedirectAttributes redirectAttributes)
	{
		if(Roles.isALL(request)) return new ModelAndView("redirect:/authentification");
		ModelAndView modelAndView = null ;
        Employee employee=microSEProxy.getEmployeeById(Long.parseLong(params.get("id"))).get();
		
		employee.setEmail(params.get("email"));
		employee.setPassword(params.get("password"));
		
		if (microSEProxy.createEmployee(employee)!=null)
			{
				modelAndView = new ModelAndView("redirect:ProfileRedirect", "message","Profile modifé");
				HttpSession session = request.getSession(false);
				 session.setAttribute("userSession", employee);
			}
		else
		{
			redirectAttributes.addFlashAttribute("employee",employee);
			modelAndView = new ModelAndView("redirect:ProfileRedirect", "message","Login existe déjà");
		}
		
		return modelAndView;
	}
}
