package com.spring.data.db.one.to.n.repositories;

import com.spring.data.db.one.to.n.entities.Company;
import com.spring.data.db.one.to.n.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("select e from Employee e where e.company.id=:id")
    List<Employee> findEmployeesByCompanyId(@Param("id") Long id);
}
