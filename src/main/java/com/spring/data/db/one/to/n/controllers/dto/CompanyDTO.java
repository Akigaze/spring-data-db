package com.spring.data.db.one.to.n.controllers.dto;



import com.spring.data.db.one.to.n.entities.Company;
import com.spring.data.db.one.to.n.entities.Employee;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyDTO implements Serializable {
    private Long id;
    private String name;
    private ZonedDateTime createdDate=ZonedDateTime.now();
    private List<EmployeeDTO> employees;

    public CompanyDTO() {
    }

    public CompanyDTO(Company company){
        this.id=company.getId();
        this.name=company.getName();
        this.createdDate=company.getCreatedDate();
        this.employees=company.getEmployees().stream().map(employee -> new EmployeeDTO(employee)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }
}
