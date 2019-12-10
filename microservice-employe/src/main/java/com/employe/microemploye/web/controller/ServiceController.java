package com.employe.microemploye.web.controller;

import com.employe.microemploye.dao.ServiceDao;
import com.employe.microemploye.model.Employee;
import com.employe.microemploye.model.Service;
import com.employe.microemploye.web.exceptions.ServiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public Optional<Service> getServiceById(@PathVariable Long id) throws ServiceNotFoundException {

        Optional<Service> service = serviceDao.findById(id);

        if(service==null) throw new ServiceNotFoundException("There is no service with id = " + id);

        return service;
    }


    @PostMapping("/services")
    public ResponseEntity<Object> createService(@RequestBody Service service) {
        Service savedService = serviceDao.save(service);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedService.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/services/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceDao.deleteById(id);
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<Object> updateService(@RequestBody Service service, @PathVariable Long id) {

        Optional<Service> studentOptional = serviceDao.findById(id);

        if (!studentOptional.isPresent())
            return ResponseEntity.notFound().build();

        service.setId(id);

        serviceDao.save(service);

        return ResponseEntity.noContent().build();
    }
}
