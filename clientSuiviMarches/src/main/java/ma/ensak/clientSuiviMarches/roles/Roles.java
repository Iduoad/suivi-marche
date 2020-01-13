package ma.ensak.clientSuiviMarches.roles;

import javax.servlet.http.HttpServletRequest;

import ma.ensak.clientSuiviMarches.beans.Employee;



public class Roles {

	public static  boolean  isALL(HttpServletRequest request)
	{
		if(request.getSession(false) == null ||   request.getSession(false).getAttribute("userSession") == null) 
			return false;
		return true;
	}
	public static  boolean  isFonctionnaire(HttpServletRequest request)
	{
		
		if(request.getSession(false) == null ||   request.getSession(false).getAttribute("userSession") == null || 
				!((Employee) request.getSession(false).getAttribute("userSession")).getJob().toLowerCase().equals("f")) 
			return false;
		return true;
	}
	public static  boolean  isChefService(HttpServletRequest request)
	{
		
		if(request.getSession(false) == null ||   request.getSession(false).getAttribute("userSession") == null || 
				!((Employee) request.getSession(false).getAttribute("userSession")).getJob().toLowerCase().equals("c")) 
			return false;
		return true;
	}public static  boolean  isDirecteur(HttpServletRequest request)
	{
		
		if(request.getSession(false) == null ||   request.getSession(false).getAttribute("userSession") == null || 
				!((Employee) request.getSession(false).getAttribute("userSession")).getJob().toLowerCase().equals("d")) 
			return false;
		return true;
	}
	public static  boolean  isAdmin(HttpServletRequest request)
	{
		
		if(request.getSession(false) == null ||   request.getSession(false).getAttribute("userSession") == null || 
				!((Employee) request.getSession(false).getAttribute("userSession")).getJob().toLowerCase().equals("a")) 
			return false;
		return true;
	}
}