package com.spring.data.db.one.to.n.repositories;

import com.spring.data.db.one.to.n.entities.Employee;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @After
    public void tearDown() throws Exception {
        entityManager.clear();
    }

    @Test
    public void should_get_info_of_all_employees(){
        //give
        entityManager.persist(new Employee("Quinn","male"));
        entityManager.persist(new Employee("Hoho","female"));
        //when
        List<Employee> employees=repository.findAll();
        //then
        assertThat(employees.size(),is(2));
        assertThat(employees.get(0).getName(),is("Quinn"));
        assertThat(employees.get(0).getGender(),is("male"));
        assertThat(employees.get(1).getName(),is("Hoho"));
        assertThat(employees.get(1).getGender(),is("female"));
    }

    @Test
    public void should_get_info_of_specific_employee_when_give_an_id(){
        //give
        Long id=Long.valueOf(entityManager.persistAndGetId(new Employee("Quinn","male")).toString());
        entityManager.persistAndGetId(new Employee("Hoho","female"));
        //when
        Employee employee=repository.findById(id).get();
        //then

        assertThat(employee.getName(),is("Quinn"));
        assertThat(employee.getGender(),is("male"));
    }
}
