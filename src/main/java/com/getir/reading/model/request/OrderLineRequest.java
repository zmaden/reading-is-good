package com.getir.reading.model.request;

import lombok.Data;

@Data
public class OrderLineRequest {
    private String code;
    private Integer quantity;
}
