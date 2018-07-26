package com.spring.data.db.one.to.n.controllers;


import com.spring.data.db.one.to.n.controllers.dto.CompanyDTO;
import com.spring.data.db.one.to.n.entities.Company;
import com.spring.data.db.one.to.n.repositories.CompanyRepository;
import com.spring.data.db.one.to.n.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private CompanyRepository repository;

    public CompanyController() {
    }
    @Autowired
    public CompanyController(CompanyRepository repository) {
        this.repository = repository;
    }
}
