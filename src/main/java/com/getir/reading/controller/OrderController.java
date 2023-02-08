package com.getir.reading.controller;

import com.getir.reading.model.request.OrderListRequest;
import com.getir.reading.model.request.OrderRequest;
import com.getir.reading.model.response.ApiResponse;
import com.getir.reading.model.response.OrderLineResponse;
import com.getir.reading.model.response.OrderResponse;
import com.getir.reading.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest order) {
        return ApiResponse.response(orderService.createOrder(order));
    }

    @GetMapping("/getByOrderNumber")
    public ApiResponse<OrderResponse> getByOrderNumber(Long orderNumber) {
        return ApiResponse.response(orderService.getByOrderNumber(orderNumber));
    }

    @PostMapping("/list")
    public ApiResponse<List<OrderResponse>> list(@RequestBody OrderListRequest request) {
        return ApiResponse.response(orderService.list(request));
    }
}
