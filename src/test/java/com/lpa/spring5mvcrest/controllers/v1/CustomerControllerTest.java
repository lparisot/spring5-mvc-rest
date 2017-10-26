package com.lpa.spring5mvcrest.controllers.v1;

import com.lpa.spring5mvcrest.api.v1.model.CustomerDTO;
import com.lpa.spring5mvcrest.controllers.RestResponseEntityExceptionHandler;
import com.lpa.spring5mvcrest.services.CustomerService;
import com.lpa.spring5mvcrest.services.ResourceNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractRestControllerTest {
    public static final Long ID = 1L;
    public static final String FIRSTNAME = "John";
    public static final String LASTNAME = "Doe";

    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;
    private MockMvc mockMvc;

    private String getCustomerUrl() {
        return CustomerController.BASE_URL + "/";
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testListCustomers() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname("A");
        customer1.setLastname("A");
        customer1.setCustomerUrl(getCustomerUrl() + "1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstname("B");
        customer2.setLastname("B");
        customer2.setCustomerUrl(getCustomerUrl() + "2");

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        //when
        when(customerService.getAllCustomers()).thenReturn(customers);

        //then
        mockMvc.perform(get(getCustomerUrl()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
        customer.setCustomerUrl(getCustomerUrl() + ID);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        //when
        mockMvc.perform(get(getCustomerUrl() + ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(getCustomerUrl() + ID)));
    }

    @Test
    public void createNewCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
        customer.setCustomerUrl(getCustomerUrl() + ID);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(customer.getLastname());
        returnDTO.setCustomerUrl(customer.getCustomerUrl());

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(post(getCustomerUrl()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(getCustomerUrl() + ID)));
    }

    @Test
    public void updateCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
        customer.setCustomerUrl(getCustomerUrl() + ID);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(customer.getLastname());
        returnDTO.setCustomerUrl(customer.getCustomerUrl());

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(getCustomerUrl() + ID).contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(getCustomerUrl() + ID)));
    }

    @Test
    public void patchCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRSTNAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(LASTNAME);
        returnDTO.setCustomerUrl(getCustomerUrl() + ID);

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(patch(getCustomerUrl() + ID).contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(getCustomerUrl() + ID)));
    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(delete(getCustomerUrl() + ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(getCustomerUrl() + "222").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}