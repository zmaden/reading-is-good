package com.getir.reading.model.response;

import lombok.Data;

@Data
public class OrderLineResponse {
    private long orderNumber;
    private String code;
    private Integer quantity;
    private Double amount;

    public OrderLineResponse(long orderNumber, String code, Integer quantity, Double amount) {
        this.orderNumber = orderNumber;
        this.code = code;
        this.quantity = quantity;
        this.amount = amount;
    }
}
