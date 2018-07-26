package com.spring.data.db.one.to.n.controllers;


import com.spring.data.db.one.to.n.controllers.dto.CompanyDTO;
import com.spring.data.db.one.to.n.entities.Company;
import com.spring.data.db.one.to.n.entities.Employee;
import com.spring.data.db.one.to.n.repositories.CompanyRepository;
import com.spring.data.db.one.to.n.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jap/v1/companies")
public class CompanyController {

    private CompanyRepository companyRepository;

    public CompanyController() {
    }
    @Autowired
    public CompanyController(CompanyRepository repository) {
        this.companyRepository = repository;
    }

    @PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public Company save(@RequestBody Company company){
        Company newer=companyRepository.save(company);
        newer.getEmployees().stream().forEach(emp->emp.setCompany(newer));
        return newer;
    }

    @GetMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Company> findAll(){
        return companyRepository.findAll();
    }

    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> findById(@PathVariable("id") Long id){
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()){
            return new ResponseEntity<Company>(byId.get(),HttpStatus.OK);
        }
        return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> deleteOne(@PathVariable("id") Long id){
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()){
            Company company=byId.get();
            companyRepository.deleteById(id);
            company.getEmployees().forEach(employee -> employee.setCompany(null));
            return new ResponseEntity<Company>(company,HttpStatus.OK);
        }else {
            return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
        }
    }
}
