package com.getir.reading.controller;

import com.getir.reading.enums.Role;
import com.getir.reading.model.request.CreateUserRequest;
import com.getir.reading.model.response.ApiResponse;
import com.getir.reading.model.response.CreateUserResponse;
import com.getir.reading.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createAdmin")
    public ApiResponse<CreateUserResponse> createAdmin(@RequestBody CreateUserRequest request) {
        return ApiResponse.response(userService.createUser(request, Role.ROLE_ADMIN));
    }
}
