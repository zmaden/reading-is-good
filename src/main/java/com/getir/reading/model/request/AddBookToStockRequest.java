package com.getir.reading.model.request;

import lombok.Data;

@Data
public class AddBookToStockRequest {
    private String code;
    private Integer quantity;
}
