package com.employe.microemploye.web.controller;

import com.employe.microemploye.dao.ServiceDao;
import com.employe.microemploye.model.Service;
import com.employe.microemploye.web.exceptions.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ServiceController {

    @Autowired
    private ServiceDao serviceDao ;

    @RequestMapping(value="/services", method= RequestMethod.GET)
    public List<Service> getAllServices() {
        return serviceDao.findAll();
    }


    @RequestMapping(value = "/services/{id}", method = RequestMethod.GET)
    public Optional<Service> getServiceById(@PathVariable int id) throws ServiceNotFoundException {

        Optional<Service> service = serviceDao.findById(id);

        if(service==null) throw new ServiceNotFoundException("There is no service with id = " + id);

        return service;
    }
}
