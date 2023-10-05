package com.banking.client_person.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import com.banking.client_person.models.dtos.CustomerRequest;
import com.banking.client_person.models.enums.GenderType;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@WebAppConfiguration
public class CustomerControllerIntegrationTest {

    private final static String BASE_URL = "/api/clientes";

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContx;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContx).build();
    }

    /**
     * Unit test for the customer creation method.
     *
     * @throws Exception if any error occurs during test execution.
     */
    @Test
    public void create() throws Exception {
        var custReq = CustomerRequest.builder()
                .name("Luis Cruz")
                .identification("100000000000001")
                .age(31)
                .gender(GenderType.male)
                .address("Ubr. El Progreso Calle #7")
                .phone("18099870000")
                .password("1234")
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(custReq)))
                .andReturn();
        assertEquals(201, result.getResponse().getStatus());
    }

    /**
     * Unit test for the customer creation method with invalid data.
     *
     * The test is expected to fail with a 400 (Bad Request) status code.
     *
     * @throws Exception if any error occurs during test execution.
     */
    @Test
    public void createWhithError() throws Exception {
        var custReq = CustomerRequest.builder()
                .name("Luis Cruz")
                .age(31)
                .gender(GenderType.male)
                .address("Ubr. El Progreso Calle #7")
                .phone("18099870000")
                .password("1234")
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(custReq)))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    /**
     * Unit test for the method of getting all customers.
     *
     * @throws Exception if any error occurs during test execution.
     */
    @Test
    public void findAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }
}
