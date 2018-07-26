package com.spring.data.db.one.to.n.controllers;

import com.spring.data.db.one.to.n.entities.Company;
import com.spring.data.db.one.to.n.entities.Employee;
import com.spring.data.db.one.to.n.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> findById(@PathVariable("id") Long id){
        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()){
            return new ResponseEntity<Employee>(byId.get(),HttpStatus.OK);
        }
        return new ResponseEntity<Employee>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> deleteOne(@PathVariable("id") Long id){
        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()){
            employeeRepository.deleteById(id);
            return new ResponseEntity<Employee>(byId.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Employee employee,@PathVariable("id") Long id){
        employee.setId(id);
        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()){
            employeeRepository.save(employee);
            return new ResponseEntity(HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
