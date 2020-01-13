package ma.ensak.clientSuiviMarches.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ma.ensak.clientSuiviMarches.beans.Notification;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceEmployeeProxy;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceNotificationProxy;
import ma.ensak.clientSuiviMarches.proxies.MicroserviceProjetProxy;
import ma.ensak.clientSuiviMarches.roles.Roles;

@Controller
public class NotificationController {
	
	@Autowired 
	private MicroserviceNotificationProxy microSNProxy;
	
	@Autowired 
	private MicroserviceEmployeeProxy microSEProxy;
	
	@Autowired 
	private MicroserviceProjetProxy microSPProxy;
	
	@RequestMapping(value = "/getDetailsNotification")
	public String getDtailsNotification(HttpServletRequest request,Model model,@RequestParam(defaultValue = "") String idd) {
		if(!Roles.isALL(request)) return "redirect:/authentification"; 
		
		microSNProxy.markNotifAsRead(Long.parseLong(idd));
		Notification notification=microSNProxy.getNotificationById(Long.parseLong(idd));
		model.addAttribute("Sectiontitle", "Notifications");
		model.addAttribute("notification", notification);
		model.addAttribute("employee", microSEProxy.getEmployeeById(notification.getEmployeeId()).get());
		model.addAttribute("objectiveOfProject", microSPProxy.getProject(notification.getProjectId()).get().getObjective());
		model.addAttribute("descriptionOfProject", microSPProxy.getProject(notification.getProjectId()).get().getDescription());
		
		return "notification/getDetailsNotification";
	}

	@RequestMapping(value = "getNotifications")
	public String getNotifications(HttpServletRequest request,Model model) {
	
		if(!Roles.isALL(request)) return "redirect:/authentification";
		
		model.addAttribute("Sectiontitle", "Notifications");
		return "notification/getNotifications";
	}
}
