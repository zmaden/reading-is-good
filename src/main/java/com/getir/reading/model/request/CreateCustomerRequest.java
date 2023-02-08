package com.getir.reading.model.request;

import lombok.Data;

@Data
public class CreateCustomerRequest {
    private String email;
    private String password;
}
