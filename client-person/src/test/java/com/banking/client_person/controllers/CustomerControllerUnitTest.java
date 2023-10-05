package com.banking.client_person.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.banking.client_person.models.dtos.CustomerRequest;
import com.banking.client_person.models.entities.Customer;
import com.banking.client_person.models.entities.Person;
import com.banking.client_person.models.enums.GenderType;
import com.banking.client_person.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)
public class CustomerControllerUnitTest {

    private final static String BASE_URL = "/api/clientes";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private Customer cust;

    @BeforeEach
    public void setUp() {

        var pers = Person.builder()
                .personId(1L)
                .name("Luis Cruz")
                .identification("100000000000001")
                .age(31)
                .gender(GenderType.male)
                .address("Ubr. El Progreso Calle #7")
                .phone("18099870000")
                .build();

        cust = Customer.builder()
                .customerId(1L)
                .person(pers)
                .password("1234")
                .status(true)
                .build();
    }

    /**
     * Test the 'create' method.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void create() throws Exception {
        var postCust = CustomerRequest.builder()
                .name("Luis Cruz")
                .identification("100000000000001")
                .age(31)
                .gender(GenderType.male)
                .address("Ubr. El Progreso Calle #7")
                .phone("18099870000")
                .password("1234")
                .build();
        Mockito.when(customerService.create(postCust)).thenReturn(null);
        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postCust)))
                .andExpect(status().isCreated());
    }

    /**
     * Test the 'findById' method.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void findById() throws Exception {
        Mockito.when(customerService.findById(1L)).thenReturn(cust);
        mockMvc.perform(get(BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value(cust.getPassword()));
    }

}