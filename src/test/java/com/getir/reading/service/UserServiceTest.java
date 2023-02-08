package com.getir.reading.service;

import com.getir.reading.enums.Role;
import com.getir.reading.model.User;
import com.getir.reading.model.request.CreateUserRequest;
import com.getir.reading.model.response.CreateUserResponse;
import com.getir.reading.repository.UserRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private CreateUserRequest createCreateUserRequest() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("email@email.com");
        request.setPassword("pwd");
        return request;
    }

    private CreateUserResponse createCreateUserResponse() {
        CreateUserResponse response = new CreateUserResponse();
        response.setEmail("email@email.com");
        response.setRole(Role.ROLE_ADMIN.toString());
        return response;
    }

    @Test
    public void createUser_shouldBeError_EmailIsNull() {
        CreateUserRequest request = createCreateUserRequest();
        request.setEmail(null);

        expectedException.expect(Exception.class);
        userService.createUser(request, Role.ROLE_ADMIN);
    }

    @Test
    public void createUser_shouldBeError_PasswordIsNull() {
        CreateUserRequest request = createCreateUserRequest();
        request.setPassword(null);

        expectedException.expect(Exception.class);
        userService.createUser(request, Role.ROLE_ADMIN);
    }

    @Test
    public void saveUser_shouldBeError_ConflictUser() {
        CreateUserRequest request = createCreateUserRequest();

        doReturn(new User()).when(userRepository).findByUsername(anyString());
        expectedException.expect(Exception.class);
        userService.createUser(request, Role.ROLE_ADMIN);
    }

    @Test
    public void saveUser_shouldBeSuccess() {
        CreateUserRequest request = createCreateUserRequest();
        CreateUserResponse response = createCreateUserResponse();

        doReturn(null).when(userRepository).findByUsername(anyString());
        doReturn("encoded").when(passwordEncoder).encode(anyString());
        doReturn(new User()).when(userRepository).save(any());
        CreateUserResponse result = userService.createUser(request, Role.ROLE_ADMIN);
        Assert.assertEquals(response.getEmail(), result.getEmail());
        Assert.assertEquals(response.getRole(), result.getRole());
    }
}