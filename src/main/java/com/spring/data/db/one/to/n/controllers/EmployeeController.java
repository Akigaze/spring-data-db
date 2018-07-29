package com.spring.data.db.one.to.n.controllers;

import com.spring.data.db.one.to.n.controllers.dto.EmployeeDTO;
import com.spring.data.db.one.to.n.entities.Company;
import com.spring.data.db.one.to.n.entities.Employee;
import com.spring.data.db.one.to.n.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<EmployeeDTO> findAll(@Param("page") Integer page,@Param("size") Integer size){
        if (page!=null&&size!=null){
            Page<Employee> all = employeeRepository.findAll(PageRequest.of(page-1, size));
            return all.getContent().stream().map(employee -> new EmployeeDTO(employee)).collect(Collectors.toList());
        }else {
            List<Employee> emps = employeeRepository.findAll();
            List<EmployeeDTO> dtos = new ArrayList<>();
            for (Employee e : emps) {
                dtos.add(new EmployeeDTO(e));
            }
            return dtos;
        }
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDTO> findById(@PathVariable("id") Long id){
        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()){
            return new ResponseEntity<EmployeeDTO>(new EmployeeDTO(byId.get()),HttpStatus.OK);
        }
        return new ResponseEntity<EmployeeDTO>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDTO> deleteOne(@PathVariable("id") Long id){
        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()){
            employeeRepository.deleteById(id);
            return new ResponseEntity<EmployeeDTO>(new EmployeeDTO(byId.get()),HttpStatus.OK);
        }else {
            return new ResponseEntity<EmployeeDTO>(HttpStatus.NOT_FOUND);
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

    @GetMapping(path = "/male",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDTO> findMale(){
        Optional<Employee> byGender = employeeRepository.findByGender("male");
        if (byGender.isPresent()){
            return new ResponseEntity<EmployeeDTO>(new EmployeeDTO(byGender.get()),HttpStatus.OK);
        }
        return new ResponseEntity<EmployeeDTO>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/female",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDTO> findFemale(){
        Optional<Employee> byGender = employeeRepository.findByGender("female");
        if (byGender.isPresent()){
            return new ResponseEntity<EmployeeDTO>(new EmployeeDTO(byGender.get()),HttpStatus.OK);
        }
        return new ResponseEntity<EmployeeDTO>(HttpStatus.BAD_REQUEST);
    }
}

