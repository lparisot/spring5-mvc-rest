package com.lpa.spring5mvcrest.services;

import com.lpa.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.lpa.spring5mvcrest.api.v1.model.CustomerDTO;
import com.lpa.spring5mvcrest.repositories.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerByLastName(String name) {
        return customerMapper.customerToCustomerDTO(customerRepository.findByLastName(name));
    }
}
