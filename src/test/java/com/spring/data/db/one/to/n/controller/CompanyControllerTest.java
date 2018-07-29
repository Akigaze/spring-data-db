package com.spring.data.db.one.to.n.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.data.db.one.to.n.controllers.CompanyController;
import com.spring.data.db.one.to.n.controllers.dto.CompanyDTO;
import com.spring.data.db.one.to.n.entities.Company;
import com.spring.data.db.one.to.n.repositories.CompanyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CompanyRepository repository;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void should_get_info_of_all_companies() throws Exception {
        Company ali =new Company("Alipay");
        Company tx =new Company("Tencent");
        List<Company> companies=Arrays.asList(ali,tx);
        given(repository.findAll()).willReturn(companies);
        mockMvc.perform(get("/jap/v1/companies")).andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("Alipay")))
                .andExpect(jsonPath("$[1].name",is("Tencent")));
    }

    @Test
    public void should_save_the_given_company() throws Exception {
        Company ali =new Company(1L,"Alipay");
        CompanyDTO dto=new CompanyDTO(ali);
        when(repository.save(ali)).thenReturn(ali);
        ResultActions result = mockMvc.perform(post("/jap/v1/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CompanyDTO(ali))));

        result.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void should_get_info_of_specific_company_when_give_the_id() throws Exception {
        Company mayun =new Company(1L,"Alipay");
        Company mahuateng =new Company(2L,"Tencent");
        List<Company> employees=Arrays.asList(mayun,mahuateng);
        List<CompanyDTO> dtos=Arrays.asList(new CompanyDTO(mayun),new CompanyDTO(mahuateng));
        given(repository.findById(1L)).willReturn(Optional.of(mayun));
        mockMvc.perform(get("/jap/v1/companies/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Alipay")))
                .andExpect(jsonPath("$.id",is(1)));
    }

    @Test
    public void should_delete_the_specific_company_when_give_id() throws Exception {

        Company mayun = new Company(1L,"Alipay");
        given(repository.findById(1L)).willReturn(Optional.of(mayun));
        ResultActions result = mockMvc.perform(delete("/jap/v1/companies/1"));
        result.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void should_update_info_of_specific_company_when_give_new_info()throws Exception{
        Company mayun = new Company(1L,"Alipay");
        Company mahuateng = new Company(1L,"Tencent");

        //when
        when(repository.findById(1L)).thenReturn(Optional.of(mayun));
        ResultActions result = mockMvc.perform(put("/jap/v1/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mahuateng)));
        //then
        result.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void should_two_employees_in_a_page_when_set_one_page_size_2() throws Exception {
        Company mayun =new Company("Alipay");
        Company mahuateng =new Company("Tencent");
        List<Company> employees=Arrays.asList(mayun,mahuateng);
        given(repository.findAll(PageRequest.of(0,2))).willReturn(new PageImpl<Company>(employees));
        mockMvc.perform(get("/jap/v1/companies?page=1&size=2")).andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("Alipay")))
                .andExpect(jsonPath("$[1].name",is("Tencent")));
    }
}
