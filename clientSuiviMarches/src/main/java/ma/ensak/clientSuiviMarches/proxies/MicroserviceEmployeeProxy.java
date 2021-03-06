package ma.ensak.clientSuiviMarches.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ma.ensak.clientSuiviMarches.beans.Employee;
import ma.ensak.clientSuiviMarches.beans.Service;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "microservice-employe", url = "localhost:9990")
public interface MicroserviceEmployeeProxy {

	//Employee
	
	 @PostMapping(value="/employees/authentification")
	    public Employee authentification (Employee employee );
	
    @RequestMapping(value="/employees", method= RequestMethod.GET)
    public List<Employee> getAllEmployees();


    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    public Optional<Employee> getEmployeeById(@PathVariable Long id);

    @RequestMapping(value = "/employees/service/{service_id}", method = RequestMethod.GET)
    public List<Employee> getEmployeesByService(@PathVariable long service_id) ;


    @RequestMapping(value = "/employees/job/{job_title}", method = RequestMethod.GET)
    public List<Employee> getEmployeesByJob(@PathVariable String job_title) ;


    @PostMapping("/employees")
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee);

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) ;

    @PutMapping("/employees/{id}")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee, @PathVariable Long id) ;
	
	//Service
	
    @RequestMapping(value="/services", method= RequestMethod.GET)
    public List<Service> getAllServices() ;


    @RequestMapping(value = "/services/{id}", method = RequestMethod.GET)
    public Optional<Service> getServiceById(@PathVariable Long id);


    @PostMapping("/services")
    public ResponseEntity<Object> createService(@RequestBody Service service) ;

    @DeleteMapping("/services/{id}")
    public void deleteService(@PathVariable Long id) ;

    @PutMapping("/services/{id}")
    public ResponseEntity<Object> updateService(@RequestBody Service service, @PathVariable Long id) ;

}