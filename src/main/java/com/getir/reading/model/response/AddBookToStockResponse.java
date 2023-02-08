package com.getir.reading.model.response;

import lombok.Data;

@Data
public class AddBookToStockResponse {
    private String code;
    private int quantity;

    public AddBookToStockResponse() {
    }

    public AddBookToStockResponse(String code, int quantity) {
        this.code = code;
        this.quantity = quantity;
    }
}
