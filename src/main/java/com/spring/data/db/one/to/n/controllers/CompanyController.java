package com.spring.data.db.one.to.n.controllers;


import com.spring.data.db.one.to.n.controllers.dto.CompanyDTO;
import com.spring.data.db.one.to.n.controllers.dto.EmployeeDTO;
import com.spring.data.db.one.to.n.entities.Company;
import com.spring.data.db.one.to.n.entities.Employee;
import com.spring.data.db.one.to.n.repositories.CompanyRepository;
import com.spring.data.db.one.to.n.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanyDTO save(@RequestBody Company company) {
        Company newer = companyRepository.save(company);
        newer.getEmployees().stream().forEach(emp -> emp.setCompany(newer));
        return new CompanyDTO(newer);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompanyDTO> findAll(@Param("page") Integer page,@Param("size") Integer size) {
        if (page!=null&&size!=null){
            Page<Company> all = companyRepository.findAll(PageRequest.of(page-1, size));
            return all.getContent().stream().map(company -> new CompanyDTO(company)).collect(Collectors.toList());
        }else {
            return companyRepository.findAll().stream().map(company -> new CompanyDTO(company)).collect(Collectors.toList());
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDTO> findById(@PathVariable("id") Long id) {
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()) {
            return new ResponseEntity<CompanyDTO>(new CompanyDTO(byId.get()), HttpStatus.OK);
        }
        return new ResponseEntity<CompanyDTO>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDTO> deleteOne(@PathVariable("id") Long id) {
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()) {
            Company company = byId.get();
            companyRepository.deleteById(id);
            company.getEmployees().forEach(employee -> employee.setCompany(null));
            return new ResponseEntity<CompanyDTO>(new CompanyDTO(byId.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<CompanyDTO>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Company company, @PathVariable("id") Long id) {
        company.setId(id);
        Optional<Company> byId = companyRepository.findById(id);
        if (byId.isPresent()) {
            companyRepository.save(company);
            company.getEmployees().forEach(employee -> {
                if (employee.getCompany() == null) {
                    employee.setCompany(company);
                }
            });
            return new ResponseEntity<Company>(company, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(path = "/{id}/employees",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@PathVariable("id") Long id){
        List<EmployeeDTO> employees= companyRepository.findEmployeesByCompanyId(id).stream().map(employee -> new EmployeeDTO(employee)).collect(Collectors.toList());
        return new ResponseEntity<List<EmployeeDTO>>(employees,HttpStatus.OK);
    }
}
