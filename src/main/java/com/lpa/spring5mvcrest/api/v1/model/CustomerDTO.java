package com.lpa.spring5mvcrest.api.v1.model;

import lombok.Data;

@Data
public class CustomerDTO {
    private String firstname;
    private String lastname;
    private String customerUrl;
}
