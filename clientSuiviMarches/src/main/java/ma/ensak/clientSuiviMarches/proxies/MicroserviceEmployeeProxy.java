package ma.ensak.clientSuiviMarches.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import ma.ensak.clientSuiviMarches.beans.Employee;
import ma.ensak.clientSuiviMarches.beans.Service;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "microservice-employe", url = "localhost:9990")
public interface MicroserviceEmployeeProxy {

	//Employee
	
	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public List<Employee> getAllEmployees();

	@RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
	public Optional<Employee> getEmployeeById(@PathVariable int id);

	@RequestMapping(value = "/employees/service/{service_id}", method = RequestMethod.GET)
	public List<Employee> getEmployeesByService(@PathVariable int service_id);

	@RequestMapping(value = "/employees/job/{job_title}", method = RequestMethod.GET)
	public List<Employee> getEmployeesByJob(@PathVariable String job_title);
	
	//Service
	
    @RequestMapping(value="/services", method= RequestMethod.GET)
    public List<Service> getAllServices() ;

    @RequestMapping(value = "/services/{id}", method = RequestMethod.GET)
    public Optional<Service> getServiceById(@PathVariable int id);

}