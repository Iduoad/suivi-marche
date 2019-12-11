package com.employe.microemploye.dao;

import com.employe.microemploye.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {

   public List<Employee> getEmployeesByService_id(Long service_id);

   public List<Employee> getAllEmployeesByJob(String job_title);

}
