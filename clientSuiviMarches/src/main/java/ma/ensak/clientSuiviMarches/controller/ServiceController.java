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

import ma.ensak.clientSuiviMarches.beans.Service;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceEmployeeProxy;



@Controller
public class ServiceController {



	@Autowired
	private MicroserviceEmployeeProxy microSEProxy;
	
	@RequestMapping(value="addOrUpdateServiceRedirect")
	public String addOrUpdateService(HttpServletRequest request , Model model ,@RequestParam(defaultValue="")String message ,@RequestParam(defaultValue="") String idd  , Service service)
	{
		
		model.addAttribute("message", "".equals(message) ? "" : message );
		model.addAttribute("service", "".equals(idd) ? service == null ? new Service() : service : microSEProxy.getServiceById(Long.parseLong(idd)));
		return "service/addOrUpdateService";
	}
	
	@RequestMapping(value="addOrUpdateService", method=RequestMethod.POST)
	public ModelAndView addOrUpdateService( HttpServletRequest request  ,RedirectAttributes redirectAttributes, Service service)
	{
		
		Long id = service.getId();
		ModelAndView modelAndView = null ;
		if(id ==null )
		{
			microSEProxy.createService(service);
			modelAndView = new ModelAndView("redirect:/getServices" , "message" ,  "Paragraphe ajouté avec succès" );
		}else
		{
			microSEProxy.updateService(service, service.getId());
			modelAndView = new ModelAndView("redirect:/getParagraphes" , "message" ,"Paragraphe modifié avec succès"  );
		}
	
		return modelAndView;
	}
	
	@RequestMapping(value="deleteService")
	public ModelAndView deleteService( HttpServletRequest request , @RequestParam(defaultValue="")String idd)
	{
		
		microSEProxy.deleteService(Long.parseLong(idd));;
		return new ModelAndView("redirect:/getServices","message","Suppréssion réussie");
	}
	
	@RequestMapping(value="getServices")
	public String getServices( HttpServletRequest request , Model model,@RequestParam(defaultValue="") String message)
	{
		
		model.addAttribute("message", "".equals(message) ? "" : message );
		model.addAttribute("services", microSEProxy.getAllServices());
		return "service/getServices";
	}
}
