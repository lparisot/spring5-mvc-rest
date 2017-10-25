package com.lpa.spring5mvcrest.controller.v1;

import com.lpa.spring5mvcrest.services.CustomerService;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
}
