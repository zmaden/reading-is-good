package com.getir.reading.service;

import com.getir.reading.enums.Role;
import com.getir.reading.exception.ExceptionFactory;
import com.getir.reading.model.Customer;
import com.getir.reading.model.request.CreateCustomerRequest;
import com.getir.reading.model.request.CreateUserRequest;
import com.getir.reading.model.response.CreateCustomerResponse;
import com.getir.reading.model.response.ListCustomerOrderResponse;
import com.getir.reading.model.response.OrderResponse;
import com.getir.reading.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, UserService userService, OrderService orderService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Transactional
    public CreateCustomerResponse createCustomer(CreateCustomerRequest request) {
        logger.info("createCustomer is called. email: {}", request.getEmail());
        String email = request.getEmail();
        if (StringUtils.isEmpty(email)) {
            ExceptionFactory.throwBadRequestException("Email should not be blank.");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            ExceptionFactory.throwBadRequestException("Email is not valid.");
        }

        if (StringUtils.isEmpty(request.getPassword())) {
            ExceptionFactory.throwBadRequestException("Password should not be blank.");
        }

        Customer existCustomer = customerRepository.findByEmail(email);
        if (existCustomer != null) {
            ExceptionFactory.throwConflictException("There is a customer belonging to this e-mail address.");
        }

        userService.createUser(new CreateUserRequest(email, request.getPassword()), Role.ROLE_USER);

        Customer customer = new Customer();
        customer.setEmail(email);
        customerRepository.save(customer);
        return new CreateCustomerResponse(email);
    }

    public ListCustomerOrderResponse getCustomerOrders(Integer page, Integer pageSize) {
        if (page == null || page < 0)
            ExceptionFactory.throwBadRequestException("Page is not valid.");
        if (pageSize == null || pageSize < 1)
            ExceptionFactory.throwBadRequestException("Page Size is not valid.");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("getCustomerOrders is called. called by: {}", email);
        List<OrderResponse> customerOrders = orderService.getCustomerOrders(email, page, pageSize);
        return new ListCustomerOrderResponse(customerOrders, page, pageSize);
    }
}
