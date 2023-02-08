package com.getir.reading.model.response;

import lombok.Data;

@Data
public class OrderLines {
    private String code;
    private Integer quantity;
    private Double amount;

    public OrderLines(String code, Integer quantity, Double amount) {
        this.code = code;
        this.quantity = quantity;
        this.amount = amount;
    }
}
