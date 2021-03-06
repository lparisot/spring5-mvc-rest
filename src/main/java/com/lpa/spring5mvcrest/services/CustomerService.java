package com.lpa.spring5mvcrest.services;

import com.lpa.spring5mvcrest.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO createNewCustomer(CustomerDTO customer);
    CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);
    CustomerDTO patchCustomer(long id, CustomerDTO customerDTO);
    void deleteCustomerById(long id);
}
