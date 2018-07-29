package com.spring.data.db.one.to.n.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.data.db.one.to.n.controllers.EmployeeController;
import com.spring.data.db.one.to.n.controllers.dto.EmployeeDTO;
import com.spring.data.db.one.to.n.entities.Employee;
import com.spring.data.db.one.to.n.repositories.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeRepository repository;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void should_get_info_of_all_employees() throws Exception {
        Employee mayun =new Employee("Mayun");
        Employee mahuateng =new Employee("Mahuateng");
        List<Employee> employees=Arrays.asList(mayun,mahuateng);
        List<EmployeeDTO> dtos=Arrays.asList(new EmployeeDTO(mayun),new EmployeeDTO(mahuateng));
        given(repository.findAll()).willReturn(employees);
        mockMvc.perform(get("/jap/v2/employees")).andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("Mayun")))
                .andExpect(jsonPath("$[1].name",is("Mahuateng")));
    }

    @Test
    public void should_save_the_given_employee() throws Exception {
        Employee mayun =new Employee("Mayun");
        given(repository.save(mayun)).willReturn(mayun);
        ResultActions result = mockMvc.perform(post("/jap/v2/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new EmployeeDTO(mayun))));

        result.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void should_get_info_of_specific_employee_when_give_the_id() throws Exception {
        Employee mayun =new Employee(1L,"Mayun");
        Employee mahuateng =new Employee(2L,"Mahuateng");
        List<Employee> employees=Arrays.asList(mayun,mahuateng);
        List<EmployeeDTO> dtos=Arrays.asList(new EmployeeDTO(mayun),new EmployeeDTO(mahuateng));
        given(repository.findById(1L)).willReturn(Optional.of(mayun));
        mockMvc.perform(get("/jap/v2/employees/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Mayun")))
                .andExpect(jsonPath("$.id",is(1)));
    }
}
