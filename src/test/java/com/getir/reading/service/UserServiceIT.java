package com.getir.reading.service;

import com.getir.reading.base.BaseIntegrationTest;
import com.getir.reading.enums.Role;
import com.getir.reading.model.request.CreateUserRequest;
import com.getir.reading.model.response.CreateUserResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class UserServiceIT extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("email@email.com");
        request.setPassword("pwd");

        CreateUserResponse result = userService.createUser(request, Role.ROLE_ADMIN);
        assertEquals(request.getEmail(), result.getEmail());
        assertEquals(Role.ROLE_ADMIN.toString(), result.getRole());
    }
}