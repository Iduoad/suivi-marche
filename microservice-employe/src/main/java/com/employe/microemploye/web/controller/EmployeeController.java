package com.employe.microemploye.web.controller;

import com.employe.microemploye.dao.EmployeeDao;
import com.employe.microemploye.model.Employee;
import com.employe.microemploye.web.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao ;

    @RequestMapping(value="/employees", method= RequestMethod.GET)
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }


    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    public Optional<Employee> getEmployeeById(@PathVariable int id) throws EmployeeNotFoundException {

        Optional<Employee> employee = employeeDao.findById(id);

        if(employee==null) throw new EmployeeNotFoundException("There is no employee with id = " + id);

        return employee;
    }

    @RequestMapping(value = "/employees/service/{service_id}", method = RequestMethod.GET)
    public List<Employee> getEmployeesByService(@PathVariable int service_id) throws EmployeeNotFoundException {
        List<Employee> employees ;
        employees = employeeDao.getEmployeesByService_id(service_id);

        if(employees.size() == 0) throw new EmployeeNotFoundException("There is no employees in service with id = " + service_id);

        return employees;
    }


    @RequestMapping(value = "/employees/job/{job_title}", method = RequestMethod.GET)
    public List<Employee> getEmployeesByJob(@PathVariable String job_title) throws EmployeeNotFoundException {
        List<Employee> employees ;
        employees = employeeDao.getAllEmployeesByJob(job_title);

        if(employees.size() == 0) throw new EmployeeNotFoundException("There is no employees wtih " + job_title + " job title !");

        return employees;
    }

}
