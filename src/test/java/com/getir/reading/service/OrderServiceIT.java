package com.getir.reading.service;

import com.getir.reading.base.BaseIntegrationTest;
import com.getir.reading.common.CommonModels;
import com.getir.reading.model.OrderLine;
import com.getir.reading.model.Orders;
import com.getir.reading.model.request.AddBookToStockRequest;
import com.getir.reading.model.request.OrderListRequest;
import com.getir.reading.model.response.OrderResponse;
import com.getir.reading.model.response.SaveBookResponse;
import com.getir.reading.repository.OrderLineRepository;
import com.getir.reading.repository.OrderRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderServiceIT extends BaseIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    private Orders createOrder() {
        SaveBookResponse bookResult = bookService.saveBook(CommonModels.createSaveBookRequest());

        int quantity = 100;
        String code = bookResult.getCode();
        AddBookToStockRequest request = CommonModels.createAddBookToStockRequest(code, quantity);
        bookService.addBookToStock(request);

        String email = "email@email.com";
        Orders orders = new Orders();
        orders.setEmail(email);
        orders.setOrderNumber(1L);
        orders = orderRepository.save(orders);

        OrderLine orderLine = new OrderLine();
        orderLine.setOrders(orders);
        orderLine.setOrderNumber(orders.getOrderNumber());
        orderLine.setCode(bookResult.getCode());
        orderLine.setQuantity(1);
        orderLine.setAmount(orderLine.getQuantity() * bookResult.getPrice());
        orderLine.setEmail(email);
        orderLineRepository.save(orderLine);

        return orders;
    }

    @Test
    public void getBook() {
        Orders orders = createOrder();

        OrderResponse result = orderService.getByOrderNumber(orders.getOrderNumber());
        assertEquals(orders.getOrderNumber(), result.getOrderNumber());
    }

    @Test
    public void list() {
        createOrder();

        Date startDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 1);
        startDate = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = dateFormat.format(startDate);

        c.add(Calendar.DATE, -2);
        Date endDate = c.getTime();
        String endDateStr = dateFormat.format(endDate);

        OrderListRequest request = new OrderListRequest();
        request.setStartDate(startDateStr);
        request.setEndDate(endDateStr);
        List<OrderResponse> result = orderService.list(request);
        assertNotNull(result);
    }

    @Test
    public void getCustomerOrders() {
        Orders orders = createOrder();

        List<OrderResponse> result = orderService.getCustomerOrders(orders.getEmail(), 1, 10);
        assertNotNull(result);
    }
}
