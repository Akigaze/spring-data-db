package com.spring.data.db.one.to.n.repositories;

import com.spring.data.db.one.to.n.entities.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_return_info_of_all_employees(){
       Employee employee1=entityManager.persistFlushFind(new Employee("Mark"));
       List<Employee> employees=new ArrayList<>();
       employees.add(employee1);
       List<Employee> employeeList=employeeRepository.findAll();
       assertThat(employeeList,is(employees));
    }

    @Test
    public void should_return_info_of_specific_employee_when_give_id(){
        Employee employee1=entityManager.persistFlushFind(new Employee("Mark"));
        Employee employee = employeeRepository.findById(employee1.getId()).get();
        assertThat(employee,is(employee1));
    }

    @Test
    public void should_return_info_of_specific_employee_when_give_name(){
        Employee employee1=entityManager.persistFlushFind(new Employee("Mark"));
        Employee employee2=entityManager.persistFlushFind(new Employee("Amy"));

        Employee employee = employeeRepository.findByName("Mark").get();
        assertThat(employee,is(employee1));
        assertThat(employee,not(employee2));

    }
}
