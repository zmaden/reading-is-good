package com.getir.reading.model.response;

import lombok.Data;

@Data
public class CreateCustomerResponse {
    private String email;

    public CreateCustomerResponse() {
    }

    public CreateCustomerResponse(String email) {
        this.email = email;
    }

}
