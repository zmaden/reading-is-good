package com.getir.reading.service;

import com.getir.reading.model.Customer;
import com.getir.reading.model.request.CreateCustomerRequest;
import com.getir.reading.model.response.CreateCustomerResponse;
import com.getir.reading.model.response.CreateUserResponse;
import com.getir.reading.model.response.ListCustomerOrderResponse;
import com.getir.reading.model.response.OrderResponse;
import com.getir.reading.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserService userService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CustomerService customerService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private CreateCustomerRequest createCreateCustomerRequest() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setEmail("email@email.com");
        request.setPassword("pwd");
        return request;
    }

    private CreateCustomerResponse createCreateCustomerResponse() {
        CreateCustomerResponse response = new CreateCustomerResponse();
        response.setEmail("email@email.com");
        return response;
    }

    @Test
    public void createCustomer_shouldBeError_EmailIsNull() {
        CreateCustomerRequest request = createCreateCustomerRequest();
        request.setEmail(null);

        expectedException.expect(Exception.class);
        customerService.createCustomer(request);
    }

    @Test
    public void createCustomer_shouldBeError_EmailIsNotValid() {
        CreateCustomerRequest request = createCreateCustomerRequest();
        request.setEmail("not-valid");

        expectedException.expect(Exception.class);
        customerService.createCustomer(request);
    }

    @Test
    public void createCustomer_shouldBeError_PasswordIsNull() {
        CreateCustomerRequest request = createCreateCustomerRequest();
        request.setPassword(null);

        expectedException.expect(Exception.class);
        customerService.createCustomer(request);
    }

    @Test
    public void saveCustomer_shouldBeError_ConflictCustomer() {
        CreateCustomerRequest request = createCreateCustomerRequest();

        doReturn(new Customer()).when(customerRepository).findByEmail(anyString());
        expectedException.expect(Exception.class);
        customerService.createCustomer(request);
    }

    @Test
    public void saveCustomer_shouldBeSuccess() {
        CreateCustomerRequest request = createCreateCustomerRequest();
        CreateCustomerResponse response = createCreateCustomerResponse();

        doReturn(null).when(customerRepository).findByEmail(anyString());
        doReturn(new CreateUserResponse()).when(userService).createUser(any(), any());
        doReturn(new Customer()).when(customerRepository).save(any());
        CreateCustomerResponse result = customerService.createCustomer(request);
        Assert.assertEquals(response.getEmail(), result.getEmail());
    }

    @Test
    public void getCustomerOrders_shouldBeError_NotValidPage() {
        expectedException.expect(Exception.class);
        customerService.getCustomerOrders(-1, 10);
    }

    @Test
    public void getCustomerOrders_shouldBeError_NotValidPageSize() {
        expectedException.expect(Exception.class);
        customerService.getCustomerOrders(1, -10);
    }

    @Test
    public void getCustomerOrders_shouldBeSuccess() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Integer page = 1;
        Integer pageSize = 10;

        List<OrderResponse> orderResponses = new ArrayList<>();
        ListCustomerOrderResponse response = new ListCustomerOrderResponse(orderResponses, page, pageSize);

        Mockito.lenient().when(orderService.getCustomerOrders(anyString(), anyInt(), anyInt())).thenReturn(orderResponses);
        ListCustomerOrderResponse result = customerService.getCustomerOrders(page, pageSize);
        Assert.assertEquals(response.getPage(), result.getPage());
        Assert.assertEquals(response.getPageSize(), result.getPageSize());
    }
}