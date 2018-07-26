package com.spring.data.db.one.to.n.repositories;

import com.spring.data.db.one.to.n.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
}
