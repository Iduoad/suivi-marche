package ma.ensak.clientSuiviMarches.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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



@Controller
public class EmployeeController {



	@Autowired
	private MicroserviceEmployeeProxy microSEProxy;
	
	@RequestMapping(value="addOrUpdateEmployeeRedirect")
	public String addOrUpdateEmployee(HttpServletRequest request , Model model ,@RequestParam(defaultValue="")String message ,@RequestParam(defaultValue="") String idd  , Employee employee)
	{
		
		model.addAttribute("message", "".equals(message) ? "" : message );
		model.addAttribute("Sectiontitle", "Employees");
		model.addAttribute("services", microSEProxy.getAllServices());
		model.addAttribute("employee", "".equals(idd) ? employee == null ? new Employee() : employee : microSEProxy.getEmployeeById(Long.parseLong(idd)));
		return "employee/addOrUpdateEmployee";
	}
	
	@RequestMapping(value="addOrUpdateEmployee", method=RequestMethod.POST)
	public ModelAndView addOrUpdateService( HttpServletRequest request  ,RedirectAttributes redirectAttributes, @RequestParam Map<String, String> params)
	{
		Employee employee=new Employee();
		
		if(!params.get("id").toString().equals("")) employee.setId(Long.parseLong(params.get("id")));
		employee.setNom(params.get("nom"));
		employee.setPrenom(params.get("prenom"));
		employee.setEmail(params.get("email"));
		employee.setJob(params.get("job"));
		employee.setPassword(params.get("password"));
		if(params.get("service") != null) employee.setService(microSEProxy.getServiceById(Long.parseLong(params.get("service"))).get());

		Long id = employee.getId();
		ModelAndView modelAndView = null ;
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
		
		microSEProxy.deleteEmployee(Long.parseLong(idd));;;
		return new ModelAndView("redirect:/getEmployees","message","Suppréssion réussie");
	}
	
	@RequestMapping(value="getEmployees")
	public String getServices( HttpServletRequest request , Model model,@RequestParam(defaultValue="") String message)
	{
		
		model.addAttribute("message", "".equals(message) ? "" : message );
		model.addAttribute("employees", microSEProxy.getAllEmployees());
		model.addAttribute("Sectiontitle", "Employees");
		return "employee/getEmployees";
	}
}
