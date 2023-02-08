package com.getir.reading.service;

import com.getir.reading.base.BaseIntegrationTest;
import com.getir.reading.enums.Role;
import com.getir.reading.model.request.CreateCustomerRequest;
import com.getir.reading.model.response.CreateCustomerResponse;
import com.getir.reading.model.response.CreateUserResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class CustomerServiceIT extends BaseIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void createCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setEmail("email@email.com");
        request.setPassword("pwd");

        CreateCustomerResponse result = customerService.createCustomer(request);
        assertEquals(request.getEmail(), result.getEmail());
    }
}
