package com.getir.reading.model.response;

import lombok.Data;

@Data
public class SaveBookResponse {
    private String code;
    private Double price;

    public SaveBookResponse() {
    }

    public SaveBookResponse(String code, Double price) {
        this.code = code;
        this.price = price;
    }
}
