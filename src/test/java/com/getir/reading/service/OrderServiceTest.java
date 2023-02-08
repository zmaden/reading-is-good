package com.getir.reading.service;

import com.getir.reading.model.Book;
import com.getir.reading.model.OrderLine;
import com.getir.reading.model.Orders;
import com.getir.reading.model.request.OrderLineRequest;
import com.getir.reading.model.request.OrderListRequest;
import com.getir.reading.model.request.OrderRequest;
import com.getir.reading.model.response.OrderResponse;
import com.getir.reading.repository.OrderLineRepository;
import com.getir.reading.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderLineRepository orderLineRepository;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private BookService bookService;
    @InjectMocks
    private OrderService orderService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private OrderRequest createOrderRequest() {
        List<OrderLineRequest> orderLines = new ArrayList<>();
        orderLines.add(createOrderLineRequest());
        OrderRequest request = new OrderRequest();
        request.setOrderLines(orderLines);
        return request;
    }

    private OrderLineRequest createOrderLineRequest() {
        OrderLineRequest request = new OrderLineRequest();
        request.setCode("code");
        request.setQuantity(2);
        return request;
    }

    private OrderListRequest createOrderListRequest() {
        OrderListRequest request = new OrderListRequest();
        request.setStartDate("2022-11-01");
        request.setEndDate("2022-11-30");
        return request;
    }

    @Test
    public void createOrder_shouldBeError_NotValidOrderLines() {
        OrderRequest request = createOrderRequest();
        request.setOrderLines(null);

        expectedException.expect(Exception.class);
        orderService.createOrder(request);
    }

    @Test
    public void createOrder_shouldBeError_CodeIsNull() {
        OrderLineRequest orderLineRequest = new OrderLineRequest();
        orderLineRequest.setCode(null);
        orderLineRequest.setQuantity(2);

        List<OrderLineRequest> orderLines = new ArrayList<>();
        orderLines.add(orderLineRequest);

        OrderRequest request = createOrderRequest();
        request.setOrderLines(orderLines);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Orders orders = new Orders();
        orders.setEmail("email@email.com");
        expectedException.expect(Exception.class);
        doReturn(orders).when(orderRepository).save(any());
        orderService.createOrder(request);
    }

    @Test
    public void createOrder_shouldBeError_QuantityIsNull() {
        OrderLineRequest orderLineRequest = new OrderLineRequest();
        orderLineRequest.setCode("code");
        orderLineRequest.setQuantity(-10);

        List<OrderLineRequest> orderLines = new ArrayList<>();
        orderLines.add(orderLineRequest);

        OrderRequest request = createOrderRequest();
        request.setOrderLines(orderLines);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Orders orders = new Orders();
        orders.setEmail("email@email.com");
        expectedException.expect(Exception.class);
        doReturn(orders).when(orderRepository).save(any());
        orderService.createOrder(request);
    }

    @Test
    public void createOrder_shouldBeError_BookNotFound() {
        OrderLineRequest orderLineRequest = new OrderLineRequest();
        orderLineRequest.setCode("code");
        orderLineRequest.setQuantity(10);

        List<OrderLineRequest> orderLines = new ArrayList<>();
        orderLines.add(orderLineRequest);

        OrderRequest request = createOrderRequest();
        request.setOrderLines(orderLines);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Orders orders = new Orders();
        orders.setEmail("email@email.com");
        expectedException.expect(Exception.class);
        doReturn(orders).when(orderRepository).save(any());
        doReturn(null).when(bookService).getBook(anyString());
        orderService.createOrder(request);
    }

    @Test
    public void createOrder_shouldBeSuccess() {
        final int quantity = 10;
        final double price = 100.0;
        OrderLineRequest orderLineRequest = new OrderLineRequest();
        orderLineRequest.setCode("code");
        orderLineRequest.setQuantity(quantity);

        List<OrderLineRequest> orderLines = new ArrayList<>();
        orderLines.add(orderLineRequest);

        OrderRequest request = createOrderRequest();
        request.setOrderLines(orderLines);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Orders orders = new Orders();
        orders.setEmail("email@email.com");

        Book book = new Book();
        book.setCode("code");
        book.setPrice(price);

        double totalAmount = orderLines.size() * price * quantity;
        OrderResponse response = new OrderResponse();
        response.setTotalAmount(totalAmount);

        doReturn(orders).when(orderRepository).save(any());
        doReturn(new OrderLine()).when(orderLineRepository).save(any());
        doReturn(book).when(bookService).getBook(anyString());
        OrderResponse result = orderService.createOrder(request);
        Assert.assertEquals(response.getTotalAmount(), result.getTotalAmount());
    }

    @Test
    public void getByOrderNumber_shouldBeError_OrderNumberIsNull() {
        expectedException.expect(Exception.class);
        orderService.getByOrderNumber(null);
    }

    @Test
    public void getByOrderNumber_shouldBeError_NoOrder() {
        expectedException.expect(Exception.class);
        doReturn(new ArrayList<>()).when(orderLineRepository).getOrderLineByOrderNumber(anyLong());
        orderService.getByOrderNumber(1L);
    }

    @Test
    public void getByOrderNumber_shouldBeSuccess() {
        Orders orders = new Orders();
        orders.setOrderNumber(1L);
        orders.setEmail("email@email.com");

        OrderLine orderLine = new OrderLine();
        orderLine.setOrderNumber(orders.getOrderNumber());
        orderLine.setOrders(orders);
        orderLine.setAmount(10.0);

        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);

        doReturn(orderLines).when(orderLineRepository).getOrderLineByOrderNumber(anyLong());
        OrderResponse result = orderService.getByOrderNumber(1L);
        Assert.assertNotNull(result);
    }

    @Test
    public void list_shouldBeError_RequestIsNull() {
        expectedException.expect(Exception.class);
        orderService.list(null);
    }

    @Test
    public void list_shouldBeError_StartDateIsNull() {
        OrderListRequest orderListRequest = createOrderListRequest();
        orderListRequest.setStartDate(null);
        expectedException.expect(Exception.class);
        orderService.list(orderListRequest);
    }

    @Test
    public void list_shouldBeError_EndDateIsNull() {
        OrderListRequest orderListRequest = createOrderListRequest();
        orderListRequest.setEndDate(null);
        expectedException.expect(Exception.class);
        orderService.list(orderListRequest);
    }

    @Test
    public void list_shouldBeError_NotValidStartDate() {
        OrderListRequest orderListRequest = createOrderListRequest();
        orderListRequest.setStartDate("notValid");
        expectedException.expect(Exception.class);
        orderService.list(orderListRequest);
    }

    @Test
    public void list_shouldBeError_NotValidEndDate() {
        OrderListRequest orderListRequest = createOrderListRequest();
        orderListRequest.setEndDate("notValid");
        expectedException.expect(Exception.class);
        orderService.list(orderListRequest);
    }

    @Test
    public void list_shouldBeSuccess() {
        String email = "email@email.com";

        Orders orders = new Orders();
        orders.setOrderNumber(1L);
        orders.setEmail(email);

        OrderLine orderLine = new OrderLine();
        orderLine.setOrderNumber(orders.getOrderNumber());
        orderLine.setOrders(orders);
        orderLine.setAmount(10.0);

        List<OrderLine> orderLines = List.of(orderLine);

        OrderListRequest orderListRequest = createOrderListRequest();
        doReturn(orderLines).when(orderLineRepository).getOrderLineByCreatedDateBetween(any(), any());
        List<OrderResponse> result = orderService.list(orderListRequest);
        Assert.assertNotNull(result);
    }

    @Test
    public void getCustomerOrders_shouldBeError_NotValidEmail() {
        expectedException.expect(Exception.class);
        orderService.getCustomerOrders(null, 1, 10);
    }

    @Test
    public void getCustomerOrders_shouldBeError_NotValidPage() {
        expectedException.expect(Exception.class);
        orderService.getCustomerOrders("email@email.com", -1, 10);
    }

    @Test
    public void getCustomerOrders_shouldBeError_NotValidPageSize() {
        expectedException.expect(Exception.class);
        orderService.getCustomerOrders("email@email.com", 1, -10);
    }

    @Test
    public void getCustomerOrders_shouldBeSuccess() {
        String email = "email@email.com";

        Orders orders = new Orders();
        orders.setOrderNumber(1L);
        orders.setEmail(email);

        OrderLine orderLine = new OrderLine();
        orderLine.setOrderNumber(orders.getOrderNumber());
        orderLine.setOrders(orders);
        orderLine.setAmount(10.0);

        int page = 1;
        int pageSize = 10;
        List<OrderLine> orderLines = List.of(orderLine);
        PageImpl<OrderLine> orderEntitiesPage =
                new PageImpl<>(orderLines,
                        PageRequest.of(page, pageSize),
                        orderLines.size());

        doReturn(orderEntitiesPage).when(orderLineRepository).getOrderLineByEmail(anyString(), any());
        List<OrderResponse> result = orderService.getCustomerOrders("email@email.com", page, pageSize);
        Assert.assertNotNull(result);
    }
}