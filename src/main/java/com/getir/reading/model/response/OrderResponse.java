package com.getir.reading.model.response;

import lombok.Data;

import java.util.List;
@Data
public class OrderResponse {
    private long orderNumber;
    private Double totalAmount;
    private List<OrderLines> orderLines;

    public OrderResponse() {
    }

    public OrderResponse(long orderNumber, Double totalAmount, List<OrderLines> orderLines) {
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.orderLines = orderLines;
    }
}
