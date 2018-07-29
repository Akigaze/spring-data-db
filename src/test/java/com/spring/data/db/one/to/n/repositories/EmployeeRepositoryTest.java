package com.spring.data.db.one.to.n.repositories;

import com.spring.data.db.one.to.n.entities.Employee;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
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

    @Test
    public void should_save_a_given_employee(){
        //give
        Employee given=new Employee(10L,"Quinn","male");
        //when
        Employee saved=repository.save(given);
        //then
        assertThat(saved.getName(),is("Quinn"));
        assertThat(saved.getGender(),is("male"));
    }

    @Test
    public void should_update_info_of_given_employee(){
        //give
        Employee saved=entityManager.persistAndFlush(new Employee("Quinn","male"));
        Employee given=new Employee(saved.getId(),"Hoho","female");
        //when
        repository.save(given);
        //then
        assertThat(repository.findById(saved.getId()).get().getName(),is("Hoho"));
        assertThat(repository.findById(saved.getId()).get().getGender(),is("female"));
    }

    @Test
    public void should_delete_the_specific_employee_by_given_id() {
        //given
        entityManager.clear();

        entityManager.persist(new Employee("Quinn", "male"));
        //when
        Long id = Long.valueOf(entityManager.persistAndGetId(new Employee("Hoho", "female")).toString());
        repository.deleteById(id);

        //then
        MatcherAssert.assertThat(repository.findAll().size(), CoreMatchers.is(1));
    }

    @Test
    public void should_get_male_employees_when_find_male() {
        //given
        Employee maleGiven=entityManager.persist(new Employee("Hoho", "fmale"));
        Employee femaleGiven=entityManager.persist(new Employee("Quinn", "male"));
        //when
        List<Employee> males=repository.findByGender("male");

        //then
        MatcherAssert.assertThat(males.size(), is(1));
        MatcherAssert.assertThat(males.get(0).getName(), is("Quinn"));
    }
}
