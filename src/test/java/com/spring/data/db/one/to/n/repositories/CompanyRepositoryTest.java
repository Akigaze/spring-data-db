package com.spring.data.db.one.to.n.repositories;

import com.spring.data.db.one.to.n.entities.Company;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @After
    public void tearDown() throws Exception {
        entityManager.clear();
    }

    @Test
    public void should_get_info_of_all_companies(){
        //give
        entityManager.persist(new Company("Alipay"));
        entityManager.persist(new Company("Tencent"));
        //when
        List<Company> companies=repository.findAll();
        //then
        assertThat(companies.size(),is(2));
        assertThat(companies.get(0).getName(),is("Alipay"));
        assertThat(companies.get(1).getName(),is("Tencent"));
    }

    @Test
    public void should_get_info_of_specific_company_when_give_an_id(){
        //give
        Long id=Long.valueOf(entityManager.persistAndGetId(new Company("Alipay")).toString());
        entityManager.persistAndGetId(new Company("Tencent"));
        //when
        Company employee=repository.findById(id).get();
        //then

        assertThat(employee.getName(),is("Alipay"));
    }

    @Test
    public void should_save_a_given_company(){
        //give
        Company given=new Company(10L,"Alipay");
        //when
        Company saved=repository.save(given);
        //then
        assertThat(saved.getName(),is("Alipay"));
    }

    @Test
    public void should_update_info_of_given_company(){
        //give
        Company saved=entityManager.persistAndFlush(new Company("Alipay"));
        Company given=new Company(saved.getId(),"Tencent");
        //when
        repository.save(given);
        //then
        assertThat(repository.findById(saved.getId()).get().getName(),is("Tencent"));
    }

    @Test
    public void should_delete_the_specific_company_by_given_id() {
        //given
        entityManager.clear();

        entityManager.persist(new Company("Alipay"));
        //when
        Long id = Long.valueOf(entityManager.persistAndGetId(new Company("Tencent")).toString());
        repository.deleteById(id);

        //then
        MatcherAssert.assertThat(repository.findAll().size(), CoreMatchers.is(1));
    }

    @Test
    public void should_two_companies_in_a_page_when_set_one_page_size_2() {
        //given
        entityManager.persist(new Company("Netease"));
        entityManager.persist(new Company("Iphone"));
        entityManager.persist(new Company("Alipay"));
        entityManager.persist(new Company("Tencent"));
        entityManager.persist(new Company("HUAWEI"));
        //when
        List<Company> companies=repository.findAll(PageRequest.of(1,2)).getContent();

        //then
        assertThat(companies.size(), is(2));
        assertThat(companies.get(0).getName(), is("Alipay"));
        assertThat(companies.get(1).getName(), is("Tencent"));
    }

    @Test
    public void should_get_employees_info_of_specific_company_by_given_id() {
        //given
        Company alipay=new Company("Alipay");
        Company tencent=new Company("Tencent");
        List<Employee> aliStuff=new ArrayList<>();
        List<Employee> tencStuff=new ArrayList<>();
        aliStuff.add(new Employee("Mayun"));
        aliStuff.add(new Employee("Xiaoming"));
        tencStuff.add(new Employee("MAhuateng"));
        alipay.setEmployees(aliStuff);
        tencent.setEmployees(tencStuff);
        Long id=Long.valueOf(entityManager.persistAndGetId(alipay).toString());
        entityManager.persist(tencent);
        //when
        List<Employee> stuff=repository.findEmployeesByCompanyId(id);

        //then
        assertThat(stuff.size(), is(2));
        assertThat(stuff.get(0).getName(), is("Mayun"));
        assertThat(stuff.get(1).getName(), is("Xiaoming"));
    }
}

