package com.spring.data.db.one.to.n.controllers.dto;


import com.spring.data.db.one.to.n.entities.Employee;

import java.io.Serializable;


public class EmployeeDTO implements Serializable {
    private final Long id;
    private final String name;
    private final Long companyID;

    public EmployeeDTO(Employee employee){
        this.id=employee.getId();
        this.name=employee.getName();
        if (employee.getCompany()!=null) {
            this.companyID = employee.getCompany().getId();
        }else {
            this.companyID = null;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCompanyID() {
        return companyID;
    }
}
