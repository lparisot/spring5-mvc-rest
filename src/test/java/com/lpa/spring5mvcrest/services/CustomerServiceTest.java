package com.lpa.spring5mvcrest.services;

import com.lpa.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.lpa.spring5mvcrest.api.v1.model.CustomerDTO;
import com.lpa.spring5mvcrest.domain.Customer;
import com.lpa.spring5mvcrest.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {
    public static final Long ID = 1L;
    public static final String FIRSTNAME = "John";
    public static final String LASTNAME = "Doe";
    public static final String CUSTOMER_URL = "/api/v1/customers/";

    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(3, customerDTOS.size());
    }

    @Test
    public void getCustomerById() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        //then
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
    }

    @Test
    public void createNewCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), savedCustomer.getFirstname());
        assertEquals(CUSTOMER_URL + ID, savedDTO.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDTO() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDTO = customerService.saveCustomerByDTO(ID, customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), savedCustomer.getFirstname());
        assertEquals(CUSTOMER_URL + ID, savedDTO.getCustomerUrl());
    }

    @Test
    public void patchCustomerByDTO() throws Exception {
        //given
        Customer originalCustomer = new Customer();
        originalCustomer.setFirstname(FIRSTNAME);
        originalCustomer.setLastname(LASTNAME);
        originalCustomer.setId(ID);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Mike");

        Customer patchedCustomer = new Customer();
        patchedCustomer.setFirstname(customerDTO.getFirstname());
        patchedCustomer.setLastname(LASTNAME);
        patchedCustomer.setId(ID);

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(originalCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(patchedCustomer);

        //when
        CustomerDTO savedDTO = customerService.patchCustomer(ID, customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), patchedCustomer.getFirstname());
        assertEquals(CUSTOMER_URL + ID, savedDTO.getCustomerUrl());
    }
}