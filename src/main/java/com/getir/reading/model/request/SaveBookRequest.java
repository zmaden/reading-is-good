package com.getir.reading.model.request;

import lombok.Data;

@Data
public class SaveBookRequest {
    private String code;
    private Double price;
}
