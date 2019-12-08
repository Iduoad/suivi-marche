package com.employe.microemploye.dao;

import com.employe.microemploye.model.Employee;
import com.employe.microemploye.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceDao extends JpaRepository<Service, Integer> {

}

