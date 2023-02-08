package com.getir.reading.service;

import com.getir.reading.enums.Role;
import com.getir.reading.exception.ExceptionFactory;
import com.getir.reading.model.User;
import com.getir.reading.model.request.CreateUserRequest;
import com.getir.reading.model.response.CreateUserResponse;
import com.getir.reading.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request, Role role) {
        logger.info("createUser is called. email: {}", request.getEmail());
        String email = request.getEmail();
        if (StringUtils.isEmpty(email)) {
            ExceptionFactory.throwBadRequestException("Email should not be blank.");
        }

        String password = request.getPassword();
        if (StringUtils.isEmpty(password)) {
            ExceptionFactory.throwBadRequestException("Password should not be blank.");
        }
        User existUser = userRepository.findByUsername(email);
        if (existUser != null) {
            ExceptionFactory.throwConflictException("There is a user belonging to this e-mail address.");
        }

        User user = new User();
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);
        return new CreateUserResponse(email, role.toString());
    }
}
