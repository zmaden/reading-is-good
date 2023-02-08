package com.getir.reading.service;

import com.getir.reading.base.BaseIntegrationTest;
import com.getir.reading.common.CommonModels;
import com.getir.reading.model.OrderLine;
import com.getir.reading.model.Orders;
import com.getir.reading.model.request.AddBookToStockRequest;
import com.getir.reading.model.response.SaveBookResponse;
import com.getir.reading.repository.OrderLineRepository;
import com.getir.reading.repository.OrderRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class StatisticsServiceIT extends BaseIntegrationTest {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Test
    public void getMonthlyReport() {
        createOrder();

        List<Map<String, Object>> result = statisticsService.getMonthlyReport("email@email.com");
        assertNotNull(result);
    }

    private void createOrder() {
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
    }
}
