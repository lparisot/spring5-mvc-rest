package com.lpa.spring5mvcrest.controller.v1;

import com.lpa.spring5mvcrest.api.v1.model.CustomerDTO;
import com.lpa.spring5mvcrest.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {
    public static final String FIRSTNAME = "John";
    public static final String LASTNAME = "Doe";

    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testListCustomers() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(1L);
        customer1.setFirstname("A");
        customer1.setLastname("A");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setFirstname("B");
        customer2.setLastname("B");

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        //when
        when(customerService.getAllCustomers()).thenReturn(customers);

        //then
        mockMvc.perform(get("/api/v1/customers/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetByNameCategories() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(1L);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        when(customerService.getCustomerByLastName(anyString())).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/" + LASTNAME).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)));
    }
}