package com.spring.data.db.one.to.n.controllers;

import com.spring.data.db.one.to.n.entities.Employee;
import com.spring.data.db.one.to.n.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/jap/v2/employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    public EmployeeController() {
    }
    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee save(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }
}
