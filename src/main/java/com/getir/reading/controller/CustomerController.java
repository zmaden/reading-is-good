package com.getir.reading.controller;

import com.getir.reading.model.request.CreateCustomerRequest;
import com.getir.reading.model.response.ApiResponse;
import com.getir.reading.model.response.CreateCustomerResponse;
import com.getir.reading.model.response.ListCustomerOrderResponse;
import com.getir.reading.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/createCustomer")
    public ApiResponse<CreateCustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
        return ApiResponse.response(customerService.createCustomer(request));
    }

    @GetMapping("/getCustomerOrders")
    public ApiResponse<ListCustomerOrderResponse> getCustomerOrders(Integer page, Integer pageSize) {
        return ApiResponse.response(customerService.getCustomerOrders(page, pageSize));
    }
}
